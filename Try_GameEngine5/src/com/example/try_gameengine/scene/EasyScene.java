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
import com.example.try_gameengine.framework.Data;
import com.example.try_gameengine.framework.GameController;
import com.example.try_gameengine.framework.GameModel;
import com.example.try_gameengine.framework.GameView;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.IMoveObserver;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.framework.TouchDispatcher;
import com.example.try_gameengine.remotecontroller.RemoteController;
import com.example.try_gameengine.scene.Scene;

public abstract class EasyScene extends Scene implements ContactListener{
//	EasyGameModel gameModel;
	Paint paint;
	protected boolean isEnablePhysical = false;
	
	private int screenW,screenH;
	
	/**Bird���A�ΥHø�e�X�p��*/
	protected Bird bird;
	
	/**touchEvent���u�ơA�קK�u���ոծ��W�c�^��*/
	byte[] lock = new byte[0];
	private final int timePause=50;
	
	/**���z�@���n��*/
//	World world;
	public LWorld world;
//	AABB aabb;  //�s����JBox2D�w�g���ݭnAABB�ϰ�F
	Vector2 gravity;
	public static final float RATE=40.0f; //���z�@�ɻP�ù������Y���C
	protected float timeStep=1f/60f;	
	
	/**�s��JBox2D�W�[���ӱ�����йB��A�ѼƧ����өx��manual�W���ѼƳ]�m�� */
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
		
		/**���O��l��*/
		gravity=new Vector2(0,-10f);
		
		/**�Ыت��z�@��*/
//		world=new World(gravity, true);
		world=new LWorld(0, 20, 1800, 1800, true, 1.0f);
		
		/**�W�[���z�@�ɤ����I����ť*/
		world.setContactListener(this);
		
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		CommonUtil.screenHeight = dm.heightPixels;
		CommonUtil.screenWidth = dm.widthPixels;
//		CommonUtil.statusBarHeight = CommonUtil.getStatusBarHeight(this);
//		CommonUtil.screenHeight -= CommonUtil.statusBarHeight;
		
		/**�o��ù��j�p*/
		this.screenW=CommonUtil.screenWidth;
		this.screenH=CommonUtil.screenHeight;
		
		/**��l�Ƥp����m*/
		AngryBirdActivity.startX=100;
		AngryBirdActivity.startY=screenH-500;
		/**��l�ƾ�ֵ�����*/
		AngryBirdActivity.touchDistance=0.2f*screenH;
		
		
		Bitmap bmpBird=BitmapUtil.redPoint;
		
		bird=new Bird(AngryBirdActivity.startX,AngryBirdActivity.startY,bmpBird.getHeight()/2f,bmpBird,Type.redBird);		

		/** �Ыإ|�P����ءA�]�m isStatic��true�A�Y�b���z�@�ɤ��O�R��A
		 * Type�]�m��ground�A�קK�Q����
		 * */
		createPolygon(5, 5, CommonUtil.screenWidth - 10, 2, true,Type.ground);
		createPolygon(5, CommonUtil.screenHeight - 10, CommonUtil.screenWidth - 10, 2, true,Type.ground);
		createPolygon(5, 5, 2, CommonUtil.screenHeight - 10, true,Type.ground);
		createPolygon(CommonUtil.screenWidth - 10, 5, 2, CommonUtil.screenHeight - 10, true,Type.ground);
		
