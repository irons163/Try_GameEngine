package com.example.try_gameengine.scene;

import org.loon.framework.android.game.physics.LBody;
import org.loon.framework.android.game.physics.LWorld;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.example.try_gameengine.R;
import com.example.try_gameengine.framework.BitmapUtil;
import com.example.try_gameengine.framework.CommonUtil;
import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.Data;
import com.example.try_gameengine.framework.GameController;
import com.example.try_gameengine.framework.GameModel;
import com.example.try_gameengine.framework.GameView;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.IMoveObserver;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.framework.ProcessBlock;
import com.example.try_gameengine.framework.ProcessBlockManager;
import com.example.try_gameengine.framework.TouchDispatcher;
import com.example.try_gameengine.remotecontroller.RemoteController;
import com.example.try_gameengine.scene.Scene;

public abstract class EasyScene extends Scene implements ContactListener{
//	EasyGameModel gameModel;
	Paint paint;
	protected boolean isEnablePhysical = false;
	
	private int screenW,screenH;
	
	protected Bird bird;
	
	byte[] lock = new byte[0];
	private final int timePause=50;
	
//	World world;
	public LWorld world;
//	AABB aabb; 
	Vector2 gravity;
	public static final float RATE=40.0f; 
	protected float timeStep=1f/60f;	
	
	protected int velocityIterations = 10;	
	protected int positionIterations = 8;
	
	public enum Type {
		redBird,
		yellowBird,
		blueBird,
		pig,
		ground,
		wood,
		glass,
		stone,
		
	}
	
	PhysicsWorld physicsWorld;
	private void initP() {
		physicsWorld = new PhysicsWorld(this);
	}
	
	private void stepPhysicsAndNavigation(float deltaTime) {
		if (physicsWorld!=null && physicsWorld.isAutoStep())
			physicsWorld.update(deltaTime);
	}
	
	private void okok(){
		
	}
	
	public EasyScene(Context context, String id) {
		super(context, id);
		// TODO Auto-generated constructor stub
		setAutoAdd(true);
		initPhysical();
	}

	public EasyScene(Context context, String id, int level) {
		super(context, id, level);
		// TODO Auto-generated constructor stub
		setAutoAdd(true);
		initPhysical();
	}
	
	public EasyScene(Context context, String id, int level, int mode) {
		super(context, id, level, mode);
		// TODO Auto-generated constructor stub
		setAutoAdd(true);
		initPhysical();
	}
	
