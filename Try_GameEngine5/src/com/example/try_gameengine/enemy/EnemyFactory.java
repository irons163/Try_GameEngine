package com.example.try_gameengine.enemy;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import android.graphics.Bitmap;

import com.example.try_gameengine.action.LRMovementActionFactory;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionFactory;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.MovementDecorator;
import com.example.try_gameengine.action.RLMovementActionFactory;

public class EnemyFactory {
	private static Bitmap redEnemyBitmap;
	private static Bitmap blueEnemyBitmap;
	
	public Enemy createRedEnemy() {
		return new RedEnemy(0, 0);
	}

	public Enemy createBlueEnemy() {
		return new BlueEnemy(10, 10);
	}

	public Enemy createRedEnemy(int[] enemyInfo){
		return new RedEnemy(enemyInfo[0], enemyInfo[1]);
	}

	public Enemy createBlueEnemy(int[] enemyInfo) {
		return new BlueEnemy(10, 10);
	}
	
	public Enemy createRLRedEnemy(int[] enemyInfo){
		return new RedEnemy(enemyInfo[0], enemyInfo[1], new RLMovementActionFactory().createMovementAction().initMovementAction());
	}
	
	public Enemy createLRRedEnemy(int[] enemyInfo){
		return new RedEnemy(enemyInfo[0], enemyInfo[1], new LRMovementActionFactory().createMovementAction().initMovementAction());
	}
	
	public Enemy createRLBlueEnemy(int[] enemyInfo){
		return new BlueEnemy(enemyInfo[0], enemyInfo[1], new RLMovementActionFactory().createMovementAction().initMovementAction());
	}
	
	public Enemy createLRBlueEnemy(int[] enemyInfo){
		return new BlueEnemy(enemyInfo[0], enemyInfo[1], new LRMovementActionFactory().createMovementAction().initMovementAction());
	}
	
	public Enemy createSpecialEnemy(Class<? extends Enemy> enemyClass, Class<? extends MovementActionFactory> actionFactoryClass,int[] enemyInfo){
		Enemy enemy = null;
		MovementAction action = null;
		try {
			if(actionFactoryClass!=null)
				action = actionFactoryClass.newInstance().createMovementAction().initMovementAction();
			enemy = enemyClass.getConstructor(int.class, int.class, MovementAction.class).newInstance(enemyInfo[0], enemyInfo[1], action);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enemy;
	}
	
	public Enemy createSpecialEnemy2(Class<? extends Enemy> enemyClass, Class<? extends MovementActionFactory> actionFactoryClass,int[] enemyInfo, List<MovementActionInfo> infos){
		Enemy enemy = null;
		MovementAction action = null;
		try {
			if(actionFactoryClass!=null)
				action = actionFactoryClass.newInstance().createMovementAction(infos).initMovementAction();
			enemy = enemyClass.getConstructor(int.class, int.class, MovementAction.class).newInstance(enemyInfo[0], enemyInfo[1], action);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enemy;
	}
	
	public Enemy createSpecialEnemy3(Class<? extends Enemy> enemyClass, Class<? extends MovementActionFactory> actionFactoryClass,int[] enemyInfo, List<MovementActionInfo> infos, List<Class<? extends MovementDecorator>> decoratorClassList){
		Enemy enemy = null;
		MovementAction action = null;
		try {
			if(actionFactoryClass!=null)
				action = actionFactoryClass.newInstance().createMovementAction(infos, decoratorClassList).initMovementAction();
			enemy = enemyClass.getConstructor(int.class, int.class, MovementAction.class).newInstance(enemyInfo[0], enemyInfo[1], action);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enemy;
	}
	
	public Enemy createSpecialEnemy4(Class<? extends Enemy> enemyClass, Class<? extends MovementActionFactory> actionFactoryClass,int[] enemyInfo, List<Class<? extends MovementDecorator>> decoratorClassList){
		Enemy enemy = null;
		MovementAction action = null;
		try {
			if(actionFactoryClass!=null)
				action = actionFactoryClass.newInstance().createMovementActionByDecorator(decoratorClassList).initMovementAction();
			enemy = enemyClass.getConstructor(int.class, int.class, MovementAction.class).newInstance(enemyInfo[0], enemyInfo[1], action);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enemy;
	}
	
	public Enemy createSpecialEnemy5(Class<? extends Enemy> enemyClass, int[] enemyInfo, MovementAction action){
		Enemy enemy = null;
		try {
			enemy = enemyClass.getConstructor(int.class, int.class, MovementAction.class).newInstance(enemyInfo[0], enemyInfo[1], action.initMovementAction());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enemy;
	}
	
	public static void setRedEnemyBitmap(Bitmap redEnemyBitmap){
		EnemyFactory.redEnemyBitmap = redEnemyBitmap;
	}
	
	public static void setBlueEnemyBitmap(Bitmap blueEnemyBitmap){
		EnemyFactory.blueEnemyBitmap = blueEnemyBitmap;
	}
	
	public static Bitmap getRedEnemyBitmap(){
		return EnemyFactory.redEnemyBitmap;
	}
	
	public static Bitmap getBlueEnemyBitmap(){
		return EnemyFactory.blueEnemyBitmap;
	}
}
