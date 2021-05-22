package ie.wit.myapplication.room_database

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import ie.wit.myapplication.room_entities.NotesEntity
import ie.wit.myapplication.room_dao.NotesDao

/**
 *  This is the database component of androidx.room which is an abstract class that extends the RoomDatabase class.
 *  This class is used for database connection and listing the entities and data access objects of the database.
 */

@Database(entities = [NotesEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    companion object { //companion object used so that the properties and functions are not tied to an instance of the class.
        var notesDatabase: NotesDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NotesDatabase {
            if (notesDatabase == null) {
                notesDatabase = Room.databaseBuilder(context, NotesDatabase::class.java, "notes.db").build()
            }
            return notesDatabase!!
        }
    }

    abstract fun noteDao():NotesDao
}