		/**�Ы�6�Ӥ�ΡAisStatic�]�m��false�A�Y�b���z�@�ɤ��O�ʺA�A���~�O�@�μv�T */
		for(int i=0;i<6;i++)
		{
			createPolygon(screenW-250,screenH-200-20*i,20,20, false,Type.wood);
		}
		/**�Ыؤ@�Ӫ������A�]�O�ʺA�� */
		createPolygon(screenW-380,screenH-250-20*6-10,80,10, false,Type.wood);
	}
	
	/**�Ыض�Ϊ�body*/
	public Body createCircle(float x,float y,float r,boolean isStatic)
	{
		/**�]�mbody�Ϊ�*/
	    CircleShape circle = new CircleShape();
	    /**�b�|�A�n�N�ù����Ѽ���ƨ쪫�z�@�ɤ� */
	    circle.setRadius(r/RATE);
		
	    /**�]�mFixtureDef */
		FixtureDef fDef=new FixtureDef();
		if(isStatic)
		{
			/**�K�׬�0�ɡA�b���z�@�ɤ������~�O�v�T�A���R� */
			fDef.density=0;
		}
		else
		{
			/**�K�פ���0�ɡA�b���z�@�ɤ��|���~�O�v�T */
			fDef.density=1;
		}
		/**�]�m�����O�A�d�� 0��1 */
		fDef.friction=1.0f;
		/**�]�m����I�����^�_�O�A?�C���g����j�����n?*/
		fDef.restitution=0.3f;
		/**�K�[�Ϊ�*/
		fDef.shape=circle;

	    /**�]�mBodyDef */
		BodyDef bodyDef=new BodyDef();
		
		/**���B�@�w�n�]�m�A�Y��density����0�A
		 * �Y���B���Nbody.type�]�m��BodyType.DYNAMIC,�����|�R��
		 * */
		bodyDef.type=isStatic?BodyType.StaticBody:BodyType.DynamicBody;
		/**�]�mbody��m�A�n�N�ù����Ѽ���ƨ쪫�z�@�ɤ� */
		bodyDef.position.set((x)/RATE, (y)/RATE);
		
		/**�Ы�body*/
		Body body=world.createBody(bodyDef);
		
		/**�K�[ m_userData */
		body.setUserData(bird);
		
	//	body.createShape(fDef); //�ª�JBox2D���Ыؤ�k
		
		/**��body�Ы�Fixture*/
		body.createFixture(fDef); 
		
	//	body.setMassFromShapes();	//�ª�JBox2D���Ыؤ�k
		
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
	
	private void setGameView(){
		gameModel = new EasyGameModel(context, null);
//		gameView = new EasyGameModel(context, null);
	}
	
	private void setGameController(){
		gameController = new EasyGameController((Activity)context, gameModel);
	}

	@Override
	public void initGameModel() {
		// TODO Auto-generated method stub
		gameModel = new EasyGameModel(context, null);
	}
	
	@Override
	public void initGameController() {
		// TODO Auto-generated method stub
		
		gameController = new EasyGameController((Activity)context, gameModel);
	}
	
//	@Override
//	public void start() {
//		// TODO Auto-generated method stub
//		super.start();
//	}
//	
//	@Override
//	public void stop() {
//		// TODO Auto-generated method stub
//		super.stop();
//	}
	
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
	public abstract void initGameView(Activity activity, IGameController gameController,IGameModel gameModel);

//	public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
//		// TODO Auto-generated method stub
//		gameModel.setSurfaceHolder(surfaceHolder);
//	}
//
//	public void restart() {
//		// TODO Auto-generated method stub
//		gameModel.restart();
//	}
	
//	public abstract void process();
//	
//	public abstract void doDraw(Canvas canvas);
	
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
		protected void initGameView(Activity activity, IGameModel gameModel) {
			// TODO Auto-generated method stub
			EasyScene.this.initGameView(activity, this, gameModel);
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
				/**�e�X�p��*/
				bird.draw(canvas, paint);
	
				/**�p�G�p���٨S�Q�o�g�A�e�X��ʪ���ֵ��y��*/
				if(!bird.getIsReleased())
				{
					canvas.drawLine(AngryBirdActivity.startX, AngryBirdActivity.startY, bird.getX(), bird.getY(), paint);
				}
	
				/**�M�����z�@�ɡA�e�XRect */
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

		/**�I���ƥ��˴��A�ѼƬO�ոեX�Ӫ� */
		if(arg1.getNormalImpulses()[0]>5)
		{
			if ( (arg0.getFixtureA().getBody().getUserData())instanceof MyRect)
			{

				MyRect rect=(MyRect)(arg0.getFixtureA().getBody().getUserData());

				/**�u���o�X�������|�Q���� */
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

