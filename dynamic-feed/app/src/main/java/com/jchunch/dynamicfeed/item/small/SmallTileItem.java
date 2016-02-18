package com.jchunch.dynamicfeed.item.small;

import com.jchunch.dynamicfeed.item.TileItem;
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
    public int getTileItemType() {
        return TILE_ITEM_TYPE_SMALL;
    }
}
