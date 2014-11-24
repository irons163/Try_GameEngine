package com.example.try_gameengine.avg;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;

import com.example.try_gameengine.assemble.AssembleView;
import com.example.try_gameengine.assemble.AssembleViewConfig;
import com.example.try_gameengine.assemble.AssembleViewConfig.DirectionConfig;
import com.example.try_gameengine.avg.MessageBoxStringView.MessageView;
import com.example.try_gameengine.viewport.MyMap;

public class AVGScreen {
	protected Context context;

	protected Command command;
	private boolean isSelectMessage, scrFlag, commandGo;
	boolean running;
	protected LMessage message;
	SystemHandler handler;
	// protected LSelect select;
	protected String scriptName;
	int delay;
	protected String[] selects = { "鵬淩三千帥不帥？" };
	boolean flag = true;
	CG scrCG;
	private int shakeNumber, sleep, sleepMax;
	SurfaceHolder holder;
	private LColor color;
	protected MessageView mapView;
	private String selectMessage;
	protected SelectView select;
	boolean isLocked = false;
	private int screenWidth = -1;
	private int screenHeight = -1;
	
	public AVGScreen(Activity context) {
		// TODO Auto-generated constructor stub
		this.context = context;

		this.handler = LSystem.getSystemHandler();

		initByAuto();
		
		
	}
	
	protected void initByAuto(){
		initByCall();
	}
	
	protected void initByCall(){
		initScript();
		
		this.delay = 30;

		running = true;
		// thread.start();

		initAVG();

		initViews((Activity)context);
	}
	
	protected void initScript(){
		this.scriptName = "res/script/s2.txt";
	}
	
	protected void initViews(Activity context){
		// AVGSreen messageBox = new AVGSreen(context,
				// message.getPrint().getView());

				AVGSreenView sreenView = new AVGSreenView(context);

				AssembleViewConfig config = new AssembleViewConfig.Builder()
						.setDirectionConfig(DirectionConfig.BOTTOM).build();

				mapView = message.getPrint().getView();

				mapView.setIsUseSelf(false);

				
				
				view = new AssembleView(sreenView, context);
				view.setForceMainLayout(true);
				v = view.generateViews();
				AssembleView view = new AssembleView(v, context);
				
				// AssembleView view2 = new AssembleView(mapView, context);
//				AssembleView view3 = new AssembleView(R.layout.game_key_controller,
//						context);
//				view3.setConfig(config);
//				int TheBellowView = 2;
//				view3.setId(TheBellowView);
				
//				 view.addSubView(view3);
//				view.addBelowView(view3, R.layout.game_key_controller);

				select = new SelectView(context, message.x, message.y, message.width, message.height);
				select.setIsUseSelf(false);
				select.setTextSize(50);
				select.setTextColor(Color.WHITE);
				select.setSelects(selects);
				
				View v = view.generateViews();
				
				context.setContentView(v);
	}
	protected AssembleView view;
	protected View v;
	
	private void initAVG() {
		// this.initDesktop();
		// this.initMessageConfig(message);
		// this.initSelectConfig(select);

		initMessage();
		int size = message.getWidth() / (message.getMessageFont().getSize());
		if (size % 2 != 0) {
			size = size - 3;
		} else {
			size = size - 4;
		}
		this.message.setMessageLength(size);
		// this.message.setLocation((getCurrentWidth() - message.getWidth()) /
		// 2,
		// getCurrentHeight() - message.getHeight() - 10);
		// this.message.setVisible(false);
		// this.select = new LSelect(dialog, 0, 0);
		// if (flag) {
		// select.setAlpha(0.5F);
		// }
		// this.select.setLocation(message.x(), message.y());
		this.scrCG = new CG();

		this.initCommandConfig(scriptName);

	}
	
	protected void initMessage(){
		this.message = new LMessage(context,
				GraphicsUtils.loadImage("res/frame.png"), 0, 0);
		if (flag) {
			message.setAlpha(0.5F);
		}
		this.message.setFontColor(new LColor(100, 100, 100));
	}

	public void initCommandConfig(String fileName) {
		if (fileName == null) {
			return;
		}
		Command.resetCache();
		if (command == null) {
			command = new Command(fileName);
		} else {
			command.formatCommand(fileName);
		}
		initCommandConfig(command);
		nextScript();
	}

