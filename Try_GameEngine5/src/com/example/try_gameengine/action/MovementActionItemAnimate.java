package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.graphics.Bitmap;

import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.LightImage;

/**
 * {@code MovementActionItemAnimate} is a movement action for animate bitmaps.
 * @author irons
 *
 */
public class MovementActionItemAnimate extends MovementAction{ 
	long millisTotal;
	long millisDelay;
	MovementActionInfo info;
	long resumeTotal;
	long resetTotal;	
	public String name;	
	private long updateTime;	
	public int frameIdx;	
	public boolean isStop = false;
	public boolean isCycleFinish = false;	
	boolean triggerEnable = false;	
	long[] frameTimes;
	int resumeFrameIndex;
	int resumeFrameCount;	
	long pauseFrameNum;
	int pauseFrameCounter;	
	FrameTrigger nextframeTrigger;
	private long lastTriggerFrameNum;
	private boolean isEnableSetSpriteAction = true;
	private Bitmap[] bitmapFrames;
	private LightImage[] lightImageFrames;
	private int[] frameTriggerTimes; 
	private float scale;
	
	/**
	 * constructor.
	 * @param bitmapFrames
	 * 			bitmapFrames for animate.
	 * @param secondPerOneTime
	 * 			secondPerOneTime is one game loop process interval how much seconds.
	 */
	public MovementActionItemAnimate(Bitmap[] bitmapFrames, float secondPerOneTime){
		this((long) (secondPerOneTime*1000/(1000.0f/Config.fps))*bitmapFrames.length, (long) (secondPerOneTime*1000/(1000.0f/Config.fps)), bitmapFrames, null, 1.0f, "MovementActionItemAnimate");
	}
	
	/**
	 * constructor.
	 * @param millisTotal
	 * 			milliseconds for whole action running.
	 * @param bitmapFrames
	 * 			bitmapFrames for animate.
	 * @param frameTriggerTimes
	 * 			an array of frames display process times, when trigger enough times, go next frame.
	 * 			
	 */
	public MovementActionItemAnimate(long millisTotal, Bitmap[] bitmapFrames, int[] frameTriggerTimes){
		this((long) (millisTotal/(1000.0f/Config.fps)), 1, bitmapFrames, frameTriggerTimes, 1.0f, "MovementActionItemAnimate");
	}
	
	/**
	 * constructor.
	 * @param millisTotal
	 * 			milliseconds for whole action running.
	 * @param bitmapFrames
	 * 			bitmapFrames for animate.
	 * @param frameTriggerTimes
	 * 			an array of frames display by process times, when trigger enough times, go next frame.
	 * @param scale
	 * 			scale.
	 * 			
	 */
	public MovementActionItemAnimate(long millisTotal, Bitmap[] bitmapFrames, int[] frameTriggerTimes, float scale){
		this((long) (millisTotal/(1000.0f/Config.fps)), 1, bitmapFrames, frameTriggerTimes, scale, "MovementActionItemAnimate");
	}
	
	/**
	 * constructor.
	 * @param triggerTotal
	 * 			trigger(process) times for whole action running.
	 * @param triggerInterval
	 * 			frames display process times, when trigger enough times, go next frame.
	 * @param bitmapFrames
	 * 			bitmapFrames for animate.
	 * @param frameTriggerTimes
	 * 			an array of frames display by process times, when trigger enough times, go next frame.
	 */
	public MovementActionItemAnimate(long triggerTotal, long triggerInterval, Bitmap[] bitmapFrames, int[] frameTriggerTimes){
		this(triggerTotal, triggerTotal, bitmapFrames, frameTriggerTimes, 1.0f, "MovementActionItemAnimate");
	}
	
	/**
	 * constructor.
	 * @param triggerTotal
	 * 			trigger(process) times for whole action running.
	 * @param triggerInterval
	 * 			frames display process times, when trigger enough times, go next frame.
	 * @param bitmapFrames
	 * 			bitmapFrames for animate.
	 * @param frameTriggerTimes
	 * 			an array of frames display by process times, when trigger enough times, go next frame.
	 * @param scale
	 * 			scale
	 */
	public MovementActionItemAnimate(long triggerTotal, long triggerInterval, Bitmap[] bitmapFrames, int[] frameTriggerTimes, float scale){
		this(triggerTotal, triggerInterval, bitmapFrames, frameTriggerTimes, scale, "MovementActionItemAnimate");
	}
	
