package com.major.beauty.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.major.beauty.R
import com.major.beauty.base.BaseAdapter
import com.major.beauty.bean.Product

/**
 * @desc: 产品列表
 * @author: Major
 * @since: 2019/6/7 23:36
 */
class ProductAdapter(context: Context) :
    BaseAdapter<Product, ProductAdapter.VH>(context) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_products, viewGroup, false)
        return VH(view)
    }

    override fun onBindViewHolder(vh: VH, i: Int) {
        mData?.get(i)?.let {
            vh.run {
                name!!.text = it.name
                instruction!!.text = it.instruction
                itemView.setOnClickListener { _ -> mListener?.onItemClick(i, it, null) }
                itemView.setOnLongClickListener { _ ->
                    mListener?.onItemLongClick(i, it, null)
                    true
                }
                itemView.setOnTouchListener { view, event ->
                    when (event!!.action) {
                        MotionEvent.ACTION_DOWN -> {
                            val upAnim = ObjectAnimator.ofFloat(view, "translationZ", 16f)
                            upAnim!!.duration = 100
                            upAnim.interpolator = DecelerateInterpolator()
                            upAnim.start()
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            val downAnim = ObjectAnimator.ofFloat(view, "translationZ", 0f)
                            downAnim!!.duration = 100
                            downAnim.interpolator = AccelerateInterpolator()
                            downAnim.start()
                        }
                    }
                    false
                }
            }
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.tv_product_name)
        var instruction: TextView = itemView.findViewById(R.id.tv_product_instruction)
    }
}