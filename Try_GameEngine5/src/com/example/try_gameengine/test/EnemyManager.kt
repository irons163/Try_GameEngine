package com.example.try_gameengine.test

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import com.example.try_gameengine.action.CopyMoveDecorator
import com.example.try_gameengine.action.DoubleDecorator
import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.MovementActionSet
import com.example.try_gameengine.action.MovementActionSetWithThread
import com.example.try_gameengine.action.MovementAtionController
import com.example.try_gameengine.action.MovementInfoFactory
import com.example.try_gameengine.action.SpecialMovementActionFactory

class EnemyManager {
    val enemies: MutableList<Enemy> = ArrayList<Enemy>()

    fun createEnemy() {
        val enemyFactory = EnemyFactory()
        enemies.add(enemyFactory.createRedEnemy())
        enemies.add(enemyFactory.createBlueEnemy())
    }

    fun createLevel1Enemy() {
        val enemyFactory = EnemyFactory()

        //		enemies.add(enemyFactory.createSpecialEnemy(RedEnemy.class, RLMovementActionFactory.class, new int[]{0, 0}));
//		enemies.add(enemyFactory.createSpecialEnemy4(RedEnemy.class, RLMovementActionFactory.class, new int[]{0, 0}, MovementActionDecoratorFactory.createCopyMovementDecorator()));
//		enemies.add(enemyFactory.createRLRedEnemy(new int[]{50, 50}));
//		enemies.add(enemyFactory.createRLBlueEnemy(new int[]{100, 100}));
//		enemies.add(enemyFactory.createSpecialEnemy(BlueEnemy.class, RLMovementActionFactory.class, new int[]{150, 150}));
//		enemies.add(enemyFactory.createSpecialEnemy2(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{300, 300}, MovementInfoFactory.createSquareMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy3(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{450, 450}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createDHDMovementDecorator()));
//		enemies.add(enemyFactory.createSpecialEnemy4(RedEnemy.class, RLMovementActionFactory.class, new int[]{600, 600}, MovementActionDecoratorFactory.createDHDMovementDecorator()));
//		enemies.add(enemyFactory.createSpecialEnemy3(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 750}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createDCMovementDecorator()));
//		enemies.add(enemyFactory.createSpecialEnemy3(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 750}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createCDMovementDecorator()));
//		enemies.add(enemyFactory.createSpecialEnemy3(RedEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 950}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createCCMovementDecorator()));
//		enemies.add(enemyFactory.createSpecialEnemy3(RedEnemy.class, SpecialMovementActionFactory.class, new int[]{750, 1050}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createCDCMovementDecorator()));
//	
//		enemies.add(enemyFactory.createSpecialEnemy(RedEnemy.class, RLMovementActionFactory.class, new int[]{0, 0}));

//		enemies.add(enemyFactory.createSpecialEnemy3(BlueEnemy.class, SpecialMovementActionFactory.class, new int[]{450, 450}, MovementInfoFactory.createSquareMovementInfo(), MovementActionDecoratorFactory.createDDMovementDecorator()));

//		enemies.add(enemyFactory.createSpecialEnemy4(RedEnemy.class, RLMovementActionFactory.class, new int[]{450, 450}, MovementActionDecoratorFactory.createCCMovementDecorator()));
        val factory = RLMovementActionFactory()
        val innerAction = factory.createMovementAction()
        //		MovementAction action = new DoubleDecorator(new DoubleDecorator(new DoubleDecorator(innerAction)));
        val action: MovementAction = MovementActionSetWithThread()
        action.addMovementAction(DoubleDecorator(innerAction))
        //		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1050}, action));
        var actionD: MovementAction = DoubleDecorator(action)
        //		MovementAction actionD = new DoubleDecorator(new DoubleDecorator(action));
        actionD = DoubleDecorator(DoubleDecorator(actionD))
        //		MovementAction actionDD = new DoubleDecorator(new DoubleDecorator(actionD));
        val actionDD: MovementAction = DoubleDecorator(actionD)
        var newaction: MovementAction = MovementActionSetWithThread()

        //		newaction.addMovementAction(new DoubleDecorator(actionDD));
//		
//		newaction = new CopyMoveDecorator(newaction);

//		newaction = new CopyMoveDecorator(new DoubleDecorator(new RLMovementActionFactory().createMovementAction()));
//		newaction = new DoubleDecorator(new DoubleDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction())));
//		newaction = new CopyMoveDecorator(new CopyMoveDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction())));
        newaction = CopyMoveDecorator(
            MovementActionSetWithThread().addMovementAction(
                CopyMoveDecorator(
                    MovementActionSetWithThread().addMovementAction(
                        RLMovementActionFactory().createMovementAction()
                    ) as MovementActionSet?
                )
            ) as MovementActionSet?
        )
        val newaction2: MovementAction = MovementActionSetWithThread()
        newaction2.addMovementAction(newaction)


        /*s
		newaction2.addMovementAction(actionDD);
//		MovementAction action2 = new MovementActionSet();
		newaction = new CopyMoveDecorator(new DoubleDecorator(new CopyMoveDecorator(new RLMovementActionFactory().createMovementAction())));
//		newaction2 = new MovementActionSet();
		newaction2.addMovementAction(newaction);
//		newaction2 = new CopyMoveDecorator(new CopyMoveDecorator(newaction2));
		newaction2 = new DoubleDecorator(new CopyMoveDecorator(newaction2));
		
		MovementAction newaction3 = new MovementActionSetWithThread();
		
		newaction3.addMovementAction(newaction2);
		newaction3.addMovementAction(new RLMovementActionFactory().createMovementAction());
		newaction3.addMovementAction(new MovementActionSetWithThread().addMovementAction(new MovementActionSetWithThread().addMovementAction(new MovementActionSetWithThread().addMovementAction(new RLMovementActionFactory().createMovementAction()))));
		newaction3 = new DoubleDecorator(new CopyMoveDecorator(newaction3));
		// */


//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, actionD));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, actionDD));
//				enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, newaction));
//				enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, newaction2));
//				enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1100}, newaction3));

//		SpecialMovementActionFactory actionFactory = new SpecialMovementActionFactory();
//		MovementAction RLDRL = actionFactory.createMovementActionByMerge(new RLMovementActionFactory().createMovementAction(), action);
//		RLDRL = actionFactory.createMovementActionByMerge(new RLMovementActionFactory().createMovementAction(), RLDRL);
//		RLDRL = new DoubleDecorator(RLDRL);
//		RLDRL.doInfo();
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 1150}, RLDRL));

