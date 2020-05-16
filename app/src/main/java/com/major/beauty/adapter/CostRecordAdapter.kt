package com.major.beauty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.major.base.util.CommonUtil
import com.major.beauty.R
import com.major.beauty.base.BaseAdapter
import com.major.beauty.bean.CostRecord
import com.major.beauty.util.TimeUtil

/**
 * Desc: 消费记录列表
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty
 * ProjectName: Beauty
 * Date: 2019/10/28 10:29
 */
class CostRecordAdapter(context: Context) : BaseAdapter<CostRecord, CostRecordAdapter.VH>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.item_cost_record, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(vh: VH, position: Int) {
        mData?.get(position)?.let {
            if (CommonUtil.isNotEmpty(it.items)) {
                val sb = StringBuilder()
                for (item in it.items!!) {
                    sb.append(item.name).append(", ")
                }
                vh.itemName!!.text = sb.toString()
            }

            if (CommonUtil.isNotEmpty(it.products)) {
                val sb = StringBuilder()
                for (product in it.products!!) {
                    sb.append(product.name).append(", ")
                }
                vh.productName!!.text = sb.toString()
            }
            vh.time!!.text = TimeUtil.format(it.createTime)
            vh.name!!.text = it.name
            vh.phone!!.text = it.phone
            vh.pay!!.text = "总计: " + it.pay
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView? = itemView.findViewById(R.id.tv_customer_name)
        var phone: TextView? = itemView.findViewById(R.id.tv_customer_phone)
        var productName: TextView? = itemView.findViewById(R.id.tv_product_name)
        var itemName: TextView? = itemView.findViewById(R.id.tv_item_name)
        var pay: TextView? = itemView.findViewById(R.id.tv_pay)
        var time: TextView? = itemView.findViewById(R.id.tv_time)

    }
}