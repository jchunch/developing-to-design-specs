package com.jchunch.dynamicfeed.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jchunch on 2/17/16.
 */
public class RegularTile extends Tile implements Parcelable {
    private static final String KEY_ARG_HEADER = "KEY_ARG_HEADER";
    private static final String KEY_ARG_BODY = "KEY_ARG_BODY";
    private static final String KEY_ARG_IMAGE_URL = "KEY_ARG_IMAGE_URL";

    private String header;
    private String body;
    private String imageUrl;

    public RegularTile(String header, String body, String imageUrl) {
        this.header = header;
        this.body = body;
        this.imageUrl = imageUrl;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        bundle.putString(KEY_ARG_HEADER, header);
        bundle.putString(KEY_ARG_BODY, body);
        bundle.putString(KEY_ARG_IMAGE_URL, imageUrl);

        // Write the key value pairs to the parcel
        parcel.writeBundle(bundle);
    }

    public static final Creator<RegularTile> CREATOR = new Creator<RegularTile>() {

        @Override
        public RegularTile createFromParcel(Parcel source) {

            // Read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle();

            // Get the values
            String header = bundle.getString(KEY_ARG_HEADER);
            String body = bundle.getString(KEY_ARG_BODY);
            String imageUrl = bundle.getString(KEY_ARG_IMAGE_URL);

            // Instantiate a person using values from the bundle
            return new RegularTile(
                    header,
                    body,
                    imageUrl
            );
        }

        @Override
        public RegularTile[] newArray(int size) {

            return new RegularTile[size];
        }
    };
}
