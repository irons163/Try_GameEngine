package com.example.try_gameengine.test

import android.test.AndroidTestCase
import com.example.try_gameengine.action.Time
import com.example.try_gameengine.framework.Config

class MovementActionTest : AndroidTestCase() {
    @kotlin.Throws(java.lang.Exception::class)
    fun testAction() {
//		List<MovementActionInfo> correctInfoList = new ArrayList<MovementActionInfo>();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		List<Enemy> enemies = new ArrayList<Enemy>(); 
//		
        // /**///		EnemyFactory enemyFactory = new EnemyFactory();
        // * enemies.add(enemyFactory.createRedEnemy());
        // * enemies.add(enemyFactory.createBlueEnemy());
        // * /
        // * /        EnemyFactory enemyFactory = new EnemyFactory();
        // * enemies.add();
        // * /        Enemy enemy = enemyFactory.createSpecialEnemy(RedEnemy.class, RLMovementActionFactory.class, new int[]{ 0, 0 });
        // * /
        // * Enemy enemy = enemies . get (0);
        // * /        MovementAction action = enemy.getAction();
        // * /
        // * /        List<MovementActionInfo> currentInfoList = new ArrayList<MovementActionInfo>();
        // * /
        // * /        MovementActionInfo info;
        // * /        for(MovementAction movementAction : action.getMovementItemList()){
            // * /            info = movementAction.getInfo();
            // * MovementActionInfo newInfo = new MovementActionInfo(
            // *info.getTotal(), info.getDelay(), info.getDx(),
            // *info.getDy()
        // );
            // * /            currentInfoList.add(info);
            // * /
            // * ////
            // * assertEquals(40, info.getDx());
            // * assertEquals(40, info.getDx());
            // * /
        // }
        // * /
        // * currentInfoList.add(new MovementActionInfo (1000, 200, 40f, 0f, "a"));
        // * currentInfoList.add(new MovementActionInfo (1000, 200, -40f, 0f, "a"));
        // * /
        // * /        assertEquals(true, correctInfoList.equals(currentInfoList));
        // * /
        // * if (correctInfoList.equals(currentInfoList)) {
            // * assertEquals(true, false);
            // *
        // } else {
            // * assertEquals(true, false);
            // *
        // } < / MovementActionInfo > < / MovementActionInfo > * /
//		
//		EnemyFactory enemyFactory = new EnemyFactory();
        // /**/        enemies.add(); */
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

    @kotlin.Throws(java.lang.Exception::class)
    fun testActionInfo() {
//		LayerManager.setNoSceneLayer();
//		LayerManager.getInstance().increaseNewLayer();
        BitmapUtil.initBitmap(mContext)
        BitmapUtil.initBitmapForTest()

        val correctInfoList: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))

        val enemyFactory = EnemyFactory()
        var enemy = enemyFactory.createSpecialEnemy(
            RedEnemy::class.java,
            RLMovementActionFactory::class.java,
            intArrayOf(0, 0)
        )

        var action: MovementAction = enemy.getAction()

        val currentInfoList: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }

        assertEquals(true, correctInfoList == currentInfoList)


