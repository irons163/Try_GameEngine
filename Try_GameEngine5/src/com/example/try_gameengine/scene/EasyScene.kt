package com.example.try_gameengine.scene

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.Manifold
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.example.try_gameengine.framework.BitmapUtil
import com.example.try_gameengine.framework.CommonUtil
import com.example.try_gameengine.framework.Config
import com.example.try_gameengine.framework.Data
import com.example.try_gameengine.framework.GameController
import com.example.try_gameengine.framework.GameModel
import com.example.try_gameengine.framework.GameView
import com.example.try_gameengine.framework.IGameController
import com.example.try_gameengine.framework.IGameModel
import com.example.try_gameengine.framework.LayerManager
import com.example.try_gameengine.framework.ProcessBlock
import com.example.try_gameengine.framework.ProcessBlockManager
import com.example.try_gameengine.framework.TouchDispatcher
import org.loon.framework.android.game.physics.LWorld

abstract class EasyScene : Scene, ContactListener {
    //	EasyGameModel gameModel;
    @JvmField
    var paint: Paint? = null
    var isEnablePhysical: Boolean = false

    private var screenW = 0
    private var screenH = 0

    protected var bird: Bird? = null

    var lock: ByteArray = ByteArray(0)
    private val timePause = 50

    //	World world;
    var world: LWorld? = null

    //	AABB aabb; 
    var gravity: Vector2? = null
    protected var timeStep: Float = 1f / 60f

    protected var velocityIterations: Int = 10
    protected var positionIterations: Int = 8

    enum class Type {
        redBird,
        yellowBird,
        blueBird,
        pig,
        ground,
        wood,
        glass,
        stone,
    }

    var physicsWorld: PhysicsWorld? = null
    private fun initP() {
        physicsWorld = PhysicsWorld(this)
    }

    private fun stepPhysicsAndNavigation(deltaTime: Float) {
        if (physicsWorld != null && physicsWorld!!.isAutoStep()) physicsWorld!!.update(deltaTime)
    }

    private fun okok() {
    }

    constructor(context: Context?, id: String?) : super(context, id) {
        // TODO Auto-generated constructor stub
        setAutoAdd(true)
        initPhysical()
    }

    constructor(context: Context?, id: String?, level: Int) : super(context, id, level) {
        // TODO Auto-generated constructor stub
        setAutoAdd(true)
        initPhysical()
    }

    constructor(context: Context?, id: String?, level: Int, mode: Int) : super(
        context,
        id,
        level,
        mode
    ) {
        // TODO Auto-generated constructor stub
        setAutoAdd(true)
        initPhysical()
    }

