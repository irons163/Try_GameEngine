package com.example.try_gameengine.action;

import java.util.List;

//import com.rits.cloning.Cloner;

import android.util.Log;

/**
 * @author irons
 *
 */
public class EaseInDecorator extends EaseRateDecorator {

	public EaseInDecorator(MovementAction action, float rate) {
		super(action, rate);
	}

	@Override
	public String getDescription() {
		return "Double " + action.getDescription();
	}

	@Override
	void doinin(final MovementActionInfo info) {
		// TODO Auto-generated method stub
		info.getData().setMovementActionItemUpdateTimeDataDelegate(new MovementActionItemTrigger.DataDelegate() {
			@Override
			public void update(float t) {
				// TODO Auto-generated method stub
				
				super.update((float) Math.pow(t, rate));
			}
		});
	}
}
