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
		/**�]�mbody�Ϊ�*/
	    CircleShape circle = new CircleShape();
	    /**�b�|�A�n�N�ù����Ѽ���ƨ쪫�z�@�ɤ� */
	    circle.setRadius(radius/EasyScene.RATE);
		
	    /**�]�mFixtureDef */
		FixtureDef fDef=new FixtureDef();
//		if(isStatic)
//		{
//			/**�K�׬�0�ɡA�b���z�@�ɤ������~�O�v�T�A���R� */
//			fDef.density=0;
//		}
//		else
//		{
//			/**�K�פ���0�ɡA�b���z�@�ɤ��|���~�O�v�T */
//			fDef.density=1;
//		}
		/**�]�m�����O�A�d�� 0��1 */
		fDef.friction=1.0f;
		/**�]�m����I�����^�_�O�A?�C���g����j�����n?*/
		fDef.restitution=0.3f;
		/**�K�[�Ϊ�*/
		fDef.shape=circle;

	    /**�]�mBodyDef */
		BodyDef bodyDef=new BodyDef();
		
		/**���B�@�w�n�]�m�A�Y��density����0�A
		 * �Y���B���Nbody.type�]�m��BodyType.DYNAMIC,�����|�R��
		 * */
		boolean isStatic = false;
		  
		bodyDef.type=isStatic?BodyType.StaticBody:BodyType.DynamicBody;
		/**�]�mbody��m�A�n�N�ù����Ѽ���ƨ쪫�z�@�ɤ� */
//		bodyDef.position.set((x)/easyScene.RATE, (y)/easyScene.RATE);
		
		/**�Ы�body*/
//		Body body=easyScene.world.createBody(bodyDef);
		
		/**�K�[ m_userData */
//		body.setUserData(bird);
		
	//	body.createShape(fDef); //�ª�JBox2D���Ыؤ�k
		
		/**��body�Ы�Fixture*/
//		body.createFixture(fDef);
		
	//	body.setMassFromShapes();	//�ª�JBox2D���Ыؤ�k
		
		
		PhysicsBody physicsBody = new PhysicsBody(bodyDef);
		physicsBody.setFixtureDef(fDef);
		
		return physicsBody;
	}
	
	public static Body createCircle(float x,float y,float r,boolean isStatic, EasyScene easyScene){
		/**�]�mbody�Ϊ�*/
	    CircleShape circle = new CircleShape();
	    /**�b�|�A�n�N�ù����Ѽ���ƨ쪫�z�@�ɤ� */
	    circle.setRadius(r/easyScene.RATE);
		
	    /**�]�mFixtureDef */
		FixtureDef fDef=new FixtureDef();
		if(isStatic)
		{
			/**�K�׬�0�ɡA�b���z�@�ɤ������~�O�v�T�A���R� */
			fDef.density=0;
		}
		else
		{
			/**�K�פ���0�ɡA�b���z�@�ɤ��|���~�O�v�T */
			fDef.density=1;
		}
		/**�]�m�����O�A�d�� 0��1 */
		fDef.friction=1.0f;
		/**�]�m����I�����^�_�O�A?�C���g����j�����n?*/
		fDef.restitution=0.3f;
		/**�K�[�Ϊ�*/
		fDef.shape=circle;

	    /**�]�mBodyDef */
		BodyDef bodyDef=new BodyDef();
		
		/**���B�@�w�n�]�m�A�Y��density����0�A
		 * �Y���B���Nbody.type�]�m��BodyType.DYNAMIC,�����|�R��
		 * */
		bodyDef.type=isStatic?BodyType.StaticBody:BodyType.DynamicBody;
		/**�]�mbody��m�A�n�N�ù����Ѽ���ƨ쪫�z�@�ɤ� */
		bodyDef.position.set((x)/easyScene.RATE, (y)/easyScene.RATE);
		
		/**�Ы�body*/
		Body body=easyScene.world.createBody(bodyDef);
		
		/**�K�[ m_userData */
//		body.setUserData(bird);
		
	//	body.createShape(fDef); //�ª�JBox2D���Ыؤ�k
		
		/**��body�Ы�Fixture*/
		body.createFixture(fDef); 
		
	//	body.setMassFromShapes();	//�ª�JBox2D���Ыؤ�k
		
		return body;
	}
}
