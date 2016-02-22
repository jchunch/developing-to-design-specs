package com.jchunch.dynamicfeed.item.large;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.TileViewHolder;
import com.jchunch.dynamicfeed.model.LargeTile;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

/**
 * Created by jchunch on 2/18/16.
 */
public class LargeTileItem extends TileItem implements Parcelable {

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
                    // picasso.load(imageUrl).into(largeTileViewHolder.imageView);
                }
            }
        }
    }

    @Override
    public int getTileItemType() {
        return TILE_ITEM_TYPE_LARGE;
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
        bundle.putParcelable(KEY_ARG_TILE, mLargeTile);

        // Write the key value pairs to the parcel
        parcel.writeBundle(bundle);
    }

    public static final Parcelable.Creator<LargeTileItem> CREATOR = new Parcelable.Creator<LargeTileItem>() {

        @Override
        public LargeTileItem createFromParcel(Parcel source) {

            // Read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle();

            // Get the values
            LargeTile largeTile = bundle.getParcelable(KEY_ARG_TILE);

            // Instantiate tile item using values from the bundle
            return new LargeTileItem(largeTile);
        }

        @Override
        public LargeTileItem[] newArray(int size) {
            return new LargeTileItem[size];
        }
    };
}
