package com.jchunch.dynamicfeed.item.small;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.TileViewHolder;
import com.jchunch.dynamicfeed.model.SmallTile;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

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
    public void buildTileItem(TileViewHolder tileViewHolder, WeakReference<Picasso> picassoWeakRef) {
        if (mSmallTile != null && tileViewHolder != null 
                && tileViewHolder instanceof SmallTileViewHolder) {
            
            SmallTileViewHolder smallTileViewHolder = (SmallTileViewHolder) tileViewHolder;
            
            String body = mSmallTile.getBody();
            String header = mSmallTile.getHeader();
            String imageUrl = mSmallTile.getImageUrl();

            smallTileViewHolder.body.setText(body != null ? body : "");
            smallTileViewHolder.header.setText(header != null ? header : "");

            if (imageUrl != null && picassoWeakRef != null) {
                Picasso picasso = picassoWeakRef.get();
                if (picasso != null) {

                    // TODO: Add `...resize(w, h).centerCrop()...`
                    // int h = smallTileViewHolder.imageView.getHeight();
                    // int w = smallTileViewHolder.imageView.getWidth();

                    picasso.load(imageUrl).into(smallTileViewHolder.imageView);
                }
            }
        }
    }

    @Override
    public int getTileItemType() {
        return TILE_ITEM_TYPE_SMALL;
    }
}
