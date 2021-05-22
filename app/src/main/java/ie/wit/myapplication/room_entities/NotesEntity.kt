package ie.wit.myapplication.room_entities

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "Notes")
/**
 * androidx room is used in this class for use of its Database Object Mapping library.
 * This class is an entity for androidx.room to mark the class as a database row and to create a database, storing information from notes input
 * java io serilizable is used as a marker interface so objects can be saved to the database
 */
class NotesEntity: Serializable {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    @ColumnInfo(name = "note_title")
    var noteTitle:String? = null

    @ColumnInfo(name = "description")
    var noteDesc:String? = null

    @ColumnInfo(name = "date_time")
    var dateTime:String? = null

    @ColumnInfo(name = "note_text")
    var noteText:String? = null

    @ColumnInfo(name = "color_scheme")
    var color:String? = null

    override fun toString(): String {
        return "$noteTitle : $dateTime"

    }
}
