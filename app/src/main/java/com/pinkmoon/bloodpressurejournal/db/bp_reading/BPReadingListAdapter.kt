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

        holder.bind(current.systolicValue.toString())
    }

    class BPReadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bpReadingItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            bpReadingItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup) : BPReadingViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
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