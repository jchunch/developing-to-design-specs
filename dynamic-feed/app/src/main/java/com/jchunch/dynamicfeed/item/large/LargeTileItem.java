package com.jchunch.dynamicfeed.item.large;

import com.jchunch.dynamicfeed.R;
import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.TileViewHolder;
import com.jchunch.dynamicfeed.model.LargeTile;

/**
 * Created by jchunch on 2/18/16.
 */
public class LargeTileItem extends TileItem {

    private LargeTile mLargeTile;

    public LargeTileItem(LargeTile largeTile) {
        mLargeTile = largeTile;
    }

    public LargeTile getLargeTile() {
        return mLargeTile;
    }

    @Override
    public void buildTileItem(TileViewHolder tileViewHolder) {
        if (mLargeTile != null && tileViewHolder != null
                && tileViewHolder instanceof LargeTileViewHolder) {

            LargeTileViewHolder largeTileViewHolder = (LargeTileViewHolder) tileViewHolder;

            String body = mLargeTile.getBody();
            String header = mLargeTile.getHeader();
            // String imageUrl = mLargeTile.getImageUrl();

            largeTileViewHolder.body.setText(body != null ? body : "");
            largeTileViewHolder.header.setText(header != null ? header : "");

            // For simplicity, network image loading not included in this demo
            largeTileViewHolder.imageView.setImageResource(R.drawable.pic_large);
        }
    }

    @Override
    public int getTileItemType() {
        return TILE_ITEM_TYPE_LARGE;
    }
}