//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 30, 0));
//		
//		MovementAction newaction5;
//		newaction5 = new InverseMovementInfoDecorator(new MovementActionSetWithThread());
//		newaction5.addMovementAction(new GravityWaveSlopePathAppendDecorator(new MovementActionItemMoveByGravity(new MovementActionInfo(1000, 200, 30, 0, "R"), new JumpController(-50, 200, 100), "")));
//		newaction5 = new MovementActionSetWithThread().addMovementAction(newaction5);
//		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction5);
//		
//		action = enemy.getAction();
//		currentInfoList.clear();
//		
//		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
//			currentInfoList.add(movementActionInfo); 
//		}
        Time.DeltaTime = 200
        action.trigger()
        assertEquals(true, correctInfoList.get(0) == currentInfoList.get(0))
        action.trigger()
        assertEquals(true, correctInfoList.get(1) == currentInfoList.get(1))
        action.trigger()
        assertEquals(true, correctInfoList.get(2) == currentInfoList.get(2))


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))

        enemy = enemyFactory.createSpecialEnemy4(
            RedEnemy::class.java,
            RLMovementActionFactory::class.java,
            intArrayOf(0, 0),
            MovementActionDecoratorFactory.createCopyMovementDecorator()
        )
        //		enemy = enemyFactory.createSpecialEnemy(RedEnemy.class, RLMovementActionFactory.class, new int[]{0, 0});
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }

        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))

        enemy = enemyFactory.createRLRedEnemy(intArrayOf(50, 50))
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))

        enemy = enemyFactory.createSpecialEnemy(
            BlueEnemy::class.java,
            RLMovementActionFactory::class.java,
            intArrayOf(150, 150)
        )
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 10f))
        correctInfoList.add(MovementActionInfo(1000, 200, -10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -10f))

        enemy = enemyFactory.createSpecialEnemy2(
            BlueEnemy::class.java,
            SpecialMovementActionFactory::class.java,
            intArrayOf(300, 300),
            MovementInfoFactory.createSquareMovementInfo()
        )
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 20f))
        correctInfoList.add(MovementActionInfo(1000, 200, -20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -20f))

        enemy = enemyFactory.createSpecialEnemy3(
            BlueEnemy::class.java,
            SpecialMovementActionFactory::class.java,
            intArrayOf(450, 450),
            MovementInfoFactory.createSquareMovementInfo(),
            MovementActionDecoratorFactory.createDHDMovementDecorator()
        )
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))

        enemy = enemyFactory.createSpecialEnemy4(
            RedEnemy::class.java,
            RLMovementActionFactory::class.java,
            intArrayOf(600, 600),
            MovementActionDecoratorFactory.createDHDMovementDecorator()
        )
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 20f))
        correctInfoList.add(MovementActionInfo(1000, 200, -20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -20f))
        correctInfoList.add(MovementActionInfo(1000, 200, 20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 20f))
        correctInfoList.add(MovementActionInfo(1000, 200, -20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -20f))

        enemy = enemyFactory.createSpecialEnemy3(
            BlueEnemy::class.java,
            SpecialMovementActionFactory::class.java,
            intArrayOf(750, 750),
            MovementInfoFactory.createSquareMovementInfo(),
            MovementActionDecoratorFactory.createDCMovementDecorator()
        )
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 20f))
        correctInfoList.add(MovementActionInfo(1000, 200, -20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -20f))
        correctInfoList.add(MovementActionInfo(1000, 200, 20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 20f))
        correctInfoList.add(MovementActionInfo(1000, 200, -20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -20f))

        enemy = enemyFactory.createSpecialEnemy3(
            BlueEnemy::class.java,
            SpecialMovementActionFactory::class.java,
            intArrayOf(750, 750),
            MovementInfoFactory.createSquareMovementInfo(),
            MovementActionDecoratorFactory.createCDMovementDecorator()
        )
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 10f))
        correctInfoList.add(MovementActionInfo(1000, 200, -10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -10f))
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 10f))
        correctInfoList.add(MovementActionInfo(1000, 200, -10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -10f))
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 10f))
        correctInfoList.add(MovementActionInfo(1000, 200, -10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -10f))
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 10f))
        correctInfoList.add(MovementActionInfo(1000, 200, -10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -10f))

        enemy = enemyFactory.createSpecialEnemy3(
            RedEnemy::class.java,
            SpecialMovementActionFactory::class.java,
            intArrayOf(750, 950),
            MovementInfoFactory.createSquareMovementInfo(),
            MovementActionDecoratorFactory.createCCMovementDecorator()
        )
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)



        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 20f))
        correctInfoList.add(MovementActionInfo(1000, 200, -20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -20f))
        correctInfoList.add(MovementActionInfo(1000, 200, 20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 20f))
        correctInfoList.add(MovementActionInfo(1000, 200, -20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -20f))
        correctInfoList.add(MovementActionInfo(1000, 200, 20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 20f))
        correctInfoList.add(MovementActionInfo(1000, 200, -20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -20f))
        correctInfoList.add(MovementActionInfo(1000, 200, 20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 20f))
        correctInfoList.add(MovementActionInfo(1000, 200, -20f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -20f))

        enemy = enemyFactory.createSpecialEnemy3(
            RedEnemy::class.java,
            SpecialMovementActionFactory::class.java,
            intArrayOf(750, 1050),
            MovementInfoFactory.createSquareMovementInfo(),
            MovementActionDecoratorFactory.createCDCMovementDecorator()
        )
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)



        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))

        enemy = enemyFactory.createSpecialEnemy4(
            RedEnemy::class.java,
            RLMovementActionFactory::class.java,
            intArrayOf(450, 450),
            MovementActionDecoratorFactory.createCCMovementDecorator()
        )
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))

        var factory = RLMovementActionFactory()
        var innerAction: MovementAction? = factory.createMovementAction()
        val DInnerAction: MovementAction = DoubleDecorator(innerAction)
        val actionFactory: SpecialMovementActionFactory = SpecialMovementActionFactory()
        var RLDRL: MovementAction? = actionFactory.createMovementActionByMerge(
            RLMovementActionFactory().createMovementAction(),
            DInnerAction
        )
        RLDRL = actionFactory.createMovementActionByMerge(
            RLMovementActionFactory().createMovementAction(),
            RLDRL
        )
        RLDRL = DoubleDecorator(RLDRL)

        enemy = enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(0, 1150), RLDRL)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)



        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 40f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -40f))


        enemy = enemyFactory.createSpecialEnemy3(
            BlueEnemy::class.java,
            SpecialMovementActionFactory::class.java,
            intArrayOf(750, 750),
            MovementInfoFactory.createSquareMovementInfo(),
            MovementActionDecoratorFactory.createDDMovementDecorator()
        )
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }

        if (correctInfoList == currentInfoList) {
            assertEquals(true, correctInfoList == currentInfoList)
        } else {
            assertEquals(true, correctInfoList == currentInfoList)
        }

        assertEquals(true, correctInfoList == currentInfoList)



        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 1280f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -1280f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 1280f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -1280f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))

        factory = RLMovementActionFactory()
        innerAction = factory.createMovementAction()
        action = MovementActionSetWithThread()
        action.addMovementAction(DoubleDecorator(innerAction))
        //		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1050}, action));
        var actionD: MovementAction = DoubleDecorator(action)
        //		MovementAction actionD = new DoubleDecorator(new DoubleDecorator(action));
        actionD = DoubleDecorator(DoubleDecorator(actionD))
        //		MovementAction actionDD = new DoubleDecorator(new DoubleDecorator(actionD));
        var actionDD: MovementAction = DoubleDecorator(actionD)
        var newaction: MovementAction = MovementActionSetWithThread()

        //		newaction.addMovementAction(new DoubleDecorator(actionDD));
