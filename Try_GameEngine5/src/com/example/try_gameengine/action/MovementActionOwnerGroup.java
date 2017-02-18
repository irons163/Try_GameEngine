package com.example.try_gameengine.action;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.framework.Sprite;

public class MovementActionOwnerGroup {
	String id;
	List<MovementAction> movementActions = new ArrayList<MovementAction>();
	List<Sprite> sprites = new ArrayList<Sprite>();
	List<Block> startBlocks = new ArrayList<Block>();
	List<Block> finishBlocks = new ArrayList<Block>();
	OnGroupListener innerOnGroupListener, onGroupListener;
	int startCount, finishCount;
	boolean isAutoResetAfterLastFinish = true;
	
	public interface Block{
		public void runBlock();
	}
	
	public MovementActionOwnerGroup(String id) {
		// TODO Auto-generated constructor stub
		this.id = id;

		innerOnGroupListener = new OnGroupListener() {
			
			@Override
			public void onStart(int startIndex) {
				// TODO Auto-generated method stub
				onGroupListener.onStart(startIndex);
			}
			
			@Override
			public void onLastStart() {
				// TODO Auto-generated method stub
				onGroupListener.onLastStart();
			}
			
			@Override
			public void onLastFinish() {
				// TODO Auto-generated method stub
				onGroupListener.onLastFinish();
			}
			
			@Override
			public void onFirstStart() {
				// TODO Auto-generated method stub
				onGroupListener.onFirstStart();
			}
			
			@Override
			public void onFirstFinish() {
				// TODO Auto-generated method stub
				onGroupListener.onFirstFinish();
			}
			
			@Override
			public void onFinish(int finishIndex) {
				// TODO Auto-generated method stub
				onGroupListener.onFinish(finishIndex);
				
				
			}
		};
	}
	
	public MovementActionOwnerGroup() {
		// TODO Auto-generated constructor stub

		innerOnGroupListener = new OnGroupListener() {
			
			@Override
			public void onStart(int startIndex) {
				// TODO Auto-generated method stub
				Block block = startBlocks.get(startIndex);
				if(block!=null)
					block.runBlock();
				
				onGroupListener.onStart(startIndex);
			}
			
			@Override
			public void onLastStart() {
				// TODO Auto-generated method stub
				onGroupListener.onLastStart();
			}
			
			@Override
			public void onLastFinish() {
				// TODO Auto-generated method stub
				onGroupListener.onLastFinish();
			}
			
			@Override
			public void onFirstStart() {
				// TODO Auto-generated method stub
				onGroupListener.onFirstStart();
			}
			
			@Override
			public void onFirstFinish() {
				// TODO Auto-generated method stub
				onGroupListener.onFirstFinish();
			}
			
			@Override
			public void onFinish(int finishIndex) {
				// TODO Auto-generated method stub
				Block block = finishBlocks.get(finishIndex);
				if(block!=null)
					block.runBlock();
				
				if(finishIndex!=movementActions.size()-1)
					run();
				onGroupListener.onFinish(finishIndex);
			}
		};
	}
	
	public void run(Sprite defaultSprite){
		MovementAction action = movementActions.get(startCount);
		if(action==null)
			return;
		Sprite sprite = sprites.get(startCount);
		if(sprite!=null)
			sprite.runMovementAction(action);
		else if(defaultSprite!=null)
			defaultSprite.runMovementAction(action);
		else {
			action.initTimer();
			action.start();
		}
	}
	
	public void run(){
		run(null);
	}
	
