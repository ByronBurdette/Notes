package notes.pkg.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

import notes.pkg.models.Note;

@Dao
public interface Dao_note {

    @Query("" +
        "insert into notes " +
        "(title, content, created_timestamp, updated_timestamp) " +
        "values (:title, :content, current_timestamp, current_timestamp)")
    long insert(String title, String content);

    @Query("select * from notes")
    LiveData<List<Note>> select_allNotes();

    @Query("select * from notes where id = :id")
    List<Note> select_note(Integer id);

    @Query("" +
        "select * " +
        "from notes " +
        "where id = (" +
            "select last_insert_rowid() " +
            "from notes " +
        ")")
    Note select_last_insert_note();

    @Delete
    int delete(Note...notes);

    @Query("" +
        "update notes " +
        "set " +
            "title = :title, " +
            "content = :content, " +
            "updated_timestamp = current_timestamp " +
        "where id = :id ")
    int update(Integer id, String title, String content);


}
