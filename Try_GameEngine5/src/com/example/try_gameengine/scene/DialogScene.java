package com.example.try_gameengine.scene;

import com.example.try_gameengine.framework.GameView;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.stage.BaseStage;
import com.example.try_gameengine.stage.Stage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

public class DialogScene extends EasyScene{
	private boolean isNeedStopCurrentActiveScene = true;

	public DialogScene(Context context, String id) {
		super(context, id);
		// TODO Auto-generated constructor stub
		mode = RESUME;
		isEnableRemoteController = false;
	}
	
	public DialogScene(Context context, String id, int level) {
		super(context, id, level);
		// TODO Auto-generated constructor stub
		mode = RESUME;
		isEnableRemoteController = false;
	}
	
	public DialogScene(Context context, String id, int level, int mode) {
		super(context, id, level, mode);
		// TODO Auto-generated constructor stub
		isEnableRemoteController = false;
	}
	
	GameView gameview;
	@Override
	public void initGameView(final Activity activity, IGameController gameController,
			IGameModel gameModel) {
		// TODO Auto-generated method stub
		class MyGameView extends GameView{

			public MyGameView(Context context, IGameController gameController,
					IGameModel gameModel) {
				super(context, gameController, gameModel);
				// TODO Auto-generated constructor stub
				setZOrderOnTop(true);    // necessary
				SurfaceHolder sfhTrackHolder = getHolder();
				sfhTrackHolder.setFormat(PixelFormat.TRANSPARENT);
//				activity.getWindow().setFormat(PixelFormat.TRANSPARENT);
			}
			
			@Override
			public boolean dispatchTouchEvent(MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		}
		
		gameview = new MyGameView(activity, gameController, gameModel);
//		gameview.surfaceCreated(gameview.getHolder());
		
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		dialogSceneDrawListener.draw(canvas);
	}

	@Override
	public void beforeGameStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrangeView(Activity activity) {
		// TODO Auto-generated method stub

	}
	
	private boolean checkContentViewExist(View parent) {
		boolean isExsit = false;
	    if (parent instanceof ViewGroup) {
	        ViewGroup group = (ViewGroup)parent;
	        for (int i = 0; i < group.getChildCount(); i++){
	        	isExsit = checkContentViewExist(group.getChildAt(i));
	        	if(isExsit)
	        		break;
	        }
	    }else{
			if(parent.equals(gameview)){
				isExsit = true;
			}
	    }
	    return isExsit;
	}
	
	private boolean removeContentView(View parent) {
		boolean isExsit = false;
	    if (parent instanceof ViewGroup) {
	        ViewGroup group = (ViewGroup)parent;
	        for (int i = 0; i < group.getChildCount(); i++){
	        	isExsit = checkContentViewExist(group.getChildAt(i));
	        	if(isExsit){
	        		group.removeView(gameview);
	        		break;
	        	}
	        }
	    }else{
			if(parent.equals(gameview)){
				isExsit = true;
			}
	    }
	    return isExsit;
	}

	@Override
	public void setActivityContentView(Activity activity) {
		// TODO Auto-generated method stub
		//		activity.addContentView(gameview, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 
		//		ViewGroup.LayoutParams.MATCH_PARENT));
		boolean isExsit = false;
		//ViewGroup view = (ViewGroup)activity.getWindow().getDecorView();
		//for(int i = 0; i < view.getChildCount(); i++){
		//	View v = view.getChildAt(i);
		//	if(v.equals(this)){
		//		isExsit = true;
		//		break;
		//	}
		//}
		isExsit = checkContentViewExist(activity.getWindow().getDecorView());
		if(!isExsit){
			activity.getWindow().addContentView(gameview, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 
				ViewGroup.LayoutParams.MATCH_PARENT));
		}
	}

	@Override
	public void afterGameStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public interface DialogSceneDrawListener{
		public void draw(Canvas canvas);
	}
	
	private DialogSceneDrawListener dialogSceneDrawListener = new DialogSceneDrawListener() {
		
		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public void setDialogSceneDraw(DialogSceneDrawListener dialogSceneDrawListener){
		this.dialogSceneDrawListener = dialogSceneDrawListener;
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
		
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
//		super.finish();
		Stage s = ((Stage)context);
		s.getSceneManager().removeScene(this);
		removeContentView(((Activity)context).getWindow().getDecorView());
	}
	
	public void setIsNeedToStopTheActiveScene(boolean isNeedStopCurrentActiveScene){
		this.isNeedStopCurrentActiveScene = isNeedStopCurrentActiveScene;
	}
	
	public boolean getIsNeedToStopTheActiveScene(){
		return isNeedStopCurrentActiveScene;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		dialogSceneTouchListener.onTouchEvent(event);
		return true;
	}
	
	public interface DialogSceneTouchListener{
		public void onTouchEvent(MotionEvent event);
	}
	
	private DialogSceneTouchListener dialogSceneTouchListener = new DialogSceneTouchListener() {
		
		@Override
		public void onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public void setDialogSceneTouchListener(DialogSceneTouchListener dialogSceneTouchListener){
		this.dialogSceneTouchListener = dialogSceneTouchListener;
	}
}
