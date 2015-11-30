package com.example.try_gameengine.physics;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.example.try_gameengine.scene.EasyScene;

public class PhysicsFactory {

	public static PhysicsBody bodyWithCircleOfRadius(float radius){
		/**³]¸mbody§Îª¬*/
	    CircleShape circle = new CircleShape();
	    /**¥b®|¡A­n±N¿Ã¹õªº°Ñ¼ÆÂà¤Æ¨ìª«²z¥@¬É¤¤ */
	    circle.setRadius(radius/EasyScene.RATE);
		
	    /**³]¸mFixtureDef */
		FixtureDef fDef=new FixtureDef();
//		if(isStatic)
//		{
//			/**±K«×¬°0®É¡A¦bª«²z¥@¬É¤¤¤£¨ü¥~¤O¼vÅT¡A¬°ÀR¤îªº */
//			fDef.density=0;
//		}
//		else
//		{
//			/**±K«×¤£¬°0®É¡A¦bª«²z¥@¬É¤¤·|¨ü¥~¤O¼vÅT */
//			fDef.density=1;
//		}
		/**³]¸m¼¯À¿¤O¡A½d³ò¬° 0¡ã1 */
		fDef.friction=1.0f;
		/**³]¸mª«Åé¸I¼²ªº¦^´_¤O¡A?»CÂæëgû®Áì¶j±µ±ñýn?*/
		fDef.restitution=0.3f;
		/**²K¥[§Îª¬*/
		fDef.shape=circle;

	    /**³]¸mBodyDef */
		BodyDef bodyDef=new BodyDef();
		
		/**¦¹³B¤@©w­n³]¸m¡A§Y¨Ïdensity¤£¬°0¡A
		 * ­Y¦¹³B¤£±Nbody.type³]¸m¬°BodyType.DYNAMIC,ª«Åé¥ç·|ÀR¤î
		 * */
		boolean isStatic = false;
		  
		bodyDef.type=isStatic?BodyType.StaticBody:BodyType.DynamicBody;
		/**³]¸mbody¦ì¸m¡A­n±N¿Ã¹õªº°Ñ¼ÆÂà¤Æ¨ìª«²z¥@¬É¤¤ */
//		bodyDef.position.set((x)/easyScene.RATE, (y)/easyScene.RATE);
		
		/**³Ð«Øbody*/
//		Body body=easyScene.world.createBody(bodyDef);
		
		/**²K¥[ m_userData */
//		body.setUserData(bird);
		
	//	body.createShape(fDef); //ÂÂª©JBox2Dªº³Ð«Ø¤èªk
		
		/**¬°body³Ð«ØFixture*/
//		body.createFixture(fDef);
		
	//	body.setMassFromShapes();	//ÂÂª©JBox2Dªº³Ð«Ø¤èªk
		
		
		PhysicsBody physicsBody = new PhysicsBody(bodyDef);
		physicsBody.setFixtureDef(fDef);
		
		return physicsBody;
	}
	
	public static Body createCircle(float x,float y,float r,boolean isStatic, EasyScene easyScene){
		/**³]¸mbody§Îª¬*/
	    CircleShape circle = new CircleShape();
	    /**¥b®|¡A­n±N¿Ã¹õªº°Ñ¼ÆÂà¤Æ¨ìª«²z¥@¬É¤¤ */
	    circle.setRadius(r/easyScene.RATE);
		
	    /**³]¸mFixtureDef */
		FixtureDef fDef=new FixtureDef();
		if(isStatic)
		{
			/**±K«×¬°0®É¡A¦bª«²z¥@¬É¤¤¤£¨ü¥~¤O¼vÅT¡A¬°ÀR¤îªº */
			fDef.density=0;
		}
		else
		{
			/**±K«×¤£¬°0®É¡A¦bª«²z¥@¬É¤¤·|¨ü¥~¤O¼vÅT */
			fDef.density=1;
		}
		/**³]¸m¼¯À¿¤O¡A½d³ò¬° 0¡ã1 */
		fDef.friction=1.0f;
		/**³]¸mª«Åé¸I¼²ªº¦^´_¤O¡A?»CÂæëgû®Áì¶j±µ±ñýn?*/
		fDef.restitution=0.3f;
		/**²K¥[§Îª¬*/
		fDef.shape=circle;

	    /**³]¸mBodyDef */
		BodyDef bodyDef=new BodyDef();
		
		/**¦¹³B¤@©w­n³]¸m¡A§Y¨Ïdensity¤£¬°0¡A
		 * ­Y¦¹³B¤£±Nbody.type³]¸m¬°BodyType.DYNAMIC,ª«Åé¥ç·|ÀR¤î
		 * */
		bodyDef.type=isStatic?BodyType.StaticBody:BodyType.DynamicBody;
		/**³]¸mbody¦ì¸m¡A­n±N¿Ã¹õªº°Ñ¼ÆÂà¤Æ¨ìª«²z¥@¬É¤¤ */
		bodyDef.position.set((x)/easyScene.RATE, (y)/easyScene.RATE);
		
		/**³Ð«Øbody*/
		Body body=easyScene.world.createBody(bodyDef);
		
		/**²K¥[ m_userData */
//		body.setUserData(bird);
		
	//	body.createShape(fDef); //ÂÂª©JBox2Dªº³Ð«Ø¤èªk
		
		/**¬°body³Ð«ØFixture*/
		body.createFixture(fDef); 
		
	//	body.setMassFromShapes();	//ÂÂª©JBox2Dªº³Ð«Ø¤èªk
		
		return body;
	}
}