    private fun initPhysical() {
        paint = Paint()
        paint!!.setStyle(Paint.Style.STROKE)
        paint!!.setAntiAlias(true)

        /**嚙踝蕭嚙瞌嚙踝蕭l嚙踝蕭 */
        gravity = Vector2(0f, -10f)

        /**嚙請建迎蕭嚙緲嚙瑾嚙踝蕭 */
//		world=new World(gravity, true);
        world = LWorld(0f, 20f, 1800, 1800, true, 1.0f)

        /**嚙磕嚙稼嚙踝蕭嚙緲嚙瑾嚙褕歹蕭嚙踝蕭嚙瘢嚙踝蕭嚙踝蕭聽 */
        world!!.setContactListener(this)

        val dm = DisplayMetrics()
        (context as Activity).getWindowManager().getDefaultDisplay().getMetrics(dm)

        CommonUtil.screenHeight = dm.heightPixels
        CommonUtil.screenWidth = dm.widthPixels

        //		CommonUtil.statusBarHeight = CommonUtil.getStatusBarHeight(this);
//		CommonUtil.screenHeight -= CommonUtil.statusBarHeight;
        /**嚙緻嚙踝蕭羅嚙踝蕭j嚙緘 */
        this.screenW = CommonUtil.screenWidth
        this.screenH = CommonUtil.screenHeight

        /**嚙踝蕭l嚙複小嚙踝蕭嚙踝蕭m */
        AngryBirdActivity.Companion.startX = 100f
        AngryBirdActivity.Companion.startY = (screenH - 500).toFloat()

        AngryBirdActivity.Companion.touchDistance = 0.2f * screenH


        val bmpBird = BitmapUtil.redPoint!!

        bird = Bird(
            AngryBirdActivity.Companion.startX,
            AngryBirdActivity.Companion.startY,
            bmpBird.getHeight() / 2f,
            bmpBird,
            Type.redBird
        )

        /** 嚙請建四嚙瞑嚙踝蕭嚙踝蕭堙A嚙稽嚙練 isStatic嚙踝蕭true嚙璀嚙磐嚙箭嚙踝蕭嚙緲嚙瑾嚙褕歹蕭嚙瞌嚙磋嚙踐的嚙璀
         * Type嚙稽嚙練嚙踝蕭ground嚙璀嚙論免嚙瞋嚙踝蕭嚙踝蕭
         // */
        createPolygon(5f, 5f, (CommonUtil.screenWidth - 10).toFloat(), 2f, true, Type.ground)
        createPolygon(
            5f,
            (CommonUtil.screenHeight - 10).toFloat(),
            (CommonUtil.screenWidth - 10).toFloat(),
            2f,
            true,
            Type.ground
        )
        createPolygon(5f, 5f, 2f, (CommonUtil.screenHeight - 10).toFloat(), true, Type.ground)
        createPolygon(
            (CommonUtil.screenWidth - 10).toFloat(),
            5f,
            2f,
            (CommonUtil.screenHeight - 10).toFloat(),
            true,
            Type.ground
        )

        /**嚙請恬蕭6嚙諉歹蕭峞AisStatic嚙稽嚙練嚙踝蕭false嚙璀嚙磐嚙箭嚙踝蕭嚙緲嚙瑾嚙褕歹蕭嚙瞌嚙褊態嚙璀嚙踝蕭嚙羯嚙瞌嚙瑾嚙諄影嚙確  */
        for (i in 0..5) {
            createPolygon(
                (screenW - 250).toFloat(),
                (screenH - 200 - 20 * i).toFloat(),
                20f,
                20f,
                false,
                Type.wood
            )
        }
        /**嚙請建一嚙諉迎蕭嚙踝蕭嚙踝蕭嚙璀嚙稽嚙瞌嚙褊態嚙踝蕭  */
        createPolygon(
            (screenW - 380).toFloat(),
            (screenH - 250 - 20 * 6 - 10).toFloat(),
            80f,
            10f,
            false,
            Type.wood
        )
    }

    /**嚙請建塚蕭峈嚙箭ody */
    fun createCircle(x: Float, y: Float, r: Float, isStatic: Boolean): Body {
        /**嚙稽嚙練body嚙諄迎蕭 */
        val circle = CircleShape()
        /**嚙箭嚙罵嚙璀嚙緯嚙瞇嚙衛對蕭嚙踝蕭嚙諸潘蕭嚙踝蕭鴘恬蕭z嚙瑾嚙褕歹蕭  */
        circle.setRadius(r / RATE)

        /**嚙稽嚙練FixtureDef  */
        val fDef = FixtureDef()
        if (isStatic) {
            /**嚙皺嚙論穿蕭0嚙褕，嚙箭嚙踝蕭嚙緲嚙瑾嚙褕歹蕭嚙踝蕭嚙踝蕭嚙羯嚙瞌嚙緞嚙確嚙璀嚙踝蕭嚙磋嚙踐的  */
            fDef.density = 0f
        } else {
            /**嚙皺嚙論歹蕭嚙踝蕭0嚙褕，嚙箭嚙踝蕭嚙緲嚙瑾嚙褕歹蕭嚙罵嚙踝蕭嚙羯嚙瞌嚙緞嚙確  */
            fDef.density = 1f
        }
        /**嚙稽嚙練嚙踝蕭嚙踝蕭嚙瞌嚙璀嚙範嚙踝蕭 0嚙踝蕭1  */
        fDef.friction = 1.0f
        /**嚙稽嚙練嚙踝蕭嚙踝蕭I嚙踝蕭嚙踝蕭嚙稷嚙稻嚙瞌嚙璀?嚙瘠嚙踝蕭嚙篇嚙踝蕭嚙踝蕭j嚙踝蕭嚙踝蕭嚙緯? */
        fDef.restitution = 0.3f
        /**嚙皺嚙稼嚙諄迎蕭 */
        fDef.shape = circle

        /**嚙稽嚙練BodyDef  */
        val bodyDef = BodyDef()

        /**嚙踝蕭嚙畿嚙瑾嚙緩嚙緯嚙稽嚙練嚙璀嚙磐嚙踝蕭density嚙踝蕭嚙踝蕭0嚙璀
         * 嚙磐嚙踝蕭嚙畿嚙踝蕭嚙瞇body.type嚙稽嚙練嚙踝蕭BodyType.DYNAMIC,嚙踝蕭嚙踝蕭嚙罵嚙磋嚙踝蕭
         // */
        bodyDef.type = if (isStatic) BodyType.StaticBody else BodyType.DynamicBody
        /**嚙稽嚙練body嚙踝蕭m嚙璀嚙緯嚙瞇嚙衛對蕭嚙踝蕭嚙諸潘蕭嚙踝蕭鴘恬蕭z嚙瑾嚙褕歹蕭  */
        bodyDef.position.set((x) / RATE, (y) / RATE)

        /**嚙請恬蕭body */
        val body = world!!.createBody(bodyDef)

        /**嚙皺嚙稼 m_userData  */
        body.setUserData(bird)


        //	body.createShape(fDef); //嚙蝓迎蕭JBox2D嚙踝蕭嚙請建歹蕭k
        /**嚙踝蕭body嚙請恬蕭Fixture */
        body.createFixture(fDef)


        //	body.setMassFromShapes();	//嚙蝓迎蕭JBox2D嚙踝蕭嚙請建歹蕭k
        return body
    }

