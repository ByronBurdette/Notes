package notes.pkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import notes.pkg.adapters.Adapter_recyclerView_notes;
import notes.pkg.models.Note;
import notes.pkg.persistence.Repository;
import notes.pkg.util.Constants;
import notes.pkg.util.VerticalSpacingItemDecorator;

public class Activity_notesList
        extends
            AppCompatActivity
        implements
            Adapter_recyclerView_notes.OnNoteListener,
            View.OnClickListener {

    // UI COMPONENTS
    private RecyclerView _recyclerView;

    // VARIABLES
    private ArrayList<Note> _notes = new ArrayList<>();
    private Adapter_recyclerView_notes _notesRecyclerViewAdapter;
    private Repository _repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _recyclerView = findViewById(R.id.recyclerView);

        _repository = new Repository(this);

        initRecyclerView();
        get_allNotes();

        setSupportActionBar((Toolbar)findViewById(R.id.notes_toolbar));
        setTitle("Notes");

        findViewById(R.id.button_new).setOnClickListener(this);
    }

    private void get_allNotes() {
        _repository.select_allNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (_notes.size() > 0)
                    _notes.clear();

                if (_notes != null)
                    _notes.addAll(notes);

                _notesRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(linearLayoutManager);

        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        _recyclerView.addItemDecoration(itemDecorator);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(_recyclerView);

        _notesRecyclerViewAdapter = new Adapter_recyclerView_notes(_notes, this);
        _recyclerView.setAdapter(_notesRecyclerViewAdapter);
    }

    private void load_note(int...position) {
        Intent intent = new Intent(this, Activity_note.class);

        intent.putExtra(Constants.Extra.selectedNote, position.length > 0 ? _notes.get(position[0]) : new Note());

        startActivity(intent);
    }

    @Override
    public void onNoteClick(int position) { load_note(position); }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_new:
                load_note();
                break;
        }
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(_notes.get(viewHolder.getAdapterPosition()));
        }
    };

    private void deleteNote(Note note) {
        _repository.delete(note);
        _notesRecyclerViewAdapter.notifyDataSetChanged();
    }
}
