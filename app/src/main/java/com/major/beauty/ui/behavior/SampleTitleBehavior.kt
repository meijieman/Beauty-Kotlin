package com.major.beauty.ui.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/6/3 23:30
 */
class SampleTitleBehavior : CoordinatorLayout.Behavior<View?> {
    // 列表顶部和title底部重合时，列表的滑动距离。
    private var deltaY = 0f

    constructor() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (deltaY == 0f) {
            deltaY = dependency.y - child.height
        }
        var dy = dependency.y - child.height
        dy = if (dy < 0) 0f else dy
        val alpha = 1 - dy / deltaY
        child.alpha = alpha
        return true
    }
}