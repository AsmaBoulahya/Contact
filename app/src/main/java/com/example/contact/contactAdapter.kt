package com.example.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.core.graphics.toColorInt
import com.example.contact.R


class ContactAdapter(
    private val context: Context,
    private val contacts: List<Contact>,
    private val onItemClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.contactName)
        val initialCircle: TextView = view.findViewById(R.id.initialCircle)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameView.text = contact.name

        // First letter (from name or number)
        val firstLetter = if (contact.name.isNotEmpty()) contact.name[0].uppercaseChar().toString()
        else contact.number[0].toString()
        holder.initialCircle.text = firstLetter

        // Color the circle
        val color = getColorForContact(contact.name)
        val background = DrawableCompat.wrap(holder.initialCircle.background)
        DrawableCompat.setTint(background, color)
        holder.initialCircle.background = background

        // Click listener
        holder.itemView.setOnClickListener {
            onItemClick(contact)
        }
    }

    override fun getItemCount(): Int = contacts.size

    // Generate consistent color per contact (based on name hash)
    private fun getColorForContact(name: String): Int {
        val colors = listOf(
            "#F44336", "#E91E63", "#9C27B0", "#3F51B5",
            "#2196F3", "#009688", "#4CAF50", "#FF9800", "#795548"
        )
        val index = (name.hashCode().absoluteValue) % colors.size
        return colors[index].toColorInt()
    }

    private val Int.absoluteValue: Int get() = if (this < 0) -this else this
}
