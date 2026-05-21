package com.example.try_gameengine.assemble

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.example.try_gameengine.assemble.AssembleViewConfig.CenterConfig
import com.example.try_gameengine.assemble.AssembleViewConfig.DirectionConfig

class AssembleView {
    var view: View?
        private set
    private var resId = 0
    private val context: Context?
    val subAssembelViews: MutableList<AssembleView> = ArrayList<AssembleView>()
    private var config: AssembleViewConfig? = null
    private var viewId = -1
    private var isForceMainLayout = false

    enum class RelationViewType {
        NONE, ABOVE, BELOW, LEFT_OF, RIGHT_OF
    }

    var relationViewType: RelationViewType = RelationViewType.NONE

    var layoutParams: RelativeLayout.LayoutParams? = null

    constructor(view: View?, context: Context?) {
        this.view = view
        this.context = context
    }

    constructor(resId: Int, context: Context?) {
        this.resId = resId
        this.context = context
        view = RelativeLayout.inflate(context, resId, null)
        view!!.setId(resId)
    }

    fun setConfig(config: AssembleViewConfig?) {
        this.config = config
    }

    fun addSubView(assembleView: AssembleView?) {
        subAssembelViews.add(assembleView!!)
    }

    fun addAboveView(assembleView: AssembleView, resId: Int) {
        assembleView.relationViewType = RelationViewType.BELOW
        assembleView.resId = resId
        subAssembelViews.add(assembleView)
    }

    fun addBelowView(assembleView: AssembleView, resId: Int) {
        assembleView.relationViewType = RelationViewType.ABOVE
        assembleView.resId = resId
        subAssembelViews.add(assembleView)
    }

    //	
    //	public void addSubView(AssembleView assembleView){
    //		assembleViews.add(assembleView);
    //	}
    var relativeLayoutMain: RelativeLayout? = null

    fun generateViews(): View? {
        if (subAssembelViews.size == 0 && !isForceMainLayout) return view

        relativeLayoutMain = RelativeLayout(context)

        //		RelativeLayout.LayoutParams layoutParams2 = createLayoutViewParams(config);

        // layoutParams2.addRule(RelativeLayout.BELOW, 105);
//		this.view.setLayoutParams(layoutParams2);
        view = settingViewParams(this, config)
        relativeLayoutMain!!.addView(this.view)

        for (assembleView in this.subAssembelViews) {
            //			view.setLayoutParams(createLayoutViewParams(assembleView.config));

            val view = settingViewParams(assembleView, assembleView.config)

            relativeLayoutMain!!.addView(view)
        }

        return relativeLayoutMain
    }

    private fun settingViewParams(assembleView: AssembleView, config: AssembleViewConfig?): View {
        var config = config
        val view: View?
        if (assembleView.view != null) {
            view = assembleView.view
        } else {
            view = RelativeLayout.inflate(assembleView.context, assembleView.resId, null)
        }

        if (config == null) config = AssembleViewConfig.Companion.createDefault(context)

        val layoutParams2: RelativeLayout.LayoutParams?
        if (config!!.w == 0f && config.h == 0f) {
            layoutParams2 = RelativeLayout.LayoutParams(
                if (config.w > 0) config.w.toInt() else RelativeLayout.LayoutParams.WRAP_CONTENT,
                if (config.h > 0) config.h.toInt() else RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        } else {
            layoutParams2 = RelativeLayout.LayoutParams(
                if (config.w > 0) config.w.toInt() else RelativeLayout.LayoutParams.MATCH_PARENT,
                if (config.h > 0) config.h.toInt() else RelativeLayout.LayoutParams.MATCH_PARENT
            )
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
        if (this.view!!.getId() == -1) this.view!!.setId(1)

        if (assembleView.viewId < 0) {
            viewId = assembleView.resId
        } else {
            viewId = assembleView.viewId
            assembleView.view!!.setId(viewId)
        }

        when (assembleView.relationViewType) {
            RelationViewType.ABOVE -> {
                val layoutParams = this.view!!.getLayoutParams() as RelativeLayout.LayoutParams
                layoutParams.addRule(RelativeLayout.ABOVE, viewId)
                this.view!!.setLayoutParams(layoutParams)
            }

            RelationViewType.BELOW -> {
                layoutParams = this.view!!.getLayoutParams() as RelativeLayout.LayoutParams
                layoutParams!!.addRule(RelativeLayout.BELOW, viewId)
                this.view!!.setLayoutParams(layoutParams)
            }

            RelationViewType.LEFT_OF -> layoutParams2.addRule(RelativeLayout.LEFT_OF, viewId)
            RelationViewType.RIGHT_OF -> layoutParams2.addRule(RelativeLayout.RIGHT_OF, viewId)
            else -> {}
        }
        assembleView.relationViewType = RelationViewType.NONE

        when (config.directionConfig) {
            DirectionConfig.TOP -> layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            DirectionConfig.BOTTOM -> layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            DirectionConfig.LEFT -> layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            DirectionConfig.RIGHT -> layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            else -> {}
        }

        when (config.centerConfig) {
            CenterConfig.CENTER -> layoutParams2.addRule(RelativeLayout.CENTER_IN_PARENT)
            CenterConfig.CENTER_HO -> layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL)
            CenterConfig.CENTER_VIRTICAL -> layoutParams2.addRule(RelativeLayout.CENTER_VERTICAL)
            else -> {}
        }

        assembleView.layoutParams = layoutParams2
        view!!.setLayoutParams(layoutParams2)
        return view
    }

    fun setId(viewId: Int) {
        this.viewId = viewId
    }

    fun setForceMainLayout(isForceMainLayout: Boolean) {
        this.isForceMainLayout = isForceMainLayout
    }

    fun addExtraView(assembleView: AssembleView, res: Int): View? {
        if (relativeLayoutMain != null) {
//			assembleView.relationViewType = RelationViewType.BELOW;
//			assembleView.resId = resId;

            val view = settingViewParams(assembleView, assembleView.config)

            //			AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F);
//			alpha.setDuration(0); // Make animation instant
//			alpha.setFillAfter(true); // Tell it to persist after the animation ends
            // And then on your layout
//			view.startAnimation(alpha);
            relativeLayoutMain!!.addView(view)
        }
        return relativeLayoutMain
    }
}
