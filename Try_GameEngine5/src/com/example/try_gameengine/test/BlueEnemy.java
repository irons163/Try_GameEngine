package com.example.try_gameengine.test;

import com.example.try_gameengine.action.MovementAction;


public class BlueEnemy extends Enemy{

	public BlueEnemy(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public BlueEnemy(int x, int y, MovementAction action) {
		super(x, y, action);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		bitmap = EnemyFactory.getBlueEnemyBitmap();
		if(bitmap==null)
			bitmap = BitmapUtil.bluePoint;
		if(bitmap==null)
			throw new NullPointerException();
	}

}
