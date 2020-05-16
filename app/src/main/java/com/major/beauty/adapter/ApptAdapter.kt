package com.major.beauty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.major.beauty.R
import com.major.beauty.base.BaseAdapter
import com.major.beauty.bean.Appointment
import com.major.beauty.util.TimeUtil

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/11/2 9:56
 */
class ApptAdapter(context: Context) : BaseAdapter<Appointment, ApptAdapter.VH>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_appt, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        mData?.get(position)?.let {
            holder.run {
                name.text = it.name
                phone.text = it.phone
                start.text = TimeUtil.format(it.startTime)
                end.text = TimeUtil.format(it.endTime)

                itemView.setOnClickListener { _ ->
                    mListener?.onItemClick(position, it, null)
                }
                itemView.setOnLongClickListener { _ ->
                    mListener?.onItemLongClick(position, it, null)
                    true
                }
            }
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.tv_appt_name)
        val phone: TextView = itemView.findViewById(R.id.tv_appt_phone)
        val start: TextView = itemView.findViewById(R.id.tv_appt_time_start)
        val end: TextView = itemView.findViewById(R.id.tv_appt_time_end)

        init {
            println("init...")
        }
    }
}