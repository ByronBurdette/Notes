package notes.pkg.async;

import android.os.AsyncTask;

import notes.pkg.models.Note;
import notes.pkg.persistence.Dao_note;

public class Async_insert extends AsyncTask<Note, Void, Void> {
    private Dao_note _dao;

    public Async_insert(Dao_note dao) {
        _dao = dao;
    }

    @Override
    protected Void doInBackground(Note...notes) {
        for (Note note : notes)
            _dao.insert(note.get_title(), note.get_content());

        return null;
    }
}
