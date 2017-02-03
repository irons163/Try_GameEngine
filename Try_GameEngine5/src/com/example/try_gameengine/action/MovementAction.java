package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.example.try_gameengine.action.listener.DefaultActionListener;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

import android.util.Log;

/**
 * This base MovementAction which can do action.
 * @author irons
 *
 */
public abstract class MovementAction {
	protected List<MovementAction> actions = new ArrayList<MovementAction>();
	protected Thread thread;
	protected TimerOnTickListener timerOnTickListener;
	protected String description = "Unknown Movement";
	List<MovementAction> copyMovementActionList = new ArrayList<MovementAction>();
	List<MovementActionInfo> currentInfoList = new ArrayList<MovementActionInfo>();
	
	List<MovementAction> movementItemList = new ArrayList<MovementAction>();
	
	List<MovementAction> totalCopyMovementActionList = new ArrayList<MovementAction>();
	
	protected boolean isCancelFocusAppendPart = false;
	
	protected boolean isFinish = false;
	
	public boolean isLoop = false;
	
	public boolean isSigleThread = false;
	
	protected static ExecutorService executor = Executors.newFixedThreadPool(20);
	
	String name="";
	
	protected MovementAction cancelAction;
	
	public boolean isRepeatSpriteActionIfMovementActionRepeat = true;
	
	public MovementAction addMovementAction(MovementAction action) {
		throw new UnsupportedOperationException();
	}
	
	public void setTimerOnTickListener(TimerOnTickListener timerOnTickListener) {
			this.timerOnTickListener = timerOnTickListener;
			setActionsTheSameTimerOnTickListener();
	}
	
	public TimerOnTickListener getTimerOnTickListener(){
		return this.timerOnTickListener;
	}
	
	protected void setActionsTheSameTimerOnTickListener(){
		
	}
	
	/**
	 * 
	 */
	public void setTimer(){
		
	}

	/**
	 * 
	 */
	public void start() {

	}
	
	/**
	 * this is listener for old movement action which use timer.
	 * @author irons
	 *
	 */
	public interface TimerOnTickListener{
		public void onTick(float dx, float dy);
	}
	
	public MovementAction initMovementAction(){
		return initTimer();
	}
	
