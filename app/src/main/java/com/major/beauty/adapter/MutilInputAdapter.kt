package com.major.beauty.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.major.beauty.R
import com.major.beauty.base.BaseAdapter
import com.major.beauty.bean.SmartItem
import com.major.beauty.ui.view.SmartItemView

/**
 * Desc: TODO
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.adapter
 * ProjectName: Beauty
 * Date: 2019/8/15 16:46
 */
class MutilInputAdapter(context: Context) : BaseAdapter<SmartItem, MutilInputAdapter.VH>(
    context
) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VH {
        val inflate = View.inflate(mContext, R.layout.item_smart_item, null)
        return VH(inflate)
    }

    override fun onBindViewHolder(viewHolder: VH, i: Int) {
        viewHolder.siv!!.setItem(mData!!.get(i))
    }

    override fun getItemViewType(position: Int): Int {
        return mData!!.get(position).type
    }

    class VH(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var siv: SmartItemView?

        init {
            siv = itemView.findViewById(R.id.siv_root)
        }
    }
}