//		MovementAction newaction4 = new CopyMoveDecorator(new MovementActionSet().addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo())));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));

//		MovementAction newaction4 = new MovementActionSet();
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		MovementAction newaction4 = new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo());
//		newaction4 = new MovementActionSet().addMovementAction(newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo())));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new DoubleDecorator(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new ReturnBackMoveDecorator(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new InverseMovementInfoDecorator(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new InverseMoveOrderDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new ReturnBackDecorator(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new ReturnBackDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new InverseMovementInfoAppendDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new CopyMoveDecorator(new DoubleDecorator(new AffterHalfPartDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet()))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new DoubleDecorator(new CopyMoveDecorator(new DoubleDecorator(new AffterHalfPartDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet())))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{300, 500}, newaction4));
//		MovementAction newaction4 = new DoubleDecorator(new PartOfAppendDecorator(new CopyMoveDecorator(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet()))))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		newaction4 = new DoubleDecorator(new MovementActionSet().addMovementAction(newaction4));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{300, 500}, newaction4));
//		MovementAction newaction4 = new DoubleDecorator(new PartOfOrigizalDecorator(new CopyMoveDecorator(new CopyMoveDecorator(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet())))))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		newaction4 = new DoubleDecorator(new MovementActionSet().addMovementAction(newaction4));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{800, 500}, newaction4));
//		MovementAction newaction4 = new DoubleDecorator(new PartOfOrigizalDecorator(new CopyMoveDecorator(new PartOfAppendDecorator(new CopyMoveDecorator(new DoubleDecorator(new PartOfAppendDecorator(new InverseMovementInfoAppendDecorator(new MovementActionSet()))))))));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		newaction4 = new DoubleDecorator(new MovementActionSet().addMovementAction(newaction4));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{800, 500}, newaction4));

//		MovementAction newaction4 = new SimultaneouslyMovementActionSet();
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquareMovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleRMovementInfo()));
//		newaction4.setMovementActionController(new MovementAtionController());
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));

//		MovementAction newaction4 = new SimultaneouslyMovementActionSet();
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createCurveSingleR12MovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createSingleR12MovementInfo()));
//		newaction4.setMovementActionController(new MovementAtionController());
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));

