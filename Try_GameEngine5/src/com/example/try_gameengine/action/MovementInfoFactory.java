package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.GameView;

public class MovementInfoFactory {
	private static List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
	
	public static List<MovementActionInfo> createSquareMovementInfo(){
		infos.clear();
		MovementActionInfo right = new MovementActionInfo(1000, 200, 10, 0, "R");
		infos.add(right);
		MovementActionInfo down = new MovementActionInfo(1000, 200, 0, 10, "D");
		infos.add(down);
		MovementActionInfo left = new MovementActionInfo(1000, 200, -10, 0, "L");
		infos.add(left);
		MovementActionInfo up = new MovementActionInfo(1000, 200, 0, -10, "U");
		infos.add(up);
		
		return infos;
	}
	
	public static List<MovementActionInfo> createSquare12MovementInfo(){
		infos.clear();
		MovementActionInfo right = new MovementActionInfo(1000, 100, 10, 0, "R");
		infos.add(right);
		MovementActionInfo down = new MovementActionInfo(1000, 100, 0, 10, "D");
		infos.add(down);
		MovementActionInfo left = new MovementActionInfo(1000, 100, -10, 0, "L");
		infos.add(left);
		MovementActionInfo up = new MovementActionInfo(1000, 100, 0, -10, "U");
		infos.add(up);
		MovementActionInfo right2 = new MovementActionInfo(1000, 100, 10, 0, "R");
		infos.add(right2);
		MovementActionInfo down2 = new MovementActionInfo(1000, 100, 0, 10, "D");
		infos.add(down2);
		MovementActionInfo left2 = new MovementActionInfo(1000, 100, -10, 0, "L");
		infos.add(left2);
		MovementActionInfo up2 = new MovementActionInfo(1000, 100, 0, -10, "U");
		infos.add(up2);
		MovementActionInfo right3 = new MovementActionInfo(1000, 100, 10, 0, "R");
		infos.add(right3);
		MovementActionInfo down3 = new MovementActionInfo(1000, 100, 0, 10, "D");
		infos.add(down3);
		MovementActionInfo left3 = new MovementActionInfo(1000, 100, -10, 0, "L");
		infos.add(left3);
		MovementActionInfo up3 = new MovementActionInfo(1000, 100, 0, -10, "U");
		infos.add(up3);
		return infos;
	}
	
	public static List<MovementActionInfo> createSingle4RMovementInfo(){
		infos.clear();
		MovementActionInfo right = new MovementActionInfo(8000, 50, 1, 0, "R");
		infos.add(right);
		MovementActionInfo down = new MovementActionInfo(8000, 50, 1, 0, "R");
		infos.add(down);
		MovementActionInfo left = new MovementActionInfo(8000, 50, 1, 0, "R");
		infos.add(left);
//		MovementActionInfo up = new MovementActionInfo(4000, 100, 1, 0, "R");
//		infos.add(up);
		
		return infos;
	}
	
	public static MovementActionInfo createSingleRMovementInfo(){
//		MovementActionInfo right = new MovementActionInfo(4000, 500, 3, 0, "R");
		MovementActionInfo right = new MovementActionInfo(1000, 200, 10, 0, "R");
		return right;
	}
	
	public static MovementActionInfo createCurveSingleRMovementInfo(){
		MovementActionInfo right = new MovementActionInfo(12000, 200, 10, 0, "R", new RotationCurveController(30));
		return right;
	}
	
	public static MovementActionInfo createGravitySingleRMovementInfo(){
		MovementActionInfo right = new MovementActionInfo(1000, 200, 10, 0, "R", true);
		return right;
	}
	
	public static MovementActionInfo createRotation45GravitySingleRMovementInfo(){
		MovementActionInfo right = new MovementActionInfo(1000, 200, 30, 0, "R", new RotationOnceController(45), true);
		return right;
	}
	
	public static MovementActionFrameInfo createFrameRotation45GravitySingleRMovementInfo(){
		MovementActionFrameInfo right = new MovementActionFrameInfo(new long[]{1000,1000,1000}, 30, 0, "R", new RotationOnceController(45), true);
		return right;
	}
	
	public static MovementActionInfo createSingleR12MovementInfo(){
		MovementActionInfo right = new MovementActionInfo(12000, 200, 10, 0, "R");
		return right;
	}
	
	public static MovementActionInfo createCurveSingleR12MovementInfo(){
		MovementActionInfo right = new MovementActionInfo(12000, 200, 10, 0, "R", new RotationCurveController(30));
		return right;
	}
	
	public static MovementActionInfo createCurveSingleR121MovementInfo(){
		MovementActionInfo right = new MovementActionInfo(12000, 200, 100, 0, "R", new RotationCurveController(50));
		return right;
	}
	
	public static MovementActionInfo createCurveSingleR122MovementInfo(){
		MovementActionInfo right = new MovementActionInfo(12000, 200, 30, 0, "R", new RotationCurveController(10));
		return right;
	}
	

}
