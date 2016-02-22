package com.jchunch.dynamicfeed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.TileViewHolder;
import com.jchunch.dynamicfeed.item.large.LargeTileViewHolder;
import com.jchunch.dynamicfeed.item.regular.RegularTileViewHolder;
import com.jchunch.dynamicfeed.item.small.SmallTileViewHolder;

import java.util.List;

/**
 * Created by jchunch on 2/17/16.
 */
public class FeedRecyclerAdapter extends RecyclerView.Adapter<TileViewHolder> {
    private static final String TAG = FeedRecyclerAdapter.class.getName();

    private List<TileItem> mTileItems;

    public FeedRecyclerAdapter(List<TileItem> tileItems) {
        mTileItems = tileItems;
    }

    @Override
    public TileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        TileViewHolder viewHolder = null;

        switch (viewType) {
            case TileItem.TILE_ITEM_TYPE_LARGE:
                View largeView = inflater.inflate(R.layout.feed_item_large, parent, false);
                viewHolder = new LargeTileViewHolder(largeView);
                break;

            case TileItem.TILE_ITEM_TYPE_REGULAR:
                View regularView = inflater.inflate(R.layout.feed_item_regular, parent, false);
                viewHolder = new RegularTileViewHolder(regularView);
                break;

            case TileItem.TILE_ITEM_TYPE_SMALL:
                View smallView = inflater.inflate(R.layout.feed_item_small, parent, false);
                viewHolder = new SmallTileViewHolder(smallView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TileViewHolder viewHolder, int position) {
        if (viewHolder == null) {
            return;
        }

        TileItem tileItem = getTileItemByPosition(position);
        if (tileItem != null) {
            tileItem.buildTileItem(viewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return mTileItems != null ? mTileItems.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {

        // Get the tile item type
        Integer discoverItemType = getTileItemType(position);
        if (discoverItemType != null) {
            return discoverItemType;
        }

        return super.getItemViewType(position);
    }

    private TileItem getTileItemByPosition(int position) {
        if (mTileItems != null && !mTileItems.isEmpty()) {
            if (position >= 0 && position < mTileItems.size()) {
                TileItem tileItem = mTileItems.get(position);
                if (tileItem != null) {
                    return tileItem;
                }
            }
        }

        return null;
    }

    private Integer getTileItemType(int position) {
        TileItem discoverItem = getTileItemByPosition(position);
        if (discoverItem != null) {
            return discoverItem.getTileItemType();
        }

        return null;
    }

    public void updateTileItems(List<TileItem> tileItems) {
        mTileItems = tileItems;
        notifyDataSetChanged();
    }
}