	/**
	 * constructor.
	 * @param triggerTotal
	 * 			trigger(process) times for whole action running.
	 * @param triggerInterval
	 * 			frames display process times, when trigger enough times, go next frame.
	 * @param bitmapFrames
	 * 			bitmapFrames for animate.
	 * @param frameTriggerTimes
	 * 			an array of frames display by process times, when trigger enough times, go next frame.
	 * @param scale
	 * 			scale
	 * @param description
	 * 			description for this movement action.
	 */
	public MovementActionItemAnimate(long triggerTotal, long triggerInterval, Bitmap[] bitmapFrames, int[] frameTriggerTimes, float scale, String description){
		this.millisTotal = triggerTotal;
		this.millisDelay = triggerInterval;
		this.description = description + ",";
		this.bitmapFrames = bitmapFrames;
		if(frameTriggerTimes == null){
			frameTriggerTimes = new int[bitmapFrames.length];
			Arrays.fill(frameTriggerTimes, (int) triggerInterval);
		}
		this.frameTriggerTimes = frameTriggerTimes;
		this.scale = scale;
//		movementItemList.add(this);
		info = new MovementActionInfo(millisTotal, millisDelay, 0, 0);
		info.setSpriteActionName(description);
	}
	
	/**
	 * constructor.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param secondPerOneTime
	 * 			secondPerOneTime is one game loop process interval how much seconds.
	 */
	public MovementActionItemAnimate(LightImage[] lightImageFrames, float secondPerOneTime){
		this((long) (secondPerOneTime*1000/(1000.0f/Config.fps))*lightImageFrames.length, (long) (secondPerOneTime*1000/(1000.0f/Config.fps)), lightImageFrames, null, 1.0f, "MovementActionItemAnimate");
	}
	
	/**
	 * constructor.
	 * @param millisTotal
	 * 			milliseconds for whole action running.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param frameTriggerTimes
	 * 			an array of frames display by process times, when trigger enough times, go next frame.
	 */
	public MovementActionItemAnimate(long millisTotal, LightImage[] lightImageFrames, int[] frameTriggerTimes){
		this((long) (millisTotal/(1000.0f/Config.fps)), 1, lightImageFrames, frameTriggerTimes, 1.0f, "MovementActionItemAnimate");
	}
	
	/**
	 * constructor.
	 * @param millisTotal
	 * 			milliseconds for whole action running.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param frameTriggerTimes
	 * 			an array of frames display by process times, when trigger enough times, go next frame.
	 * @param scale
	 * 			scale.
	 */
	public MovementActionItemAnimate(long millisTotal, LightImage[] lightImageFrames, int[] frameTriggerTimes, float scale){
		this((long) (millisTotal/(1000.0f/Config.fps)), 1, lightImageFrames, frameTriggerTimes, scale, "MovementActionItemAnimate");
	}
	
	/**
	 * constructor.
	 * @param triggerTotal
	 * 			trigger(process) times for whole action running.
	 * @param triggerInterval
	 * 			frames display process times, when trigger enough times, go next frame.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param frameTriggerTimes
	 * 			an array of frames display by process times, when trigger enough times, go next frame.
	 */
	public MovementActionItemAnimate(long triggerTotal, long triggerInterval, LightImage[] lightImageFrames, int[] frameTriggerTimes){
		this(triggerTotal, triggerTotal, lightImageFrames, frameTriggerTimes, 1.0f, "MovementActionItemAnimate");
	}
	
	/**
	 * constructor.
	 * @param triggerTotal
	 * 			trigger(process) times for whole action running.
	 * @param triggerInterval
	 * 			frames display process times, when trigger enough times, go next frame.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param frameTriggerTimes
	 * 			an array of frames display by process times, when trigger enough times, go next frame.
	 * @param scale
	 * 			scale.
	 * 			
	 */
	public MovementActionItemAnimate(long triggerTotal, long triggerInterval, LightImage[] lightImageFrames, int[] frameTriggerTimes, float scale){
		this(triggerTotal, triggerInterval, lightImageFrames, frameTriggerTimes, scale, "MovementActionItemAnimate");
	}
	
	/**
	 * constructor.
	 * @param triggerTotal
	 * 			trigger(process) times for whole action running.
	 * @param triggerInterval
	 * 			trigger(process) times for whole action running.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param frameTriggerTimes
	 * 			an array of frames display by process times, when trigger enough times, go next frame.
	 * @param scale
	 * 			scale.
	 * @param description
	 * 			description of this movement action.
	 */
	public MovementActionItemAnimate(long triggerTotal, long triggerInterval, LightImage[] lightImageFrames, int[] frameTriggerTimes, float scale, String description){
		this.millisTotal = triggerTotal;
		this.millisDelay = triggerInterval;
		this.description = description + ",";
		this.lightImageFrames = lightImageFrames;
		this.bitmapFrames = new Bitmap[lightImageFrames.length];
		
		if(frameTriggerTimes == null){
			frameTriggerTimes = new int[lightImageFrames.length];
			Arrays.fill(frameTriggerTimes, (int) triggerInterval);
		}
		
		this.frameTriggerTimes = frameTriggerTimes;
		
		this.scale = scale;
//		movementItemList.add(this);
		info = new MovementActionInfo(millisTotal, millisDelay, 0, 0);
		info.setSpriteActionName(description);
	}
	
