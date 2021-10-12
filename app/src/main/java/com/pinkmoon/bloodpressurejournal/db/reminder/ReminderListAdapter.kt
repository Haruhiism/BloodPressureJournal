package com.pinkmoon.bloodpressurejournal.db.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pinkmoon.bloodpressurejournal.R

class ReminderListAdapter(private val listener: OnItemClickListener) : ListAdapter<Reminder, ReminderListAdapter.ReminderViewHolder> (ReminderComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val current = getItem(position)

        holder.bindTvMedicationName(current.medId.toString())
        holder.bindTvReminderTime(current.scheduledTime)
    }

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMedicationName = itemView.findViewById<TextView>(R.id.tv_item_reminder_medication_name)
        private val tvReminderTime = itemView.findViewById<TextView>(R.id.tv_item_reminder_reminder_time)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    val reminder = getItem(position)
                    listener.onItemClick(reminder)
                }
            }
        }

        fun bindTvMedicationName(name: String){
            tvMedicationName.text = name
        }

        fun bindTvReminderTime(time: String){
            tvReminderTime.text = time
        }
    }

    class ReminderComparator : DiffUtil.ItemCallback<Reminder>() {
        override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem.pId == newItem.pId
        }

        override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem == newItem
        }

    }

    interface OnItemClickListener {
        fun onItemClick(reminder: Reminder)
    }
}