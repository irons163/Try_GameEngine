package com.example.try_gameengine.scene

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import com.example.try_gameengine.framework.GameView
import com.example.try_gameengine.framework.IGameController
import com.example.try_gameengine.framework.IGameModel
import com.example.try_gameengine.framework.LayerManager
import com.example.try_gameengine.stage.Stage

class DialogScene : EasyScene {
    var isNeedToStopTheActiveScene: Boolean = true

    constructor(context: Context?, id: String?) : super(context, id) {
        // TODO Auto-generated constructor stub
        mode = Scene.Companion.RESUME
        isEnableRemoteController = false
    }

    constructor(context: Context?, id: String?, level: Int) : super(context, id, level) {
        // TODO Auto-generated constructor stub
        mode = Scene.Companion.RESUME
        isEnableRemoteController = false
    }

    constructor(context: Context?, id: String?, level: Int, mode: Int) : super(
        context,
        id,
        level,
        mode
    ) {
        // TODO Auto-generated constructor stub
        isEnableRemoteController = false
    }

    var gameview: GameView? = null
    override fun initGameView(
        activity: Activity?, gameController: IGameController?,
        gameModel: IGameModel?
    ): GameView {
        // TODO Auto-generated method stub
        class MyGameView(
            context: Context?, gameController: IGameController?,
            gameModel: IGameModel
        ) : GameView(context, gameController!!, gameModel!!) {
            init {
                // TODO Auto-generated constructor stub
                setZOrderOnTop(true) // necessary
                val sfhTrackHolder = getHolder()
                sfhTrackHolder.setFormat(PixelFormat.TRANSPARENT)
                //				activity.getWindow().setFormat(PixelFormat.TRANSPARENT);
            }

            //			@Override
            //			public boolean dispatchTouchEvent(MotionEvent event) {
            //				// TODO Auto-generated method stub
            //				return true;
            //			}
            public override fun onTouchEvent(event: MotionEvent?): Boolean {
                // TODO Auto-generated method stub
                return super.onTouchEvent(event)
            }
        }

        gameview = MyGameView(activity, gameController, gameModel!!)
        return gameview!!
        //		gameview.surfaceCreated(gameview.getHolder());
    }

    override fun process() {
        // TODO Auto-generated method stub
    }

    override fun doDraw(canvas: Canvas?) {
        // TODO Auto-generated method stub
        dialogSceneDrawListener.draw(canvas)
    }

    override fun beforeGameStart() {
        // TODO Auto-generated method stub
    }

    override fun arrangeView(activity: Activity?) {
        // TODO Auto-generated method stub
    }

    private fun checkContentViewExist(parent: View): Boolean {
        var isExsit = false
        if (parent is ViewGroup) {
            val group = parent
            for (i in 0..<group.getChildCount()) {
                isExsit = checkContentViewExist(group.getChildAt(i))
                if (isExsit) break
            }
        } else {
            if (parent == gameview) {
                isExsit = true
            }
        }
        return isExsit
    }

    private fun removeContentView(parent: View): Boolean {
        var isExsit = false
        if (parent is ViewGroup) {
            val group = parent
            for (i in 0..<group.getChildCount()) {
                isExsit = checkContentViewExist(group.getChildAt(i))
                if (isExsit) {
//	        		Canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
//	        		gameview.draw(canvas);
//	        		gameModel.d
                    val holder = gameview!!.getHolder()
                    val canvas = holder.lockCanvas()
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                    holder.unlockCanvasAndPost(canvas)
                    holder.setFormat(PixelFormat.TRANSPARENT)


//	        		holder.setFormat(PixelFormat.OPAQUE);
                    gameview!!.setVisibility(View.GONE)
                    gameview!!.refreshDrawableState()
                    gameview!!.invalidate()
                    gameview!!.postInvalidate()
                    //	        		((SurfaceView)gameview).invalidate();
                    group.removeView(gameview)

                    gameview!!.destroyDrawingCache()


//	        		group.removeAllViews();
                    group.invalidate()
                    group.postInvalidate()
                    group.refreshDrawableState()
                    group.requestLayout()
                    break
                }
            }
        } else {
            if (parent == gameview) {
                isExsit = true
            }
        }
        return isExsit
    }

    override fun setActivityContentView(activity: Activity?) {
        activity ?: return
        // TODO Auto-generated method stub
        //		activity.addContentView(gameview, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 
        //		ViewGroup.LayoutParams.MATCH_PARENT));
        var isExsit = false
        //ViewGroup view = (ViewGroup)activity.getWindow().getDecorView();
        //for(int i = 0; i < view.getChildCount(); i++){
        //	View v = view.getChildAt(i);
        //	if(v.equals(this)){
        //		isExsit = true;
        //		break;
        //	}
        //}
        isExsit = checkContentViewExist(activity.getWindow().getDecorView())
        if (!isExsit) {
            activity.getWindow().addContentView(
                gameview, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    override fun afterGameStart() {
        // TODO Auto-generated method stub
    }

    override fun surfaceChanged(
        holder: SurfaceHolder?, format: Int, width: Int,
        height: Int
    ) {
        // TODO Auto-generated method stub
    }

    interface DialogSceneDrawListener {
        fun draw(canvas: Canvas?)
    }

    private var dialogSceneDrawListener: DialogSceneDrawListener =
        object : DialogSceneDrawListener {
            override fun draw(canvas: Canvas?) {
                // TODO Auto-generated method stub
            }
        }

    fun setDialogSceneDraw(dialogSceneDrawListener: DialogSceneDrawListener) {
        this.dialogSceneDrawListener = dialogSceneDrawListener
    }

    override fun stop() {
        // TODO Auto-generated method stub
        super.stop()
    }

    override fun finish() {
        // TODO Auto-generated method stub
//		super.finish();
        setMode(Scene.Companion.FINISHED)
        val s = (context as Stage)
        s.getSceneManager()
            .removeSceneButNotDestroy(this) //if use removeScene, it made call finish() loop.
        removeContentView((context as Activity).getWindow().getDecorView())
        gameModel!!.setData(DestoryData())
        LayerManager.Companion.getInstance().deleteSceneLayersBySceneLayerLevel(sceneLayerLevel)
        //		((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
//		((Activity)context).getWindow().getDecorView().invalidate();
    }

    public override fun onTouchEvent(event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        dialogSceneTouchListener.onTouchEvent(event)
        return true
    }

    interface DialogSceneTouchListener {
        fun onTouchEvent(event: MotionEvent?)
    }

    private var dialogSceneTouchListener: DialogSceneTouchListener =
        object : DialogSceneTouchListener {
            override fun onTouchEvent(event: MotionEvent?) {
                // TODO Auto-generated method stub
            }
        }

    fun setDialogSceneTouchListener(dialogSceneTouchListener: DialogSceneTouchListener) {
        this.dialogSceneTouchListener = dialogSceneTouchListener
    }

    fun setGameView(gameView: GameView) {
        this.gameview = gameView
    }
}
