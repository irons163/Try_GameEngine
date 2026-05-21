package com.example.try_gameengine.framework

import java.util.Random

class Minimax {
    val bestMove: Unit
        get() {
            val board = CharArray(64)
            val nextPos = getNextMove(board)
        }

    fun getNextPosition(board: CharArray): Int {
        val gameState: Int = INPROGRESS
        val nextPos = getNextMove(board)
        println("nextPos:" + nextPos)
        return nextPos
    }

    private fun isWin(board: CharArray, enemy: Char): Boolean {
        var isWin = true
        for (c in board) {
            if (c == enemy) {
                isWin = false
                break
            }
        }
        return isWin
    }

    // test
    private var searchDeep = 0
    private var callTimes = 0

    /**
     * ?瑕?'x'??銝?郊韏唳?
     // */
    fun getNextMove(board: CharArray): Int {
        val nextPos = minimax(board, 50)
        println("searchDeep:" + (6 - searchDeep) + ", callTimes:" + callTimes)
        return nextPos
    }

    /**
     * ?斗皜豢??臬蝏?鈭???仃韐交???
     // */
    fun isGameOver(board: CharArray): Boolean {
        val gameState = gameState(board)
        return (gameState == WIN || gameState == LOSE || gameState == DRAW)
    }

    /**
     * 隞?x'??摨行????撠?憭抒?瘜?
     // */
    fun minimax(board: CharArray, depth: Int): Int {
        val bestMoves = IntArray(64)
        var index = 0
        var deepIndex = 0

        val shortestDeeps = IntArray(64)
        var shortestDeep = depth
        // test
        searchDeep = depth
        callTimes = 0

        var bestValue: Int = -INFINITY
        for (pos in 0..63) {
            if (board[pos] == empty) {
                board[pos] = x

                bestMinDeep = 0
                bestMaxDeep = 0

                val value = min(board, depth)
                //System.out.println(pos+":"+value);
                if (value > bestValue) {
                    bestValue = value
                    index = 0
                    bestMoves[index] = pos
                    shortestDeeps[deepIndex] = pos
                    shortestDeep = bestMinDeep
                } else if (value == bestValue) {
                    index++
                    bestMoves[index] = pos
                    if (bestMinDeep > shortestDeep) {
                        shortestDeep = bestMinDeep
                        deepIndex = 0
                        shortestDeeps[deepIndex] = pos
                    } else if (bestMinDeep == shortestDeep) {
                        deepIndex++
                        shortestDeeps[deepIndex] = pos
                    }
                }

                board[pos] = empty
            }
        }

        println("index:" + index + " bestValue:" + bestValue)
        if (index > 0 && bestValue != INFINITY && bestValue != -INFINITY) {
            index = (Random(System.currentTimeMillis()).nextInt() ushr 1) % index
        } else if (index > 0) {
            if (deepIndex > 0) {
                return shortestDeeps[(Random(System.currentTimeMillis()).nextInt() ushr 1) % deepIndex]
            } else {
//				index = (new Random(System.currentTimeMillis()).nextInt()>>>1)%index;
                return shortestDeeps[deepIndex]
            }
        }
        return bestMoves[index]
    }

