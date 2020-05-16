package com.major.beauty.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @desc: recyclerView 通用 adapter
 * @author: Major
 * @since: 2019/3/10 11:19
 */
abstract class BaseAdapter<T, V : RecyclerView.ViewHolder>(protected var mContext: Context) :
    RecyclerView.Adapter<V>() {

    protected var mData: MutableList<T>? = null
        get() = field

    fun getData(): MutableList<T>? {
        return mData
    }

    fun setData(data: MutableList<T>?) {
        mData = data
        notifyDataSetChanged()
    }

    fun addData(data: T) {
        mData = mData ?: mutableListOf()
        mData!!.add(data)
        notifyDataSetChanged()
    }

    fun del(pos: Int) {
        if (pos < 0 || pos > mData!!.size - 1) {
            return
        }
        mData!!.removeAt(pos)
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos, mData!!.size - 1, "")
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    protected var mListener: OnItemClickListener<T>? = null
    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        mListener = listener
    }

    interface OnItemClickListener<T> {
        fun onItemClick(pos: Int, bean: T, view: View?)
        fun onItemLongClick(pos: Int, bean: T, view: View?) {}
    }

}