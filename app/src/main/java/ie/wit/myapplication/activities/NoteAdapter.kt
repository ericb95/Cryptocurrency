package ie.wit.myapplication.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import ie.wit.myapplication.R
import kotlinx.android.synthetic.main.fragment_my_notes.view.*
import kotlin.collections.ArrayList
import ie.wit.myapplication.room_entities.NotesEntity

// adapter class to connect the code from the notes data to the recyclerview
class NoteAdapter() :

    RecyclerView.Adapter<NoteAdapter.NotesViewHolder>() {
    var listener:OnItemClickListener? = null
    var arrList = ArrayList<NotesEntity>()

    //Initialising ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_my_notes,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNotes: List<NotesEntity>){
        arrList = arrNotes as ArrayList<NotesEntity>
    }

    fun setOnClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    //Binding the viewholder and adapter
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.noteTitle2.text = arrList[position].noteTitle
        holder.itemView.noteDesc2.text = arrList[position].noteDesc
        holder.itemView.dateTime2.text = arrList[position].dateTime

        if (arrList[position].color != null){
            holder.itemView.noteView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
        }else{
            holder.itemView.noteView.setCardBackgroundColor(Color.parseColor(R.color.dark_grey.toString()))
        }

        holder.itemView.noteView.setOnClickListener {
            listener!!.onClicked(arrList[position].id!!)
        }
    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view)


    interface OnItemClickListener{
        fun onClicked(noteId:Int)
    }

}