package com.example.try_gameengine.physics

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.example.try_gameengine.scene.EasyScene

object PhysicsFactory {
    fun bodyWithCircleOfRadius(radius: Float): PhysicsBody {
        /**設置body形狀 */
        val circle = CircleShape()
        /**半徑，要將螢幕的參數轉化到物理世界中  */
        circle.setRadius(radius / EasyScene.Companion.RATE)

        /**設置FixtureDef  */
        val fDef = FixtureDef()
        //		if(isStatic)
//		{
//			/**密度為0時，在物理世界中不受外力影響，為靜止的 */
//			fDef.density=0;
//		}
//		else
//		{
//			/**密度不為0時，在物理世界中會受外力影響 */
//			fDef.density=1;
//		}
        /**設置摩擦力，範圍為 0∼1  */
        fDef.friction = 1.0f
        /**設置物體碰撞的回復力，?翟醬螅鍰逶接械? */
        fDef.restitution = 0.3f
        /**添加形狀 */
        fDef.shape = circle

        /**設置BodyDef  */
        val bodyDef = BodyDef()

        /**此處一定要設置，即使density不為0，
         * 若此處不將body.type設置為BodyType.DYNAMIC,物體亦會靜止
         // */
        val isStatic = false

        bodyDef.type = if (isStatic) BodyType.StaticBody else BodyType.DynamicBody


        /**設置body位置，要將螢幕的參數轉化到物理世界中  */
//		bodyDef.position.set((x)/easyScene.RATE, (y)/easyScene.RATE);
        /**創建body */
//		Body body=easyScene.world.createBody(bodyDef);
        /**添加 m_userData  */
//		body.setUserData(bird);

        //	body.createShape(fDef); //舊版JBox2D的創建方法
        /**為body創建Fixture */
//		body.createFixture(fDef);

        //	body.setMassFromShapes();	//舊版JBox2D的創建方法
        val physicsBody = PhysicsBody(bodyDef)
        physicsBody.setFixtureDef(fDef)

        return physicsBody
    }

    fun createCircle(x: Float, y: Float, r: Float, isStatic: Boolean, easyScene: EasyScene): Body {
        /**設置body形狀 */
        val circle = CircleShape()
        /**半徑，要將螢幕的參數轉化到物理世界中  */
        circle.setRadius(r / EasyScene.Companion.RATE)

        /**設置FixtureDef  */
        val fDef = FixtureDef()
        if (isStatic) {
            /**密度為0時，在物理世界中不受外力影響，為靜止的  */
            fDef.density = 0f
        } else {
            /**密度不為0時，在物理世界中會受外力影響  */
            fDef.density = 1f
        }
        /**設置摩擦力，範圍為 0∼1  */
        fDef.friction = 1.0f
        /**設置物體碰撞的回復力，?翟醬螅鍰逶接械? */
        fDef.restitution = 0.3f
        /**添加形狀 */
        fDef.shape = circle

        /**設置BodyDef  */
        val bodyDef = BodyDef()

        /**此處一定要設置，即使density不為0，
         * 若此處不將body.type設置為BodyType.DYNAMIC,物體亦會靜止
         // */
        bodyDef.type = if (isStatic) BodyType.StaticBody else BodyType.DynamicBody
        /**設置body位置，要將螢幕的參數轉化到物理世界中  */
        bodyDef.position.set((x) / EasyScene.Companion.RATE, (y) / EasyScene.Companion.RATE)

        /**創建body */
        val body = easyScene.world!!.createBody(bodyDef)

        /**添加 m_userData  */
//		body.setUserData(bird);

        //	body.createShape(fDef); //舊版JBox2D的創建方法
        /**為body創建Fixture */
        body.createFixture(fDef)


        //	body.setMassFromShapes();	//舊版JBox2D的創建方法
        return body
    }
}