//		
//		newaction = new CopyMoveDecorator(newaction);
        newaction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(RLMovementActionFactory().createMovementAction()) as MovementActionSet?
        )
        var newaction2: MovementAction = MovementActionSetWithThread()
        newaction2.addMovementAction(newaction)
        newaction2.addMovementAction(actionDD)
        //		MovementAction action2 = new MovementActionSet();
        newaction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(
                CopyMoveDecorator(
                    MovementActionSetWithThread().addMovementAction(
                        RLMovementActionFactory().createMovementAction()
                    ) as MovementActionSet?
                )
            ) as MovementActionSet?
        )
        //		newaction2 = new MovementActionSet();
        newaction2.addMovementAction(newaction)
        newaction2 = CopyMoveDecorator(newaction2 as MovementActionSet)

        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(0, 1100), newaction2)
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }

        if (correctInfoList == currentInfoList) {
            assertEquals(true, correctInfoList == currentInfoList)
        } else {
            assertEquals(true, correctInfoList == currentInfoList)
        }

        assertEquals(true, correctInfoList == currentInfoList)



        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 1280f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -1280f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 1280f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -1280f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))

        factory = RLMovementActionFactory()
        innerAction = factory.createMovementAction()
        action = MovementActionSetWithThread()
        action.addMovementAction(DoubleDecorator(innerAction))
        //		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1050}, action));
        actionD = DoubleDecorator(action)
        //		MovementAction actionD = new DoubleDecorator(new DoubleDecorator(action));
        actionD = DoubleDecorator(DoubleDecorator(actionD))
        //		MovementAction actionDD = new DoubleDecorator(new DoubleDecorator(actionD));
        actionDD = DoubleDecorator(actionD)
        newaction = MovementActionSetWithThread()

        //		newaction.addMovementAction(new DoubleDecorator(actionDD));