	private void initPhysical(){
		paint=new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		
		/**嚙踝蕭嚙瞌嚙踝蕭l嚙踝蕭*/
		gravity=new Vector2(0,-10f);
		
		/**嚙請建迎蕭嚙緲嚙瑾嚙踝蕭*/
//		world=new World(gravity, true);
		world=new LWorld(0, 20, 1800, 1800, true, 1.0f);
		
		/**嚙磕嚙稼嚙踝蕭嚙緲嚙瑾嚙褕歹蕭嚙踝蕭嚙瘢嚙踝蕭嚙踝蕭聽*/
		world.setContactListener(this);
		
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		CommonUtil.screenHeight = dm.heightPixels;
		CommonUtil.screenWidth = dm.widthPixels;
//		CommonUtil.statusBarHeight = CommonUtil.getStatusBarHeight(this);
//		CommonUtil.screenHeight -= CommonUtil.statusBarHeight;
		
		/**嚙緻嚙踝蕭羅嚙踝蕭j嚙緘*/
		this.screenW=CommonUtil.screenWidth;
		this.screenH=CommonUtil.screenHeight;
		
		/**嚙踝蕭l嚙複小嚙踝蕭嚙踝蕭m*/
		AngryBirdActivity.startX=100;
		AngryBirdActivity.startY=screenH-500;

		AngryBirdActivity.touchDistance=0.2f*screenH;
		
		
		Bitmap bmpBird=BitmapUtil.redPoint;
		
		bird=new Bird(AngryBirdActivity.startX,AngryBirdActivity.startY,bmpBird.getHeight()/2f,bmpBird,Type.redBird);		

		/** 嚙請建四嚙瞑嚙踝蕭嚙踝蕭堙A嚙稽嚙練 isStatic嚙踝蕭true嚙璀嚙磐嚙箭嚙踝蕭嚙緲嚙瑾嚙褕歹蕭嚙瞌嚙磋嚙踐的嚙璀
		 * Type嚙稽嚙練嚙踝蕭ground嚙璀嚙論免嚙瞋嚙踝蕭嚙踝蕭
		 * */
		createPolygon(5, 5, CommonUtil.screenWidth - 10, 2, true,Type.ground);
		createPolygon(5, CommonUtil.screenHeight - 10, CommonUtil.screenWidth - 10, 2, true,Type.ground);
		createPolygon(5, 5, 2, CommonUtil.screenHeight - 10, true,Type.ground);
		createPolygon(CommonUtil.screenWidth - 10, 5, 2, CommonUtil.screenHeight - 10, true,Type.ground);
		
		/**嚙請恬蕭6嚙諉歹蕭峞AisStatic嚙稽嚙練嚙踝蕭false嚙璀嚙磐嚙箭嚙踝蕭嚙緲嚙瑾嚙褕歹蕭嚙瞌嚙褊態嚙璀嚙踝蕭嚙羯嚙瞌嚙瑾嚙諄影嚙確 */
		for(int i=0;i<6;i++)
		{
			createPolygon(screenW-250,screenH-200-20*i,20,20, false,Type.wood);
		}
		/**嚙請建一嚙諉迎蕭嚙踝蕭嚙踝蕭嚙璀嚙稽嚙瞌嚙褊態嚙踝蕭 */
		createPolygon(screenW-380,screenH-250-20*6-10,80,10, false,Type.wood);
	}
	
	/**嚙請建塚蕭峈嚙箭ody*/
	public Body createCircle(float x,float y,float r,boolean isStatic)
	{
		/**嚙稽嚙練body嚙諄迎蕭*/
	    CircleShape circle = new CircleShape();
	    /**嚙箭嚙罵嚙璀嚙緯嚙瞇嚙衛對蕭嚙踝蕭嚙諸潘蕭嚙踝蕭鴘恬蕭z嚙瑾嚙褕歹蕭 */
	    circle.setRadius(r/RATE);
		
	    /**嚙稽嚙練FixtureDef */
		FixtureDef fDef=new FixtureDef();
		if(isStatic)
		{
			/**嚙皺嚙論穿蕭0嚙褕，嚙箭嚙踝蕭嚙緲嚙瑾嚙褕歹蕭嚙踝蕭嚙踝蕭嚙羯嚙瞌嚙緞嚙確嚙璀嚙踝蕭嚙磋嚙踐的 */
			fDef.density=0;
		}
		else
		{
			/**嚙皺嚙論歹蕭嚙踝蕭0嚙褕，嚙箭嚙踝蕭嚙緲嚙瑾嚙褕歹蕭嚙罵嚙踝蕭嚙羯嚙瞌嚙緞嚙確 */
			fDef.density=1;
		}
		/**嚙稽嚙練嚙踝蕭嚙踝蕭嚙瞌嚙璀嚙範嚙踝蕭 0嚙踝蕭1 */
		fDef.friction=1.0f;
		/**嚙稽嚙練嚙踝蕭嚙踝蕭I嚙踝蕭嚙踝蕭嚙稷嚙稻嚙瞌嚙璀?嚙瘠嚙踝蕭嚙篇嚙踝蕭嚙踝蕭j嚙踝蕭嚙踝蕭嚙緯?*/
		fDef.restitution=0.3f;
		/**嚙皺嚙稼嚙諄迎蕭*/
		fDef.shape=circle;

	    /**嚙稽嚙練BodyDef */
		BodyDef bodyDef=new BodyDef();
		
		/**嚙踝蕭嚙畿嚙瑾嚙緩嚙緯嚙稽嚙練嚙璀嚙磐嚙踝蕭density嚙踝蕭嚙踝蕭0嚙璀
		 * 嚙磐嚙踝蕭嚙畿嚙踝蕭嚙瞇body.type嚙稽嚙練嚙踝蕭BodyType.DYNAMIC,嚙踝蕭嚙踝蕭嚙罵嚙磋嚙踝蕭
		 * */
		bodyDef.type=isStatic?BodyType.StaticBody:BodyType.DynamicBody;
		/**嚙稽嚙練body嚙踝蕭m嚙璀嚙緯嚙瞇嚙衛對蕭嚙踝蕭嚙諸潘蕭嚙踝蕭鴘恬蕭z嚙瑾嚙褕歹蕭 */
		bodyDef.position.set((x)/RATE, (y)/RATE);
		
		/**嚙請恬蕭body*/
		Body body=world.createBody(bodyDef);
		
		/**嚙皺嚙稼 m_userData */
		body.setUserData(bird);
		
	//	body.createShape(fDef); //嚙蝓迎蕭JBox2D嚙踝蕭嚙請建歹蕭k
		
		/**嚙踝蕭body嚙請恬蕭Fixture*/
		body.createFixture(fDef); 
		
	//	body.setMassFromShapes();	//嚙蝓迎蕭JBox2D嚙踝蕭嚙請建歹蕭k
		
		return body;
	}
	
