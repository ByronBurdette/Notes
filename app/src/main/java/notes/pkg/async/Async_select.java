package notes.pkg.async;

import android.os.AsyncTask;

import java.util.List;

import notes.pkg.models.Note;
import notes.pkg.persistence.Dao_note;

public class Async_select extends AsyncTask<Void, Void, Note> {

    private Dao_note _dao;

    public Async_select(Dao_note dao) { _dao = dao; }

    @Override
    protected Note doInBackground(Void... voids) {
        return _dao.select_last_insert_note();
    }
}
