package notes.pkg.persistence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import notes.pkg.async.Async_delete;
import notes.pkg.async.Async_insert;
import notes.pkg.async.Async_select;
import notes.pkg.async.Async_update;
import notes.pkg.models.Note;

public class Repository {

    private Database _database;

    public Repository(Context context) {
        this._database = Database.getInstance(context);
    }

    public void insert(Note note) {
        new Async_insert(_database.getNoteDao()).execute(note);
    }

    public void update(Note note) {
        new Async_update(_database.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> select_allNotes() {
        return _database.getNoteDao().select_allNotes();
    }

    public Note select_last_insert_note() {
        try {
            return new Async_select(_database.getNoteDao()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Note note) {
        new Async_delete(_database.getNoteDao()).execute(note);
    }
}
