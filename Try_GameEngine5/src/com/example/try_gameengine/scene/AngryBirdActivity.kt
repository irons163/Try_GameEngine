package com.example.try_gameengine.scene

import android.app.Activity
import android.os.Bundle

class AngryBirdActivity : Activity() {
    //	MySurfaceView surfaceView;
    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        surfaceView=new MySurfaceView(this);
//        setContentView(surfaceView);
    }

    companion object {
        /**??撠??屆?桃???之?踹漲 */
        const val RubberBandLength: Float = 50f

        /**敺?撠?曏???雿蔭 */
        var startX: Float = 0f
        var startY: Float = 0f

        /**?孵撠??????刻??湛??靚??嗅?銝箇撖寞???閮??曏?撠?
         * ??誑霈曄蔭?嫣葉撠??刻器銝?????湛??喳?撠? */
        var touchDistance: Float = 0f
    }
}