//		MovementAction newaction4 = new SimultaneouslyMovementActionSet();
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createCurveSingleR121MovementInfo()));
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createCurveSingleR122MovementInfo()));
//		newaction4.setMovementActionController(new MovementAtionController());
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{500, 800}, newaction4));

//		MovementAction newaction4 = new SimultaneouslyMovementActionSet();
//		newaction4.addMovementAction(new CopyMoveDecorator(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSquare12MovementInfo())));
//		newaction4.addMovementAction(new SpecialMovementActionFactory().createMovementAction(MovementInfoFactory.createSingle4RMovementInfo()));
//		newaction4.setMovementActionController(new MovementAtionController());
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{0, 800}, newaction4));

//		MovementAction newaction4 = new CopyMoveDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createCurveSingleRMovementInfo()));
        // /**/        newaction4.setMovementActionController(new MovementAtionController ()); */
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));

//		MovementAction newaction4 = new CopyMoveDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createRotation45GravitySingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new CopyMoveDecorator(new MovementActionSet());

//		MovementAction newaction4 = new GravityInverseAngelMovementInfoAppendDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createRotation45GravitySingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new GravityCyclePathMovementInfoAppendDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createRotation45GravitySingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new GravityInversePathMovementInfoAppendDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createRotation45GravitySingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new GravityWavePathMovementInfoAppendDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createRotation45GravitySingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));
//		MovementAction newaction4 = new GravitySlopeWavePathMovementInfoAppendDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createRotation45GravitySingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));

//		MovementAction newaction4 = new GravitySlopeWavePathMovementInfoAppendDecorator(new MovementActionSet());
//		newaction4.addMovementAction(new MovementActionFrameItem(MovementInfoFactory.createFrameRotation45GravitySingleRMovementInfo()));
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{100, 500}, newaction4));

//		MovementAction newaction4 = new SimultaneouslyMovementActionSet();
//		newaction4.addMovementAction(new MovementActionItem(MovementInfoFactory.createCircleMovementInfo()));
//		newaction4.setMovementActionController(new MovementAtionController());
//		enemies.add(enemyFactory.createSpecialEnemy5(RedEnemy.class, new int[]{500, 800}, newaction4));
    }

    fun createLevel2Enemy() {
        val enemyFactory = EnemyFactory()
        enemies.add(enemyFactory.createRLRedEnemy(intArrayOf(50, 50)))
        enemies.add(enemyFactory.createRLBlueEnemy(intArrayOf(100, 100)))
        enemies.add(
            enemyFactory.createSpecialEnemy(
                BlueEnemy::class.java,
                RLMovementActionFactory::class.java,
                intArrayOf(150, 150)
            )
        )
        enemies.add(
            enemyFactory.createSpecialEnemy2(
                BlueEnemy::class.java,
                SpecialMovementActionFactory::class.java,
                intArrayOf(300, 300),
                MovementInfoFactory.createSquareMovementInfo()
            )
        )
    }

    fun drawEnemies(canvas: Canvas?) {
        for (enemy in enemies) {
//			enemy.draw(canvas);
            enemy.drawSelf(canvas, null)
        }
    }

    fun moveEnemies(dx: Int, dy: Int) {
        for (enemy in enemies) {
            enemy.move(dx.toFloat(), dy.toFloat())
        }
    }

    fun moveEnemiesUpAndDown(dy: Int) {
        for (enemy in enemies) {
            enemy.moveUpAndDown(dy.toFloat())
        }
    }

    fun moveEnemiesLeftAndRight(dx: Int) {
        for (enemy in enemies) {
            enemy.moveLeftAndRight(dx.toFloat())
        }
    }

    fun startMoveEnemies() {
        for (enemy in enemies) {
            enemy.getMovementAction().start()
        }
    }

    fun showEnemiesMovementDescriptioins() {
        for (enemy in enemies) {
            val description = enemy.getMovementActionDescriptions()
            Log.e("description", description)
        }
    }

    companion object {
        fun setRedEnemyBitmap(redEnemyBitmap: Bitmap?) {
            EnemyFactory.Companion.setRedEnemyBitmap(redEnemyBitmap)
        }

        fun setBlueEnemyBitmap(blueEnemyBitmap: Bitmap?) {
            EnemyFactory.Companion.setBlueEnemyBitmap(blueEnemyBitmap)
        }
    }
}
