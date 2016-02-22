package com.jchunch.dynamicfeed.item.small;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.TileViewHolder;
import com.jchunch.dynamicfeed.model.SmallTile;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

/**
 * Created by jchunch on 2/18/16.
 */
public class SmallTileItem extends TileItem implements Parcelable {

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

                    // picasso.load(imageUrl).into(smallTileViewHolder.imageView);
                }
            }
        }
    }

    @Override
    public int getTileItemType() {
        return TILE_ITEM_TYPE_SMALL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        // Create a bundle for the key value pairs
        Bundle bundle = new Bundle();

        // Insert the key value pairs to the bundle
        bundle.putParcelable(KEY_ARG_TILE, mSmallTile);

        // Write the key value pairs to the parcel
        parcel.writeBundle(bundle);
    }

    public static final Creator<SmallTileItem> CREATOR = new Creator<SmallTileItem>() {

        @Override
        public SmallTileItem createFromParcel(Parcel source) {

            // Read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle();

            // Get the values
            SmallTile smallTile = bundle.getParcelable(KEY_ARG_TILE);

            // Instantiate tile item using values from the bundle
            return new SmallTileItem(smallTile);
        }

        @Override
        public SmallTileItem[] newArray(int size) {
            return new SmallTileItem[size];
        }
    };
}
