package com.example.try_gameengine.framework;

import android.graphics.Point;
import android.os.Handler;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * APlayerManager
 * @author irons
 *
 */
public abstract class APlayerManager implements IPlayerManager {
	private Point clickPoint;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			detectSomeOneWinAndToNextPlayerTurn();
		};
	};
	
	private boolean isSomeOneWin = false;
	private IChessBoard jumpChessBoard;
	private Logic logic;
	protected List<IPlayer> playersBySquential;
	protected IPlayer whoPlay;
	protected int whoRun = 1;
	private IChessPointManager chessPointManager;
	protected IPlayerFactory playerFactory;

	/**
	 * 
	 */
	public APlayerManager() {
		playersBySquential = new ArrayList<IPlayer>();
		initPlayerFactory();
		
	}
	
	/**
	 * 
	 */
	protected void initPlayerFactory() {
		playerFactory = new PlayerFactory(chessPointManager);
	}

	/**
	 * @param player
	 * @param paramList
	 */
	private void AiPlayerProcess(final IPlayer player, List<Point> paramList) {
		new Thread(new Runnable() {

			@Override
			public void run() {

			}
		}).start();
	}

	/**
	 * 
	 */
	private void decideNextPlayer() {
		this.whoRun = (1 + this.whoRun);
		if (this.whoRun != this.playersBySquential.size())
			return;
		this.whoRun = 0;
	}

	/**
	 * 
	 */
	private void detectSomeOneWinAndToNextPlayerTurn() {
		decideNextPlayer();
		
	}

	/**
	 * @param paramIPlayer
	 * @return
	 */
	private boolean isAiPlayerRun(IPlayer paramIPlayer) {
		if (paramIPlayer instanceof AiPlayer)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param paramMotionEvent
	 * @param player
	 * @return
	 */
	private boolean isClickonPointGroup(MotionEvent paramMotionEvent,
			IPlayer player) {
		boolean a = false;

		Point newPoint = jumpChessBoard.newPoint(
				Float.valueOf(paramMotionEvent.getX()),
				Float.valueOf(paramMotionEvent.getY()));

		if ((newPoint.x >= 0 && newPoint.x < jumpChessBoard.getAllExistPoints().length)
				&& (newPoint.y >= 0 && newPoint.y < jumpChessBoard
						.getAllExistPoints()[0].length)) {
			int playerIndex = playersBySquential.indexOf(player)+1;

			if (jumpChessBoard.getAllExistPoints()[newPoint.x][newPoint.y] == playerIndex){
				a = true;
				Logic.jumps.clear();
				if(playerIndex==1){
					logic.startToDetectedTopToDown(newPoint.x, newPoint.y, playerIndex);
				}else{
					logic.startToDetectedDownToTop(newPoint.x, newPoint.y, playerIndex);
				}
			}
		}
		return a;
	}

	private void playerRun(MotionEvent paramMotionEvent, IPlayer paramIPlayer,
			Point paramPoint) {

		this.whoRun = this.playersBySquential.indexOf(paramIPlayer);
		detectSomeOneWinAndToNextPlayerTurn();
	}

	private void setFirstPlayer() {
		this.whoRun = 0;
	}

	public IPlayer getBefforePlayer() {
		return null;
	}

	public IPlayer getCurrentPlayer() {
		return (IPlayer) this.playersBySquential.get(this.whoRun);
	}

	public IPlayer getNextPlayer() {
		return null;
	}

	public List<IPlayer> getWinner() {
		List<IPlayer> winnerArrayList = new ArrayList<IPlayer>();
		for (IPlayer player : playersBySquential) {
			winnerArrayList.add(player);
		}
		return winnerArrayList;
	}

	public boolean isAllPlayersDone() {
		return false;
	}

	public boolean isPlayerCanRun() {
		if (this.whoRun >= 0)
			return true;
		else
			return false;
	}

	public boolean isPlayerProcessing() {
		if (this.whoRun == -1)
			return true;
		else
			return false;
	}

	public boolean isSomeOneWin() {
		return this.isSomeOneWin;
	}

	boolean isCanPutChessPoint = true;

	public void onTouchEvent(MotionEvent paramMotionEvent) {
		if (paramMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			touchPerform(paramMotionEvent);
		}
	}
	
	protected void touchPerform(MotionEvent paramMotionEvent){
		if (isPlayerCanRun()) {
			this.whoPlay = getCurrentPlayer();
//			isClickonPointGroup(paramMotionEvent, this.whoPlay);
			isClick(paramMotionEvent, this.whoPlay);
			// if(winLoseLogic.isWin(clickPoint)){
		} else if (isPlayerProcessing()) {
//			if (isClickonPointGroup(paramMotionEvent, this.whoPlay)) {
			if(isClick(paramMotionEvent, this.whoPlay)){
//				isClick(paramMotionEvent, this.whoPlay);
				whoRun = -1;
			} else
				playerRun(paramMotionEvent, this.whoPlay, this.clickPoint);
		}
	}
	
	protected abstract boolean isClick(MotionEvent paramMotionEvent,
			IPlayer player);

//	public void setBoard(IChessBoard paramJumpChessBoard) {
//		this.jumpChessBoard = paramJumpChessBoard;
//	}

	 public void setLogic(Logic paramLogic) {
	 this.logic = paramLogic;
	 }

	public void setOnProcessing() {
		this.whoRun = -1;
	}

	public void setPlayersBySquential(List<IPlayer> paramList) {
		this.playersBySquential = paramList;
		setFirstPlayer();
	}

	public List<IPlayer> getPlayersBySquential() {
		return playersBySquential;
	}

	public void startPlayByFirstPlayer() {

	}

	public void toBefforePlayer() {
	}

	public void toNextPlayer() {
	}

}