	public void addMovementAction(Sprite sprite, MovementAction action, Block startBlock, Block finishBlock) {
		// TODO Auto-generated constructor stub
		sprites.add(sprite);
		action = MAction2.sequence(new MovementAction[]{action});
		movementActions.add(action);
		
		action.setActionListener(new IActionListener() {
			
			@Override
			public void beforeChangeFrame(int nextFrameId) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterChangeFrame(int periousFrameId) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void actionStart() {
				// TODO Auto-generated method stub
				if(startCount==0)
					innerOnGroupListener.onFirstStart();
				innerOnGroupListener.onStart(startCount);
				if(startCount==movementActions.size()-1){
					innerOnGroupListener.onLastStart();
					startCount=0;
				}else{
					startCount++;
				}				
			}
			
			@Override
			public void actionFinish() {
				// TODO Auto-generated method stub
				if(finishCount==0)
					innerOnGroupListener.onFirstFinish();
				innerOnGroupListener.onFinish(finishCount);
				Log.e(MovementActionOwnerGroup.class.getName(), "finishCount: "+finishCount+"");
				Log.e(MovementActionOwnerGroup.class.getName(), "movementActions size: " + movementActions.size()+"");
				if(finishCount==movementActions.size()-1){
					innerOnGroupListener.onLastFinish();
					if(isAutoResetAfterLastFinish){
						movementActions.clear();
						finishCount=0;
					}
				}else{
					finishCount++;
				}
				
			}
			
			@Override
			public void actionCycleFinish() {
				// TODO Auto-generated method stub
			}
		});
		
		startBlocks.add(startBlock);
		finishBlocks.add(finishBlock);
	}
	
	public void setMovementActionListener2(Sprite sprite, MovementAction action, final IActionListener actionListener){
		action = MAction2.sequence(new MovementAction[]{action});
		
		action.setActionListener(new IActionListener() {
			
			@Override
			public void beforeChangeFrame(int nextFrameId) {
				// TODO Auto-generated method stub
				actionListener.beforeChangeFrame(nextFrameId);
			}
			
			@Override
			public void afterChangeFrame(int periousFrameId) {
				// TODO Auto-generated method stub
				actionListener.afterChangeFrame(periousFrameId);
			}
			
			@Override
			public void actionStart() {
				// TODO Auto-generated method stub
				actionListener.actionStart();
				
				if(startCount==0)
					onGroupListener.onFirstStart();
				onGroupListener.onStart(startCount);
				if(startCount==movementActions.size()-1){
					onGroupListener.onLastStart();
					startCount=0;
				}else{
					startCount++;
				}				
			}
			
			@Override
			public void actionFinish() {
				// TODO Auto-generated method stub
				actionListener.actionFinish();
				
				if(finishCount==0)
					onGroupListener.onFirstFinish();
				onGroupListener.onFinish(finishCount);
				Log.e(MovementActionOwnerGroup.class.getName(), "finishCount: "+finishCount+"");
				Log.e(MovementActionOwnerGroup.class.getName(), "movementActions size: " + movementActions.size()+"");
				if(finishCount==movementActions.size()-1){
					onGroupListener.onLastFinish();
					if(isAutoResetAfterLastFinish){
						movementActions.clear();
						finishCount=0;
					}
				}else{
					finishCount++;
				}
				
			}
			
			@Override
			public void actionCycleFinish() {
				// TODO Auto-generated method stub
				actionListener.actionCycleFinish();
			}
		});
		
		sprite.runMovementAction(action);
	}
	
	
	public void addMovementAction3(Sprite sprite, MovementAction action){
		action = MAction2.sequence(new MovementAction[]{action});
		
		action.setActionListener(new IActionListener() {
			
			@Override
			public void beforeChangeFrame(int nextFrameId) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterChangeFrame(int periousFrameId) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void actionStart() {
				// TODO Auto-generated method stub
				if(startCount==0)
					onGroupListener.onFirstStart();
				onGroupListener.onStart(startCount);
				if(startCount==movementActions.size()-1){
					onGroupListener.onLastStart();
					startCount=0;
				}else{
					startCount++;
				}				
			}
			
			@Override
			public void actionFinish() {
				// TODO Auto-generated method stub
				if(finishCount==0)
					onGroupListener.onFirstFinish();
				onGroupListener.onFinish(finishCount);
				Log.e(MovementActionOwnerGroup.class.getName(), "finishCount: "+finishCount+"");
				Log.e(MovementActionOwnerGroup.class.getName(), "movementActions size: " + movementActions.size()+"");
				if(finishCount==movementActions.size()-1){
					onGroupListener.onLastFinish();
					if(isAutoResetAfterLastFinish){
						movementActions.clear();
						finishCount=0;
					}
				}else{
					finishCount++;
				}
				
			}
			
			@Override
			public void actionCycleFinish() {
				// TODO Auto-generated method stub
			}
		});
		
		sprite.runMovementAction(action);
	}
	
