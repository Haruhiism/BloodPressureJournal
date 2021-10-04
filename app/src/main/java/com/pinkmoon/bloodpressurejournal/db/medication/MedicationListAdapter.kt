package com.pinkmoon.bloodpressurejournal.db.medication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pinkmoon.bloodpressurejournal.R


class MedicationListAdapter(private val listener: OnItemClickListener) : ListAdapter<Medication, MedicationListAdapter.MedicationViewHolder>(MedicationComparator()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medication, parent, false)

        return MedicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val current = getItem(position)

        holder.bindTvMedicationName(current.name)
        holder.bindTvDosage(current.dosage)
        holder.bindTvQuantity(current.quantity)
    }

    inner class MedicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMedicationName = itemView.findViewById<TextView>(R.id.tv_item_medication_name)
        private val tvDosage = itemView.findViewById<TextView>(R.id.tv_item_medication_dosage)
        private val tvQuantity = itemView.findViewById<TextView>(R.id.tv_item_medication_quantity)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    val medication = getItem(position)
                    listener.onItemClick(medication)
                }
            }
        }

        fun bindTvMedicationName(text: String?) {
            tvMedicationName.text = text
        }

        fun bindTvDosage(dosage: Double) {
            tvDosage.text = "$dosage mg"
        }

        fun bindTvQuantity(quantity: Int) {
            tvQuantity.text = quantity.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(medication: Medication)
    }

    class MedicationComparator : DiffUtil.ItemCallback<Medication>() {
        override fun areItemsTheSame(oldItem: Medication, newItem: Medication): Boolean {
            return oldItem.pId == newItem.pId
        }

        override fun areContentsTheSame(oldItem: Medication, newItem: Medication): Boolean {
            return oldItem == newItem
        }

    }

}