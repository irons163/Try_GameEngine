package com.example.try_gameengine.script;

import java.util.Random;

import android.content.Context;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.viewport.FileUtil;

/**
 * ScriptPaser is paser to pase the txt for script. 
 * @author irons
 *
 */
public class ScriptPaser {
	String move = "Move";
	String random = "R";
	String msg = "Msg";
	String pause = "Pause";
	String pauseFPS = "PauseFPS";
	String dir = "Dir";
	String loop = "Loop";
	
	boolean isScriptFinish = false;
	
	/**
	 * @param context
	 * @param scriptName
	 * @return
	 */
	private String getScript(Context context, String scriptName){
		String s = FileUtil.readFileFromAssetsF(context, scriptName);
		return s;
	}
	
	/**
	 * @param s
	 * @return
	 */
	private String[] splitLine(String s){
		return s.split("\n");
	}
	
	/**
	 * @param s
	 * @return
	 */
	private String[] splitToken(String s){
		return s.split(" ");
	}
	
	Sprite sprite;
	String[] strLines;
	float dx, dy;
	String command;
	
	/**
	 * @param context
	 * @param sprite
	 * @param scriptName
	 */
	public void paser(Context context, Sprite sprite, String scriptName){
		
		this.sprite = sprite;
		String text = getScript(context, scriptName);
		
		strLines = splitLine(text);
	
	}
	
	boolean canGoNextScriptLine = true;
	
	int lineIndex = 0;
	int triggerCount = 0;
	int triggerLimit = 0;
	int triggerCycle = 0;
	int pauseCount = 0;
	
	/**
	 * 
	 */
	public void nextScriptLine(){
		
		if(canGoNextScriptLine){
		
		if(lineIndex == strLines.length){
			command = "";
			isScriptFinish = true;
			return;
		}
			
		String s = strLines[lineIndex];
		
		lineIndex++;
		canGoNextScriptLine = false;
		
			String[] str = splitToken(s);
			command = str[0];
			
			if(command.equals(move)){
				
				if(!str[1].contains(random)){
					String[] range = str[1].split(",");
					dx = Float.parseFloat(range[0]);
				}else{
					String[] range = str[1].substring(1).split(",");
					Random random = new Random();
					int min = Integer.parseInt(range[0]);
					int max = Integer.parseInt(range[1]);
					dx = random.nextInt( max - min +1 ) + min;
					
					if(range.length>2){
						int minLinit = Integer.parseInt(range[2]);
						if(Math.abs(dx) < minLinit){
							if(dx>0){
								dx += minLinit;
							}else if(dx<0){
								dx -= minLinit;
							}else{
								int type = random.nextInt(2);
								if(type==0){
									dx += minLinit;
								}else{
									dx -= minLinit;
								}
							}
						}
					}
//					dx = Float.parseFloat(random);
				}
				
				if(!str[2].contains(random)){
					String[] range = str[2].split(",");
					dy = Float.parseFloat(range[1]);
				}else{
					String[] range = str[2].substring(1).split(",");
					Random random = new Random();
					int min = Integer.parseInt(range[0]);
					int max = Integer.parseInt(range[1]);
					dy = random.nextInt( max - min ) + min;
//					dx = Float.parseFloat(random);
				}
				
				triggerLimit = Integer.parseInt(str[3]);
				triggerCycle = Integer.parseInt(str[4]);
				
			}else if(command.equals(dir)){
				String dir = str[1];
				sprite.setAction(dir);
			}else if(command.equals(pause)){
				String dir = str[1];
				triggerLimit = Integer.parseInt(dir);
			}else if(command.equals(loop)){
				lineIndex = 0;
				canGoNextScriptLine = true;
			}
			
			
		}
	}
	
	public void trigger(Sprite sprite){		
		if(command.equals(move)){
			if(triggerCount!=0 && triggerCount%triggerLimit==0){
				scriptTriggerLisener.onTriggerBefforeCommand();
				sprite.move(dx, dy);
				scriptTriggerLisener.onTriggerAffterCommand();
				if(triggerCount==triggerLimit*triggerCycle)
					canGoNextScriptLine = true;
			}		
		}else if(command.equals(pause)){
			if(triggerCount==triggerLimit){
				scriptTriggerLisener.onTriggerBefforeCommand();
				scriptTriggerLisener.onTriggerAffterCommand();
				canGoNextScriptLine = true;
			}
		}
		
		triggerCount++;
		
		if(canGoNextScriptLine){
			triggerCount=0;
		}
	}
	
	/**
	 * 
	 */
	public void triggerAndDoCommandInSprite(){		
		if(command.equals(move)){
			if(triggerCount!=0 && triggerCount%triggerLimit==0){
				scriptTriggerLisener.onTriggerBefforeCommand();
//				sprite.move(dx, dy);
				scriptTriggerDoCommandLisener.onCommandMove(dx, dy);
				scriptTriggerLisener.onTriggerAffterCommand();
				if(triggerCount==triggerLimit*triggerCycle)
					canGoNextScriptLine = true;
			}		
		}else if(command.equals(pause)){
			if(triggerCount==triggerLimit){
				scriptTriggerLisener.onTriggerBefforeCommand();
				scriptTriggerDoCommandLisener.onCommandPause();
				scriptTriggerLisener.onTriggerAffterCommand();
				canGoNextScriptLine = true;
			}
		}
		
		triggerCount++;
		
		if(canGoNextScriptLine){
			triggerCount=0;
		}
	}
	
	/**
	 * @return
	 */
	public boolean isPause(){
		return command.equals(pause);
	}
	
	/**
	 * @return
	 */
	public boolean isMove(){
		return command.equals(move);
	}
	
	/**
	 * @return
	 */
	public float getDx(){
		return dx;
	}
	
	/**
	 * @return
	 */
	public float getDy(){
		return dy;
	}
	
	/**
	 * @return
	 */
	public boolean isScriptFinish(){
		return isScriptFinish;
	}
	
	/**
	 * @param dx
	 */
	public void setDx(float dx){
		this.dx = dx;
	}
	
	/**
	 * @param dy
	 */
	public void setDy(float dy){
		this.dy = dy;
	}
	
	/**
	 * @author irons
	 *
	 */
	public interface ScriptTriggerLisener{
		void onTriggerBefforeCommand();
		void onTriggerAffterCommand();
	}
	
	/**
	 * this listener used for script trigger do command.
	 * @author irons
	 *
	 */
	public interface ScriptTriggerDoCommandLisener{
		void onCommandMove(float dx, float dy);
		void onCommandPause();
	}
	
	ScriptTriggerLisener scriptTriggerLisener = new ScriptTriggerLisener() {
		
		@Override
		public void onTriggerBefforeCommand() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTriggerAffterCommand() {
			// TODO Auto-generated method stub
			
		}
	};
	
	ScriptTriggerDoCommandLisener scriptTriggerDoCommandLisener = new ScriptTriggerDoCommandLisener() {
		
		@Override
		public void onCommandMove(float dx, float dy) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onCommandPause() {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**
	 * set listener.
	 * @param scriptTriggerLisener
	 */
	public void setScriptTriggerLisener(ScriptTriggerLisener scriptTriggerLisener){
		this.scriptTriggerLisener = scriptTriggerLisener;
	}
	
	/**
	 * get listener.
	 * @param scriptTriggerDoCommandLisener
	 */
	public void setScriptTriggerDoCommandLisener(ScriptTriggerDoCommandLisener scriptTriggerDoCommandLisener){
		this.scriptTriggerDoCommandLisener = scriptTriggerDoCommandLisener;
	}
}
