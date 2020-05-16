package com.major.beauty.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/6/3 22:52
 */
class SpaceDecoration : RecyclerView.ItemDecoration {

    private var mSpace = 10

    constructor() {}

    constructor(space: Int) {
        mSpace = space
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        super.getItemOffsets(outRect, view, parent, state)
        val layoutManager: RecyclerView.LayoutManager? = parent.getLayoutManager()
        if (layoutManager is LinearLayoutManager) {
            if (layoutManager.orientation == LinearLayoutManager.HORIZONTAL) {
                outRect[mSpace, 0, mSpace] = 0
            } else {
                outRect[0, mSpace, 0] = mSpace
            }
        }
    }
}