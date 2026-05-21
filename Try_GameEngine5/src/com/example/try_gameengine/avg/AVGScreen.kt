package com.example.try_gameengine.avg

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.example.try_gameengine.assemble.AssembleView
import com.example.try_gameengine.assemble.AssembleViewConfig
import com.example.try_gameengine.assemble.AssembleViewConfig.DirectionConfig
import com.example.try_gameengine.avg.MessageBoxStringView.MessageView
import com.example.try_gameengine.viewport.MyMap

class AVGScreen(context: Activity?) {
    protected var context: Context?

    protected var command: Command? = null
    private var isSelectMessage = false
    private var scrFlag = false
    private var commandGo = false
    var running: Boolean = false
    protected var message: LMessage? = null
    var handler: SystemHandler?

    // protected LSelect select;
    protected var scriptName: String? = null
    var delay: Int = 0
    protected var selects: Array<String> = arrayOf("鵬淩三千帥不帥？")
    var flag: Boolean = true
    var scrCG: CG? = null
    private val shakeNumber = 0
    private var sleep = 0
    private var sleepMax = 0
    var holder: SurfaceHolder? = null
    private val color: LColor? = null
    protected var mapView: MessageView? = null
    private var selectMessage: String? = null
    protected var select: SelectView? = null
    var isLocked: Boolean = false
    private var screenWidth = -1
    private var screenHeight = -1

    protected fun initByAuto() {
        initByCall()
    }

    protected fun initByCall() {
        initScript()

        this.delay = 30

        running = true

        // thread.start();
        initAVG()

        initViews((context as android.app.Activity?)!!)
    }

    protected fun initScript() {
        this.scriptName = "res/script/s2.txt"
    }

    protected fun initViews(context: Activity) {
        // AVGSreen messageBox = new AVGSreen(context,
        // message.getPrint().getView());

        val sreenView = AVGSreenView(context)

        val config = AssembleViewConfig.Builder()
            .setDirectionConfig(DirectionConfig.BOTTOM).build()

        mapView = message!!.getPrint().getView()

        mapView!!.setIsUseSelf(false)



        view = AssembleView(sreenView, context)
        view!!.setForceMainLayout(true)
        v = view!!.generateViews()
        val view = AssembleView(v, context)


        // AssembleView view2 = new AssembleView(mapView, context);
//				AssembleView view3 = new AssembleView(R.layout.game_key_controller,
//						context);
//				view3.setConfig(config);
//				int TheBellowView = 2;
//				view3.setId(TheBellowView);

//				 view.addSubView(view3);
//				view.addBelowView(view3, R.layout.game_key_controller);
        select = SelectView(context, message!!.x, message!!.y, message!!.width, message!!.height)
        select!!.setIsUseSelf(false)
        select!!.setTextSize(50)
        select!!.setTextColor(Color.WHITE)
        select!!.setSelects(selects)

        val v = view.generateViews()

        context.setContentView(v)
    }

    protected var view: AssembleView? = null
    protected var v: View? = null

    init {
        // TODO Auto-generated constructor stub
        this.context = context

        this.handler = LSystem.getSystemHandler()

        initByAuto()
    }

    private fun initAVG() {
        // this.initDesktop();
        // this.initMessageConfig(message);
        // this.initSelectConfig(select);

        initMessage()
        var size = message!!.getWidth() / (message!!.getMessageFont()!!.getSize())
        if (size % 2 != 0) {
            size = size - 3
        } else {
            size = size - 4
        }
        this.message!!.messageLength = size
        // this.message.setLocation((getCurrentWidth() - message.getWidth()) /
        // 2,
        // getCurrentHeight() - message.getHeight() - 10);
        // this.message.setVisible(false);
        // this.select = new LSelect(dialog, 0, 0);
        // if (flag) {
        // select.setAlpha(0.5F);
        // }
        // this.select.setLocation(message.x(), message.y());
        this.scrCG = CG()

        this.initCommandConfig(scriptName)
    }

    protected fun initMessage() {
        this.message = LMessage(
            context,
            GraphicsUtils.loadImage("res/frame.png"), 0, 0
        )
        if (flag) {
            message!!.setAlpha(0.5f)
        }
        this.message!!.setFontColor(LColor(100, 100, 100))
    }

