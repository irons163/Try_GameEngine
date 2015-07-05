package com.example.try_gameengine.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;

import com.example.try_gameengine.BitmapUtil;
import com.example.try_gameengine.action.CopyMoveDecorator;
import com.example.try_gameengine.action.DoubleDecorator;
import com.example.try_gameengine.action.InverseMoveOrderDecorator;
import com.example.try_gameengine.action.InverseMovementInfoAppendDecorator;
import com.example.try_gameengine.action.InverseMovementInfoDecorator;
import com.example.try_gameengine.action.MAction;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionDecoratorFactory;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.MovementActionItem;
import com.example.try_gameengine.action.MovementActionItemBaseReugularFPS;
import com.example.try_gameengine.action.MovementActionSet;
import com.example.try_gameengine.action.MovementActionSetWithThreadPool;
import com.example.try_gameengine.action.MovementAtionController;
import com.example.try_gameengine.action.MovementInfoFactory;
import com.example.try_gameengine.action.PartOfAppendDecorator;
import com.example.try_gameengine.action.PartOfOrigizalDecorator;
import com.example.try_gameengine.action.RLMovementActionFactory;
import com.example.try_gameengine.action.SpecialMovementActionFactory;
import com.example.try_gameengine.enemy.BlueEnemy;
import com.example.try_gameengine.enemy.Enemy;
import com.example.try_gameengine.enemy.EnemyFactory;
import com.example.try_gameengine.enemy.RedEnemy;
import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.framework.Sprite;

public class MovementActionTest extends AndroidTestCase{

	public void testAction() throws Exception{
//		List<MovementActionInfo> correctInfoList = new ArrayList<MovementActionInfo>();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		List<Enemy> enemies = new ArrayList<Enemy>(); 
//		
//////		EnemyFactory enemyFactory = new EnemyFactory();
//////		enemies.add(enemyFactory.createRedEnemy());
//////		enemies.add(enemyFactory.createBlueEnemy());
////		
////		EnemyFactory enemyFactory = new EnemyFactory();
//////		enemies.add();
////		Enemy enemy = enemyFactory.createSpecialEnemy(RedEnemy.class, RLMovementActionFactory.class, new int[]{0, 0});
////		
//////		Enemy enemy = enemies.get(0);
////		MovementAction action = enemy.getAction();
////		
////		List<MovementActionInfo> currentInfoList = new ArrayList<MovementActionInfo>();
////		
////		MovementActionInfo info;
////		for(MovementAction movementAction : action.getMovementItemList()){
////			info = movementAction.getInfo();
//////			MovementActionInfo newInfo = new MovementActionInfo(
//////					info.getTotal(), info.getDelay(), info.getDx(),
//////					info.getDy());
////			currentInfoList.add(info);
////
////			
//////			assertEquals(40, info.getDx());
//////			assertEquals(40, info.getDx());
////		}
////		
//////		currentInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f, "a"));
//////		currentInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f, "a"));
////		
////		assertEquals(true, correctInfoList.equals(currentInfoList));
////		
//////		if(correctInfoList.equals(currentInfoList)){
//////			assertEquals(true, false);
//////		}else{
//////			assertEquals(true, false);
//////		}
//		
//		EnemyFactory enemyFactory = new EnemyFactory();
////		enemies.add();
//		Enemy enemy ;
//		MovementAction action;
//		List<MovementActionInfo> currentInfoList = new ArrayList<MovementActionInfo>();
//		MovementActionInfo info;
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		enemy = enemyFactory.createSpecialEnemy4(RedEnemy.class, RLMovementActionFactory.class, new int[]{0, 0}, MovementActionDecoratorFactory.createCopyMovementDecorator());
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementAction movementAction : action.getMovementItemList()){
//			info = movementAction.getInfo();
//			boolean isExist = false; 
//			for(MovementActionInfo actionInfo : currentInfoList){
//				if(actionInfo == info){
//					isExist = true;
//					break;
//				}
//			}
//			if(!isExist)
//				currentInfoList.add(info);
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//		
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		enemy = enemyFactory.createRLRedEnemy(new int[]{50, 50});
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementAction movementAction : action.getMovementItemList()){
//			info = movementAction.getInfo();
//			currentInfoList.add(info);
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//		
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		enemy = enemyFactory.createSpecialEnemy(BlueEnemy.class, RLMovementActionFactory.class, new int[]{150, 150});
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementAction movementAction : action.getMovementItemList()){
//			info = movementAction.getInfo();
//			currentInfoList.add(info);
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
//		
//		enemy = enemyFactory.createSpecialEnemy2(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{300, 300}, MovementInfoFactory.createSquareMovementInfo());
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementAction movementAction : action.getMovementItemList()){
//			info = movementAction.getInfo();
//			currentInfoList.add(info);
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		
//		enemy = enemyFactory.createSpecialEnemy3(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{450, 450}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createDHDMovementDecorator());
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementAction movementAction : action.getMovementItemList()){
//			info = movementAction.getInfo();
//			currentInfoList.add(info);
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		
//		enemy = enemyFactory.createSpecialEnemy4(RedEnemy.class, RLMovementActionFactory.class, new int[]{600, 600}, MovementActionDecoratorFactory.createDHDMovementDecorator());
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementAction movementAction : action.getMovementItemList()){
//			info = movementAction.getInfo();
//			currentInfoList.add(info);
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		
//		enemy = enemyFactory.createSpecialEnemy3(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 750}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createDCMovementDecorator());
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementAction movementAction : action.getMovementItemList()){
//			info = movementAction.getInfo();
//			currentInfoList.add(info);
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
//		
//		enemy = enemyFactory.createSpecialEnemy3(RedEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 950}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createCCMovementDecorator());
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementAction movementAction : action.getMovementItemList()){
//			info = movementAction.getInfo();
//			currentInfoList.add(info);
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		
//		enemy = enemyFactory.createSpecialEnemy3(RedEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 1050}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createCDCMovementDecorator());
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementAction movementAction : action.getMovementItemList()){
//			info = movementAction.getInfo();
//			currentInfoList.add(info);
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
//		
//		RLMovementActionFactory factory = new RLMovementActionFactory();
//		MovementAction innerAction = factory.createMovementAction();
//		MovementAction DInnerAction = new DoubleDecorator(innerAction);
//		SpecialMovementActionFactory actionFactory = new SpecialMovementActionFactory();
//		MovementAction RLDRL = actionFactory.createMovementActionByMerge(new RLMovementActionFactory().createMovementAction(), DInnerAction);
//		RLDRL = actionFactory.createMovementActionByMerge(new RLMovementActionFactory().createMovementAction(), RLDRL);
//		RLDRL = new DoubleDecorator(RLDRL);
//		
//		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1150}, RLDRL);
//		
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementAction movementAction : action.getMovementItemList()){
//			info = movementAction.getInfo();
//			boolean isExist = false; 
//			for(MovementActionInfo actionInfo : currentInfoList){
//				if(actionInfo == info){
//					isExist = true;
//					break;
//				}
//			}
//			if(!isExist)
//				currentInfoList.add(info);
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
	}

