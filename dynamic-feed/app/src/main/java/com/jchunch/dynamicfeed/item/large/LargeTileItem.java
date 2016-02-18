package com.jchunch.dynamicfeed.item.large;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.model.LargeTile;

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
    public int getTileItemType() {
        return TILE_ITEM_TYPE_LARGE;
    }
}
