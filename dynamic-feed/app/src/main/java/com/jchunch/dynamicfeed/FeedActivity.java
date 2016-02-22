package com.jchunch.dynamicfeed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.network.FeedNetworkService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchunch on 2/16/16.
 */
public class FeedActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = FeedActivity.class.getName();
    private static final String KEY_ARG_CHILD_VIEW_INDEX = "KEY_ARG_CHILD_VIEW_INDEX";
    private static final String KEY_ARG_TILE_ITEMS = "KEY_ARG_TILE_ITEMS";

    private Button mRetry;
    private FeedRecyclerAdapter mRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<TileItem> mTileItems;
    private RecyclerView mRecyclerView;
    private ViewFlipper mViewFlipper;

    private enum ViewState {
        ERROR,
        FEED,
        LOADING

        // This can also be used for denied run-time permissions, empty states, etc.
    }

    private BroadcastReceiver mNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onReceive");
            }

            String action = intent.getAction();
            if (action != null && action.equals(FeedNetworkService.ACTION)) {

                boolean success = intent.getBooleanExtra(FeedNetworkService.KEY_ARG_SUCCESS, false);
                if (success) {

                    // Get tile items
                    mTileItems = intent.getParcelableArrayListExtra(
                            FeedNetworkService.KEY_ARG_TILE_ITEMS);

                    // Update view state if tile items available
                    if (mTileItems != null) {
                        mRecyclerAdapter.updateTileItems(mTileItems);
                        updateViewFlipper(ViewState.FEED);
                        return;
                    }
                }

                // Response was unsuccessful
                updateViewFlipper(ViewState.ERROR);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feed);

        mRetry = (Button) findViewById(R.id.error_button_retry);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mViewFlipper = (ViewFlipper) findViewById(R.id.main_view_flipper);

        mRetry.setOnClickListener(this);

        // Init variables for recycler view
        mTileItems = new ArrayList<TileItem>();
        mRecyclerAdapter = new FeedRecyclerAdapter(mTileItems);
        mLinearLayoutManager = new LinearLayoutManager(this);

        // Setup recycler view
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        if (savedInstanceState == null) {
            updateViewFlipper(ViewState.LOADING);

            // Request feed content
            Intent intent = new Intent(this, FeedNetworkService.class);
            startService(intent);
        } else {

            // Restore view state
            int childViewIndex = savedInstanceState.getInt(KEY_ARG_CHILD_VIEW_INDEX);
            mViewFlipper.setDisplayedChild(childViewIndex);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register broadcast receiver
        IntentFilter intentFilter = new IntentFilter(FeedNetworkService.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mNetworkReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNetworkReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int childViewIndex = mViewFlipper.getDisplayedChild();
        outState.putInt(KEY_ARG_CHILD_VIEW_INDEX, childViewIndex);

        outState.putParcelableArrayList(KEY_ARG_TILE_ITEMS, (ArrayList<? extends Parcelable>) mTileItems);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error_button_retry:
                updateViewFlipper(ViewState.LOADING);

                // Request feed content
                Intent intent = new Intent(this, FeedNetworkService.class);
                startService(intent);
                break;
        }
    }

    private Integer getViewFlipperChildIndexFromResId(Integer childViewResId) {
        Integer viewFlipperChildIndex = null;

        if (childViewResId != null) {
            for (int i = 0; i < mViewFlipper.getChildCount(); i++) {
                View childView = mViewFlipper.getChildAt(i);
                if (childViewResId == childView.getId()) {
                    return i;
                }
            }
        }

        return viewFlipperChildIndex;
    }

    private void updateViewFlipper(ViewState viewState) {
        Integer childViewResId = null;

        switch (viewState) {
            case ERROR:
                childViewResId = R.id.main_layout_error;
                break;

            case FEED:
                childViewResId = R.id.main_recycler_view;
                break;

            case LOADING:
                childViewResId = R.id.main_layout_loading;
        }

        Integer viewFlipperChildIndex = getViewFlipperChildIndexFromResId(childViewResId);
        if (viewFlipperChildIndex != null) {
            mViewFlipper.setDisplayedChild(viewFlipperChildIndex);
        }
    }
}