	public Body createPolygon(float x,float y,float width,float height,boolean isStatic,Type type)
	{
		PolygonShape polygon =new PolygonShape();
		
		polygon.setAsBox(width/2/RATE, height/2/RATE);
		
		FixtureDef fDef=new FixtureDef();
		if(isStatic)
		{
			fDef.density=0;
		}
		else
		{
			fDef.density=1;
		}
		fDef.friction=1.0f;
		fDef.restitution=0.0f;
		
		fDef.shape=polygon;

		BodyDef bodyDef=new BodyDef();
		
		bodyDef.type=isStatic?BodyType.StaticBody:BodyType.DynamicBody;//new
		
		bodyDef.position.set((x+width/2)/RATE,(y+height/2)/RATE );
		
		Body body=world.createBody(bodyDef);

		body.setUserData(new MyRect(x,y,width,height,type));
	
	//	body.createShape(polygonDef);
	//	body.setMassFromShapes();
		body.createFixture(fDef);
		
		return body;
	}

	@Override
	public void initGameModel() {
		gameModel = new EasyGameModel(context, null);
	}
	
	@Override
	public void initGameController() {
		gameController = new EasyGameController((Activity)context, gameModel);
	}
	
	public abstract GameView initGameView(Activity activity, IGameController gameController,IGameModel gameModel);
	
	// If override, need super.process().
	public void process(){
		LayerManager.getInstance().processLayersForNegativeZOrder();
		LayerManager.getInstance().processLayersForOppositeZOrder();
	}
	
	// If override, need super.doDraw(Canvas canvas).
	public void doDraw(Canvas canvas){
		LayerManager.getInstance().drawLayersForNegativeZOrder(canvas, null);
		LayerManager.getInstance().drawLayersForOppositeZOrder(canvas, null);
	}
	