	public void testActionInfo() throws Exception{
		LayerManager.setNoSceneLayer();
		BitmapUtil.initBitmap(mContext);
//		BitmapUtil.initBitmapForTest();
		
		List<MovementActionInfo> correctInfoList = new ArrayList<MovementActionInfo>();
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		EnemyFactory enemyFactory = new EnemyFactory();
		Enemy enemy = enemyFactory.createSpecialEnemy(RedEnemy.class, RLMovementActionFactory.class, new int[]{0, 0});

		MovementAction action = enemy.getAction();
		
		List<MovementActionInfo> currentInfoList = new ArrayList<MovementActionInfo>();
	
		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}
		
		assertEquals(true, correctInfoList.equals(currentInfoList));

		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		enemy = enemyFactory.createSpecialEnemy4(RedEnemy.class, RLMovementActionFactory.class, new int[]{0, 0}, MovementActionDecoratorFactory.createCopyMovementDecorator());
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}
		
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		enemy = enemyFactory.createRLRedEnemy(new int[]{50, 50});
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		enemy = enemyFactory.createSpecialEnemy(BlueEnemy.class, RLMovementActionFactory.class, new int[]{150, 150});
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));

		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
		
		enemy = enemyFactory.createSpecialEnemy2(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{300, 300}, MovementInfoFactory.createSquareMovementInfo());
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));

		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		
		enemy = enemyFactory.createSpecialEnemy3(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{450, 450}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createDHDMovementDecorator());
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));

		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		
		enemy = enemyFactory.createSpecialEnemy4(RedEnemy.class, RLMovementActionFactory.class, new int[]{600, 600}, MovementActionDecoratorFactory.createDHDMovementDecorator());
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));

		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		
		enemy = enemyFactory.createSpecialEnemy3(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 750}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createDCMovementDecorator());
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));

		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		
		enemy = enemyFactory.createSpecialEnemy3(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 750}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createCDMovementDecorator());
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
		
		enemy = enemyFactory.createSpecialEnemy3(RedEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 950}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createCCMovementDecorator());
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));

		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		
		enemy = enemyFactory.createSpecialEnemy3(RedEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 1050}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createCDCMovementDecorator());
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));

		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		enemy = enemyFactory.createSpecialEnemy4(RedEnemy.class, RLMovementActionFactory.class, new int[]{450, 450}, MovementActionDecoratorFactory.createCCMovementDecorator());
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		
		RLMovementActionFactory factory = new RLMovementActionFactory();
		MovementAction innerAction = factory.createMovementAction();
		MovementAction DInnerAction = new DoubleDecorator(innerAction);
		SpecialMovementActionFactory actionFactory = new SpecialMovementActionFactory();
		MovementAction RLDRL = actionFactory.createMovementActionByMerge(new RLMovementActionFactory().createMovementAction(), DInnerAction);
		RLDRL = actionFactory.createMovementActionByMerge(new RLMovementActionFactory().createMovementAction(), RLDRL);
		RLDRL = new DoubleDecorator(RLDRL);
		
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1150}, RLDRL);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));

		
		enemy = enemyFactory.createSpecialEnemy3(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 750}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createDDMovementDecorator());
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		
		if(correctInfoList.equals(currentInfoList)){
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}else{
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}
		
		assertEquals(true, correctInfoList.equals(currentInfoList));	
		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 1280f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -1280f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 1280f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -1280f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));

		factory = new RLMovementActionFactory();
		innerAction = factory.createMovementAction();
		action = new MovementActionSet();
		action.addMovementAction(new DoubleDecorator(innerAction));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1050}, action));
		MovementAction actionD = new DoubleDecorator(action);