    fun initCommandConfig(fileName: String?) {
        if (fileName == null) {
            return
        }
        Command.Companion.resetCache()
        if (command == null) {
            command = Command(fileName)
        } else {
            command!!.formatCommand(fileName)
        }
        initCommandConfig(command)
        nextScript()
    }

    fun paint(canvas: Canvas, paint: Paint?) {
        if (!running) {
            return
        }
        if (sleep == 0) {
            if (scrCG == null) {
                return
            }
            if (scrCG!!.getBackgroundCG() != null) {
                val graphicsObject = GraphicsObject(
                    canvas,
                    paint
                )

                if (shakeNumber > 0) {
                    canvas.drawBitmap(
                        scrCG!!.getBackgroundCG()!!, (shakeNumber / 2
                                - LSystem.random.nextInt(shakeNumber)).toFloat(), (shakeNumber
                                / 2 - LSystem.random.nextInt(shakeNumber)).toFloat(), paint
                    )
                } else {
                    // canvas.save();
                    // scrCG.setBackgroundCG(Bitmap.createScaledBitmap(scrCG.getBackgroundCG(),
                    // scrCG.getBackgroundCG().getWidth()*2,
                    // scrCG.getBackgroundCG().getHeight()*2, false));
                    // canvas.scale(3f, 3f, scrCG.getBackgroundCG().getWidth() /
                    // 2, scrCG.getBackgroundCG().getHeight() / 2);//
                    // X方向縮放比例，Y方向縮放比例，縮放中心X,縮放中心Y


                    val m = Matrix()


                    canvas.drawBitmap(
                        scrCG!!.getBackgroundCG()!!, 0f, 0f,
                        Paint()
                    )

                    // canvas.drawColor(Color.RED);

                    // canvas.restore();
                    // canvas.drawBitmap(scrCG.getBackgroundCG(), null, new
                    // RectF(0,0,800,1000), paint);
                }
            }
            var moveCount = 0
            for (i in 0..<scrCG!!.getCharas().size) {
                val chara = scrCG!!.getCharas().get(i) as Chara
                var value = 1.0f
                if (chara.next()) {
                    value = chara.getNextAlpha()
                    moveCount++
                }

                if (paint != null) {
                    MyMap.setInfo(320, 480, screenWidth, screenHeight)


//					MyMap.getWH(w, h).y;
                    paint.setAlpha((255 * value).toInt())

                    //					chara.draw(canvas);
                    val pointF: PointF?
                    val newW: Int
                    val newH: Int
                    if (chara.getWidth() > 320 / 2) {
                        pointF = MyMap.setImageXYByOldXY(chara.getMoveX(), chara.getY() / 2)
                        newW = MyMap.getWH(chara.getWidth() / 2, chara.getHeight() / 2).x
                        newH = MyMap.getWH(chara.getWidth() / 2, chara.getHeight() / 2).y
                    } else {
                        pointF = MyMap.setImageXYByOldXY(chara.getMoveX(), chara.getY() / 2)
                        newW = MyMap.getWH(chara.getWidth(), chara.getHeight()).x
                        newH = MyMap.getWH(chara.getWidth(), chara.getHeight()).y
                    }
                    chara.drawReSize(canvas, pointF!!.x, pointF.y, newW.toFloat(), newH.toFloat())
                    //					chara.drawReSize(canvas, pointF.x, pointF.y, MyMap.getWH(chara.getWidth(), chara.getHeight()).x, MyMap.getWH(chara.getWidth(), chara.getHeight()).y);
                    paint.setAlpha(255)
                } else {
                    MyMap.setInfo(320, 480, screenWidth, screenHeight)


//					chara.draw(canvas);
                    val pointF: PointF?
                    val newW: Int
                    val newH: Int
                    if (chara.getWidth() > 320 / 2) {
                        pointF = MyMap.setImageXYByOldXY(chara.getMoveX(), chara.getY() / 2)
                        newW = MyMap.getWH(chara.getWidth() / 2, chara.getHeight() / 2).x
                        newH = MyMap.getWH(chara.getWidth() / 2, chara.getHeight() / 2).y
                    } else {
                        pointF = MyMap.setImageXYByOldXY(chara.getMoveX(), chara.getY() / 2)
                        newW = MyMap.getWH(chara.getWidth(), chara.getHeight()).x
                        newH = MyMap.getWH(chara.getWidth(), chara.getHeight()).y
                    }
                    chara.drawReSize(canvas, pointF!!.x, pointF.y, newW.toFloat(), newH.toFloat())
                    //					chara.drawReSize(canvas, pointF.x, pointF.y, MyMap.getWH(chara.getWidth(), chara.getHeight()).x, MyMap.getWH(chara.getWidth(), chara.getHeight()).y);
                }
            }
            drawScreen(canvas, paint)
        } else {
            sleep--
            if (color != null) {
                val alpha = (sleepMax - sleep).toFloat() / sleepMax
                if (alpha < 1.0) {
                    if (scrCG!!.getBackgroundCG() != null) {
                        canvas.drawBitmap(scrCG!!.getBackgroundCG()!!, 0f, 0f, paint)
                    }
                    // LColor c = g.getColor();
                    paint!!.setColor(
                        LColor.Companion.getARGB(
                            color.getRed(),
                            color.getGreen(), color.getBlue(),
                            (255 * alpha).toInt()
                        )
                    )
                    // paint.setColor();
                    // g.fillRect(0, 0, getCurrentWidth(), getCurrentHeight());
                    // g.setColor(c);
                }
            }
        }
    }

