package com.example.try_gameengine.framework

class ProcessBlockManager {
    private val preProcessBlocksList: MutableList<ProcessBlock?> = ArrayList<ProcessBlock?>()

    private object ProcessBlockManagerHolder {
        var ProcessBlockManager: ProcessBlockManager = ProcessBlockManager()
    }

    fun setPreProcessBlock(processBlock: ProcessBlock?, sceneIndex: Int) {
    }

    fun setPreProcessBlock(processBlock: ProcessBlock?, gameModel: GameModel?) {
//		gameModel.set
    }

    companion object {
        fun getInstance(): ProcessBlockManager {
            return ProcessBlockManagerHolder.ProcessBlockManager
        }
    }
}
