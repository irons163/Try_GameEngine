//package com.example.try_gameengine.avg;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import com.example.try_gameengine.assemble.AssembleView;
//import com.example.try_gameengine.assemble.AssembleViewConfig;
//import com.example.try_gameengine.assemble.AssembleViewConfig.CenterConfig;
//import com.example.try_gameengine.assemble.AssembleViewConfig.DirectionConfig;
//import com.example.try_gameengine.framework.AChessGameModel;
//
////import android.support.v7.app.ActionBarActivity;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//public class MainActivity extends Activity {
//	MyAVGScreen myAVGScreen;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		setContentView(R.layout.activity_main);
//		
//		myAVGScreen = new MyAVGScreen(this);
//	}
//	
//
//	
//	class MyAVGScreen extends AVGScreen{
//		// �ۭq�R�O�]���Ǧۭq�R�O���F��X�g���F����A��ڤ����ˡ^
//		String flag ;
//
//		String[] selects ;
//
//		int type;
//		
//		public MyAVGScreen(Activity context) {
//			super(context);
//			// TODO Auto-generated constructor stub
//		}
//		
//		@Override
//		public void initCommandConfig(Command command) {
//			flag = "�ۭq�R�O.";
//			selects = new String[]{ "�P��T�d�Ӥ��ӡH" };
//			// ��l�Ʈɹw�]�ܼ�
//			command.setVariable("p", "res/p.png");
//			command.setVariable("sel0", selects[0]);
//		}
//		
//		@Override
//		protected void initMessage() {
//			// TODO Auto-generated method stub
//			message.setFontColor(LColor.white);
//		}
//		
//		@Override
//		public synchronized boolean nextScript(String mes) {
//			// TODO Auto-generated method stub
//			// �ۭq�R�O�]���Ǧۭq�R�O���F��X�g���F����A��ڤ����ˡ^
////			if (roleName != null) {
////				if ("noname".equalsIgnoreCase(mes)) {
////					roleName.setVisible(false);
////				} else if ("name0".equalsIgnoreCase(mes)) {
////					roleName.setVisible(true);
////					roleName.setBackground("res/name0.png",false);
////					roleName.setLocation(5, 15);
////				} else if ("name1".equalsIgnoreCase(mes)) {
////					roleName.setVisible(true);
////					roleName.setBackground("res/name1.png",false);
////					roleName.setLocation(getWidth() - roleName.getWidth() - 5, 15);
////				}
////			}
//			if ((flag + "�P�P").equalsIgnoreCase(mes)) {
//				// �K�[�}���ƥ�аO�]�ݭn�I�������^
////				setScrFlag(true);
//				type = 1;
//				return false;
//			} else if ((flag + "�h���a�A�P�P").equalsIgnoreCase(mes)) {
//				type = 0;
//			} else if ((flag + "����Ѥ~").equalsIgnoreCase(mes)) {
//				
//				AssembleViewConfig config = new AssembleViewConfig.Builder()
//				.setCenterConfig(CenterConfig.CENTER).build();
//				final AssembleView view3 = new AssembleView(R.layout.selects_layout,
//						context);
//				view3.setConfig(config);
//				myAVGScreen.view.addExtraView(view3, R.layout.selects_layout);
//				
//				myAVGScreen.setLocked(true);
//				
//				Button button = (Button) view3.getView().findViewById(R.id.button1);
//				button.setOnClickListener(new View.OnClickListener() {
//					
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//						((ViewGroup)view3.getView().getParent()).removeView(view3.getView());
//						myAVGScreen.setLocked(false);
//						myAVGScreen.nextScript();
//					}
//				});
//				
////				message.setVisible(false);
////				setScrFlag(true);
//				// �j����w�}��
////				setLocked(true);
////				LButton yes = new LButton("res/dialog_yes.png", 112, 33) {
////					public void doClick() {
////						// �Ѱ���w
////						setLocked(false);
////						// Ĳ�o�ƥ�
////						 click();
////						// �R����e���s
////						remove(this);
////					}
////				};
////				centerOn(yes);
////				add(yes);
//				return false;
//			}
//			return true;
//		}
//		
//		public void drawScreen(Canvas g, Paint paint) {
//			switch (type) {
//			case 1:
//				if(paint==null)
//					paint = new Paint();
//				paint.setAntiAlias(true);
//				int colorTmp = paint.getColor();
//				paint.setColor(LColor.white.getARGB());
//				g.drawRect(0, 130, 100, 100, paint);
//				paint.setAntiAlias(false);
//				paint.setColor(colorTmp);
//				break;
//
//			default:
//				break;
//			}
//		}
//		
//		class MyButton extends Button{
//
//			public MyButton(Context context) {
//				super(context);
//				// TODO Auto-generated constructor stub
//			}
//			
//		}
//	}
//}
//
//
//
