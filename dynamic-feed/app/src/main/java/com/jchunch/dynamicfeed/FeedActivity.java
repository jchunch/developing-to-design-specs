package com.jchunch.dynamicfeed;

import android.os.Bundle;
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
import com.jchunch.dynamicfeed.network.NetworkActivity;
import com.jchunch.dynamicfeed.network.constants.Endpoints;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchunch on 2/16/16.
 */
public class FeedActivity extends NetworkActivity implements OnClickListener {
    private static final String TAG = FeedActivity.class.getName();

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
    }

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d(TAG, "onSaveInstanceState");
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
                executeNetworkRequest();
                break;

            default:
                LogUtil.w(TAG, "onCreate: unknown view clicked");
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

    protected void executeNetworkRequest() {
        updateViewFlipper(ViewState.LOADING);
        super.executeNetworkRequest();
    }

    protected void handleNetworkResponse() {
        if (!TextUtils.isEmpty(mResponseBodyJson)) {
            mTileItems = TileItemUtils.getTileItemsFromResponseBodyJson(mResponseBodyJson);
            mRecyclerAdapter.updateTileItems(mTileItems);
            updateViewFlipper(ViewState.FEED);
        } else {
            updateViewFlipper(ViewState.ERROR);
        }
    }

    protected void initEndpoint() {
        mEndpoint = Endpoints.ENDPOINT_VALUE_DYNAMIC_FEED;
    }
}
