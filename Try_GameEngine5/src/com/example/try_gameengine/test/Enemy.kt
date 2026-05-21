package com.example.try_gameengine.test

import android.os.CountDownTimer
import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener
import com.example.try_gameengine.action.MovementActionInfo
import com.example.try_gameengine.action.MovementAtionController
import com.example.try_gameengine.framework.Sprite
import com.example.try_gameengine.observer.Observer
import com.example.try_gameengine.observer.Subject

abstract class Enemy : Sprite, Subject, Observer {
    //	private float x, y;
    //	protected Bitmap bitmap;
    //	protected MovementAction action;
    private val observers: MutableList<Observer> = ArrayList<Observer>()

    constructor(x: Float, y: Float) : super(x, y, true) {
        // TODO Auto-generated constructor stub
//		this.x = x;
//		this.y = y;
//		this.bitmap = BitmapUtil.redPoint;
        initBitmap()
        setWH()
    }

    constructor(x: Float, y: Float, action: MovementAction?) : super(x, y, true) {
        //		this.x = x;
//		this.y = y;
//		this.bitmap = BitmapUtil.redPoint;
        initBitmap()
        setWH()
        setMovementAction(action)
        setMovementActioinTimerOnTickListener()
        //		if(action instanceof SimultaneouslyMultiCircleMovementActionSet)
//		infos = ((SimultaneouslyMultiCircleMovementActionSet)action).getCurrentInfoList();
    }

    abstract fun initBitmap()

    private fun setWH() {
        setInitWidth(getBitmap().getWidth())
        setInitHeight(getBitmap().getHeight())
    }

    fun startMovementActioin() {
        if (getMovementAction() != null) getMovementAction().start()
    }

    private fun setMovementActioinTimerOnTickListener() {
        if (getMovementAction() != null) getMovementAction().setTimerOnTickListener(object :
            TimerOnTickListener {
            override fun onTick(dx: Float, dy: Float) {
                // TODO Auto-generated method stub
                move(dx, dy)
            }
        })
    }

    //	public void draw(Canvas canvas){
    //		canvas.drawBitmap(bitmap, x, y, null);
    //	}
    override fun move(dx: Float, dy: Float) {
//		x += dx;
//		y += dy;

        setX(getCenterX() + dx - getWidth() / 2)
        setY(getCenterY() + dy - getHeight() / 2)

        notifyObservers()
    }

    fun moveLeftAndRight(dx: Float) {
//		x += dx;
    }

    fun moveUpAndDown(dy: Float) {
//		y += dy;
    }

    fun moveRandom() {
    }

    fun moveUP() {
        val countDownTimer: CountDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // TODO Auto-generated method stub
//				enemyManager.moveEnemiesUpAndDown(30);
            }

            override fun onFinish() {
                // TODO Auto-generated method stub
            }
        }

        countDownTimer.start()
    }

    fun startMoveLeft(s: Int, delay: Int, dx: Float) {
        val countDownTimer: CountDownTimer =
            object : CountDownTimer((s * 1000).toLong(), (delay * 1000).toLong()) {
                override fun onTick(millisUntilFinished: Long) {
                    // TODO Auto-generated method stub
                    moveUP()
                }

                override fun onFinish() {
                    // TODO Auto-generated method stub
                }
            }

        countDownTimer.start()
    }

    fun startMoveLeftAndRight(s: Int, delay: Int, dx: Float) {
        val countDownTimer: CountDownTimer =
            object : CountDownTimer((s * 1000).toLong(), (delay * 1000).toLong()) {
                override fun onTick(millisUntilFinished: Long) {
                    // TODO Auto-generated method stub
                    moveUP()
                }

                override fun onFinish() {
                    // TODO Auto-generated method stub
                }
            }

        countDownTimer.start()
    }

    val movementActionDescriptions: String?
        get() = getMovementAction().getDescription()

    var action: MovementAction?
        get() = getMovementAction()
        set(action) {
            setMovementAction(action)
        }

    val c: MovementAtionController?
        get() = getMovementAction().controller

    var infos: MutableList<MovementActionInfo?>? = null

    override fun update(mx: Float, my: Float, angle: Float) {
        // TODO Auto-generated method stub
//		setX(mx);
//		setY(my);

//		for(MovementActionInfo info : infos){
//			((ICircleController)info.getRotationController()).action(mx, my, angle);
//		}

//		PointF pointF = ((SimultaneouslyMultiCircleMovementActionSet)getMovementAction()).notyMediator2(((ICircleController)infos.get(0).getRotationController()), mx, my, angle);
//		if(pointF!=null){
//		setX(pointF.x);
//		setY(pointF.y);
//		}
    }

    override fun registerObserver(o: Observer?) {
        // TODO Auto-generated method stub
        observers.add(o!!)
    }

    override fun removeObserver(o: Observer?) {
        // TODO Auto-generated method stub
        val i = observers.indexOf(o)
        if (i >= 0) {
            observers.removeAt(i)
        }
    }

    override fun notifyObservers() {
        // TODO Auto-generated method stub
        for (observer in observers) {
            observer.update(getX(), getY(), 5f)
        }
    }
}