//		
//		newaction = new CopyMoveDecorator(newaction);
        newaction = DoubleDecorator(
            MovementActionSetWithThread().addMovementAction(
                CopyMoveDecorator(
                    MovementActionSetWithThread().addMovementAction(
                        RLMovementActionFactory().createMovementAction()
                    ) as MovementActionSet?
                )
            ) as MovementActionSet?
        )
        newaction2 = MovementActionSetWithThread()
        newaction2.addMovementAction(newaction)
        newaction2.addMovementAction(actionDD)
        //		MovementAction action2 = new MovementActionSet();
        newaction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(
                DoubleDecorator(
                    CopyMoveDecorator(
                        MovementActionSetWithThread().addMovementAction(
                            RLMovementActionFactory().createMovementAction()
                        ) as MovementActionSet?
                    )
                )
            ) as MovementActionSet?
        )
        //		newaction2 = new MovementActionSet();
        newaction2.addMovementAction(newaction)
        newaction2 = CopyMoveDecorator(newaction2 as MovementActionSet)

        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(0, 1100), newaction2)
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }

        if (correctInfoList == currentInfoList) {
            assertEquals(true, correctInfoList == currentInfoList)
        } else {
            assertEquals(true, correctInfoList == currentInfoList)
        }

        assertEquals(true, correctInfoList == currentInfoList)



        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 2560f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -2560f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 2560f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -2560f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))

        factory = RLMovementActionFactory()
        innerAction = factory.createMovementAction()
        action = MovementActionSetWithThread()
        action.addMovementAction(DoubleDecorator(innerAction))
        //		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1050}, action));
        actionD = DoubleDecorator(action)
        //		MovementAction actionD = new DoubleDecorator(new DoubleDecorator(action));
        actionD = DoubleDecorator(DoubleDecorator(actionD))
        //		MovementAction actionDD = new DoubleDecorator(new DoubleDecorator(actionD));
        actionDD = DoubleDecorator(actionD)
        newaction = MovementActionSetWithThread()

        //		newaction.addMovementAction(new DoubleDecorator(actionDD));
