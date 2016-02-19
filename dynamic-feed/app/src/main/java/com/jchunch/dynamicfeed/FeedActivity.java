package com.jchunch.dynamicfeed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.large.LargeTileItem;
import com.jchunch.dynamicfeed.item.regular.RegularTileItem;
import com.jchunch.dynamicfeed.item.small.SmallTileItem;
import com.jchunch.dynamicfeed.model.LargeTile;
import com.jchunch.dynamicfeed.model.RegularTile;
import com.jchunch.dynamicfeed.model.SmallTile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchunch on 2/16/16.
 */
public class FeedActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = FeedActivity.class.getName();
    private static final String KEY_ARG_CHILD_VIEW_INDEX = "KEY_ARG_CHILD_VIEW_INDEX";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feed);

        mRetry = (Button) findViewById(R.id.error_button_retry);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mViewFlipper = (ViewFlipper) findViewById(R.id.main_view_flipper);

        // Init variables for recycler view
        mTileItems = new ArrayList<TileItem>();
        Picasso picasso = Picasso.with(this);
        mRecyclerAdapter = new FeedRecyclerAdapter(mTileItems, picasso);
        mLinearLayoutManager = new LinearLayoutManager(this);

        // Setup recycler view
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this));
        mRecyclerView.setAdapter(mRecyclerAdapter);

        if (savedInstanceState == null) {

            // Load content
            // FIXME: Replace this with API
            mTileItems = new ArrayList<TileItem>();
            mTileItems.add(new SmallTileItem(new SmallTile("Small Header", "Small Body", null)));
            mTileItems.add(new RegularTileItem(new RegularTile("Regular Header", "Regular Body", null)));
            mTileItems.add(new LargeTileItem(new LargeTile("Large Header", "Large Body", null)));
            mRecyclerAdapter.updateTileItems(mTileItems);

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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int childViewIndex = mViewFlipper.getDisplayedChild();
        outState.putInt(KEY_ARG_CHILD_VIEW_INDEX, childViewIndex);
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
                // Load content
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
