package com.jchunch.dynamicfeed.item.large;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.TileViewHolder;
import com.jchunch.dynamicfeed.model.LargeTile;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

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
    public void buildTileItem(TileViewHolder tileViewHolder, WeakReference<Picasso> picassoWeakRef) {
        if (mLargeTile != null && tileViewHolder != null
                && tileViewHolder instanceof LargeTileViewHolder) {

            LargeTileViewHolder largeTileViewHolder = (LargeTileViewHolder) tileViewHolder;

            String body = mLargeTile.getBody();
            String header = mLargeTile.getHeader();
            String imageUrl = mLargeTile.getImageUrl();

            largeTileViewHolder.body.setText(body != null ? body : "");
            largeTileViewHolder.header.setText(header != null ? header : "");

            if (imageUrl != null && picassoWeakRef != null) {
                Picasso picasso = picassoWeakRef.get();
                if (picasso != null) {
                    picasso.load(imageUrl).into(largeTileViewHolder.imageView);
                }
            }
        }
    }

    @Override
    public int getTileItemType() {
        return TILE_ITEM_TYPE_LARGE;
    }
}