//		
//		newaction = new CopyMoveDecorator(newaction);
        newaction = DoubleDecorator(
            CopyMoveDecorator(
                MovementActionSetWithThread().addMovementAction(RLMovementActionFactory().createMovementAction()) as MovementActionSet?
            )
        )
        newaction2 = MovementActionSetWithThread()
        newaction2.addMovementAction(newaction)
        newaction2.addMovementAction(actionDD)
        //		MovementAction action2 = new MovementActionSet();
        newaction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(
                DoubleDecorator(
                    CopyMoveDecorator(
                        MovementActionSetWithThread().addMovementAction(
                            RLMovementActionFactory().createMovementAction()
                        ) as MovementActionSet?
                    )
                )
            ) as MovementActionSet?
        )
        //		newaction2 = new MovementActionSet();
        newaction2.addMovementAction(newaction)
        newaction2 = DoubleDecorator(CopyMoveDecorator(newaction2 as MovementActionSet))

        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(0, 1100), newaction2)
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }

        if (correctInfoList == currentInfoList) {
            assertEquals(true, correctInfoList == currentInfoList)
        } else {
            assertEquals(true, correctInfoList == currentInfoList)
        }

        assertEquals(true, correctInfoList == currentInfoList)




        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))

        newaction = MovementActionSetWithThread()
        newaction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(
                CopyMoveDecorator(
                    MovementActionSetWithThread().addMovementAction(
                        RLMovementActionFactory().createMovementAction()
                    ) as MovementActionSet?
                )
            ) as MovementActionSet?
        )
        newaction2 = MovementActionSetWithThread()
        newaction2.addMovementAction(newaction)
        newaction2 = DoubleDecorator(CopyMoveDecorator(newaction2 as MovementActionSet))

        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(0, 1100), newaction2)
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }

        if (correctInfoList == currentInfoList) {
            assertEquals(true, correctInfoList == currentInfoList)
        } else {
            assertEquals(true, correctInfoList == currentInfoList)
        }

        assertEquals(true, correctInfoList == currentInfoList)



        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 2560f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -2560f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 2560f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -2560f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 40f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -40f, 0f))

        factory = RLMovementActionFactory()
        innerAction = factory.createMovementAction()
        action = MovementActionSetWithThread()
        action.addMovementAction(DoubleDecorator(innerAction))
        actionD = DoubleDecorator(action)
        actionD = DoubleDecorator(DoubleDecorator(actionD))
        actionDD = DoubleDecorator(actionD)
        newaction = MovementActionSetWithThread()
        newaction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(
                CopyMoveDecorator(
                    MovementActionSetWithThread().addMovementAction(
                        RLMovementActionFactory().createMovementAction()
                    ) as MovementActionSet?
                )
            ) as MovementActionSet?
        )
        newaction2 = MovementActionSetWithThread()
        newaction2.addMovementAction(newaction)
        newaction2.addMovementAction(actionDD)
        newaction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(
                DoubleDecorator(
                    CopyMoveDecorator(
                        MovementActionSetWithThread().addMovementAction(
                            RLMovementActionFactory().createMovementAction()
                        ) as MovementActionSet?
                    )
                )
            ) as MovementActionSet?
        )
        newaction2.addMovementAction(newaction)
        newaction2 = DoubleDecorator(CopyMoveDecorator(newaction2 as MovementActionSet))

        var newaction3: MovementAction = MovementActionSetWithThread()

        newaction3.addMovementAction(newaction2)
        newaction3.addMovementAction(RLMovementActionFactory().createMovementAction())
        newaction3.addMovementAction(
            MovementActionSetWithThread().addMovementAction(
                MovementActionSetWithThread().addMovementAction(
                    MovementActionSetWithThread().addMovementAction(RLMovementActionFactory().createMovementAction())
                )
            )
        )

        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(0, 1100), newaction3)
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }

        if (correctInfoList == currentInfoList) {
            assertEquals(true, correctInfoList == currentInfoList)
        } else {
            assertEquals(true, correctInfoList == currentInfoList)
        }

        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 5120f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -5120f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 5120f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -5120f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 5120f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -5120f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -160f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 5120f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -5120f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -320f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 80f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, -80f, 0f))

        factory = RLMovementActionFactory()
        innerAction = factory.createMovementAction()
        action = MovementActionSetWithThread()
        action.addMovementAction(DoubleDecorator(innerAction))
        actionD = DoubleDecorator(action)
        actionD = DoubleDecorator(DoubleDecorator(actionD))
        actionDD = DoubleDecorator(actionD)
        newaction = MovementActionSetWithThread()
        newaction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(
                CopyMoveDecorator(
                    MovementActionSetWithThread().addMovementAction(
                        RLMovementActionFactory().createMovementAction()
                    ) as MovementActionSet?
                )
            ) as MovementActionSet?
        )
        newaction2 = MovementActionSetWithThread()
        newaction2.addMovementAction(newaction)
        newaction2.addMovementAction(actionDD)
        newaction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(
                DoubleDecorator(
                    CopyMoveDecorator(
                        MovementActionSetWithThread().addMovementAction(
                            RLMovementActionFactory().createMovementAction()
                        ) as MovementActionSet?
                    )
                )
            ) as MovementActionSet?
        )
        newaction2.addMovementAction(newaction)
        newaction2 = DoubleDecorator(CopyMoveDecorator(newaction2 as MovementActionSet))

        newaction3 = MovementActionSetWithThread()

        newaction3.addMovementAction(newaction2)
        newaction3.addMovementAction(RLMovementActionFactory().createMovementAction())
        newaction3.addMovementAction(
            MovementActionSetWithThread().addMovementAction(
                MovementActionSetWithThread().addMovementAction(
                    MovementActionSetWithThread().addMovementAction(RLMovementActionFactory().createMovementAction())
                )
            )
        )
        newaction3 = DoubleDecorator(CopyMoveDecorator(newaction3 as MovementActionSet))

        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(0, 1100), newaction3)
        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }

        if (correctInfoList == currentInfoList) {
            assertEquals(true, correctInfoList == currentInfoList)
        } else {
            assertEquals(true, correctInfoList == currentInfoList)
        }

        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))

        var newaction4: MovementAction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(
                MovementActionItemCountDownTimer(
                    MovementInfoFactory.createSingleRMovementInfo()
                )
            ) as MovementActionSet?
        )
        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(100, 500), newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))

        newaction4 = MovementActionSetWithThread()
        newaction4.addMovementAction(MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo()))
        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(100, 500), newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))

        newaction4 =
            MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo())
        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(100, 500), newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 20f, 0f))

        newaction4 =
            DoubleDecorator(MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo()))
        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(100, 500), newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, -10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -10f))
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 10f))

        newaction4 = InverseMovementInfoDecorator(
            SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo())
        )
        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(100, 500), newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)



        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, -10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -10f))
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 10f))
        correctInfoList.add(MovementActionInfo(1000, 200, -10f, 0f))

        newaction4 = InverseMovementInfoDecorator(MovementActionSetWithThread())
        newaction4.addMovementAction(
            SpecialMovementActionFactory().createMovementAction(
                MovementInfoFactory.createSquareMovementInfo()
            )
        )
        newaction4.addMovementAction(MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo()))
        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(100, 500), newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


        correctInfoList.clear()
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, -10f))
        correctInfoList.add(MovementActionInfo(1000, 200, -10f, 0f))
        correctInfoList.add(MovementActionInfo(1000, 200, 0f, 10f))
        correctInfoList.add(MovementActionInfo(1000, 200, 10f, 0f))


        newaction4 = InverseMoveOrderDecorator(MovementActionSetWithThread())
        newaction4.addMovementAction(
            SpecialMovementActionFactory().createMovementAction(
                MovementInfoFactory.createSquareMovementInfo()
            )
        )
        newaction4.addMovementAction(MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo()))
        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(100, 500), newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)


