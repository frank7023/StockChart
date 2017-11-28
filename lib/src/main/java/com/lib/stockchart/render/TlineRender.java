package com.lib.stockchart.render;

import android.graphics.Canvas;
import android.util.Log;

import com.lib.stockchart.draw.DrawTline;
import com.lib.stockchart.draw.DrawTurnover;
import com.lib.stockchart.draw.IDraw;
import com.lib.stockchart.entry.EntryManager;

import java.util.List;

/**
 * description: 分时图 + 成交量(不可以滑动, 仅仅显示当天开盘走势信息)
 * created by kalu on 2017/11/9 0:59
 */
public class TlineRender extends BaseRender {

    @Override
    public void onCanvas(Canvas canvas, int indexBegin, int indexEnd, int indexCount, int indexMax, float xoffsetLeft, float xoffsetRight) {

        final List<IDraw> drawList = getDrawList();

        if (indexMax <= 0) {
            // Log.e("TlineRender", "onCanvas ==> 空数据");
            for (IDraw drawing : drawList) {
                drawing.onDrawNull(canvas);
            }
        } else {
           // Log.e("TlineRender", "onCanvas ==> 有数据");

            final float minPrice = EntryManager.getInstance().calculatePriceMin(0, indexMax);
            final float maxPrice = EntryManager.getInstance().calculatePriceMax(0, indexMax);
            final float turnoverMax = EntryManager.getInstance().calculateTurnoverMax(0, indexMax);
            calculateData(0, indexMax, indexMax);

            for (int i = 0; i < drawList.size(); i++) {
                final IDraw temp = drawList.get(i);
                temp.onDrawData(this, canvas, indexMax, 0, indexMax, minPrice, maxPrice, turnoverMax, getxHighligh(), getyHighligh(), xoffsetLeft, xoffsetRight);
            }
        }
    }

    @Override
    public void addData() {

        final List<IDraw> drawList = getDrawList();
        drawList.clear();
        drawList.add(new DrawTline());
        drawList.add(new DrawTurnover());
    }
}