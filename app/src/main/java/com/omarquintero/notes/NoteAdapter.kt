package com.omarquintero.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter: ListAdapter<Note, NoteAdapter.NoteHolder>(diffCallback) {
    private var listener: OnItemClickListener? = null

    companion object {
        var diffCallback: DiffUtil.ItemCallback<Note> = object: DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.idNote == newItem.idNote
            }
            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                var equal: Boolean = true
                equal = equal && oldItem.idNote == newItem.idNote
                equal = equal && oldItem.title == newItem.title
                equal = equal && oldItem.description == newItem.description
                equal = equal && oldItem.priority == newItem.priority
                return equal
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_node, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote: Note = getItem(position)
        holder.bindData(currentNote)
    }

    fun getItemAt(position: Int): Note{
        return getItem(position)
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    inner class NoteHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var txtTitle: TextView? = null
        private var txtPriority: TextView? = null
        private var txtDescription: TextView? = null

        init{
            txtTitle = itemView.findViewById(R.id.txtTitle)
            txtPriority = itemView.findViewById(R.id.txtPriority)
            txtDescription = itemView.findViewById(R.id.txtDescription)

            //Set Listener to the view using an interface in the adapter
            itemView.setOnClickListener { view ->
                if(listener!=null && adapterPosition!=RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(getItem(adapterPosition))
                }
            }
        }

        fun bindData(note: Note){
            txtTitle!!.text = note.title
            txtPriority!!.text = note.priority.toString()
            txtDescription!!.text = note.description
        }
    }


}