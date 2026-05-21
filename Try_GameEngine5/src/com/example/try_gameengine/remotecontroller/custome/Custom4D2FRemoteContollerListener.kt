package com.example.try_gameengine.remotecontroller.custome

import android.util.Log

class Custom4D2FRemoteContollerListener : Custom4D2FRemoteController.RemoteContollerListener {
    private val keySequence = LinkedHashSet<Int?>()
    private var move: Int = NONE
    var isPressLeftMoveBtn: Boolean = false
    var isPressRightMoveBtn: Boolean = false
    var isPressUpMoveBtn: Boolean = false
    var isPressDownMoveBtn: Boolean = false
    private val currentCommandType = ArrayList<Custom4D2FCommandType?>()

    private var custom4d2fRemoteContollerListener: Custom4D2FRemoteController.RemoteContollerListener =
        object : Custom4D2FRemoteController.RemoteContollerListener {
            override fun pressDown(commandTypes: MutableList<Custom4D2FCommandType?>?) {
                // TODO Auto-generated method stub
            }
        }

    fun setCustom4D2FRemoteContollerListener(custom4d2fRemoteContollerListener: Custom4D2FRemoteController.RemoteContollerListener) {
        this.custom4d2fRemoteContollerListener = custom4d2fRemoteContollerListener
    }

    fun getCustom4D2FRemoteContollerListener(): Custom4D2FRemoteController.RemoteContollerListener {
        return custom4d2fRemoteContollerListener
    }

    private fun getLastMoveAfterRemoveMove(curentMove: Int): Int {
        var lastMove: Int = NONE
        var tmpMove: Int = NONE
        var keySequenceIterator = keySequence.iterator()

        while (keySequenceIterator.hasNext()) {
            tmpMove = keySequenceIterator.next()!!
            if (tmpMove == curentMove) keySequenceIterator.remove()
        }

        keySequenceIterator = keySequence.iterator()
        while (keySequenceIterator.hasNext()) {
            lastMove = keySequenceIterator.next()!!
        }

        Log.e("lastMove", lastMove.toString() + "")

        return lastMove
    }

    fun getCurrentMove(): Int {
        return move
    }

    override fun pressDown(commandTypes: MutableList<Custom4D2FCommandType?>?) {
        // TODO Auto-generated method stub
        commandTypes ?: return
        for (commandType in commandTypes) {
            when (commandType) {
                Custom4D2FCommandType.RightKeyUpCommand -> {
                    isPressRightMoveBtn = false
                    move = getLastMoveAfterRemoveMove(RIGHT)

                    if (!isPressLeftMoveBtn && !isPressRightMoveBtn && !isPressUpMoveBtn && !isPressDownMoveBtn) {
                        currentCommandType.clear()
                        currentCommandType.add(commandType)
                        custom4d2fRemoteContollerListener.pressDown(currentCommandType)
                    }
                }

                Custom4D2FCommandType.RightKeyDownCommand -> {
                    isPressRightMoveBtn = true
                    move = RIGHT
                    keySequence.remove(move)
                    keySequence.add(move)
                }

                Custom4D2FCommandType.LeftKeyDownCommand -> {
                    isPressLeftMoveBtn = true
                    move = LEFT
                    keySequence.remove(move)
                    keySequence.add(move)
                }

                Custom4D2FCommandType.LeftKeyUpCommand -> {
                    isPressLeftMoveBtn = false
                    move = getLastMoveAfterRemoveMove(LEFT)

                    if (!isPressLeftMoveBtn && !isPressRightMoveBtn && !isPressUpMoveBtn && !isPressDownMoveBtn) {
                        currentCommandType.clear()
                        currentCommandType.add(commandType)
                        custom4d2fRemoteContollerListener.pressDown(currentCommandType)
                    }
                }

                Custom4D2FCommandType.UPKeyDownCommand -> {
                    isPressUpMoveBtn = true
                    move = UP
                    keySequence.remove(move)
                    keySequence.add(move)
                }

                Custom4D2FCommandType.UPKeyUpCommand -> {
                    isPressUpMoveBtn = false
                    move = getLastMoveAfterRemoveMove(UP)

                    if (!isPressLeftMoveBtn && !isPressRightMoveBtn && !isPressUpMoveBtn && !isPressDownMoveBtn) {
                        currentCommandType.clear()
                        currentCommandType.add(commandType)
                        custom4d2fRemoteContollerListener.pressDown(currentCommandType)
                    }
                }

                Custom4D2FCommandType.DownKeyDownCommand -> {
                    isPressDownMoveBtn = true
                    move = DOWN
                    keySequence.remove(move)
                    keySequence.add(move)
                }

                Custom4D2FCommandType.DownKeyUpCommand -> {
                    isPressDownMoveBtn = false
                    move = getLastMoveAfterRemoveMove(DOWN)

                    if (!isPressLeftMoveBtn && !isPressRightMoveBtn && !isPressUpMoveBtn && !isPressDownMoveBtn) {
                        currentCommandType.clear()
                        currentCommandType.add(commandType)
                        custom4d2fRemoteContollerListener.pressDown(currentCommandType)
                    }
                }

                else -> {}
            }
        }
    }

    companion object {
        const val NONE: Int = 0
        const val LEFT: Int = 1
        const val RIGHT: Int = 2
        const val UP: Int = 3
        const val DOWN: Int = 4
    }
}
