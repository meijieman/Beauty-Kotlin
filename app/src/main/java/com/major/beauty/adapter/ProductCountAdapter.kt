package com.major.beauty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.major.base.util.ToastUtil
import com.major.beauty.R
import com.major.beauty.base.BaseAdapter
import com.major.beauty.bean.ProductCount
import java.util.*

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/9/10 21:51
 */
class ProductCountAdapter(context: Context) :
    BaseAdapter<ProductCount, ProductCountAdapter.VH>(context) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VH {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.item_product_count, viewGroup, false)
        return VH(view)
    }

    override fun onBindViewHolder(vh: VH, i: Int) {
        val productCount = mData!![i]
        vh.name!!.text = productCount.productName
        vh.count!!.text = String.format(
            Locale.CHINESE,
            "%d%s",
            productCount.count,
            productCount.productUnit
        )
        if (i == mData!!.size - 1) {
            vh.add!!.text = "add"
        } else {
            vh.add!!.text = "del"
        }
        vh.add!!.setOnClickListener { view: View? ->
            if (i == mData!!.size - 1) {
                if (mData!!.size > 20) {
                    ToastUtil.showShort("最多只能添加20个产品")
                    return@setOnClickListener
                }
                // 增加产品
                addData(ProductCount())
            } else {
                // 删除产品
                del(i)
                notifyDataSetChanged()
            }
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: Button = itemView.findViewById(R.id.btn_product_name)
        var count: Button = itemView.findViewById(R.id.btn_product_count)
        var add: Button = itemView.findViewById(R.id.btn_product_add)
    }
}