    fun createPolygon(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        isStatic: Boolean,
        type: Type?
    ): Body {
        val polygon = PolygonShape()

        polygon.setAsBox(width / 2 / RATE, height / 2 / RATE)

        val fDef = FixtureDef()
        if (isStatic) {
            fDef.density = 0f
        } else {
            fDef.density = 1f
        }
        fDef.friction = 1.0f
        fDef.restitution = 0.0f

        fDef.shape = polygon

        val bodyDef = BodyDef()

        bodyDef.type = if (isStatic) BodyType.StaticBody else BodyType.DynamicBody //new

        bodyDef.position.set((x + width / 2) / RATE, (y + height / 2) / RATE)

        val body = world!!.createBody(bodyDef)

        body.setUserData(MyRect(x, y, width, height, type))


        //	body.createShape(polygonDef);
        //	body.setMassFromShapes();
        body.createFixture(fDef)

        return body
    }

    override fun initGameModel() {
        gameModel = EasyGameModel(context, null)
    }

    override fun initGameController() {
        gameController = EasyGameController(context as Activity?, gameModel)
    }

    abstract fun initGameView(
        activity: Activity?,
        gameController: IGameController?,
        gameModel: IGameModel?
    ): GameView?

    // If override, need super.process().
    open fun process() {
        LayerManager.Companion.getInstance().processLayersForNegativeZOrder()
        LayerManager.Companion.getInstance().processLayersForOppositeZOrder()
    }

    // If override, need super.doDraw(Canvas canvas).
    open fun doDraw(canvas: Canvas?) {
        LayerManager.Companion.getInstance().drawLayersForNegativeZOrder(canvas, null)
        LayerManager.Companion.getInstance().drawLayersForOppositeZOrder(canvas, null)
    }

    // If override, need super.onSceneTouchEvent(MotionEvent event).
    fun onSceneTouchEvent(event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
//		boolean isTouched =  
//				TouchDispatcher.getInstance().onTouchEvent(event) ||
//				LayerManager.getInstance().onTouchLayersForOppositeZOrder(event) ||
//				LayerManager.getInstance().onTouchLayersForNegativeZOrder(event);
        val isTouched: Boolean = TouchDispatcher.Companion.getInstance().onTouchEvent(event)
        return isTouched
    }

    abstract fun beforeGameStart()

    abstract fun arrangeView(activity: Activity?)

    abstract fun setActivityContentView(activity: Activity?)

    abstract fun afterGameStart()

