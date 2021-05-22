package ie.wit.myapplication.room_dao

import androidx.room.*
import ie.wit.myapplication.room_entities.NotesEntity

/**
 * The DAO component of androidx.room is used here to mark the interface class as a Data Access Object to define interactions with the database.
 * Query methods include SQL statements for selecting, inserting, deleting and updating notes to run on method call. androidx automatically binds the parameters of the methods into SQLite bind arguments.
 */
@Dao
interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAllNotes() : List<NotesEntity>

    @Query("SELECT * FROM notes WHERE id =:id")
    suspend fun getNote(id:Int) : NotesEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:NotesEntity)

    @Update
    suspend fun updateNote(note:NotesEntity)

    @Delete
    suspend fun deleteAllNotes(note:NotesEntity)

    @Query("DELETE FROM notes WHERE id =:id")
    suspend fun deleteNote(id:Int)
}