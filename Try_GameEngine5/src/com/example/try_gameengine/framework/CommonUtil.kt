package com.example.try_gameengine.framework

import android.content.Context
import android.content.res.Resources

object CommonUtil {
    var screenWidth: Int = 0
    var screenHeight: Int = 0

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     * 
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     // */
    fun convertDpToPixel(dp: Float): Float {
//	    Resources resources = context.getResources();
        val metrics = Resources.getSystem().getDisplayMetrics()
        val px = dp * (metrics.densityDpi / 160f)
        return px
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     * 
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     // */
    fun convertPixelsToDp(px: Float): Float {
//	    Resources resources = context.getResources();
        val metrics = Resources.getSystem().getDisplayMetrics()
        val dp = px / (metrics.densityDpi / 160f)
        return dp
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     * 
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     // */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.getResources()
        val metrics = resources.getDisplayMetrics()
        val px = dp * (metrics.densityDpi / 160f)
        return px
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     * 
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     // */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        val resources = context.getResources()
        val metrics = resources.getDisplayMetrics()
        val dp = px / (metrics.densityDpi / 160f)
        return dp
    }

    fun converDxWithDefaultScreenPersentToCurrentScreenPersent(dx: Float): Float {
        var dx = dx
        dx = dx * (Config.currentScreenWidth / Config.defaultScreenWidth)
        return dx
    }

    fun converDyWithDefaultScreenPersentToCurrentScreenPersent(dy: Float): Float {
        var dy = dy
        dy = dy * (Config.currentScreenHeight / Config.defaultScreenHeight)
        return dy
    }
}
