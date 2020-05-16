package com.major.beauty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class SearchCustomerAdapter(context: Context) :
    BaseAdapter<Customer, SearchCustomerAdapter.VH>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.item_customer_search, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val notice = mData!![position]
        holder.run {
            name!!.text = notice.name
            phone!!.text = notice.phone
            itemView.setOnClickListener { _ ->
                mListener?.onItemClick(position, notice, null)
            }
            itemView.setOnLongClickListener { _ ->
                mListener?.onItemLongClick(position, notice, null)
                true
            }
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.tv_customer_name)
        var phone: TextView = itemView.findViewById(R.id.tv_customer_phone)

    }
}