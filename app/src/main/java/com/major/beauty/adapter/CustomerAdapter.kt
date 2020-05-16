package com.major.beauty.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.major.beauty.R
import com.major.beauty.base.BaseAdapter
import com.major.beauty.bean.Customer

/**
 * @desc: 顾客列表
 * @author: Major
 * @since: 2019/3/2 10:33
 */
class CustomerAdapter(context: Context) : BaseAdapter<Customer, CustomerAdapter.VH>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.item_customer, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        mData?.get(position)?.let {
            holder.run {
                name?.text = it.name
                phone?.text = it.phone
                address?.text = it.company
                icon?.setImageResource(R.mipmap.ic_launcher)

                itemView.setOnClickListener { _ -> mListener?.onItemClick(position, it, icon) }
                itemView.setOnLongClickListener { _ ->
                    mListener?.onItemLongClick(position, it, icon)
                    true
                }
                itemView.setOnTouchListener { view: View, event: MotionEvent ->
                    when (event.action) {
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

        var name: TextView = itemView.findViewById(R.id.tv_customer_name)
        var phone: TextView = itemView.findViewById(R.id.tv_customer_phone)
        var address: TextView = itemView.findViewById(R.id.tv_customer_address)
        var icon: ImageView = itemView.findViewById(R.id.iv_icon)

    }
}