//		MovementAction actionD = new DoubleDecorator(new DoubleDecorator(action));
		actionD = new DoubleDecorator(new DoubleDecorator(actionD));
//		MovementAction actionDD = new DoubleDecorator(new DoubleDecorator(actionD));
		MovementAction actionDD = new DoubleDecorator(actionD);
		MovementAction newaction = new MovementActionSet();
//		newaction.addMovementAction(new DoubleDecorator(actionDD));
//		
//		newaction = new CopyMoveDecorator(newaction);
		
		newaction = new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction());
		MovementAction newaction2 = new MovementActionSet();
		newaction2.addMovementAction(newaction);
		newaction2.addMovementAction(actionDD);
//		MovementAction action2 = new MovementActionSet();
		newaction = new CopyMoveDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction()));
//		newaction2 = new MovementActionSet();
		newaction2.addMovementAction(newaction);
		newaction2 = new CopyMoveDecorator(newaction2);
		
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, newaction2);
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		
		if(correctInfoList.equals(currentInfoList)){
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}else{
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}
		
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 1280f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -1280f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 1280f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -1280f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));

		factory = new RLMovementActionFactory();
		innerAction = factory.createMovementAction();
		action = new MovementActionSet();
		action.addMovementAction(new DoubleDecorator(innerAction));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1050}, action));
		actionD = new DoubleDecorator(action);
//		MovementAction actionD = new DoubleDecorator(new DoubleDecorator(action));
		actionD = new DoubleDecorator(new DoubleDecorator(actionD));
//		MovementAction actionDD = new DoubleDecorator(new DoubleDecorator(actionD));
		actionDD = new DoubleDecorator(actionD);
		newaction = new MovementActionSet();
//		newaction.addMovementAction(new DoubleDecorator(actionDD));
//		
//		newaction = new CopyMoveDecorator(newaction);
		
		newaction = new DoubleDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction()));
		newaction2 = new MovementActionSet();
		newaction2.addMovementAction(newaction);
		newaction2.addMovementAction(actionDD);
//		MovementAction action2 = new MovementActionSet();
		newaction = new CopyMoveDecorator(new DoubleDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction())));
