package notes.pkg.async;

import android.os.AsyncTask;

import notes.pkg.models.Note;
import notes.pkg.persistence.Dao_note;

public class Async_update extends AsyncTask<Note, Void, Void> {

    private Dao_note _dao;

    public Async_update(Dao_note dao) { _dao = dao; }

    @Override
    protected Void doInBackground(Note...notes) {
        for (Note note : notes)
            _dao.update(note.get_id(), note.get_title(), note.get_content());
        return null;
    }
}
