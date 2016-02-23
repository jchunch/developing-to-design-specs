package com.jchunch.dynamicfeed.item.small;

import com.jchunch.dynamicfeed.R;
import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.TileViewHolder;
import com.jchunch.dynamicfeed.model.SmallTile;

/**
 * Created by jchunch on 2/18/16.
 */
public class SmallTileItem extends TileItem {

    private SmallTile mSmallTile;

    public SmallTileItem(SmallTile smallTile) {
        mSmallTile = smallTile;
    }

    public SmallTile getSmallTile() {
        return mSmallTile;
    }
    
    @Override
    public void buildTileItem(TileViewHolder tileViewHolder) {
        if (mSmallTile != null && tileViewHolder != null 
                && tileViewHolder instanceof SmallTileViewHolder) {
            
            SmallTileViewHolder smallTileViewHolder = (SmallTileViewHolder) tileViewHolder;
            
            String body = mSmallTile.getBody();
            String header = mSmallTile.getHeader();
            // String imageUrl = mSmallTile.getImageUrl();

            smallTileViewHolder.body.setText(body != null ? body : "");
            smallTileViewHolder.header.setText(header != null ? header : "");

            // For simplicity, network image loading not included in this demo
            smallTileViewHolder.imageView.setImageResource(R.drawable.pic_small);
        }
    }

    @Override
    public int getTileItemType() {
        return TILE_ITEM_TYPE_SMALL;
    }
}