	@Override
	public void start() {
		resumeFrameIndex = 0;
		resumeFrameCount = 0;
		pauseFrameNum = 0;
		pauseFrameCounter = 0;
		isStop = false;
		isCycleFinish = false;
		
		info.getSprite().addActionFPS(info.getSpriteActionName(), bitmapFrames, frameTriggerTimes, scale, isLoop, new com.example.try_gameengine.framework.IActionListener() {
			
			@Override
			public void beforeChangeFrame(int nextFrameId) {
				if(lightImageFrames!=null)
					info.getSprite().setLightImage(lightImageFrames[nextFrameId]);
			}
			
			@Override
			public void afterChangeFrame(int periousFrameId) {
				
			}
			
			@Override
			public void actionFinish() {
				isStop = true;
				doReset();	
				triggerEnable = false;
				
				if(actionListener!=null)
					actionListener.actionFinish();
				
				synchronized (MovementActionItemAnimate.this) {
					MovementActionItemAnimate.this.notifyAll();
				}
			}
		});
		
		if(!isEnableSetSpriteAction)
			isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat;
		if(info.getSprite()!=null && isEnableSetSpriteAction)
			info.getSprite().setAction(info.getSpriteActionName());
		
		triggerEnable = true;
		isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat;
	}

	/**
	 * 
	 * @author irons
	 *
	 */
	public interface FrameTrigger{
		public void trigger();
	}
	
	@Override
	public void trigger(){
		if(triggerEnable && pauseFrameCounter==pauseFrameNum){
			
			pauseFrameNum = 0;
			pauseFrameCounter = 0;
			myTrigger.trigger();
		}else if(triggerEnable){
			pauseFrameCounter++;
		}else{
			pauseFrameNum = 0;
			pauseFrameCounter = 0;
		}
	}
	
	/**
	 * 
	 */
	FrameTrigger myTrigger = new FrameTrigger() {
		
		@Override
		public void trigger() {
			// TODO Auto-generated method stub
			frameTriggerFPSStart();
			
		}
	};
	
	/**
	 * 
	 * @param nextframeTrigger
	 * @return
	 */
	public FrameTrigger setNextFrameTrigger(FrameTrigger nextframeTrigger){
		
		this.nextframeTrigger = nextframeTrigger;
		
		return myTrigger;
	}
	
	@Override
	public void setActionListener(IActionListener actionListener){
		this.actionListener = actionListener;
	}

	private void frameTriggerFPSStart(){

	}

	@Override
	protected MovementAction initTimer(){ super.initTimer();
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		
		resumeFrameIndex = 0;
		return this;
	}
	
	/**
	 * reset action.
	 */
	private void doReset(){
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
	}
	
	@Override
	public MovementAction getAction(){
		return this;
	}
	
	@Override
	public List<MovementAction> getActions(){
		return actions;
	}

	@Override
	public MovementActionInfo getInfo() {
		return info;
	}

	@Override
	public void setInfo(MovementActionInfo info) {
		this.info = info;
	}
	
	@Override
	public List<MovementAction> getCurrentActionList() {
		List<MovementAction> actions = new ArrayList<MovementAction>();
		actions.add(this);
		return actions;
	}
	
	@Override
	public List<MovementActionInfo> getCurrentInfoList() {
		List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
		infos.add(this.info);
		currentInfoList.add(this.info);
		return infos;
	}
	
	@Override
	public List<MovementActionInfo> getMovementInfoList() {
		List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
		infos.add(this.info);
		return infos;
	}
	
	@Override
	public void cancelMove(){
		isStop = true;
		synchronized (MovementActionItemAnimate.this) {
			MovementActionItemAnimate.this.notifyAll();
		}
	}
	
	@Override
	void pause(){	
		pauseFrameNum = millisDelay;
	}
	
