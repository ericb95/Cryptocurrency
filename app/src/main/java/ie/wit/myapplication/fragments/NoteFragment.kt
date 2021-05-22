package ie.wit.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ie.wit.myapplication.R
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import ie.wit.myapplication.activities.NoteAdapter
import ie.wit.myapplication.room_database.NotesDatabase
import ie.wit.myapplication.room_entities.NotesEntity
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_new_note.*


/**
 * Fragments used to define the layout of notes section of the app and is hosted by the main activity. In this case, this fragment inflates the notes page layout.
 */
class NoteFragment : BaseFragment() {
    var notesArray = ArrayList<NotesEntity>()
    var notesAdapter: NoteAdapter = NoteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflating the main notes page layout
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                notesAdapter!!.setData(notes)
                notesArray = notes as ArrayList<NotesEntity>
                recycler_view.adapter = notesAdapter
            }
        }

        notesAdapter!!.setOnClickListener(onClicked)

        floatingButton.setOnClickListener {
            replaceFragment(NewNoteFragment.newInstance(),false)
        }

        search_view.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

        override fun onQueryTextChange(p0: String?): Boolean {
            var tempArr = ArrayList<NotesEntity>()

            for (arr in notesArray){
                if (arr.noteTitle!!.lowercase(Locale.getDefault()).contains(p0.toString())){
                    tempArr.add(arr)
                }
            }
            notesAdapter.setData(tempArr)
            notesAdapter.notifyDataSetChanged()
            return true
        }

    })
    }

    private val onClicked = object :NoteAdapter.OnItemClickListener{
        override fun onClicked(noteId: Int) {
            var fragment :Fragment
            var bundle = Bundle()
            bundle.putInt("noteId",noteId)
            fragment = NewNoteFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment,false)
        }

    }

    fun replaceFragment(fragment:Fragment, istransition:Boolean){
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }


}
