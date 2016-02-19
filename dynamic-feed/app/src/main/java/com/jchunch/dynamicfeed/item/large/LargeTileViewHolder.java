package com.jchunch.dynamicfeed.item.large;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jchunch.dynamicfeed.R;
import com.jchunch.dynamicfeed.item.TileViewHolder;

/**
 * Created by jchunch on 2/18/16.
 */
public class LargeTileViewHolder extends TileViewHolder {

    public ImageView imageView;
    public TextView body;
    public TextView header;

    public LargeTileViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.large_item_image_view);
        body = (TextView) itemView.findViewById(R.id.large_item_body);
        header = (TextView) itemView.findViewById(R.id.large_item_header);
    }
}