//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -10f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -10f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 10f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		
//		newaction4 = new CopyMoveDecorator((MovementActionSet)new MovementActionSetWithThread().addMovementAction(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSetWithThread())))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo()));
//		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
//		
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
//			currentInfoList.add(movementActionInfo); 
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
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		newaction4 = new DoubleDecorator(new PartOfAppendDecorator(new CopyMoveDecorator((MovementActionSet)new MovementActionSetWithThread().addMovementAction(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSetWithThread())))))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo()));
//		newaction4 = new MovementActionSetWithThread().addMovementAction(newaction4);
//		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
//		
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
//			currentInfoList.add(movementActionInfo); 
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//		
//		
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -80f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 80f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		
//		newaction4 = new DoubleDecorator(new PartOfAppendDecorator(new CopyMoveDecorator((MovementActionSet)new MovementActionSetWithThread().addMovementAction(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSetWithThread())))))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo()));
//		newaction4 = new DoubleDecorator(new MovementActionSetWithThread().addMovementAction(newaction4));
//		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
//		
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
//			currentInfoList.add(movementActionInfo); 
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//		
//		
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -80f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 80f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		newaction4 = new DoubleDecorator(new PartOfOrigizalDecorator(new CopyMoveDecorator((MovementActionSet)new MovementActionSetWithThread().addMovementAction(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSetWithThread())))))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo()));
//		newaction4 = new DoubleDecorator(new MovementActionSetWithThread().addMovementAction(newaction4));
//		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
//		
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
//			currentInfoList.add(movementActionInfo); 
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//		
//		
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -80f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 80f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -80f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 80f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		newaction4 = new DoubleDecorator(new PartOfOrigizalDecorator(new CopyMoveDecorator((MovementActionSet)new MovementActionSetWithThread().addMovementAction(new CopyMoveDecorator((MovementActionSet)new MovementActionSetWithThread().addMovementAction(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSetWithThread())))))))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo()));
//		newaction4 = new DoubleDecorator(new MovementActionSetWithThread().addMovementAction(newaction4));
//		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
//		
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
//			currentInfoList.add(movementActionInfo); 
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
//		
//		
//		
//		correctInfoList.clear();
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -20f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -20f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 20f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -80f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 80f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 80f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -80f, 0f));
//
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, -40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 40f, 0f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, 0f, 40f));
//		correctInfoList.add(new MovementActionInfo(1000, 200, -40f, 0f));
//		
//		newaction4 = new DoubleDecorator(new PartOfOrigizalDecorator(new CopyMoveDecorator((MovementActionSet)new MovementActionSetWithThread().addMovementAction(new PartOfOrigizalDecorator(new CopyMoveDecorator((MovementActionSet)new MovementActionSetWithThread().addMovementAction(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSetWithThread()))))))))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItemCountDownTimer(MovementInfoFactory.createSingleRMovementInfo()));
//		newaction4 = new DoubleDecorator(new MovementActionSetWithThread().addMovementAction(newaction4));
//		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
//		
//		action = enemy.getAction();
//		currentInfoList.clear();
//
//		for(MovementActionInfo movementActionInfo : action.getStartMovementInfoList()){
//			currentInfoList.add(movementActionInfo); 
//		}	
//		assertEquals(true, correctInfoList.equals(currentInfoList));
        correctInfoList.clear()
        //		correctInfoList.add(new MovementActionInfo(1000, 20, 0.19999999f, 0f));
        correctInfoList.add(MovementActionInfo(50, 1, 0.19999999f, 0f))

        Config.fps = 50f
        newaction4 = MovementActionSetWithThread()
        newaction4.addMovementAction(MAction.moveByX(10f, 1000))
        newaction4.addMovementAction(MAction.runBlock(object : MActionBlock {
            override fun runBlock() {
                // TODO Auto-generated method stub
            }
        }))

        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(100, 500), newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)

        correctInfoList.clear()
        //		correctInfoList.add(new MovementActionInfo(1000, 20, 0.19999999f, 0f));
        correctInfoList.add(MovementActionInfo(1000, 20, 0.2f, 0f))

        Config.fps = 50f
        newaction4 = MovementActionSetWithThread()
        newaction4.addMovementAction(MAction2.moveByX(10f, 1000))
        newaction4.addMovementAction(MAction.runBlock(object : MActionBlock {
            override fun runBlock() {
                // TODO Auto-generated method stub
            }
        }))

        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(100, 500), newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)

        correctInfoList.clear()
        //		correctInfoList.add(new MovementActionInfo(1000, 20, 0.19999999f, 0f));
        correctInfoList.add(MovementActionInfo(50, 1, -0.2f, 0f))

        Config.fps = 50f
        newaction4 = MovementActionSetWithThread()
        newaction4.addMovementAction(MAction.moveTo(90f, 500f, 1000))
        newaction4.addMovementAction(MAction.runBlock(object : MActionBlock {
            override fun runBlock() {
                // TODO Auto-generated method stub
            }
        }))


