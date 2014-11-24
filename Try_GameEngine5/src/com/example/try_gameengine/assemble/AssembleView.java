package com.example.try_gameengine.assemble;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.assemble.AssembleViewConfig.CenterConfig;
import com.example.try_gameengine.assemble.AssembleViewConfig.DirectionConfig;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class AssembleView {
	private View view;
	private int resId;
	private Context context;
	private List<AssembleView> assembleViews = new ArrayList<AssembleView>();  
	private AssembleViewConfig config;
	private int viewId = -1;
	private boolean isForceMainLayout = false;
	
	enum RelationViewType{
		NONE, ABOVE, BELOW, LEFT_OF, RIGHT_OF
	}
	
	RelationViewType relationViewType = RelationViewType.NONE;
	
	RelativeLayout.LayoutParams layoutParams;
	
	public AssembleView(View view, Context context){
		this.view = view;
		this.context = context;
	}
	
	public AssembleView(int resId, Context context){
		this.resId = resId;
		this.context = context;
		view = RelativeLayout.inflate(context, resId, null);
		view.setId(resId);
	}
	
	public void setConfig(AssembleViewConfig config){
		this.config = config;
	}
	
	public void addSubView(AssembleView assembleView){
		assembleViews.add(assembleView);
	}
	
	public void addAboveView(AssembleView assembleView, int resId){
		assembleView.relationViewType = RelationViewType.BELOW;
		assembleView.resId = resId;
		assembleViews.add(assembleView);
	}
	
	public void addBelowView(AssembleView assembleView, int resId){
		assembleView.relationViewType = RelationViewType.ABOVE;
		assembleView.resId = resId;
		assembleViews.add(assembleView);
	}
//	
//	public void addSubView(AssembleView assembleView){
//		assembleViews.add(assembleView);
//	}
	
	RelativeLayout relativeLayoutMain;
	
	public View generateViews(){
		if(assembleViews.size()==0 && !isForceMainLayout)
			return view;
		
		relativeLayoutMain = new RelativeLayout(context);
//		RelativeLayout.LayoutParams layoutParams2 = createLayoutViewParams(config);
			
		// layoutParams2.addRule(RelativeLayout.BELOW, 105);
//		this.view.setLayoutParams(layoutParams2);
		
		view = settingViewParams(this, config);
		relativeLayoutMain.addView(this.view);
		
		for(AssembleView assembleView : assembleViews){
			
//			view.setLayoutParams(createLayoutViewParams(assembleView.config));
			
			View view = settingViewParams(assembleView, assembleView.config);
			
			relativeLayoutMain.addView(view);
		}
		
		return relativeLayoutMain;
	}
	
	private View settingViewParams(AssembleView assembleView, AssembleViewConfig config){
		View view;
		if(assembleView.view!=null){
			view = assembleView.view;
		}else{
			view = RelativeLayout.inflate(assembleView.context, assembleView.resId, null);
		}
		
		if(config==null)
			config = AssembleViewConfig.createDefault(context);
		
		RelativeLayout.LayoutParams layoutParams2;
		if(config.w==0 && config.h==0){
			layoutParams2 = new RelativeLayout.LayoutParams(
					config.w > 0 ? (int)config.w : LayoutParams.WRAP_CONTENT, config.h > 0 ? (int)config.h : LayoutParams.WRAP_CONTENT);
		}else{
			layoutParams2 = new RelativeLayout.LayoutParams(
					config.w > 0 ? (int)config.w : LayoutParams.MATCH_PARENT, config.h > 0 ? (int)config.h : LayoutParams.MATCH_PARENT);
		}
		
		
//		((RelativeLayout.LayoutParams)view.getLayoutParams()).getRules();
		
//		layoutParams2.addRule(Color.WHITE, );
		
//		if(config.persentX > 0 || config.persentY > 0){
//			
//			view.set
//		}else{
//			view.setX(config.x);
//			view.setY(config.y);
//		}
			
		if(this.view.getId()==-1)
			this.view.setId(1);
		
		if(assembleView.viewId<0){
			viewId = assembleView.resId;
		}else{
			viewId = assembleView.viewId;
			assembleView.view.setId(viewId);
		}
		
		switch (assembleView.relationViewType) {
		case ABOVE:
			RelativeLayout.LayoutParams layoutParams = (LayoutParams) this.view.getLayoutParams();
			layoutParams.addRule(RelativeLayout.ABOVE, viewId);
			this.view.setLayoutParams(layoutParams);
			break;
		case BELOW:
			layoutParams = (LayoutParams) this.view.getLayoutParams();
			layoutParams.addRule(RelativeLayout.BELOW, viewId);
			this.view.setLayoutParams(layoutParams);
//			layoutParams2.addRule(RelativeLayout.BELOW, this.view.getId()==-1 ? 1 : this.view.getId());
//			layoutParams2.addRule(RelativeLayout.BELOW, this.view.getId());
			break;
		case LEFT_OF:
			layoutParams2.addRule(RelativeLayout.LEFT_OF, viewId);
			break;
		case RIGHT_OF:
			layoutParams2.addRule(RelativeLayout.RIGHT_OF, viewId);
			break;
		default:
			break;
		}	
		assembleView.relationViewType = RelationViewType.NONE;
		
		switch (config.directionConfig) {
		case TOP:
			layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			break;
		case BOTTOM:
			layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			break;
		case LEFT:
			layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			break;
		case RIGHT:
			layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			break;
		default:
			break;
		}
		
		switch (config.centerConfig) {
		case CENTER:
			layoutParams2.addRule(RelativeLayout.CENTER_IN_PARENT);
			break;
		case CENTER_HO:
			layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
			break;
		case CENTER_VIRTICAL:
			layoutParams2.addRule(RelativeLayout.CENTER_VERTICAL);
			break;
		default:
			break;
		}
		
		assembleView.layoutParams = layoutParams2;
		view.setLayoutParams(layoutParams2);
		return view;
	}
	
	public View getView(){
		return view;
	}
	
	public void setId(int viewId){
		this.viewId = viewId;
	}
	
	public void setForceMainLayout(boolean isForceMainLayout){
		this.isForceMainLayout = isForceMainLayout;
	}
	
	public List<AssembleView> getSubAssembelViews(){
		return assembleViews;
	}
	
	public View addExtraView(AssembleView assembleView, int res){
		if(relativeLayoutMain!=null){
//			assembleView.relationViewType = RelationViewType.BELOW;
//			assembleView.resId = resId;
			
			View view = settingViewParams(assembleView, assembleView.config);
//			AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F);
//			alpha.setDuration(0); // Make animation instant
//			alpha.setFillAfter(true); // Tell it to persist after the animation ends
			// And then on your layout
//			view.startAnimation(alpha);
			
			relativeLayoutMain.addView(view);
		}
		return relativeLayoutMain;
	}
}
