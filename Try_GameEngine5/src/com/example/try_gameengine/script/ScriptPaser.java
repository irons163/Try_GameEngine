package com.example.try_gameengine.script;

import java.util.Random;

import android.content.Context;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.viewport.FileUtil;

public class ScriptPaser {
	String move = "Move";
	String random = "R";
	String msg = "Msg";
	String pause = "Pause";
	String pauseFPS = "PauseFPS";
	String dir = "Dir";
	String loop = "Loop";
	
	boolean isScriptFinish = false;
	
	private String getScript(Context context, String scriptName){
		String s = FileUtil.readFileFromAssetsF(context, scriptName);
		return s;
	}
	
	private String[] splitLine(String s){
		return s.split("\n");
	}
	
	private String[] splitToken(String s){
		return s.split(" ");
	}
	
	Sprite sprite;
	String[] strLines;
	float dx, dy;
	String command;
	
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
					dx = random.nextInt( max - min ) + min;
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
	
	public boolean isPause(){
		return command.equals(pause);
	}
	
	public boolean isMove(){
		return command.equals(move);
	}
	
	public float getDx(){
		return dx;
	}
	
	public float getDy(){
		return dy;
	}
	
	public boolean isScriptFinish(){
		return isScriptFinish;
	}
	
	public void setDx(float dx){
		this.dx = dx;
	}
	
	public void setDy(float dy){
		this.dy = dy;
	}
	
	public interface ScriptTriggerLisener{
		void onTriggerBefforeCommand();
		void onTriggerAffterCommand();
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
	
	public void setScriptTriggerLisener(ScriptTriggerLisener scriptTriggerLisener){
		this.scriptTriggerLisener = scriptTriggerLisener;
	}
}