//		enemy = enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4);
        enemy = enemyFactory.createRedEnemy(intArrayOf(100, 500))
        enemy.runMovementAction(newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)

        correctInfoList.clear()
        //		correctInfoList.add(new MovementActionInfo(1000, 20, 0.19999999f, 0f));
        correctInfoList.add(MovementActionInfo(1000, 20, 1.8f, 0f))

        Config.fps = 50f
        newaction4 = MovementActionSetWithThread()
        newaction4.addMovementAction(MAction2.moveByX(90f, 1000))
        newaction4.addMovementAction(MAction.runBlock(object : MActionBlock {
            override fun runBlock() {
                // TODO Auto-generated method stub
            }
        }))

        enemy =
            enemyFactory.createSpecialEnemy5(RedEnemy::class.java, intArrayOf(100, 500), newaction4)

        action = enemy.getAction()
        currentInfoList.clear()

        for (movementActionInfo in action.getStartMovementInfoList()) {
            currentInfoList.add(movementActionInfo)
        }
        assertEquals(true, correctInfoList == currentInfoList)
    }

    @LargeTest
    @kotlin.Throws(java.lang.Exception::class)
    fun testActionThreadPool() {
        for (i in 0..39) {
            val movementAction: MovementAction = MovementActionSetWithThreadPool()
            movementAction.setMovementActionController(MovementAtionController())
            val actionName = ""

            val sprite: Sprite = Sprite(10f, 10f, false)
            val info: MovementActionInfo =
                MovementActionInfo(1000, 1, 0f, -10f, "", sprite, actionName)
            val action: MovementAction = MovementActionItemBaseReugularFPS(info)
            movementAction.addMovementAction(action)

            movementAction.setTimerOnTickListener(object : TimerOnTickListener {
                override fun onTick(dx: kotlin.Float, dy: kotlin.Float) {
                    // TODO Auto-generated method stub

//					move(dx, dy);
                }
            })

            movementAction.initMovementAction()

            movementAction.start()

            sprite.setMovementAction(movementAction)

            movementAction.controller.cancelAllMove()

            Thread.sleep(500)
        }

        assertEquals(true, Thread.activeCount() < 30)
    }
}
