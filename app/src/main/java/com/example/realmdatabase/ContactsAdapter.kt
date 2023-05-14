package com.example.realmdatabase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdatabase.databinding.ItemContactBinding


class ContactsAdapter: ListAdapter<Contact, ContactsAdapter.MyViewHolder>(MyDiffUtil) {

    private var onClickListener: OnClickListener? = null

    object MyDiffUtil : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class MyViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvNameAndSurname = binding.tvNameAndSurname
        val tvNumber = binding.tvNumber
        val btnMake = binding.btnMake
        val btnUpdate = binding.btvUpdate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = getItem(position)
        holder.tvNameAndSurname.text = "${note?.name} ${note?.surname}"
        holder.tvNumber.text = note?.number
        holder.btnMake.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, note)
            }
        }
    }


    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: Contact)
    }

    fun setData(allContacts: List<Contact>) {
        this.submitList(allContacts)
        notifyDataSetChanged()
    }

}