	final public void paint(Canvas canvas, Paint paint) {
		if (!running) {
			return;
		}
		if (sleep == 0) {
			if (scrCG == null) {
				return;
			}
			if (scrCG.getBackgroundCG() != null) {
				GraphicsObject graphicsObject = new GraphicsObject(canvas,
						paint);

				if (shakeNumber > 0) {
					canvas.drawBitmap(scrCG.getBackgroundCG(), shakeNumber / 2
							- LSystem.random.nextInt(shakeNumber), shakeNumber
							/ 2 - LSystem.random.nextInt(shakeNumber), paint);
				} else {
					// canvas.save();
					// scrCG.setBackgroundCG(Bitmap.createScaledBitmap(scrCG.getBackgroundCG(),
					// scrCG.getBackgroundCG().getWidth()*2,
					// scrCG.getBackgroundCG().getHeight()*2, false));
					// canvas.scale(3f, 3f, scrCG.getBackgroundCG().getWidth() /
					// 2, scrCG.getBackgroundCG().getHeight() / 2);//
					// X方向縮放比例，Y方向縮放比例，縮放中心X,縮放中心Y
					
					
					
					Matrix m = new Matrix();
					
					
					canvas.drawBitmap(scrCG.getBackgroundCG(), 0, 0,
							new Paint());

					// canvas.drawColor(Color.RED);

					// canvas.restore();
					// canvas.drawBitmap(scrCG.getBackgroundCG(), null, new
					// RectF(0,0,800,1000), paint);
				}
			}
			int moveCount = 0;
			for (int i = 0; i < scrCG.getCharas().size(); i++) {
				Chara chara = (Chara) scrCG.getCharas().get(i);
				float value = 1.0f;
				if (chara.next()) {
					value = chara.getNextAlpha();
					moveCount++;
				}

				if (paint != null) {
					MyMap.setInfo(320,480 , screenWidth, screenHeight);
					
					
//					MyMap.getWH(w, h).y;
					
					paint.setAlpha((int) (255 * value));
//					chara.draw(canvas);
					
					PointF pointF;
					int newW, newH;
					if(chara.getWidth()>320/2){
						pointF = MyMap.setImageXYByOldXY(chara.getMoveX(), chara.getY()/2);
						newW = MyMap.getWH(chara.getWidth()/2, chara.getHeight()/2).x;
						newH = MyMap.getWH(chara.getWidth()/2, chara.getHeight()/2).y;
					}else{
						pointF = MyMap.setImageXYByOldXY(chara.getMoveX(), chara.getY()/2);
						newW = MyMap.getWH(chara.getWidth(), chara.getHeight()).x;
						newH = MyMap.getWH(chara.getWidth(), chara.getHeight()).y;
					}
					chara.drawReSize(canvas, pointF.x, pointF.y, newW, newH);
//					chara.drawReSize(canvas, pointF.x, pointF.y, MyMap.getWH(chara.getWidth(), chara.getHeight()).x, MyMap.getWH(chara.getWidth(), chara.getHeight()).y);
					paint.setAlpha(255);
				} else {
					MyMap.setInfo(320,480 , screenWidth, screenHeight);
					
//					chara.draw(canvas);
					PointF pointF;
					int newW, newH;
					if(chara.getWidth()>320/2){
						pointF = MyMap.setImageXYByOldXY(chara.getMoveX(), chara.getY()/2);
						newW = MyMap.getWH(chara.getWidth()/2, chara.getHeight()/2).x;
						newH = MyMap.getWH(chara.getWidth()/2, chara.getHeight()/2).y;
					}else{
						pointF = MyMap.setImageXYByOldXY(chara.getMoveX(), chara.getY()/2);
						newW = MyMap.getWH(chara.getWidth(), chara.getHeight()).x;
						newH = MyMap.getWH(chara.getWidth(), chara.getHeight()).y;
					}
					chara.drawReSize(canvas, pointF.x, pointF.y, newW, newH);
//					chara.drawReSize(canvas, pointF.x, pointF.y, MyMap.getWH(chara.getWidth(), chara.getHeight()).x, MyMap.getWH(chara.getWidth(), chara.getHeight()).y);
				}

			}
			 drawScreen(canvas, paint);

		} else {
			sleep--;
			if (color != null) {
				float alpha = (float) (sleepMax - sleep) / sleepMax;
				if (alpha < 1.0) {
					if (scrCG.getBackgroundCG() != null) {
						canvas.drawBitmap(scrCG.getBackgroundCG(), 0, 0, paint);
					}
					// LColor c = g.getColor();
					paint.setColor(LColor.getARGB(color.getRed(),
							color.getGreen(), color.getBlue(),
							(int) (255 * alpha)));
					// paint.setColor();
					// g.fillRect(0, 0, getCurrentWidth(), getCurrentHeight());
					// g.setColor(c);
				}
			}
		}
	}
	
