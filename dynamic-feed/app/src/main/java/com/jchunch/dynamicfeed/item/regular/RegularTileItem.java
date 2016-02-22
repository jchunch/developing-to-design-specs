package com.jchunch.dynamicfeed.item.regular;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.item.TileViewHolder;
import com.jchunch.dynamicfeed.model.RegularTile;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

/**
 * Created by jchunch on 2/18/16.
 */
public class RegularTileItem extends TileItem implements Parcelable {

    private RegularTile mRegularTile;

    public RegularTileItem(RegularTile regularTile) {
        mRegularTile = regularTile;
    }

    public RegularTile getRegularTile() {
        return mRegularTile;
    }

    @Override
    public void buildTileItem(TileViewHolder tileViewHolder, WeakReference<Picasso> picassoWeakRef) {
        if (mRegularTile != null && tileViewHolder != null 
                && tileViewHolder instanceof RegularTileViewHolder) {
            
            RegularTileViewHolder regularTileViewHolder = (RegularTileViewHolder) tileViewHolder;

            String body = mRegularTile.getBody();
            String header = mRegularTile.getHeader();
            String imageUrl = mRegularTile.getImageUrl();

            regularTileViewHolder.body.setText(body != null ? body : "");
            regularTileViewHolder.header.setText(header != null ? header : "");

            if (imageUrl != null && picassoWeakRef != null) {
                Picasso picasso = picassoWeakRef.get();
                if (picasso != null) {
                    // picasso.load(imageUrl).into(regularTileViewHolder.imageView);
                }
            }
        }
    }

    @Override
    public int getTileItemType() {
        return TILE_ITEM_TYPE_REGULAR;
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
        bundle.putParcelable(KEY_ARG_TILE, mRegularTile);

        // Write the key value pairs to the parcel
        parcel.writeBundle(bundle);
    }

    public static final Parcelable.Creator<RegularTileItem> CREATOR = new Parcelable.Creator<RegularTileItem>() {

        @Override
        public RegularTileItem createFromParcel(Parcel source) {

            // Read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle();

            // Get the values
            RegularTile regularTile = bundle.getParcelable(KEY_ARG_TILE);

            // Instantiate tile item using values from the bundle
            return new RegularTileItem(regularTile);
        }

        @Override
        public RegularTileItem[] newArray(int size) {
            return new RegularTileItem[size];
        }
    };
}