	public void addMovementAction(String id, MovementAction movementAction) {
		// TODO Auto-generated constructor stub
		this.id = id;
		movementActions.add(movementAction);
	}
	
	public void setOnGroupListener(OnGroupListener onGroupListener){
		this.onGroupListener = onGroupListener;
	}
	
	public void setMovementActionListener(MovementAction action, final IActionListener actionListener){
		action.setActionListener(new IActionListener() {
			
			@Override
			public void beforeChangeFrame(int nextFrameId) {
				// TODO Auto-generated method stub
				actionListener.beforeChangeFrame(nextFrameId);
			}
			
			@Override
			public void afterChangeFrame(int periousFrameId) {
				// TODO Auto-generated method stub
				actionListener.afterChangeFrame(periousFrameId);
			}
			
			@Override
			public void actionStart() {
				// TODO Auto-generated method stub
				actionListener.actionStart();
				
				if(startCount==0)
					onGroupListener.onFirstStart();
				onGroupListener.onStart(startCount);
				if(startCount==movementActions.size()-1){
					onGroupListener.onLastStart();
					startCount=0;
				}else{
					startCount++;
				}				
			}
			
			@Override
			public void actionFinish() {
				// TODO Auto-generated method stub
				actionListener.actionFinish();
				
				if(finishCount==0)
					onGroupListener.onFirstFinish();
				onGroupListener.onFinish(finishCount);
				Log.e(MovementActionOwnerGroup.class.getName(), "finishCount: "+finishCount+"");
				Log.e(MovementActionOwnerGroup.class.getName(), "movementActions size: " + movementActions.size()+"");
				if(finishCount==movementActions.size()-1){
					onGroupListener.onLastFinish();
					if(isAutoResetAfterLastFinish){
						movementActions.clear();
						finishCount=0;
					}
				}else{
					finishCount++;
				}
				
			}
			
			@Override
			public void actionCycleFinish() {
				// TODO Auto-generated method stub
				actionListener.actionCycleFinish();
			}
		});
	}
	
	public void removeMovementAction(MovementAction action){
		movementActions.remove(action);
	}
	
	public void startAll(){
		for(int i = 0; i<movementActions.size(); i++){
			MovementAction action = movementActions.get(i);
			action.start();
		}
	}
	
	public void start(MovementAction action){
//		startCount--;
//		finishCount--;
		action.start();
	}
	
	public void stop(MovementAction action){
		action.controller.cancelAllMove();
	}
	
	public void stopAll(){
		for(int i = 0; i<movementActions.size(); i++){
			MovementAction action = movementActions.get(i);
			action.controller.cancelAllMove();
		}
	}
	
	public void clear(){
		movementActions.clear();
		startCount=0;
		finishCount=0;
	}
	
	public void reset(){
		for(MovementAction action : movementActions){
//			action.controller.do;
		}
		startCount=0;
		finishCount=0;
	}
	
	public void setAutoResetAfterLastFinish(boolean isAutoResetAfterLastFinish ){
		this.isAutoResetAfterLastFinish = isAutoResetAfterLastFinish;
	}
	
	public interface OnGroupListener{
		public void onFirstStart();
		public void onLastStart();
		public void onStart(int startIndex);
		public void onFirstFinish();
		public void onLastFinish();
		public void onFinish(int finishIndex);
	}
}