	protected void drawScreen(Canvas canvas, Paint paint){
		
	}

//	 public void initCommandConfig(final Command command){};
	public boolean nextScript(String message) {
		return true;
	};

	// protected Sprites sprites;

	public void initCommandConfig(Command command) {
		// 初始化時預設變數
//		command.setVariable("p", "res/p.png");
//		command.setVariable("sel0", selects[0]);
	}

	public synchronized void nextScript() {
		if (command != null && running) {
			for (; commandGo = command.next();) {
				String result = command.doExecute();
				if (result == null) {
					nextScript();
					break;
				}
				if (!nextScript(result)) {
					break;
				}
				List<?> commands = Command.splitToList(result, " ");
				int size = commands.size();
				String cmdFlag = (String) commands.get(0);

				String mesFlag = null, orderFlag = null, lastFlag = null;
				if (size == 2) {
					mesFlag = (String) commands.get(1);
				} else if (size == 3) {
					mesFlag = (String) commands.get(1);
					orderFlag = (String) commands.get(2);
				} else if (size == 4) {
					mesFlag = (String) commands.get(1);
					orderFlag = (String) commands.get(2);
					lastFlag = (String) commands.get(3);
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_WAIT)) {
					scrFlag = true;
					break;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SNOW)
						|| cmdFlag.equalsIgnoreCase(CommandType.L_RAIN)
						|| cmdFlag.equalsIgnoreCase(CommandType.L_PETAL)) {
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
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SNOWSTOP)
						|| cmdFlag.equalsIgnoreCase(CommandType.L_RAINSTOP)
						|| cmdFlag.equalsIgnoreCase(CommandType.L_PETALSTOP)) {
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
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_PLAY)) {
					playtAssetsMusic(mesFlag, false);
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_PLAYLOOP)) {
					playtAssetsMusic(mesFlag, true);
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_PLAYSTOP)) {
					if (NumberUtils.isNan(mesFlag)) {
						stopAssetsMusic(Integer.parseInt(mesFlag));
					} else {
						stopAssetsMusic();
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_FADEOUT)
						|| cmdFlag.equalsIgnoreCase(CommandType.L_FADEIN)) {
					scrFlag = true;
					LColor color = LColor.black;
					if (mesFlag.equalsIgnoreCase("red")) {
						color = LColor.red;
					} else if (mesFlag.equalsIgnoreCase("yellow")) {
						color = LColor.yellow;
					} else if (mesFlag.equalsIgnoreCase("white")) {
						color = LColor.white;
					} else if (mesFlag.equalsIgnoreCase("black")) {
						color = LColor.black;
					} else if (mesFlag.equalsIgnoreCase("cyan")) {
						color = LColor.cyan;
					} else if (mesFlag.equalsIgnoreCase("green")) {
						color = LColor.green;
					} else if (mesFlag.equalsIgnoreCase("orange")) {
						color = LColor.orange;
					} else if (mesFlag.equalsIgnoreCase("pink")) {
						color = LColor.pink;
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
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SELLEN)) {
					if (mesFlag != null) {
						if (NumberUtils.isNan(mesFlag)) {
							// select.setLeftOffset(Integer.parseInt(mesFlag));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SELTOP)) {
					if (mesFlag != null) {
						if (NumberUtils.isNan(mesFlag)) {
							// select.setTopOffset(Integer.parseInt(mesFlag));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MESLEN)) {
					if (mesFlag != null) {
						if (NumberUtils.isNan(mesFlag)) {
							message.setMessageLength(Integer.parseInt(mesFlag));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MESTOP)) {
					if (mesFlag != null) {
						if (NumberUtils.isNan(mesFlag)) {
							message.setTopOffset(Integer.parseInt(mesFlag));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MESLEFT)) {
					if (mesFlag != null) {
						if (NumberUtils.isNan(mesFlag)) {
							message.setLeftOffset(Integer.parseInt(mesFlag));
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MESCOLOR)) {
					if (mesFlag != null) {
						if (mesFlag.equalsIgnoreCase("red")) {
							message.setFontColor(LColor.red);
						} else if (mesFlag.equalsIgnoreCase("yellow")) {
							message.setFontColor(LColor.yellow);
						} else if (mesFlag.equalsIgnoreCase("white")) {
							message.setFontColor(LColor.white);
						} else if (mesFlag.equalsIgnoreCase("black")) {
							message.setFontColor(LColor.black);
						} else if (mesFlag.equalsIgnoreCase("cyan")) {
							message.setFontColor(LColor.cyan);
						} else if (mesFlag.equalsIgnoreCase("green")) {
							message.setFontColor(LColor.green);
						} else if (mesFlag.equalsIgnoreCase("orange")) {
							message.setFontColor(LColor.orange);
						} else if (mesFlag.equalsIgnoreCase("pink")) {
							message.setFontColor(LColor.pink);
						}
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MES)) {
					// if (select.isVisible()) {
					// select.setVisible(false);
					// }
					scrFlag = true;
					String nMessage = mesFlag;
					message.setMessage(StringUtils.replace(nMessage, "&", " "));
					message.setVisible(true);
					break;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_MESSTOP)) {
					scrFlag = true;
					message.setVisible(false);
					// select.setVisible(false);
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SELECT)) {
					 selectMessage = mesFlag;
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SELECTS)) {
					// if (message.isVisible()) {
					// message.setVisible(false);
					// }
					 select.setVisible(true);
					scrFlag = true;
					isSelectMessage = true;
					String[] selects = command.getReads();
					select.setMessage(selectMessage, selects);
					break;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SHAKE)) {
					// shakeNumber = Integer.valueOf(mesFlag).intValue();
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_CGWAIT)) {
					scrFlag = false;
					break;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_SLEEP)) {
					sleep = Integer.valueOf(mesFlag).intValue();
					sleepMax = Integer.valueOf(mesFlag).intValue();
					scrFlag = false;
					break;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_FLASH)) {
					scrFlag = true;
					String[] colors = mesFlag.split(",");
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
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_GB)) {

					if (mesFlag == null) {
						return;
					}
					if (mesFlag.equalsIgnoreCase("none")) {
						scrCG.noneBackgroundCG();
					} else {
						scrCG.setBackgroundCG(mesFlag);
						int w = 0, h = 0;
						if(screenWidth<=-1 && screenHeight<=-1){
							w = scrCG.getBackgroundCG().getWidth();
							h = scrCG.getBackgroundCG().getHeight();
						}else if(screenWidth<=-1){
							w = scrCG.getBackgroundCG().getWidth();
							h = screenHeight;
						}else if(screenHeight<=-1){
							w = screenWidth;
							h = scrCG.getBackgroundCG().getHeight();
						}
						else{
							w = screenWidth;
							h = screenHeight;
						}
						
//						int w = scrCG.getBackgroundCG().getWidth();
						scrCG.setBackgroundCG(Bitmap.createScaledBitmap(scrCG
								.getBackgroundCG(), w, h, false));
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_CG)) {

					if (mesFlag == null) {
						return;
					}
					if (mesFlag.equalsIgnoreCase(CommandType.L_DEL)) {
						if (orderFlag != null) {
							scrCG.removeImage(orderFlag);
						} else {
							scrCG.clear();
						}
					} else if (lastFlag != null
							&& CommandType.L_TO.equalsIgnoreCase(orderFlag)) {
						Chara chara = scrCG.removeImage(mesFlag);
						if (chara != null) {
							int x = chara.getX();
							int y = chara.getY();
							chara = new Chara(lastFlag, 0, 0, screenWidth);
							chara.setMove(false);
							chara.setX(x);
							chara.setY(y);
							scrCG.addChara(lastFlag, chara);
						}
					} else {
						int x = 0, y = 0;
						if (orderFlag != null) {
							x = Integer.parseInt(orderFlag);
						}
						if (size >= 4) {
							y = Integer.parseInt((String) commands.get(3));
						}
						scrCG.addImage(mesFlag, x, y, screenWidth);
					}
					continue;
				}
				if (cmdFlag.equalsIgnoreCase(CommandType.L_EXIT)) {
					scrFlag = true;
					// setFPS(LSystem.DEFAULT_MAX_FPS);
					running = false;
					// onExit();
					break;
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
	 */
	public void playtAssetsMusic(final String file, final boolean loop) {
		if (handler != null) {
			handler.getAssetsSound().playSound(file, loop);
		}
	}

	/**
	 * 設置Assets中的音訊檔音量
	 * 
	 * @param vol
	 */
	public void resetAssetsMusic(final int vol) {
		if (handler != null) {
			handler.getAssetsSound().setSoundVolume(vol);
		}
	}

	/**
	 * 重置Assets中的音訊檔
	 * 
	 */
	public void resetAssetsMusic() {
		if (handler != null) {
			handler.getAssetsSound().resetSound();
		}
	}

	/**
	 * 中斷Assets中的音訊檔
	 */
	public void stopAssetsMusic() {
		if (handler != null) {
			handler.getAssetsSound().stopSound();
		}
	}

	/**
	 * 中斷Assets中指定索引的音訊檔
	 */
	public void stopAssetsMusic(int index) {
		if (handler != null) {
			handler.getAssetsSound().stopSound(index);
		}
	}
	
	public void setAVGScreenWH(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	private void bb(MotionEvent event) {
		if (!running) {
			return;
		}
		 if (isLocked) {
		 return;
		 }
		
		if(message.isVisible()){
			message.onTouch(event);
		}
		if(select.isVisible){
			select.onTouchEvent(event);
		}
		
		if (message.isVisible() && !message.isComplete()) {
			return;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float touchX = event.getX();
			float touchY = event.getY();

			// if ((touchX >= message.x && touchX <= message.x + message.width)
			// && (touchY >= message.y && touchY <= message.y
			// + message.height)) {

			boolean isNext = false;
			if (!isSelectMessage && sleep <= 0) {
				if (!scrFlag) {
					scrFlag = true;
				}
				if (message.isVisible()) {
					// isNext = message.intersects(touchX, touchY);
					isNext = true;
				} else {
					isNext = true;
				}
			}
			 else if (scrFlag && select.getResultIndex() != -1) {
			 onSelect(selectMessage, select.getResultIndex());
			 isNext = this.intersects(touchX, touchY);
			 message.setVisible(false);
			 select.setVisible(false);
			 isSelectMessage = false;
			 selectMessage = null;
			 }
			
			if (isNext && !isSelectMessage) {
				nextScript();
			}

		}
		// }
	}
	
	private boolean intersects(float x , float y){
		 if ((x >= message.x && x <= message.x + message.width)
					 && (y >= message.y && y <= message.y
					 + message.height)) {
			 return true;
		 }
		return false;
	}

//	private int getResultIndex(){
//		return selects.length;
//	}
	
	public void onSelect(String message, int type) {
		if (selects[0].equalsIgnoreCase(message)) {
			command.setVariable("sel0", String.valueOf(type));
		}
	}
	
	public void setLocked(boolean isLocked){
		this.isLocked = isLocked;
	}
	
	public boolean isLocked(){
		return isLocked;
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

	public class AVGSreenView extends SurfaceView implements Callback {
		private Canvas canvas;
		boolean isUseSelf = true;
		
		public AVGSreenView(Activity context) {
			// TODO Auto-generated constructor stub
			super(context);

			holder = getHolder();
			holder.addCallback(this);

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
		
		public void setIsUseSelf(boolean isUseSelf){
			this.isUseSelf = false;
		}
		
		public void setCanvas(Canvas canvas){
			this.canvas = canvas;
		}

		public void draw() {
			if(isUseSelf){
				canvas = holder.lockCanvas();
				canvas.drawColor(Color.WHITE);
			}

			paint(canvas, null);

			mapView.setCanvas(canvas);
			mapView.draw();

			select.setCanvas(canvas);
			select.draw();
			
			if(isUseSelf)
				holder.unlockCanvasAndPost(canvas);
		}

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				while (running) {
					// repaint();

					// message.createCustomUI(g, x, y, w, h);

					draw();

					AVGUtils.pause(delay);
					// if (desktop != null) {
					// desktop.update(delay);
					// }
					// if (sprites != null) {
					// sprites.update(delay);
					// }
				}
			}
		});

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub

			bb(event);

			return super.onTouchEvent(event);
		}

		@Override
		public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void surfaceCreated(SurfaceHolder arg0) {
			// TODO Auto-generated method stub
			if(isUseSelf)
				thread.start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder arg0) {
			// TODO Auto-generated method stub

		}

	}
}

