package com.jchunch.dynamicfeed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.TileItemUtils;
import com.jchunch.dynamicfeed.log.LogUtil;
import com.jchunch.dynamicfeed.network.Endpoints;
import com.jchunch.dynamicfeed.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchunch on 2/16/16.
 */
public class FeedActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = FeedActivity.class.getName();
    private static final String KEY_ARG_CHILD_VIEW_INDEX = "KEY_ARG_CHILD_VIEW_INDEX";
    private static final String KEY_ARG_RESPONSE_BODY_JSON = "KEY_ARG_RESPONSE_BODY_JSON";

    private Button mRetry;
    private FeedRecyclerAdapter mRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<TileItem> mTileItems;
    private RecyclerView mRecyclerView;
    private String mResponseBodyJson;
    private ViewFlipper mViewFlipper;

    private enum ViewState {
        ERROR,
        FEED,
        LOADING
    }

    private BroadcastReceiver mNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.d(TAG, "onReceive");

            String action = intent.getAction();
            if (action != null && action.equals(NetworkService.ACTION)) {
                mResponseBodyJson = intent.getStringExtra(NetworkService.KEY_ARG_RESPONSE_BODY_JSON);
                if (!TextUtils.isEmpty(mResponseBodyJson)) {

                    // Get tile items
                    mTileItems = TileItemUtils.getTileItemsFromJsonString(mResponseBodyJson);

                    // Update recycler adapter
                    mRecyclerAdapter.updateTileItems(mTileItems);

                    // Set feed view state
                    updateViewFlipper(ViewState.FEED);
                    return;
                }
            }

            // Set error view state
            updateViewFlipper(ViewState.ERROR);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate");

        setContentView(R.layout.activity_feed);

        // Setup views
        mRetry = (Button) findViewById(R.id.error_button_retry);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mViewFlipper = (ViewFlipper) findViewById(R.id.main_view_flipper);

        // Setup interfaces
        mRetry.setOnClickListener(this);

        // Init variables for recycler view
        mTileItems = new ArrayList<TileItem>();
        mRecyclerAdapter = new FeedRecyclerAdapter(mTileItems);
        mLinearLayoutManager = new LinearLayoutManager(this);

        // Setup recycler view
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        // Attempt to restore state, otherwise request content
        if (savedInstanceState == null) {

            // Request feed content
            Intent intent = NetworkService.newInstanceIntent(this, Endpoints.ENDPOINT_VALUE_DYNAMIC_FEED);
            startService(intent);

            // Set loading view state
            updateViewFlipper(ViewState.LOADING);

        } else {

            // Restore feed content
            mResponseBodyJson = savedInstanceState.getString(KEY_ARG_RESPONSE_BODY_JSON);
            mTileItems = TileItemUtils.getTileItemsFromJsonString(mResponseBodyJson);
            mRecyclerAdapter.updateTileItems(mTileItems);

            // Restore view state
            int childViewIndex = savedInstanceState.getInt(KEY_ARG_CHILD_VIEW_INDEX);
            mViewFlipper.setDisplayedChild(childViewIndex);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume");

        // Register broadcast receiver
        IntentFilter intentFilter = new IntentFilter(NetworkService.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mNetworkReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause");

        // Unregister broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNetworkReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d(TAG, "onSaveInstanceState");

        // Save view state
        int childViewIndex = mViewFlipper.getDisplayedChild();
        outState.putInt(KEY_ARG_CHILD_VIEW_INDEX, childViewIndex);

        // Save feed content
        outState.putString(KEY_ARG_RESPONSE_BODY_JSON, mResponseBodyJson);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy");
    }

    @Override
    public void onClick(View v) {
        LogUtil.d(TAG, "onClick");

        switch (v.getId()) {
            case R.id.error_button_retry:

                // Request feed content
                Intent intent = new Intent(this, NetworkService.class);
                startService(intent);

                // Set loading view state
                updateViewFlipper(ViewState.LOADING);
                break;

            default:
                LogUtil.d(TAG, "onCreate: unknown view clicked");
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
