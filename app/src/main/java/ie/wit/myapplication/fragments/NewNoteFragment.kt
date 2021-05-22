package ie.wit.myapplication.fragments


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ie.wit.myapplication.R
import ie.wit.myapplication.room_database.NotesDatabase
import ie.wit.myapplication.room_entities.NotesEntity
import kotlinx.android.synthetic.main.fragment_new_note.*
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*

/**
 * fragment for inflating the create note screen
 */
class NewNoteFragment : BaseFragment(), EasyPermissions.PermissionCallbacks,EasyPermissions.RationaleCallbacks{

    var colorSelect = "#b4ee8f"
    var dateCurrent:String? = null
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteId = requireArguments().getInt("noteId",-1)

    }

    // Inflate the create new note fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_note, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (noteId != -1) {

            launch {
                context?.let {
                    var notes = NotesDatabase.getDatabase(it).noteDao().getNote(noteId)
                    colorTag.setBackgroundColor(Color.parseColor(notes.color))
                    noteTitle.setText(notes.noteTitle)
                    noteDescription.setText(notes.noteDesc)
                    noteEntry.setText(notes.noteText)

                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

        dateCurrent = dateFormat.format(Date())
        colorTag.setBackgroundColor(Color.parseColor(colorSelect))
        dateTime.text = dateCurrent

        saveImg.setOnClickListener {
            if (noteId != -1) {
                updateNote()
            } else {
                saveNote()
            }
        }

        backImg.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

        private fun updateNote() {
            launch {

                context?.let {
                    var notes = NotesDatabase.getDatabase(it).noteDao().getNote(noteId)

                    notes.noteTitle = noteTitle.text.toString()
                    notes.noteDesc = noteDescription.text.toString()
                    notes.noteText = noteEntry.text.toString()
                    notes.dateTime = dateCurrent
                    notes.color = colorSelect

                    NotesDatabase.getDatabase(it).noteDao().updateNote(notes)
                    noteTitle.setText("")
                    noteDescription.setText("")
                    noteEntry.setText("")
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }

        private fun saveNote() {
            if (noteTitle.text.isNullOrEmpty()) {
                Toast.makeText(context, "Enter a note title", Toast.LENGTH_SHORT).show()
            } else if (noteDescription.text.isNullOrEmpty()) {

                Toast.makeText(context, "Enter a note description", Toast.LENGTH_SHORT).show()
            } else if (noteEntry.text.isNullOrEmpty()) {

                Toast.makeText(context, "Enter a note", Toast.LENGTH_SHORT).show()
            } else {
                launch {
                    var notes = NotesEntity()
                    notes.noteTitle = noteTitle.text.toString()
                    notes.noteDesc = noteDescription.text.toString()
                    notes.noteText = noteEntry.text.toString()
                    notes.dateTime = dateCurrent
                    notes.color = colorSelect

                    context?.let {
                        NotesDatabase.getDatabase(it).noteDao().insertNote(notes)
                        noteTitle.setText("")
                        noteDescription.setText("")
                        noteEntry.setText("")
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }
        }

        private fun deleteNote() {
            launch {
                context?.let {
                    NotesDatabase.getDatabase(it).noteDao().deleteNote(noteId)
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }

        val BroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                var actionColor = p1!!.getStringExtra("action")

                when (actionColor!!) {
                    "Green" -> {
                        colorSelect = p1.getStringExtra("selectedColor")!!
                        colorTag.setBackgroundColor(Color.parseColor(colorSelect))
                    }

                    "Blue" -> {
                        colorSelect = p1.getStringExtra("selectedColor")!!
                        colorTag.setBackgroundColor(Color.parseColor(colorSelect))
                    }

                    "Purple" -> {
                        colorSelect = p1.getStringExtra("selectedColor")!!
                        colorTag.setBackgroundColor(Color.parseColor(colorSelect))

                    }

                    "Orange" -> {
                        colorSelect = p1.getStringExtra("selectedColor")!!
                        colorTag.setBackgroundColor(Color.parseColor(colorSelect))
                    }

                    "Black" -> {
                        colorSelect = p1.getStringExtra("selectedColor")!!
                        colorTag.setBackgroundColor(Color.parseColor(colorSelect))

                    }

                    "DeleteNote" -> {
                        //delete note
                        deleteNote()
                    }

                    else -> {
                        colorSelect = p1.getStringExtra("selectedColor")!!
                        colorTag.setBackgroundColor(Color.parseColor(colorSelect))
                    }
                }
            }
        }

        override fun onDestroy() {
            LocalBroadcastManager.getInstance(requireContext())
                .unregisterReceiver(BroadcastReceiver)
            super.onDestroy()
        }

        //Managing android runtime permissions with EasyPermissions
        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
        ) {
            EasyPermissions.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults,
                requireActivity()
            )
        }


        override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
            if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms)) {
                AppSettingsDialog.Builder(requireActivity()).build().show()
            }
        }

        override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

        }

        override fun onRationaleDenied(requestCode: Int) {

        }

        override fun onRationaleAccepted(requestCode: Int) {

        }

    }

