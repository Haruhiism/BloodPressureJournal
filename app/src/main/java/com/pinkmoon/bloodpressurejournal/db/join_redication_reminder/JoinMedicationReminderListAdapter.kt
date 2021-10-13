package com.pinkmoon.bloodpressurejournal.db.join_redication_reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pinkmoon.bloodpressurejournal.R

class JoinMedicationReminderListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<JoinMedicationReminder, JoinMedicationReminderListAdapter.JoinMedicationReminderViewHolder>
        (JoinMedicationReminderComparator()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JoinMedicationReminderListAdapter.JoinMedicationReminderViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_join_medication_reminder, parent, false)
        return JoinMedicationReminderViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: JoinMedicationReminderListAdapter.JoinMedicationReminderViewHolder,
        position: Int
    ) {
        val current = getItem(position)
        if(current.scheduledTime != null){
            holder.bindMedicationName(current.name)
            holder.bindReminderTime(current.scheduledTime)
        }
    }

    inner class JoinMedicationReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMedicationName = itemView.findViewById<TextView>(R.id.tv_item_join_medication_reminder_name)
        private val tvReminderTime = itemView.findViewById<TextView>(R.id.tv_item_join_medication_reminder_time)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    val medicationReminder = getItem(position)
                    listener.onItemClick(medicationReminder)
                }
            }
        }

        fun bindMedicationName(name: String) {
            tvMedicationName.text = name
        }

        fun bindReminderTime(time: String) {
            tvReminderTime.text = time
        }
    }

    class JoinMedicationReminderComparator : DiffUtil.ItemCallback<JoinMedicationReminder>(){
        override fun areItemsTheSame(
            oldItem: JoinMedicationReminder,
            newItem: JoinMedicationReminder
        ): Boolean {
            return oldItem.pId == newItem.pId
        }

        override fun areContentsTheSame(
            oldItem: JoinMedicationReminder,
            newItem: JoinMedicationReminder
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(medicationReminder: JoinMedicationReminder)
    }
}