//		newaction2 = new MovementActionSet();
		newaction2.addMovementAction(newaction);
		newaction2 = new CopyMoveDecorator(newaction2);
		
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, newaction2);
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		
		if(correctInfoList.equals(currentInfoList)){
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}else{
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}
		
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 2560f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -2560f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 2560f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -2560f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));

		factory = new RLMovementActionFactory();
		innerAction = factory.createMovementAction();
		action = new MovementActionSet();
		action.addMovementAction(new DoubleDecorator(innerAction));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1050}, action));
		actionD = new DoubleDecorator(action);
//		MovementAction actionD = new DoubleDecorator(new DoubleDecorator(action));
		actionD = new DoubleDecorator(new DoubleDecorator(actionD));
//		MovementAction actionDD = new DoubleDecorator(new DoubleDecorator(actionD));
		actionDD = new DoubleDecorator(actionD);
		newaction = new MovementActionSet();
//		newaction.addMovementAction(new DoubleDecorator(actionDD));
//		
//		newaction = new CopyMoveDecorator(newaction);
		
		newaction = new DoubleDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction()));
		newaction2 = new MovementActionSet();
		newaction2.addMovementAction(newaction);
		newaction2.addMovementAction(actionDD);
//		MovementAction action2 = new MovementActionSet();
		newaction = new CopyMoveDecorator(new DoubleDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction())));
