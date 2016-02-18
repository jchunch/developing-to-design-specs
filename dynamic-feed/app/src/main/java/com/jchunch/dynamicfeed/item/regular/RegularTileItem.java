package com.jchunch.dynamicfeed.item.regular;

import com.jchunch.dynamicfeed.item.TileItem;
import com.jchunch.dynamicfeed.model.RegularTile;

/**
 * Created by jchunch on 2/18/16.
 */
public class RegularTileItem extends TileItem {

    private RegularTile mRegularTile;

    public RegularTileItem(RegularTile regularTile) {
        mRegularTile = regularTile;
    }

    public RegularTile getRegularTile() {
        return mRegularTile;
    }

    @Override
    public int getTileItemType() {
        return TILE_ITEM_TYPE_REGULAR;
    }
}
