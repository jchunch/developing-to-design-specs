package com.jchunch.dynamicfeed;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jchunch on 2/17/16.
 */
public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private static final String TAG = FeedRecyclerAdapter.class.getName();

    private List<FeedItem> mFeedItems;

    public FeedRecyclerAdapter(List<FeedItem> feedItems) {
        mFeedItems = feedItems;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mFeedItems != null ? mFeedItems.size() : 0;
    }
}
