@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.assemble

import android.content.Context
import android.view.View
import android.widget.RelativeLayout

internal fun AssembleView.getLayoutParams() = this.layoutParams
internal fun AssembleView.getRelativeLayoutMain() = this.relativeLayoutMain
internal fun AssembleView.getSubAssembelViews() = this.subAssembelViews
internal fun AssembleView.getView() = this.view
internal fun AssembleView.setLayoutParams(value: RelativeLayout.LayoutParams?) { this.layoutParams = value }
internal fun AssembleView.setRelativeLayoutMain(value: RelativeLayout?) { this.relativeLayoutMain = value }
internal fun AssembleViewConfig.getCenterConfig() = this.centerConfig
internal fun AssembleViewConfig.getDirectionConfig() = this.directionConfig
internal fun AssembleViewConfig.getH() = this.h
internal fun AssembleViewConfig.getPersentX() = this.persentX
internal fun AssembleViewConfig.getPersentY() = this.persentY
internal fun AssembleViewConfig.getW() = this.w
internal fun AssembleViewConfig.getX() = this.x
internal fun AssembleViewConfig.getY() = this.y
