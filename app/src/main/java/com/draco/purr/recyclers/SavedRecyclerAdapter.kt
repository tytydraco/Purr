package com.draco.purr.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.draco.purr.R

class SavedRecyclerAdapter(val context: Context): RecyclerView.Adapter<SavedRecyclerAdapter.ViewHolder>() {
    private val list = sortedSetOf<String>()
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    var pickHook: (String) -> Unit = {}
    var deleteHook: (String) -> Unit = {}

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content: TextView = view.findViewById(R.id.content)
        val delete: ImageButton = view.findViewById(R.id.delete)
    }

    fun updateList(refresh: Boolean = true) {
        list.clear()
        list.addAll(prefs.getStringSet("saved", setOf()) ?: emptySet())
        if (refresh)
            notifyDataSetChanged()
    }

    fun saveList() {
        with(prefs.edit()) {
            putStringSet("saved", list)
            apply()
        }
    }

    fun add(text: String) {
        list.add(text)
        notifyDataSetChanged()
        saveList()
    }

    fun delete(text: String) {
        list.remove(text)
        notifyDataSetChanged()
        saveList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        updateList(false)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = list.elementAt(position)
        holder.content.text = text.replace(";", " x ")
        holder.itemView.setOnClickListener { pickHook(text) }
        holder.delete.setOnClickListener { deleteHook(text) }
    }

    override fun getItemCount() = list.size
}