package com.jchunch.dynamicfeed.item.regular;

import com.jchunch.dynamicfeed.R;
import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.TileViewHolder;
import com.jchunch.dynamicfeed.model.RegularTile;

/**
 * Created by jchunch on 2/18/16.
 */
public class RegularTileItem extends TileItem {

    private RegularTile mRegularTile;

    public RegularTileItem(RegularTile regularTile) {
        mRegularTile = regularTile;
    }

    public RegularTile getRegularTile() {
        return mRegularTile;
    }

    @Override
    public void buildTileItem(TileViewHolder tileViewHolder) {
        if (mRegularTile != null && tileViewHolder != null 
                && tileViewHolder instanceof RegularTileViewHolder) {
            
            RegularTileViewHolder regularTileViewHolder = (RegularTileViewHolder) tileViewHolder;

            String body = mRegularTile.getBody();
            String header = mRegularTile.getHeader();
            // String imageUrl = mRegularTile.getImageUrl();

            regularTileViewHolder.body.setText(body != null ? body : "");
            regularTileViewHolder.header.setText(header != null ? header : "");

            // For simplicity, network image loading not included in this example
            regularTileViewHolder.imageView.setImageResource(R.drawable.pic_two);
        }
    }

    @Override
    public int getTileItemType() {
        return TILE_ITEM_TYPE_REGULAR;
    }
}
