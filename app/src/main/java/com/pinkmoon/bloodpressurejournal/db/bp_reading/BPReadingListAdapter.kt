package com.pinkmoon.bloodpressurejournal.db.bp_reading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pinkmoon.bloodpressurejournal.R

class BPReadingListAdapter : ListAdapter<BPReading, BPReadingListAdapter.BPReadingViewHolder>(BPReadingComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BPReadingViewHolder {
        return BPReadingViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BPReadingViewHolder, position: Int) {
        val current = getItem(position)

        holder.bindSystolicVal(current.systolicValue.toString())
        holder.bindDiastolicVal(current.diastolicValue.toString())
        holder.bindPulseVal(current.pulseValue.toString())
        holder.bindTimestampVal(current.formatDatePretty(current.timeStamp))
    }

    class BPReadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bpReadingSystolicVal: TextView = itemView.findViewById(R.id.tv_item_bp_readings_systolic)
        private val bpReadingDiastolicVal: TextView = itemView.findViewById(R.id.tv_item_bp_readings_diastolic)
        private val bpReadingsPulseVal: TextView = itemView.findViewById(R.id.tv_item_bp_readings_pulse)
        private val bpReadingsTimestamp: TextView = itemView.findViewById(R.id.tv_item_bp_readings_timestamp)

        fun bindSystolicVal(text: String?) {
            bpReadingSystolicVal.text = text
        }

        fun bindDiastolicVal(text: String?) {
            bpReadingDiastolicVal.text = text
        }

        fun bindPulseVal(text: String?) {
            bpReadingsPulseVal.text = text
        }

        fun bindTimestampVal(text: String?) {
            bpReadingsTimestamp.text = text
        }

        companion object {
            fun create(parent: ViewGroup) : BPReadingViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_bp_readings, parent, false)

                view.setOnClickListener {

                }
                return BPReadingViewHolder(view)
            }
        }
    }

    class BPReadingComparator : DiffUtil.ItemCallback<BPReading>() {
        override fun areItemsTheSame(oldItem: BPReading, newItem: BPReading): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BPReading, newItem: BPReading): Boolean {
            return oldItem.pId == newItem.pId
        }

    }
}