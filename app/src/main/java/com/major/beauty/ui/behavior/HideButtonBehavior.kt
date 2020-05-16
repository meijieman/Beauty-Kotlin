package com.major.beauty.ui.behavior

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

/**
 * Desc: 向上滑动隐藏FloatingActionButton，向下滑动显示FloatingActionButton
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.ui.behavior
 * ProjectName: Beauty
 * Date: 2019/6/4 13:53
 */
class HideButtonBehavior : CoordinatorLayout.Behavior<View>() {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int
    ): Boolean {
        //滚动发生时，关心该事件。
        return true
    }

    /**
     * 滚动开始时，监听该事件。
     * 滚动监听
     */
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed)
        if (dy < 0) {
            ViewCompat.animate(child).scaleX(1f).scaleY(1f).start() //显示和不显示
        } else {
            ViewCompat.animate(child).scaleX(0f).scaleY(0f).start()
        }
    }
}