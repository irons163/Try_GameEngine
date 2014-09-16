package com.example.try_gameengine.framework;

import java.util.Random;

public class Minimax {
	public void getBestMove(){
		char[] board = new char[64];
		int nextPos = getNextMove(board);
	}

	public int getNextPosition(char[] board) {
		int gameState = INPROGRESS;			
			int nextPos = getNextMove(board);
			System.out.println("nextPos:"+nextPos);
			return nextPos;
	}
	
	static final char x = 'x';
	static final char o = 'o';
	static final char empty = '\0';
	
	static final int INFINITY = 100;
	static final int WIN = +INFINITY;
	static final int LOSE = -INFINITY;
	static final int DOUBLE_LINK = INFINITY/2;
	static final int DRAW = 0;
	static final int INPROGRESS = 1;
	
	static final int[][] WIN_STATUS = {
		{0, 1, 2},
		{3, 4, 5},
		{6, 7, 8},
		{0, 3, 6},
		{1, 4, 7},
		{2, 5, 8},
		{0, 4, 8},
		{2, 4, 6}
	};
	
	private boolean isWin(char[] board, char enemy){
		boolean isWin = true;
		for(char c : board){
			if(c==enemy){
				isWin=false;
				break;
			}
		}
		return isWin;
	}
	
	// Âº???∂Ô?ÊØè‰∏™‰ΩçÁΩÆ?Ñ‰º∞??
	static final int[] INITIAL_POS_VALUE = {
		3, 2, 3,
		2, 4, 2,
		3, 2, 3
	};
	
	// test
	private int searchDeep;
	private int callTimes;
	
	/**
	 * ?∑Â?'x'?Ñ‰?‰∏?≠•Ëµ∞Ê?
	 */
	public int getNextMove(char[] board){
		int nextPos = minimax(board, 50);
		System.out.println("searchDeep:"+(6-searchDeep)+", callTimes:"+callTimes);
		return nextPos;
	}
	
	/**
	 * ?§Êñ≠Ê∏∏Ê??ØÂê¶ÁªìÊ?‰∫ÜÔ??úÂà©?ÅÂ§±Ë¥•Ê??åÂ?
	 */
	public boolean isGameOver(char[] board){
		int gameState = gameState(board);
		return (gameState==WIN || gameState==LOSE || gameState==DRAW);
	}
	
	/**
	 * ‰ª?x'?ÑË?Â∫¶Êù•?ÉË??ÑÊ?Â∞èÊ?Â§ßÁ?Ê≥?
	 */
	public int minimax(char[] board, int depth){
		int[] bestMoves = new int[64];
		int index = 0;
		int deepIndex = 0;
		
		int[] shortestDeeps = new int[64];
		int shortestDeep = depth;
		// test
		searchDeep = depth;
		callTimes = 0;
		
		int bestValue = -INFINITY;
		for(int pos=0; pos<64; pos++){
			
			if(board[pos]==empty){
				board[pos] = x;
				
				bestMinDeep=0;
				bestMaxDeep=0;
				
				int value = min(board, depth);
				//System.out.println(pos+":"+value);
				if(value>bestValue){
					bestValue = value;
					index = 0;
					bestMoves[index] = pos;
					shortestDeeps[deepIndex] = pos;
					shortestDeep = bestMinDeep;
				}else
				if(value==bestValue){
					index++;
					bestMoves[index] = pos;
					if(bestMinDeep > shortestDeep){
						shortestDeep = bestMinDeep;
						deepIndex = 0;
						shortestDeeps[deepIndex] = pos;
					}
					else if(bestMinDeep == shortestDeep){
						deepIndex++;
						shortestDeeps[deepIndex] = pos;
					}
				}
				
				board[pos] = empty;
			}
			
		}
		
		System.out.println("index:"+index+" bestValue:"+bestValue);
		if(index>0 && bestValue!= INFINITY && bestValue!= -INFINITY){
			index = (new Random(System.currentTimeMillis()).nextInt()>>>1)%index;
		}else if(index>0){
			if(deepIndex>0){
				return shortestDeeps[(new Random(System.currentTimeMillis()).nextInt()>>>1)%deepIndex];
			}else{
//				index = (new Random(System.currentTimeMillis()).nextInt()>>>1)%index;
				return shortestDeeps[deepIndex];
			}
		}
		return bestMoves[index];
		
	}
	
