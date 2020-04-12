package notes.pkg.async;

import android.os.AsyncTask;

import notes.pkg.models.Note;
import notes.pkg.persistence.Dao_note;

public class Async_delete extends AsyncTask<Note, Void, Void> {

    private Dao_note _dao;

    public Async_delete(Dao_note dao) { _dao = dao; }

    @Override
    protected Void doInBackground(Note... notes) {
        _dao.delete(notes);

        return null;
    }
}