    protected fun drawScreen(canvas: Canvas?, paint: Paint?) {
    }

    //	 public void initCommandConfig(final Command command){};
    fun nextScript(message: String?): Boolean {
        return true
    } // protected Sprites sprites;

    fun initCommandConfig(command: Command?) {
        // 初始化時預設變數
//		command.setVariable("p", "res/p.png");
//		command.setVariable("sel0", selects[0]);
    }

    @Synchronized
    fun nextScript() {
        if (command != null && running) {
            while (command!!.next().also { commandGo = it }) {
                val result = command!!.doExecute()
                if (result == null) {
                    nextScript()
                    break
                }
                if (!nextScript(result)) {
                    break
                }
                val commands: MutableList<*> = Conversion.Companion.splitToList(result, " ")
                val size = commands.size
                val cmdFlag = commands.get(0) as String

                var mesFlag: String? = null
                var orderFlag: String? = null
                var lastFlag: String? = null
                if (size == 2) {
                    mesFlag = commands.get(1) as String?
                } else if (size == 3) {
                    mesFlag = commands.get(1) as String?
                    orderFlag = commands.get(2) as String?
                } else if (size == 4) {
                    mesFlag = commands.get(1) as String?
                    orderFlag = commands.get(2) as String?
                    lastFlag = commands.get(3) as String?
                }
                if (cmdFlag.equals(CommandType.Companion.L_WAIT, ignoreCase = true)) {
                    scrFlag = true
                    break
                }
                if (cmdFlag.equals(CommandType.Companion.L_SNOW, ignoreCase = true)
                    || cmdFlag.equals(CommandType.Companion.L_RAIN, ignoreCase = true)
                    || cmdFlag.equals(CommandType.Companion.L_PETAL, ignoreCase = true)
                ) {
                    // if (sprites != null) {
                    // boolean flag = false;
                    // ISprite[] ss = sprites.getSprites();
                    // for (int i = 0; i < ss.length; i++) {
                    // ISprite s = ss[i];
                    // if (s instanceof FreedomEffect) {
                    // flag = true;
                    // break;
                    // }
                    // }
                    // if (!flag) {
                    // if (cmdFlag.equalsIgnoreCase(CommandType.L_SNOW)) {
                    // sprites.add(FreedomEffect.getSnowEffect());
                    // } else if (cmdFlag
                    // .equalsIgnoreCase(CommandType.L_RAIN)) {
                    // sprites.add(FreedomEffect.getRainEffect());
                    // } else if (cmdFlag
                    // .equalsIgnoreCase(CommandType.L_PETAL)) {
                    // sprites.add(FreedomEffect.getPetalEffect());
                    // }
                    // }
                    // }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_SNOWSTOP, ignoreCase = true)
                    || cmdFlag.equals(CommandType.Companion.L_RAINSTOP, ignoreCase = true)
                    || cmdFlag.equals(CommandType.Companion.L_PETALSTOP, ignoreCase = true)
                ) {
                    // if (sprites != null) {
                    // ISprite[] ss = sprites.getSprites();
                    // for (int i = 0; i < ss.length; i++) {
                    // ISprite s = ss[i];
                    // if (s instanceof FreedomEffect) {
                    // if (cmdFlag
                    // .equalsIgnoreCase(CommandType.L_SNOWSTOP)) {
                    // if (((FreedomEffect) s).getKernels()[0] instanceof
                    // SnowKernel) {
                    // sprites.remove(s);
                    // }
                    // } else if (cmdFlag
                    // .equalsIgnoreCase(CommandType.L_RAINSTOP)) {
                    // if (((FreedomEffect) s).getKernels()[0] instanceof
                    // RainKernel) {
                    // sprites.remove(s);
                    // }
                    // } else if (cmdFlag
                    // .equalsIgnoreCase(CommandType.L_PETALSTOP)) {
                    // if (((FreedomEffect) s).getKernels()[0] instanceof
                    // PetalKernel) {
                    // sprites.remove(s);
                    // }
                    // }
                    //
                    // }
                    // }
                    // }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_PLAY, ignoreCase = true)) {
                    playtAssetsMusic(mesFlag, false)
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_PLAYLOOP, ignoreCase = true)) {
                    playtAssetsMusic(mesFlag, true)
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_PLAYSTOP, ignoreCase = true)) {
                    if (NumberUtils.isNan(mesFlag)) {
                        stopAssetsMusic(mesFlag!!.toInt())
                    } else {
                        stopAssetsMusic()
                    }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_FADEOUT, ignoreCase = true)
                    || cmdFlag.equals(CommandType.Companion.L_FADEIN, ignoreCase = true)
                ) {
                    scrFlag = true
                    var color: LColor = LColor.Companion.black
                    if (mesFlag.equals("red", ignoreCase = true)) {
                        color = LColor.Companion.red
                    } else if (mesFlag.equals("yellow", ignoreCase = true)) {
                        color = LColor.Companion.yellow
                    } else if (mesFlag.equals("white", ignoreCase = true)) {
                        color = LColor.Companion.white
                    } else if (mesFlag.equals("black", ignoreCase = true)) {
                        color = LColor.Companion.black
                    } else if (mesFlag.equals("cyan", ignoreCase = true)) {
                        color = LColor.Companion.cyan
                    } else if (mesFlag.equals("green", ignoreCase = true)) {
                        color = LColor.Companion.green
                    } else if (mesFlag.equals("orange", ignoreCase = true)) {
                        color = LColor.Companion.orange
                    } else if (mesFlag.equals("pink", ignoreCase = true)) {
                        color = LColor.Companion.pink
                    }
                    // if (sprites != null) {
                    // sprites.removeAll();
                    // if (cmdFlag.equalsIgnoreCase(CommandType.L_FADEIN)) {
                    // sprites.add(Fade.getInstance(Fade.TYPE_FADE_IN,
                    // color));
                    // } else {
                    // sprites.add(Fade.getInstance(Fade.TYPE_FADE_OUT,
                    // color));
                    // }
                    // }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_SELLEN, ignoreCase = true)) {
                    if (mesFlag != null) {
                        if (NumberUtils.isNan(mesFlag)) {
                            // select.setLeftOffset(Integer.parseInt(mesFlag));
                        }
                    }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_SELTOP, ignoreCase = true)) {
                    if (mesFlag != null) {
                        if (NumberUtils.isNan(mesFlag)) {
                            // select.setTopOffset(Integer.parseInt(mesFlag));
                        }
                    }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_MESLEN, ignoreCase = true)) {
                    if (mesFlag != null) {
                        if (NumberUtils.isNan(mesFlag)) {
                            message!!.messageLength = mesFlag.toInt()
                        }
                    }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_MESTOP, ignoreCase = true)) {
                    if (mesFlag != null) {
                        if (NumberUtils.isNan(mesFlag)) {
                            message!!.topOffset = mesFlag.toInt()
                        }
                    }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_MESLEFT, ignoreCase = true)) {
                    if (mesFlag != null) {
                        if (NumberUtils.isNan(mesFlag)) {
                            message!!.leftOffset = mesFlag.toInt()
                        }
                    }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_MESCOLOR, ignoreCase = true)) {
                    if (mesFlag != null) {
                        if (mesFlag.equals("red", ignoreCase = true)) {
                            message!!.setFontColor(LColor.Companion.red)
                        } else if (mesFlag.equals("yellow", ignoreCase = true)) {
                            message!!.setFontColor(LColor.Companion.yellow)
                        } else if (mesFlag.equals("white", ignoreCase = true)) {
                            message!!.setFontColor(LColor.Companion.white)
                        } else if (mesFlag.equals("black", ignoreCase = true)) {
                            message!!.setFontColor(LColor.Companion.black)
                        } else if (mesFlag.equals("cyan", ignoreCase = true)) {
                            message!!.setFontColor(LColor.Companion.cyan)
                        } else if (mesFlag.equals("green", ignoreCase = true)) {
                            message!!.setFontColor(LColor.Companion.green)
                        } else if (mesFlag.equals("orange", ignoreCase = true)) {
                            message!!.setFontColor(LColor.Companion.orange)
                        } else if (mesFlag.equals("pink", ignoreCase = true)) {
                            message!!.setFontColor(LColor.Companion.pink)
                        }
                    }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_MES, ignoreCase = true)) {
                    // if (select.isVisible()) {
                    // select.setVisible(false);
                    // }
                    scrFlag = true
                    val nMessage = mesFlag
                    message!!.setMessage(StringUtils.Companion.replace(nMessage, "&", " "))
                    message!!.setVisible(true)
                    break
                }
                if (cmdFlag.equals(CommandType.Companion.L_MESSTOP, ignoreCase = true)) {
                    scrFlag = true
                    message!!.setVisible(false)
                    // select.setVisible(false);
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_SELECT, ignoreCase = true)) {
                    selectMessage = mesFlag
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_SELECTS, ignoreCase = true)) {
                    // if (message.isVisible()) {
                    // message.setVisible(false);
                    // }
                    select!!.setVisible(true)
                    scrFlag = true
                    isSelectMessage = true
                    val selects = command!!.getReads()
                    select!!.setMessage(selectMessage ?: "", Array(selects.size) { selects[it] ?: "" })
                    break
                }
                if (cmdFlag.equals(CommandType.Companion.L_SHAKE, ignoreCase = true)) {
                    // shakeNumber = Integer.valueOf(mesFlag).intValue();
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_CGWAIT, ignoreCase = true)) {
                    scrFlag = false
                    break
                }
                if (cmdFlag.equals(CommandType.Companion.L_SLEEP, ignoreCase = true)) {
                    sleep = mesFlag!!.toInt()
                    sleepMax = mesFlag.toInt()
                    scrFlag = false
                    break
                }
                if (cmdFlag.equals(CommandType.Companion.L_FLASH, ignoreCase = true)) {
                    scrFlag = true
                    val colors: Array<String?> =
                        mesFlag!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    // if (color == null && colors != null && colors.length ==
                    // 3) {
                    // color = new LColor(Integer.valueOf(colors[0])
                    // .intValue(), Integer.valueOf(colors[1])
                    // .intValue(), Integer.valueOf(colors[2])
                    // .intValue());
                    // sleep = 20;
                    // sleepMax = sleep;
                    // scrFlag = false;
                    // } else {
                    // color = null;
                    // }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_GB, ignoreCase = true)) {
                    if (mesFlag == null) {
                        return
                    }
                    if (mesFlag.equals("none", ignoreCase = true)) {
                        scrCG!!.noneBackgroundCG()
                    } else {
                        scrCG!!.setBackgroundCG(mesFlag)
                        var w = 0
                        var h = 0
                        if (screenWidth <= -1 && screenHeight <= -1) {
                            w = scrCG!!.getBackgroundCG()!!.getWidth()
                            h = scrCG!!.getBackgroundCG()!!.getHeight()
                        } else if (screenWidth <= -1) {
                            w = scrCG!!.getBackgroundCG()!!.getWidth()
                            h = screenHeight
                        } else if (screenHeight <= -1) {
                            w = screenWidth
                            h = scrCG!!.getBackgroundCG()!!.getHeight()
                        } else {
                            w = screenWidth
                            h = screenHeight
                        }


//						int w = scrCG.getBackgroundCG().getWidth();
                        scrCG!!.setBackgroundCG(
                            Bitmap.createScaledBitmap(
                                scrCG!!
                                    .getBackgroundCG()!!, w, h, false
                            )
                        )
                    }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_CG, ignoreCase = true)) {
                    if (mesFlag == null) {
                        return
                    }
                    if (mesFlag.equals(CommandType.Companion.L_DEL, ignoreCase = true)) {
                        if (orderFlag != null) {
                            scrCG!!.removeImage(orderFlag)
                        } else {
                            scrCG!!.clear()
                        }
                    } else if (lastFlag != null
                        && CommandType.Companion.L_TO.equals(orderFlag, ignoreCase = true)
                    ) {
                        var chara = scrCG!!.removeImage(mesFlag)
                        if (chara != null) {
                            val x = chara.getX()
                            val y = chara.getY()
                            chara = Chara(lastFlag, 0, 0, screenWidth)
                            chara.setMove(false)
                            chara.setX(x)
                            chara.y = y
                            scrCG!!.addChara(lastFlag, chara)
                        }
                    } else {
                        var x = 0
                        var y = 0
                        if (orderFlag != null) {
                            x = orderFlag.toInt()
                        }
                        if (size >= 4) {
                            y = (commands.get(3) as String?)!!.toInt()
                        }
                        scrCG!!.addImage(mesFlag, x, y, screenWidth)
                    }
                    continue
                }
                if (cmdFlag.equals(CommandType.Companion.L_EXIT, ignoreCase = true)) {
                    scrFlag = true
                    // setFPS(LSystem.DEFAULT_MAX_FPS);
                    running = false
                    // onExit();
                    break
                }
            }
        }
    }

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    // // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.main, menu);
    // return true;
    // }
    //
    // @Override
    // public boolean onOptionsItemSelected(MenuItem item) {
    // // Handle action bar item clicks here. The action bar will
    // // automatically handle clicks on the Home/Up button, so long
    // // as you specify a parent activity in AndroidManifest.xml.
    // int id = item.getItemId();
    // if (id == R.id.action_settings) {
    // return true;
    // }
    // return super.onOptionsItemSelected(item);
    // }
    /**
     * 播放Assets中的音訊檔
     * 
     * @param file
     * @param loop
     // */
    fun playtAssetsMusic(file: String?, loop: Boolean) {
        if (handler != null) {
            handler!!.getAssetsSound()!!.playSound(file, loop)
        }
    }

    /**
     * 設置Assets中的音訊檔音量
     * 
     * @param vol
     // */
    fun resetAssetsMusic(vol: Int) {
        if (handler != null) {
            handler!!.getAssetsSound()!!.setSoundVolume(vol)
        }
    }

    /**
     * 重置Assets中的音訊檔
     * 
     // */
    fun resetAssetsMusic() {
        if (handler != null) {
            handler!!.getAssetsSound()!!.resetSound()
        }
    }

    /**
     * 中斷Assets中的音訊檔
     // */
    fun stopAssetsMusic() {
        if (handler != null) {
            handler!!.getAssetsSound()!!.stopSound()
        }
    }

    /**
     * 中斷Assets中指定索引的音訊檔
     // */
    fun stopAssetsMusic(index: Int) {
        if (handler != null) {
            handler!!.getAssetsSound()!!.stopSound(index)
        }
    }

    fun setAVGScreenWH(screenWidth: Int, screenHeight: Int) {
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight
    }

    private fun bb(event: MotionEvent) {
        if (!running) {
            return
        }
        if (isLocked) {
            return
        }

        if (message!!.isVisible()) {
            message!!.onTouch(event)
        }
        if (select!!.isVisible) {
            select!!.onTouchEvent(event)
        }

        if (message!!.isVisible() && !message!!.isComplete) {
            return
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            val touchX = event.getX()
            val touchY = event.getY()

            // if ((touchX >= message.x && touchX <= message.x + message.width)
            // && (touchY >= message.y && touchY <= message.y
            // + message.height)) {
            var isNext = false
            if (!isSelectMessage && sleep <= 0) {
                if (!scrFlag) {
                    scrFlag = true
                }
                if (message!!.isVisible()) {
                    // isNext = message.intersects(touchX, touchY);
                    isNext = true
                } else {
                    isNext = true
                }
            } else if (scrFlag && select!!.getResultIndex() != -1) {
                onSelect(selectMessage, select!!.getResultIndex())
                isNext = this.intersects(touchX, touchY)
                message!!.setVisible(false)
                select!!.setVisible(false)
                isSelectMessage = false
                selectMessage = null
            }

            if (isNext && !isSelectMessage) {
                nextScript()
            }
        }
        // }
    }

    private fun intersects(x: Float, y: Float): Boolean {
        if ((x >= message!!.x && x <= message!!.x + message!!.width)
            && (y >= message!!.y && (y <= message!!.y
                    + message!!.height))
        ) {
            return true
        }
        return false
    }

    //	private int getResultIndex(){
    //		return selects.length;
    //	}
    fun onSelect(message: String?, type: Int) {
        if (selects[0].equals(message, ignoreCase = true)) {
            command!!.setVariable("sel0", type.toString())
        }
    }

    // private void aa(){
    // if (!running) {
    // return;
    // }
    // // if (locked) {
    // // return;
    // // }
    // if (message.isVisible() && !message.isComplete()) {
    // return;
    // }
    //
    // boolean isNext = false;
    // if (!isSelectMessage && sleep <= 0) {
    // if (!scrFlag) {
    // scrFlag = true;
    // }
    // if (message.isVisible()) {
    // isNext = message.intersects(getTouchX(), getTouchY());
    // } else {
    // isNext = true;
    // }
    // }
    // // else if (scrFlag && select.getResultIndex() != -1) {
    // // onSelect(selectMessage, select.getResultIndex());
    // // isNext = select.intersects(getTouchX(), getTouchY());
    // // message.setVisible(false);
    // // select.setVisible(false);
    // // isSelectMessage = false;
    // // selectMessage = null;
    // // }
    // if (isNext && !isSelectMessage) {
    // nextScript();
    // }
    // }
    inner class AVGSreenView(context: Activity?) : SurfaceView(context), SurfaceHolder.Callback {
        private var canvas: Canvas? = null
        var isUseSelf: Boolean = true

        fun setIsUseSelf(isUseSelf: Boolean) {
            this.isUseSelf = false
        }

        fun setCanvas(canvas: Canvas) {
            this.canvas = canvas
        }

        fun draw() {
            if (isUseSelf) {
                canvas = holder.lockCanvas()
                canvas!!.drawColor(Color.WHITE)
            }

            paint(canvas!!, null)

            val currentCanvas = canvas!!
            mapView!!.setCanvas(currentCanvas)
            mapView!!.draw()

            select!!.setCanvas(currentCanvas)
            select!!.draw()

            if (isUseSelf) holder!!.unlockCanvasAndPost(currentCanvas)
        }

        var thread: Thread = Thread(object : Runnable {
            override fun run() {
                while (running) {
                    // repaint();

                    // message.createCustomUI(g, x, y, w, h);

                    draw()

                    AVGUtils.pause(delay.toLong())
                    // if (desktop != null) {
                    // desktop.update(delay);
                    // }
                    // if (sprites != null) {
                    // sprites.update(delay);
                    // }
                }
            }
        })

        init {
            // TODO Auto-generated constructor stub
            this@AVGScreen.holder = getHolder()
            this@AVGScreen.holder!!.addCallback(this)

            // this.context = context;
            //
            // this.handler = LSystem.getSystemHandler();
            //
            // this.scriptName = "res/script/s1.txt";
            // this.delay = 30;
            //
            // running = true;
            // // thread.start();
            //
            // initAVG();

            // AVGSreen messageBox = new AVGSreen(context,
            // message.getPrint().getView());
            //
            // AssembleViewConfig config = new
            // AssembleViewConfig.Builder().setDirectionConfig(DirectionConfig.BOTTOM).build();
            // View mapView = msgview;
            // AssembleView view = new AssembleView(mapView , context);
            // AssembleView view2 = new
            // AssembleView(R.layout.game_key_controller,
            // context);
            // view2.setConfig(config);
            //
            // view.addBelowView(view2, R.layout.game_key_controller);
            //
            // context.setContentView(view.generateViews());
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            // TODO Auto-generated method stub

            bb(event)

            return super.onTouchEvent(event)
        }

        override fun surfaceChanged(
            arg0: SurfaceHolder, arg1: Int, arg2: Int,
            arg3: Int
        ) {
            // TODO Auto-generated method stub
        }

        override fun surfaceCreated(arg0: SurfaceHolder) {
            // TODO Auto-generated method stub
            if (isUseSelf) thread.start()
        }

        override fun surfaceDestroyed(arg0: SurfaceHolder) {
            // TODO Auto-generated method stub
        }
    }
}