    protected fun beforeGameStop() {
        //do something
    }

    protected fun afterGameStop() {
        //do something
    }

    abstract fun surfaceChanged(
        holder: SurfaceHolder?, format: Int, width: Int,
        height: Int
    )

    internal inner class EasyGameController(activity: Activity?, gameModel: IGameModel?) :
        GameController(activity, gameModel!!) {
        init {
            // TODO Auto-generated constructor stub
            BitmapUtil.initBitmap(context!!)
            BitmapUtil.initBitmapForTest()
            //			remoteController = RemoteController.createRemoteController();
        }

        override fun initGameView(activity: Activity?, gameModel: IGameModel?): GameView? {
            // TODO Auto-generated method stub
            return this@EasyScene.initGameView(activity, this, gameModel)
        }

        public override fun arrangeView() {
            // TODO Auto-generated method stub
            this@EasyScene.arrangeView(activity)
        }

        override fun setActivityContentView(activity: Activity?) {
            // TODO Auto-generated method stub
            this@EasyScene.setActivityContentView(activity)
        }

        public override fun beforeGameStart() {
            // TODO Auto-generated method stub
            this@EasyScene.beforeGameStart()
        }

        public override fun afterGameStart() {
            // TODO Auto-generated method stub
            this@EasyScene.afterGameStart()
        }

        override fun beforeGameStop() {
            // TODO Auto-generated method stub
            this@EasyScene.beforeGameStop()
        }

        override fun afterGameStop() {
            // TODO Auto-generated method stub
            this@EasyScene.afterGameStop()
        }

        public override fun start() {
            // TODO Auto-generated method stub
            initStart(mode)
        }

        override fun surfaceChanged(
            holder: SurfaceHolder?, format: Int, width: Int,
            height: Int
        ) {
            // TODO Auto-generated method stub
            this@EasyScene.surfaceChanged(holder, format, width, height)
        }

        public override fun onTouchEvent(event: MotionEvent?) {
            // TODO Auto-generated method stub
//			boolean isRemoteControllerCatchTouchEvent = false;
//			if(isEnableRemoteController && remoteController!=null)
//				isRemoteControllerCatchTouchEvent = remoteController.onTouchEvent(event);
//			
//			if(!isRemoteControllerCatchTouchEvent)
//				super.onTouchEvent(event);
            super.onTouchEvent(event)
        }
    }

