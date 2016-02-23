package com.jchunch.dynamicfeed.item;

/**
 * Created by jchunch on 2/17/16.
 */
public abstract class TileItem {

    public static final int TILE_ITEM_TYPE_LARGE = 0;
    public static final int TILE_ITEM_TYPE_REGULAR = 1;
    public static final int TILE_ITEM_TYPE_SMALL = 2;

    public abstract void buildTileItem(TileViewHolder tileViewHolder);

    public abstract int getTileItemType();
}