	// If override, need super.onSceneTouchEvent(MotionEvent event).
	public boolean onSceneTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
//		boolean isTouched =  
//				TouchDispatcher.getInstance().onTouchEvent(event) ||
//				LayerManager.getInstance().onTouchLayersForOppositeZOrder(event) ||
//				LayerManager.getInstance().onTouchLayersForNegativeZOrder(event);
		boolean isTouched = TouchDispatcher.getInstance().onTouchEvent(event);
		return isTouched;
	}
	
	public abstract void beforeGameStart();

	public abstract void arrangeView(Activity activity);

	public abstract void setActivityContentView(Activity activity);
	
	public abstract void afterGameStart();
	
	protected void beforeGameStop(){
		//do something
	};
	
	protected void afterGameStop(){
		//do something
	};
	
	public abstract void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height);
	
	class EasyGameController extends GameController {
		
		public EasyGameController(Activity activity, IGameModel gameModel) {
			super(activity, gameModel);
			// TODO Auto-generated constructor stub
			BitmapUtil.initBitmap(context);
			BitmapUtil.initBitmapForTest();
//			remoteController = RemoteController.createRemoteController();
		}

		@Override
		protected GameView initGameView(Activity activity, IGameModel gameModel) {
			// TODO Auto-generated method stub
			return EasyScene.this.initGameView(activity, this, gameModel);
		}

		@Override
		public void arrangeView() {
			// TODO Auto-generated method stub
			EasyScene.this.arrangeView(activity);
		}

		@Override
		protected void setActivityContentView(Activity activity) {
			// TODO Auto-generated method stub
			EasyScene.this.setActivityContentView(activity);
		}
		
		@Override
		public void beforeGameStart() {
			// TODO Auto-generated method stub
			EasyScene.this.beforeGameStart();
		}

		@Override
		public void afterGameStart() {
			// TODO Auto-generated method stub
			EasyScene.this.afterGameStart();
		}
		
		@Override
		protected void beforeGameStop() {
			// TODO Auto-generated method stub
			EasyScene.this.beforeGameStop();
		}

		@Override
		protected void afterGameStop() {
			// TODO Auto-generated method stub
			EasyScene.this.afterGameStop();
		}
		
		@Override
		public void start() {
			// TODO Auto-generated method stub
			initStart(mode);		
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			EasyScene.this.surfaceChanged(holder, format, width, height);
		}
		
		@Override
		public void onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
//			boolean isRemoteControllerCatchTouchEvent = false;
//			if(isEnableRemoteController && remoteController!=null)
//				isRemoteControllerCatchTouchEvent = remoteController.onTouchEvent(event);
//			
//			if(!isRemoteControllerCatchTouchEvent)
//				super.onTouchEvent(event);
				super.onTouchEvent(event);
		}
	}

	class EasyGameModel extends GameModel {

//		public EasyGameView(Context context, IGameController gameController,
//				IGameModel gameModel) {
//			super(context, gameController, gameModel);
//			// TODO Auto-generated constructor stub
//		}

		public EasyGameModel(Context context, Data data) {
			super(context, data);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void process() {
			// TODO Auto-generated method stub
//			super.process();
			TouchDispatcher.getInstance().dispatch();
			EasyScene.this.process();
			LayerManager.getInstance().processHUDLayers();
		}
		
		@Override
		public void doDraw(Canvas canvas) {
			// TODO Auto-generated method stub
//			super.doDraw(canvas);
			/*
			LayerManager.getInstance().drawLayersForNegativeZOrder(canvas, null);
			EasyScene.this.drawSelf(canvas, null);
			LayerManager.getInstance().drawLayersForOppositeZOrder(canvas, null);
			EasyScene.this.doDraw(canvas);
			*/
			
//			LayerManager.getInstance().drawLayersForNegativeZOrder(canvas, null);
//			LayerManager.getInstance().drawLayersForOppositeZOrder(canvas, null);
			EasyScene.this.doDraw(canvas);
			
			if(isEnableRemoteController && remoteController!=null)
				remoteController.drawRemoteController(canvas, null);
			if(isEnablePhysical){
				/**嚙箴嚙碼嚙緘嚙踝蕭*/
				bird.draw(canvas, paint);
	
				/**嚙緘嚙瘦嚙緘嚙踝蕭嚙誶沒嚙瞋嚙緻嚙篇嚙璀嚙箴嚙碼嚙踝蕭坁嚙踝蕭嚙誰蛛蕭嚙緙嚙踝蕭*/
				if(!bird.getIsReleased())
				{
					canvas.drawLine(AngryBirdActivity.startX, AngryBirdActivity.startY, bird.getX(), bird.getY(), paint);
				}
	
				/**嚙瞎嚙踝蕭嚙踝蕭嚙緲嚙瑾嚙褕，嚙箴嚙碼Rect */
	//			Body body = world.getBodyList();
				for (int i = 1; i < world.getBodyCount(); i++) {
					LBody body = world.getBodyList().get(i);
					if ((body.getUserData()) instanceof MyRect) {
						MyRect rect = (MyRect) (body.getUserData());
						rect.draw(canvas, paint);
					}
	//				body = body.m_next;
				}
			}
			
			if(Config.SystemCamera.getViewPort()!=null){
				canvas.save(Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
				canvas.setMatrix(Config.SystemCamera.getViewPort().getMatrix());
				LayerManager.getInstance().drawHUDLayers(canvas, null);
				canvas.restore();
			}
			
			if(getCamera().getViewPort()!=null){
				canvas.save(Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
				canvas.setMatrix(getCamera().getViewPort().getMatrix());
				LayerManager.getInstance().drawHUDLayers(canvas, null);
				canvas.restore();
			}else{
				LayerManager.getInstance().drawHUDLayers(canvas, null);
			}
		}
		
		@Override
		public void onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			/*
			boolean isTouched =  
					LayerManager.getInstance().onTouchLayersForOppositeZOrder(event) ||
					EasyScene.this.onTouchEvent(event) ||
					LayerManager.getInstance().onTouchLayersForNegativeZOrder(event);
			*/
//			boolean isTouched =  
//					LayerManager.getInstance().onTouchLayersForOppositeZOrder(event) ||
//					LayerManager.getInstance().onTouchLayersForNegativeZOrder(event);
//			super.onTouchEvent(event);
			if(!LayerManager.getInstance().onTouchHUDLayers(event)){
				boolean isRemoteControllerCatchTouchEvent = false;
				if(isEnableRemoteController && remoteController!=null)
					isRemoteControllerCatchTouchEvent = remoteController.onTouchEvent(event);
				
				if(!isRemoteControllerCatchTouchEvent)
					EasyScene.this.onSceneTouchEvent(event);
			}
		}
		
		@Override
		public void addPreProcessBlock(ProcessBlock processBlock) {
			// TODO Auto-generated method stub
//			super.addPreProcessBlock(processBlock);
			ProcessBlockManager.getInstance().setPreProcessBlock(processBlock, getLayerLevel());
		}
		
		
	}
	
	public boolean isEnablePhysical() {
		return isEnablePhysical;
	}

	public void setEnablePhysical(boolean isEnablePhysical) {
		this.isEnablePhysical = isEnablePhysical;
	}

	@Override
	public void beginContact(Contact arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub

		if(arg1.getNormalImpulses()[0]>5)
		{
			if ( (arg0.getFixtureA().getBody().getUserData())instanceof MyRect)
			{

				MyRect rect=(MyRect)(arg0.getFixtureA().getBody().getUserData());

				if(rect.getType()==Type.stone
				||rect.getType()==Type.wood
				||rect.getType()==Type.pig
				||rect.getType()==Type.glass)
				{
					arg0.getFixtureA().getBody().setUserData(null);
				}
			}
			
			if ( (arg0.getFixtureB().getBody().getUserData())instanceof MyRect)
			{
				
				MyRect rect=(MyRect)(arg0.getFixtureB().getBody().getUserData());

				if(rect.getType()==Type.stone
				||rect.getType()==Type.wood
				||rect.getType()==Type.pig
				||rect.getType()==Type.glass)
				{
					arg0.getFixtureB().getBody().setUserData(null);
				}
			}
		
		}
	
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}
}