    /**
     * 隡啣??賣嚗?靘?銝芸????嚗摰?皜豢?AI??雿?
     // */
    fun gameState(board: CharArray): Int {
        var result: Int = INPROGRESS
        var isFull = true
        var sum = 0
        var index = 0
        // is game over?
        for (pos in 0..63) {
            val chess = board[pos]
            if (empty == chess) {
                isFull = false
            } else {
                sum += chess.code
                index = pos
            }
        }


        // 憒??臬?憪???蝙?典?撅??
        val isInitial = (sum == x.code || sum == o.code)
        if (isInitial) {
            return (if (sum == x.code) 1 else -1) * INITIAL_POS_VALUE[index]
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
        if (isWin(board, o)) {
            result = WIN
        } else if (isWin(board, x)) {
            result = LOSE
        }

        if ((result != WIN) and (result != LOSE)) {
            if (isFull) {
                // is draw
                result = DRAW
            } else {
                // check double link
                // finds[0]->'x', finds[1]->'o'
                val finds = IntArray(2)
                for (status in WIN_STATUS) {
                    var chess: Char = empty
                    var hasEmpty = false
                    var count = 0
                    for (i in status.indices) {
                        if (board[status[i]] == empty) {
                            hasEmpty = true
                        } else {
                            if (chess == empty) {
                                chess = board[status[i]]
                            }
                            if (board[status[i]] == chess) {
                                count++
                            }
                        }
                    }
                    if (hasEmpty && count > 1) {
                        if (chess == x) {
                            finds[0]++
                        } else {
                            finds[1]++
                        }
                    }
                }


                // check two in one line
                if (finds[1] > 0) {
                    result = -DOUBLE_LINK
                } else if (finds[0] > 0) {
                    result = DOUBLE_LINK
                }
            }
        }

        return result
    }

    var bestMinDeep: Int = 0
    var bestMaxDeep: Int = 0

    /**
     * 撖嫣?'x'嚗摯?潸?憭批笆?嗉??
     // */
    fun max(board: CharArray, depth: Int): Int {
        val evalValue = gameState(board)

        val isGameOver = (evalValue == WIN || evalValue == LOSE || evalValue == DRAW)

        val deep = depth

        searchDeep = kotlin.math.min(searchDeep, depth)
        if (depth == 0 || isGameOver) {
            // test
            bestMinDeep = kotlin.math.max(depth, bestMinDeep)
            return evalValue
        }


        // test
        callTimes++

        var bestValue: Int = -INFINITY
        for (pos in 0..63) {
            if (board[pos] == empty) {
                // try
                board[pos] = x


                // maximixing
//				bestValue = Math.max(bestValue, min(board, depth-1));
                val min = min(board, depth - 1)
                if (min >= bestValue) {
                    bestValue = min
                    //					bestMinDeep = Math.max(depth, bestMinDeep);
                }


                // reset
                board[pos] = empty
            }
        }


//		if(bestValue==INFINITY){
//			deep = 
//		}
        return bestValue
    }

    /**
     * 撖嫣?'o'嚗摯?潸?撠笆?嗉??
     // */
    fun min(board: CharArray, depth: Int): Int {
        val evalValue = gameState(board)

        val isGameOver = (evalValue == WIN || evalValue == LOSE || evalValue == DRAW)
        searchDeep = kotlin.math.min(searchDeep, depth)
        if (depth == 0 || isGameOver) {
            // test
            bestMinDeep = kotlin.math.max(depth, bestMinDeep)
            return evalValue
        }


        // test
        callTimes++

        var bestValue: Int = +INFINITY
        for (pos in 0..63) {
            if (board[pos] == empty) {
                // try
                board[pos] = o


                // minimixing
//				bestValue = Math.min(bestValue, max(board, depth-1));
                val max = max(board, depth - 1)
                if (max <= bestValue) {
                    bestValue = max
                    bestMaxDeep = kotlin.math.max(depth, bestMaxDeep)
                }


                // reset
                board[pos] = empty
            }
        }

        return bestValue
    }

    companion object {
        const val x: Char = 'x'
        const val o: Char = 'o'
        const val empty: Char = '\u0000'

        const val INFINITY: Int = 100
        val WIN: Int = +INFINITY
        val LOSE: Int = -INFINITY
        val DOUBLE_LINK: Int = INFINITY / 2
        const val DRAW: Int = 0
        const val INPROGRESS: Int = 1

        val WIN_STATUS: Array<IntArray> = arrayOf<IntArray>(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
        )

        // 撘???塚?瘥葵雿蔭?摯??
        val INITIAL_POS_VALUE: IntArray = intArrayOf(
            3, 2, 3,
            2, 4, 2,
            3, 2, 3
        )
    }
}