    internal inner class EasyGameModel  //		public EasyGameView(Context context, IGameController gameController,
    //				IGameModel gameModel) {
    //			super(context, gameController, gameModel);
    //			// TODO Auto-generated constructor stub
    //		}
        (context: Context?, data: Data?) : GameModel(context, data) {
        public override fun process() {
            // TODO Auto-generated method stub
//			super.process();
//			TouchDispatcher.getInstance().dispatch();
            this@EasyScene.process()
            LayerManager.Companion.getInstance().processHUDLayers()
        }

        public override fun doDraw(canvas: Canvas?) {
            canvas ?: return
            // TODO Auto-generated method stub
//			super.doDraw(canvas);
            /*
			LayerManager.getInstance().drawLayersForNegativeZOrder(canvas, null);
			EasyScene.this.drawSelf(canvas, null);
			LayerManager.getInstance().drawLayersForOppositeZOrder(canvas, null);
			EasyScene.this.doDraw(canvas);
			// */

//			LayerManager.getInstance().drawLayersForNegativeZOrder(canvas, null);
//			LayerManager.getInstance().drawLayersForOppositeZOrder(canvas, null);

            this@EasyScene.doDraw(canvas)

            val activeRemoteController = remoteController
            if (isEnableRemoteController && activeRemoteController != null) activeRemoteController.drawRemoteController(
                canvas,
                null
            )
            if (isEnablePhysical) {
                /**嚙箴嚙碼嚙緘嚙踝蕭 */
                bird!!.draw(canvas, paint)

                /**嚙緘嚙瘦嚙緘嚙踝蕭嚙誶沒嚙瞋嚙緻嚙篇嚙璀嚙箴嚙碼嚙踝蕭坁嚙踝蕭嚙誰蛛蕭嚙緙嚙踝蕭 */
                if (!bird!!.isReleased) {
                    canvas.drawLine(
                        AngryBirdActivity.Companion.startX,
                        AngryBirdActivity.Companion.startY,
                        bird!!.x,
                        bird!!.y,
                        paint!!
                    )
                }

                /**嚙瞎嚙踝蕭嚙踝蕭嚙緲嚙瑾嚙褕，嚙箴嚙碼Rect  */
                //			Body body = world.getBodyList();
                for (i in 1..<world!!.bodyCount) {
                    val body = world!!.bodyList.get(i)
                    if ((body!!.userData) is MyRect) {
                        val rect = (body.userData) as MyRect
                        rect.draw(canvas, paint)
                    }
                    //				body = body.m_next;
                }
            }

            val systemCamera = Config.SystemCamera
            if (systemCamera != null && systemCamera.getViewPort() != null) {
                canvas.save()
                canvas.setMatrix(systemCamera.getViewPort()!!.getMatrix())
                LayerManager.Companion.getInstance().drawHUDLayers(canvas, null)
                canvas.restore()
            }

            if (getCamera()!!.getViewPort() != null) {
                canvas.save()
                canvas.setMatrix(getCamera()!!.getViewPort()!!.getMatrix())
                LayerManager.Companion.getInstance().drawHUDLayers(canvas, null)
                canvas.restore()
            } else {
                LayerManager.Companion.getInstance().drawHUDLayers(canvas, null)
            }
        }

        public override fun onTouchEvent(event: MotionEvent?) {
            // TODO Auto-generated method stub
            /*
			boolean isTouched =  
					LayerManager.getInstance().onTouchLayersForOppositeZOrder(event) ||
					EasyScene.this.onTouchEvent(event) ||
					LayerManager.getInstance().onTouchLayersForNegativeZOrder(event);
			// */
//			boolean isTouched =  
//					LayerManager.getInstance().onTouchLayersForOppositeZOrder(event) ||
//					LayerManager.getInstance().onTouchLayersForNegativeZOrder(event);
//			super.onTouchEvent(event);
            if (!LayerManager.Companion.getInstance().onTouchHUDLayers(event)) {
                var isRemoteControllerCatchTouchEvent = false
                val activeRemoteController = remoteController
                if (isEnableRemoteController && activeRemoteController != null) isRemoteControllerCatchTouchEvent =
                    activeRemoteController.onTouchEvent(event)

                if (!isRemoteControllerCatchTouchEvent) this@EasyScene.onSceneTouchEvent(event)
            }
        }

        override fun addPreProcessBlock(processBlock: ProcessBlock?) {
            // TODO Auto-generated method stub
//			super.addPreProcessBlock(processBlock);
            ProcessBlockManager.Companion.getInstance()
                .setPreProcessBlock(processBlock, getLayerLevel())
        }
    }

    override fun beginContact(arg0: Contact?) {
        // TODO Auto-generated method stub
    }

    override fun endContact(arg0: Contact?) {
        // TODO Auto-generated method stub
    }

    override fun postSolve(arg0: Contact?, arg1: ContactImpulse?) {
        // TODO Auto-generated method stub

        if (arg0 == null || arg1 == null) return
        if (arg1.getNormalImpulses()[0] > 5) {
            val bodyA = arg0.getFixtureA()!!.getBody()!!
            val bodyB = arg0.getFixtureB()!!.getBody()!!
            if ((bodyA.getUserData()) is MyRect) {
                val rect = (bodyA.getUserData()) as MyRect

                if (rect.getType() == Type.stone || rect.getType() == Type.wood || rect.getType() == Type.pig || rect.getType() == Type.glass) {
                    bodyA.setUserData(null)
                }
            }

            if ((bodyB.getUserData()) is MyRect) {
                val rect = (bodyB.getUserData()) as MyRect

                if (rect.getType() == Type.stone || rect.getType() == Type.wood || rect.getType() == Type.pig || rect.getType() == Type.glass) {
                    bodyB.setUserData(null)
                }
            }
        }
    }

    override fun preSolve(arg0: Contact?, arg1: Manifold?) {
        // TODO Auto-generated method stub
    }

    companion object {
        const val RATE: Float = 40.0f
    }
}