//		newaction2 = new MovementActionSet();
		newaction2.addMovementAction(newaction);
		newaction2 = new DoubleDecorator(new CopyMoveDecorator(newaction2));
		
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, newaction2);
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		
		if(correctInfoList.equals(currentInfoList)){
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}else{
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}
		
		assertEquals(true, correctInfoList.equals(currentInfoList));

		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));

		newaction = new MovementActionSet();
		newaction = new CopyMoveDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction()));
		newaction2 = new MovementActionSet();
		newaction2.addMovementAction(newaction);
		newaction2 = new DoubleDecorator(new CopyMoveDecorator(newaction2));
		
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, newaction2);
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		
		if(correctInfoList.equals(currentInfoList)){
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}else{
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}
		
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 2560, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -2560, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 2560f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -2560f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		factory = new RLMovementActionFactory();
		innerAction = factory.createMovementAction();
		action = new MovementActionSet();
		action.addMovementAction(new DoubleDecorator(innerAction));
		actionD = new DoubleDecorator(action);
		actionD = new DoubleDecorator(new DoubleDecorator(actionD));
		actionDD = new DoubleDecorator(actionD);
		newaction = new MovementActionSet();
		newaction = new CopyMoveDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction()));
		newaction2 = new MovementActionSet();
		newaction2.addMovementAction(newaction);
		newaction2.addMovementAction(actionDD);
		newaction = new CopyMoveDecorator(new DoubleDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction())));
		newaction2.addMovementAction(newaction);
		newaction2 = new DoubleDecorator(new CopyMoveDecorator(newaction2));
		
		MovementAction newaction3 = new MovementActionSet();
		
		newaction3.addMovementAction(newaction2);
		newaction3.addMovementAction(new RLMovementActionFactory().createMovementAction());
		newaction3.addMovementAction(new MovementActionSet().addMovementAction(new MovementActionSet().addMovementAction(new MovementActionSet().addMovementAction(new RLMovementActionFactory().createMovementAction()))));
		
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, newaction3);
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		
		if(correctInfoList.equals(currentInfoList)){
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}else{
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}
		
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 5120f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -5120f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 5120f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -5120f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 5120f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -5120f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -160f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 5120f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -5120f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -320f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		
		factory = new RLMovementActionFactory();
		innerAction = factory.createMovementAction();
		action = new MovementActionSet();
		action.addMovementAction(new DoubleDecorator(innerAction));
		actionD = new DoubleDecorator(action);
		actionD = new DoubleDecorator(new DoubleDecorator(actionD));
		actionDD = new DoubleDecorator(actionD);
		newaction = new MovementActionSet();
		newaction = new CopyMoveDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction()));
		newaction2 = new MovementActionSet();
		newaction2.addMovementAction(newaction);
		newaction2.addMovementAction(actionDD);
		newaction = new CopyMoveDecorator(new DoubleDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction())));
		newaction2.addMovementAction(newaction);
		newaction2 = new DoubleDecorator(new CopyMoveDecorator(newaction2));
		
		newaction3 = new MovementActionSet();
		
		newaction3.addMovementAction(newaction2);
		newaction3.addMovementAction(new RLMovementActionFactory().createMovementAction());
		newaction3.addMovementAction(new MovementActionSet().addMovementAction(new MovementActionSet().addMovementAction(new MovementActionSet().addMovementAction(new RLMovementActionFactory().createMovementAction()))));
		newaction3 = new DoubleDecorator(new CopyMoveDecorator(newaction3));
		
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, newaction3);
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		
		if(correctInfoList.equals(currentInfoList)){
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}else{
			assertEquals(true, correctInfoList.equals(currentInfoList));
		}
		
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		
		MovementAction newaction4 = new CopyMoveDecorator(new MovementActionSet().addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo())));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		
		newaction4 = new MovementActionSet();
		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		
		newaction4 = new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo());
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		
		newaction4 = new DoubleDecorator(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
		
		newaction4 = new InverseMovementInfoDecorator(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		
		newaction4 = new InverseMovementInfoDecorator(new MovementActionSet());
		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		
		
		newaction4 = new InverseMoveOrderDecorator(new MovementActionSet());
		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		
		newaction4 = new CopyMoveDecorator(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet()))));
		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		List<MovementAction> actions = action.getMovementItemList();
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		newaction4 = new DoubleDecorator(new PartOfAppendDecorator(new CopyMoveDecorator(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet()))))));
		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
		newaction4 = new MovementActionSet().addMovementAction(newaction4);
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		actions = action.getMovementItemList();
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -80f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 80f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		
		newaction4 = new DoubleDecorator(new PartOfAppendDecorator(new CopyMoveDecorator(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet()))))));
		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
		newaction4 = new DoubleDecorator(new MovementActionSet().addMovementAction(newaction4));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		actions = action.getMovementItemList();
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -80f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 80f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		newaction4 = new DoubleDecorator(new PartOfOrigizalDecorator(new CopyMoveDecorator(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet()))))));
		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
		newaction4 = new DoubleDecorator(new MovementActionSet().addMovementAction(newaction4));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		actions = action.getMovementItemList();
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -80f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 80f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -80f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 80f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		newaction4 = new DoubleDecorator(new PartOfOrigizalDecorator(new CopyMoveDecorator(new CopyMoveDecorator(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet())))))));
		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
		newaction4 = new DoubleDecorator(new MovementActionSet().addMovementAction(newaction4));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		actions = action.getMovementItemList();
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -80f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 80f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));

		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
		
		newaction4 = new DoubleDecorator(new PartOfOrigizalDecorator(new CopyMoveDecorator(new PartOfOrigizalDecorator(new CopyMoveDecorator(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet()))))))));
		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
		newaction4 = new DoubleDecorator(new MovementActionSet().addMovementAction(newaction4));
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();

		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		actions = action.getMovementItemList();
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		correctInfoList.clear();
		correctInfoList.add(new MovementActionInfo(1000, 20, 0.19999999f, 0f));
		
		Config.fps = 50;
		newaction4 = new MovementActionSet();
		newaction4.addMovementAction(MAction.moveByX(10f, 1000));
		newaction4.addMovementAction(MAction.runBlock(new MAction.MActionBlock() {
			
			@Override
			public void runBlock() {
				// TODO Auto-generated method stub
				
			}
		}));
		
		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
		
		action = enemy.getAction();
		currentInfoList.clear();
		
		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
			currentInfoList.add(movementActionInfo); 
		}	
		actions = action.getMovementItemList();
		assertEquals(true, correctInfoList.equals(currentInfoList));
		
		
	}
	
	public void testActionThreadPool() throws Exception{
		
		for(int i = 0; i < 40; i++){
			
			MovementAction movementAction = new MovementActionSetWithThreadPool();
			movementAction.setMovementActionController(new MovementAtionController());
			String actionName = "";
			
			Sprite sprite = new Sprite(10, 10, false);
			MovementActionInfo info = new MovementActionInfo(1000, 1, 0, -10, "", null, false, sprite, actionName);
			MovementAction action = new MovementActionItemBaseReugularFPS(info);
			movementAction.addMovementAction(action);
			
			movementAction.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
				
				@Override
				public void onTick(float dx, float dy) {
					// TODO Auto-generated method stub
					
//					move(dx, dy);
					
				}
			});
			
			movementAction.initMovementAction();
			
			movementAction.start();
			
			sprite.setMovementAction(movementAction);
			
			movementAction.controller.cancelAllMove();
			
			Thread.sleep(500);
		}
		
		assertEquals(true, Thread.activeCount() < 20);
	}
}