	/**
	 * ‰º∞Â??ΩÊï∞ÔºåÊ?‰æõ‰?‰∏™ÂêØ?ëÂ??ÑÂ?ÔºåÂÜ≥ÂÆö‰?Ê∏∏Ê?AI?ÑÈ?‰Ω?
	 */
	public int gameState(char[] board){
		int result = INPROGRESS;
		boolean isFull = true;
		int sum = 0;
		int index = 0;
		// is game over?
		for(int pos=0; pos<64; pos++){
			char chess = board[pos];
			if(empty==chess){
				isFull = false;
			}else{
				sum += chess;
				index = pos;
			}
		}
		
		// Â¶ÇÊ??ØÂ?ÂßãÁä∂?ÅÔ??ô‰Ωø?®Â?Â±??
		boolean isInitial = (sum==x||sum==o);
		if(isInitial){
			return (sum==x?1:-1)*INITIAL_POS_VALUE[index];
		}
		
		// is Max win/lose?
//		for(int[] status : WIN_STATUS){
//			char chess = board[status[0]];
//			if(chess==empty){
//				continue;
//			}
//			int i = 1;
//			for(; i<status.length; i++){
//				if(board[status[i]]!=chess){
//					break;
//				}
//			}
//			if(i==status.length){
//				result = chess==x ? WIN : LOSE;
//				break;
//			}
//		}
		
		if(isWin(board, o)){
			result=WIN;
		}else if(isWin(board, x)){
			result=LOSE;
		}
		
		if(result!=WIN & result!=LOSE){
			
			if(isFull){
				// is draw
				result = DRAW;
			}else{
				// check double link
				// finds[0]->'x', finds[1]->'o'
				int[] finds = new int[2];
				for(int[] status : WIN_STATUS){
					char chess = empty;
					boolean hasEmpty = false;
					int count = 0;
					for(int i=0; i<status.length; i++){
						if(board[status[i]]==empty){
							hasEmpty = true;
						}else{
							if(chess==empty){
								chess = board[status[i]];
							}
							if(board[status[i]]==chess){
								count++;
							}
						}
					}
					if(hasEmpty && count>1){
						if(chess==x){
							finds[0]++;
						}else{
							finds[1]++;
						}
					}
				}
				
				// check two in one line
				if(finds[1]>0){
					result = -DOUBLE_LINK;
				}else
				if(finds[0]>0){
					result = DOUBLE_LINK;
				}
				
			}
			
		}
		
		return result;
		
	}
	
	int bestMinDeep=0;
	int bestMaxDeep=0;
	
	/**
	 * ÂØπ‰?'x'Ôºå‰º∞?ºË?Â§ßÂØπ?∂Ë??âÂà©
	 */
	public int max(char[] board, int depth){
		
		int evalValue = gameState(board);
		
		boolean isGameOver = (evalValue==WIN || evalValue==LOSE || evalValue==DRAW);
		
		int deep = depth;
		
		searchDeep = Math.min(searchDeep, depth);
		if(depth==0||isGameOver){
			// test
			bestMinDeep = Math.max(depth, bestMinDeep);
			return evalValue;
		}
		
		// test
		callTimes++;
		
		int bestValue = -INFINITY;
		for(int pos=0; pos<64; pos++){
			
			if(board[pos]==empty){
				// try
				board[pos] = x;
				
				// maximixing
//				bestValue = Math.max(bestValue, min(board, depth-1));
				int min = min(board, depth-1);
				if(min >= bestValue){
					bestValue = min;
//					bestMinDeep = Math.max(depth, bestMinDeep);
				}
				
				// reset
				board[pos] = empty;
			}
			
		}
		
//		if(bestValue==INFINITY){
//			deep = 
//		}
		
		return bestValue;
		
	}
	
	/**
	 * ÂØπ‰?'o'Ôºå‰º∞?ºË?Â∞èÂØπ?∂Ë??âÂà©
	 */
	public int min(char[] board, int depth){
		
		int evalValue = gameState(board);
		
		boolean isGameOver = (evalValue==WIN || evalValue==LOSE || evalValue==DRAW);
		searchDeep = Math.min(searchDeep, depth);
		if(depth==0||isGameOver){
			// test
			bestMinDeep = Math.max(depth, bestMinDeep);
			return evalValue;
		}
		
		// test
		callTimes++;
		
		int bestValue = +INFINITY;
		for(int pos=0; pos<64; pos++){
			
			if(board[pos]==empty){
				// try
				board[pos] = o;
				
				// minimixing
//				bestValue = Math.min(bestValue, max(board, depth-1));
				int max = max(board, depth-1);
				if(max <= bestValue){
					bestValue = max;
					bestMaxDeep = Math.max(depth, bestMaxDeep);
				}
				
				// reset
				board[pos] = empty;
			}
			
		}
		
		return bestValue;
		
	}
}
