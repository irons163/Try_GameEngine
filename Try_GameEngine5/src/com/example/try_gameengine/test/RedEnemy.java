package com.example.try_gameengine.test;

import com.example.try_gameengine.action.MovementAction;

public class RedEnemy extends Enemy{

	public RedEnemy(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public RedEnemy(int x, int y, MovementAction action) {
		super(x, y, action);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		setBitmap(EnemyFactory.getRedEnemyBitmap());
		if(getBitmap()==null)
			setBitmap(BitmapUtil.redPoint);
		if(getBitmap()==null)
			throw new NullPointerException();
	}

}