	@Override
	public boolean isFinish(){
		return isStop;
	}
	
//	@Override
//	public IMovementActionMemento createMovementActionMemento(){
//		movementActionMemento = new MovementActionItemBaseReugularFPSMementoImpl(actions, thread, timerOnTickListener, name, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCycleFinish, isCycleFinish, isCycleFinish, isCycleFinish, name, cancelAction, millisTotal, millisDelay, info, resumeTotal, resetTotal, name, updateTime, frameIdx, isStop, isCycleFinish, triggerEnable, frameTimes, resumeFrameIndex, resumeFrameCount, pauseFrameNum, pauseFrameCounter, nextframeTrigger, lastTriggerFrameNum);
//		if(this.info!=null){
//			this.info.createIMovementActionInfoMemento();
//		}
//		return movementActionMemento;
//	}
//	
//	@Override
//	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
//		super.restoreMovementActionMemento(this.movementActionMemento);
//		MovementActionItemBaseReugularFPSMementoImpl mementoImpl = (MovementActionItemBaseReugularFPSMementoImpl) this.movementActionMemento;
//		this.millisTotal = mementoImpl.millisTotal;
//		this.millisDelay = mementoImpl.millisDelay;
//		this.info = mementoImpl.info;
//		this.resumeTotal = mementoImpl.resumeTotal;
//		this.resetTotal = mementoImpl.resetTotal;
//		this.name = mementoImpl.name;
//		this.updateTime = mementoImpl.updateTime;
//		this.frameIdx = mementoImpl.frameIdx;
//		this.isStop = mementoImpl.isStop;
//		this.isCycleFinish = mementoImpl.isCycleFinish;
//		this.triggerEnable = mementoImpl.triggerEnable;
//		this.frameTimes = mementoImpl.frameTimes;
//		this.resumeFrameIndex = mementoImpl.resumeFrameIndex;
//		this.resumeFrameCount = mementoImpl.resumeFrameCount;
//		this.pauseFrameNum = mementoImpl.pauseFrameNum;
//		this.pauseFrameCounter = mementoImpl.pauseFrameCounter;
//		this.nextframeTrigger = mementoImpl.nextframeTrigger;
//		this.lastTriggerFrameNum = mementoImpl.lastTriggerFrameNum;
//		
//		if(this.info!=null){
//			this.info.restoreMovementActionMemento(null);
//		}
//		doReset();
//	}
//	
//	protected static class MovementActionItemBaseReugularFPSMementoImpl extends MovementActionMementoImpl{
//		long millisTotal;
//		long millisDelay;
//		MovementActionInfo info;
//		long resumeTotal;
//		long resetTotal;	
//		public String name;	
//		private long updateTime;	
//		public int frameIdx;	
//		public boolean isStop = false;
//		public boolean isCycleFinish = false;	
//		boolean triggerEnable = false;	
//		long[] frameTimes;
//		int resumeFrameIndex;
//		int resumeFrameCount;	
//		long pauseFrameNum;
//		int pauseFrameCounter;	
//		FrameTrigger nextframeTrigger;
//		private long lastTriggerFrameNum;
//		
//		public MovementActionItemBaseReugularFPSMementoImpl(
//				List<MovementAction> actions, Thread thread,
//				TimerOnTickListener timerOnTickListener, String description,
//				List<MovementAction> copyMovementActionList,
//				List<MovementActionInfo> currentInfoList,
//				List<MovementAction> movementItemList,
//				List<MovementAction> totalCopyMovementActionList,
//				boolean isCancelFocusAppendPart, boolean isFinish,
//				boolean isLoop, boolean isSigleThread, String name,
//				MovementAction cancelAction,
//				long millisTotal,
//				long millisDelay, MovementActionInfo info,
//				long resumeTotal, long resetTotal, String name2,
//				long updateTime, int frameIdx, boolean isStop,
//				boolean isCycleFinish, boolean triggerEnable,
//				long[] frameTimes, int resumeFrameIndex, int resumeFrameCount,
//				long pauseFrameNum, int pauseFrameCounter,
//				FrameTrigger nextframeTrigger, long lastTriggerFrameNum) {
//			super(actions, thread, timerOnTickListener, description,
//					copyMovementActionList, currentInfoList, movementItemList,
//					totalCopyMovementActionList, isCancelFocusAppendPart,
//					isFinish, isLoop, isSigleThread, name, cancelAction);
//			this.millisTotal = millisTotal;
//			this.millisDelay = millisDelay;
//			this.info = info;
//			this.resumeTotal = resumeTotal;
//			this.resetTotal = resetTotal;
//			name = name2;
//			this.updateTime = updateTime;
//			this.frameIdx = frameIdx;
//			this.isStop = isStop;
//			this.isCycleFinish = isCycleFinish;
//			this.triggerEnable = triggerEnable;
//			this.frameTimes = frameTimes;
//			this.resumeFrameIndex = resumeFrameIndex;
//			this.resumeFrameCount = resumeFrameCount;
//			this.pauseFrameNum = pauseFrameNum;
//			this.pauseFrameCounter = pauseFrameCounter;
//			this.nextframeTrigger = nextframeTrigger;
//			this.lastTriggerFrameNum = lastTriggerFrameNum;
//		}
//			
//	}
	
	@Override
	public void accept(IMovementActionVisitor movementActionVisitor){
		movementActionVisitor.visitLeaf(this);
	}
}