	/**
	 * @return
	 */
	protected MovementAction initTimer(){
		return this;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public MovementAction getAction(){
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<MovementAction> getActions(){
		return actions;
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract MovementActionInfo getInfo();
	
	public void setInfo(MovementActionInfo info){
		
	}
	
	/**
	 * get description.
	 * @return string
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * get current active list.
	 * @return list
	 */
	public abstract List<MovementAction> getCurrentActionList();
	
	/**
	 * get current info list.
	 * @return list
	 */
	public abstract List<MovementActionInfo> getCurrentInfoList();
	
	/**
	 * 
	 */
	public void doInfo(){
		getCurrentInfoList();
	}

	/**
	 * get movement action list.
	 * @return
	 */
	public List<MovementAction> getMovementItemList() {
		return movementItemList;
	}
	
	/**
	 * @return
	 */
	public List<MovementActionInfo> getMovementInfoList() {
		return currentInfoList;
	}

	/**
	 * 
	 */
	public void doIn(){
		
	}

	/**
	 * @return
	 */
	public List<MovementActionInfo> getStartMovementInfoList() {
		getCurrentInfoList();
		return getMovementInfoList();
	}

	/**
	 * @return
	 */
	public boolean isCancelFocusAppendPart() {
		return isCancelFocusAppendPart;
	}

	/**
	 * @param isCancelFocusAppendPart
	 */
	public void setCancelFocusAppendPart(boolean isCancelFocusAppendPart) {
		this.isCancelFocusAppendPart = isCancelFocusAppendPart;
	}
	
	/**
	 * cancel all movementActions.
	 */
	void cancelAllMove(){
		if(this.getAction().actions.size()!=0){
			for(MovementAction action : this.getAction().actions){
				action.cancelMove();
				Log.e("action", "cancel");
			}
			if(this.thread!=null)
			this.thread.interrupt();
		}else{
			cancelMove();
		}
	}
	
	/**
	 * cancel movement action which current active.
	 */
	void cancelMove(){
		for(MovementAction action : cancelAction.getAction().actions){
			action.cancelMove();
		}
		
//		if(cancelAction.getAction().actions.size()!=0){
//			for(MovementAction action : cancelAction.getAction().actions){
//				action.cancelMove();
//			}
//		}else{
//			cancelAction.cancelMove();
//		}
		
		if(!isSigleThread && this.thread!=null)
			this.thread.interrupt();
	}
	
	/**
	 * 
	 */
	void pause(){
		cancelAction.getAction().pause();
	}
	
	public MovementAtionController controller;
	
	/**
	 * @param controller
	 */
	public void setMovementActionController(MovementAtionController controller){
		this.controller = controller;
		this.controller.setMovementAction(this);
	}
	
	/**
	 * check is cancelAction Finish or not.
	 * @return
	 */
	public boolean isFinish(){
		return cancelAction.getAction().isFinish();
	}
	
	/**
	 * set a name like tag.
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * get name from movement action.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * get specific movement from the movementAction composites.
	 * @param name
	 * @return
	 */
	public MovementAction getPartOfMovementActionByName(String name){
		return getMovement(this, name);
	} 
	
	/**
	 * @param action
	 * @param name
	 * @return
	 */
	private MovementAction getMovement(MovementAction action, String name){
		for(MovementAction movementAction : action.getAction().getActions()){
			if(action.name.equals(name))
				return action;
			return getMovement(movementAction, name);
		}
		return null;
	}
	
	/**
	 * This is important. It the way to process the movement actions.
	 */
	public void trigger(){
		this.getAction().trigger();
	}
	
	/**
	 * Set an action listener as a callback to deal with movement action.
	 * @param actionListener as call back.
	 */
	public void setActionListener(IActionListener actionListener){
		this.actionListener = actionListener;
	}
	
	protected IActionListener actionListener = new DefaultActionListener();
	
	/**
	 * get action listener from movement action.
	 * @return IActionListener.
	 */
	public IActionListener getActionListener(){ 
		return actionListener;
	} 
	
	/**
	 * set movement action loop or not.
	 * @param isLoop
	 */
	public void setIsLoop(boolean isLoop){
		this.getAction().isLoop = isLoop;
	}
	
	public void setIsSingleThread(boolean isSigleThread){
		this.isSigleThread = isSigleThread;
	}
	
	/**
	 * @param spriteX
	 * @param spriteY
	 */
	public void modifyWithSpriteXY(float spriteX, float spriteY){
		for(MovementActionInfo movementActionInfo : currentInfoList){
			movementActionInfo.modifyInfoWithSpriteXY(spriteX, spriteY);
		}
	}
	
	/**
	 * accept visitor to control or change the movement action.
	 * @param movementActionVisitor
	 */
	public abstract void accept(IMovementActionVisitor movementActionVisitor);
	
	IMovementActionMemento movementActionMemento=null;
	
	//not use yet
	public IMovementActionMemento createMovementActionMemento(){
		movementActionMemento = new MovementActionMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction);
		return movementActionMemento;
	}
	
	/**
	 * get what index in thread pool.
	 * @return index
	 */
	public static int getThreadPoolNumber(){
		if(executor instanceof ThreadPoolExecutor){
			return ((ThreadPoolExecutor)executor).getActiveCount();
		}
		return 0;
	}
	
	/**
	 * restore movement action from memento.
	 * @param movementActionMemento
	 */
	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
//		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) this.movementActionMemento;
		this.actions = mementoImpl.getActions();
		this.thread = mementoImpl.thread;
		this.timerOnTickListener = mementoImpl.timerOnTickListener;
		this.description = mementoImpl.description;
		this.copyMovementActionList = mementoImpl.copyMovementActionList;
		this.currentInfoList = mementoImpl.currentInfoList;
		this.movementItemList = mementoImpl.movementItemList;
		this.totalCopyMovementActionList = mementoImpl.totalCopyMovementActionList;
		this.isCancelFocusAppendPart = mementoImpl.isCancelFocusAppendPart;
		this.isFinish = mementoImpl.isFinish;
		this.isLoop = mementoImpl.isLoop;
		this.isSigleThread = mementoImpl.isSigleThread;
		this.name = mementoImpl.name;
		this.cancelAction = mementoImpl.cancelAction;
	}
	
	/**
	 * MovementActionMementoImpl is implement for IMovementActionMemento. Use to save status.
	 * @author irons
	 *
	 */
	protected static class MovementActionMementoImpl implements IMovementActionMemento{
		private List<MovementAction> actions;
		private Thread thread;
		private TimerOnTickListener timerOnTickListener;
		private String description = "Unknown Movement";
		private List<MovementAction> copyMovementActionList;
		private List<MovementActionInfo> currentInfoList;
		
		private List<MovementAction> movementItemList;
		
		private List<MovementAction> totalCopyMovementActionList;
		
		private boolean isCancelFocusAppendPart;
		
		private boolean isFinish;
		
		private boolean isLoop;
		
		private boolean isSigleThread;
		
		private String name;

		private MovementAction cancelAction;
		
		public MovementActionMementoImpl(List<MovementAction> actions,
				Thread thread, TimerOnTickListener timerOnTickListener,
				String description,
				List<MovementAction> copyMovementActionList,
				List<MovementActionInfo> currentInfoList,
				List<MovementAction> movementItemList,
				List<MovementAction> totalCopyMovementActionList,
				boolean isCancelFocusAppendPart, boolean isFinish,
				boolean isLoop, boolean isSigleThread, String name,
				MovementAction cancelAction) {
			super();
			this.actions = actions;
			this.thread = thread;
			this.timerOnTickListener = timerOnTickListener;
			this.description = description;
			this.copyMovementActionList = copyMovementActionList;
			this.currentInfoList = currentInfoList;
			this.movementItemList = movementItemList;
			this.totalCopyMovementActionList = totalCopyMovementActionList;
			this.isCancelFocusAppendPart = isCancelFocusAppendPart;
			this.isFinish = isFinish;
			this.isLoop = isLoop;
			this.isSigleThread = isSigleThread;
			this.name = name;
			this.cancelAction = cancelAction;
		}

		public List<MovementAction> getActions() {
			return actions;
		}

		public void setActions(List<MovementAction> actions) {
			this.actions = actions;
		}

		public Thread getThread() {
			return thread;
		}

		public void setThread(Thread thread) {
			this.thread = thread;
		}

		public TimerOnTickListener getTimerOnTickListener() {
			return timerOnTickListener;
		}

		public void setTimerOnTickListener(TimerOnTickListener timerOnTickListener) {
			this.timerOnTickListener = timerOnTickListener;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public List<MovementAction> getCopyMovementActionList() {
			return copyMovementActionList;
		}

		public void setCopyMovementActionList(
				List<MovementAction> copyMovementActionList) {
			this.copyMovementActionList = copyMovementActionList;
		}

		public List<MovementActionInfo> getCurrentInfoList() {
			return currentInfoList;
		}

		public void setCurrentInfoList(List<MovementActionInfo> currentInfoList) {
			this.currentInfoList = currentInfoList;
		}

		public List<MovementAction> getMovementItemList() {
			return movementItemList;
		}

		public void setMovementItemList(List<MovementAction> movementItemList) {
			this.movementItemList = movementItemList;
		}

		public List<MovementAction> getTotalCopyMovementActionList() {
			return totalCopyMovementActionList;
		}

		public void setTotalCopyMovementActionList(
				List<MovementAction> totalCopyMovementActionList) {
			this.totalCopyMovementActionList = totalCopyMovementActionList;
		}

		public boolean isCancelFocusAppendPart() {
			return isCancelFocusAppendPart;
		}

		public void setCancelFocusAppendPart(boolean isCancelFocusAppendPart) {
			this.isCancelFocusAppendPart = isCancelFocusAppendPart;
		}

		public boolean isFinish() {
			return isFinish;
		}

		public void setFinish(boolean isFinish) {
			this.isFinish = isFinish;
		}

		public boolean isLoop() {
			return isLoop;
		}

		public void setLoop(boolean isLoop) {
			this.isLoop = isLoop;
		}

		public boolean isSigleThread() {
			return isSigleThread;
		}

		public void setSigleThread(boolean isSigleThread) {
			this.isSigleThread = isSigleThread;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public MovementAction getCancelAction() {
			return cancelAction;
		}

		public void setCancelAction(MovementAction cancelAction) {
			this.cancelAction = cancelAction;
		}
		
		protected void setThreadPool(int nThreads){
			executor.shutdown();
			if(nThreads<=0){
				executor = Executors.newCachedThreadPool();
			}else{
				executor = Executors.newFixedThreadPool(nThreads);
			}
			
		}
	}
}
