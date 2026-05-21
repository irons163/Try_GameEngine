package com.example.try_gameengine.action

/**
 * MovementInfoFactory create MovementInfo. This is maybe removed.
 * @author irons
 // */
object MovementInfoFactory {
    private val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()

    fun createSquareMovementInfo(): MutableList<MovementActionInfo?> {
        infos.clear()
        val right = MovementActionInfo(1000, 200, 10f, 0f, "R")
        infos.add(right)
        val down = MovementActionInfo(1000, 200, 0f, 10f, "D")
        infos.add(down)
        val left = MovementActionInfo(1000, 200, -10f, 0f, "L")
        infos.add(left)
        val up = MovementActionInfo(1000, 200, 0f, -10f, "U")
        infos.add(up)

        return infos
    }

    /**
     * @return
     // */
    fun createSquare12MovementInfo(): MutableList<MovementActionInfo?> {
        infos.clear()
        val right = MovementActionInfo(1000, 100, 10f, 0f, "R")
        infos.add(right)
        val down = MovementActionInfo(1000, 100, 0f, 10f, "D")
        infos.add(down)
        val left = MovementActionInfo(1000, 100, -10f, 0f, "L")
        infos.add(left)
        val up = MovementActionInfo(1000, 100, 0f, -10f, "U")
        infos.add(up)
        val right2 = MovementActionInfo(1000, 100, 10f, 0f, "R")
        infos.add(right2)
        val down2 = MovementActionInfo(1000, 100, 0f, 10f, "D")
        infos.add(down2)
        val left2 = MovementActionInfo(1000, 100, -10f, 0f, "L")
        infos.add(left2)
        val up2 = MovementActionInfo(1000, 100, 0f, -10f, "U")
        infos.add(up2)
        val right3 = MovementActionInfo(1000, 100, 10f, 0f, "R")
        infos.add(right3)
        val down3 = MovementActionInfo(1000, 100, 0f, 10f, "D")
        infos.add(down3)
        val left3 = MovementActionInfo(1000, 100, -10f, 0f, "L")
        infos.add(left3)
        val up3 = MovementActionInfo(1000, 100, 0f, -10f, "U")
        infos.add(up3)
        return infos
    }

    fun createSingle4RMovementInfo(): MutableList<MovementActionInfo?> {
        infos.clear()
        val right = MovementActionInfo(8000, 50, 1f, 0f, "R")
        infos.add(right)
        val down = MovementActionInfo(8000, 50, 1f, 0f, "R")
        infos.add(down)
        val left = MovementActionInfo(8000, 50, 1f, 0f, "R")
        infos.add(left)

        //		MovementActionInfo up = new MovementActionInfo(4000, 100, 1, 0, "R");
//		infos.add(up);
        return infos
    }

    fun createSingleRMovementInfo(): MovementActionInfo {
//		MovementActionInfo right = new MovementActionInfo(4000, 500, 3, 0, "R");
        val right = MovementActionInfo(1000, 200, 10f, 0f, "R")
        return right
    }

    //	public static MovementActionInfo createCurveSingleRMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(12000, 200, 10, 0, "R", new RotationCurveController(30));
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo createGravitySingleRMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(1000, 200, 10, 0, "R", true);
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo createRotation45GravitySingleRMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(1000, 200, 30, 0, "R", new RotationOnceController(45), true);
    //		return right;
    //	}
    //	public static MovementActionFrameInfo createFrameRotation45GravitySingleRMovementInfo(){
    //		MovementActionFrameInfo right = new MovementActionFrameInfo(new long[]{1000,1000,1000}, 30, 0, "R", new RotationOnceController(45), true);
    //		return right;
    //	}
    fun createSingleR12MovementInfo(): MovementActionInfo {
        val right = MovementActionInfo(12000, 200, 10f, 0f, "R")
        return right
    } //	public static MovementActionInfo createCurveSingleR12MovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(12000, 200, 10, 0, "R", new RotationCurveController(30));
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo createCurveSingleR121MovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(12000, 200, 100, 0, "R", new RotationCurveController(50));
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo createCurveSingleR122MovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(12000, 200, 30, 0, "R", new RotationCurveController(10));
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo createCircleMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(24000, 100, 10, 0, "R", new CircleController(-10, 400f, 700f, 450f, 750f));
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo createSubCircleMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(24000, 100, 30, 0, "R", new CircleController(-10, 450, 750, 500, 800));
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo create2CircleMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(24000, 100, 30, 0, "R", new Circle22Controller(-10, 400, 700, 450, 750));
    //		GameView.circleController1 = (Circle22Controller) right.getRotationController();
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo create2SubCircleMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(24000, 500, 30, 0, "R", new Circle22Controller(-10, 450, 750, 500, 800));
    //		GameView.circleController2 = (Circle22Controller) right.getRotationController();
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo createSub2CircleMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(24000, 100, 30, 0, "R", new CircleController(-10, 450, 750, 500, 800));
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo create3CircleMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(24000, 100, 10, 0, "R", new CircleController(5, 400, 700, 450, 750));
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo create3SubCircleMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(24000, 50, 30, 0, "R", new CircleController(5, 450, 750, 500, 800));
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo create32CircleMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(24000, 100, 30, 0, "R", new CircleController(5, 500, 800, 550, 850));
    //		return right;
    //	}
    //	
    //	public static MovementActionInfo create3Sub2CircleMovementInfo(){
    //		MovementActionInfo right = new MovementActionInfo(24000, 100, 30, 0, "R", new CircleController(5, 550, 800, 600, 900));
    //		return right;
    //	}
}
