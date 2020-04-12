package notes.pkg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import notes.pkg.models.Note;
import notes.pkg.persistence.Repository;
import notes.pkg.util.Constants;
import notes.pkg.util.Utility;

public class Activity_note extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener, View.OnTouchListener {

    // UI COMPONENTS
    private EditText _editText_title;
    private EditText _editText_content;
    private ImageButton _button_save;
    private TextView _textView_updateTimestamp;
    private LinearLayout _linearLayout_fuckme;

    // VARIABLES
    private Note _note;
    private Note _noteClone;
    private Repository _repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_note);

        //set other
        _repository = new Repository(this);

        // set views
        _editText_title = findViewById(R.id.editText_title);
        _editText_content = findViewById(R.id.editText_content);
        _button_save = findViewById(R.id.button_save);
        _textView_updateTimestamp = findViewById(R.id.textView_updateTimestamp);
        _linearLayout_fuckme = findViewById(R.id.linearLayout_content);

        // set event listeners
        _editText_title.setOnFocusChangeListener(this);
        _editText_content.setOnFocusChangeListener(this);
        _button_save.setOnClickListener(this);
        _linearLayout_fuckme.setOnClickListener(this);

        // initialize note
        _note = getIntent().getParcelableExtra(Constants.Extra.selectedNote);
        _noteClone = _note.clone();
        _editText_title.setText(_note.get_title());
        _editText_content.setText(_note.get_content());
        _textView_updateTimestamp.setText(_note.get_updated_timestamp() == null ? "" : "Last modified: " + Utility.ezDate(_note.get_updated_timestamp()));
    }

    @Override
    public void onBackPressed() {
        save();
        super.onBackPressed();
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    private void save() {
        _note.set_title(_editText_title.getText().toString());
        _note.set_content(_editText_content.getText().toString());

        if (_note.isNew()) {
            _repository.insert(_note);

            // set this note == inserted note
//            _note = _repository.select_last_insert_note();
//            _noteClone = _note.clone();
        }
        else {
            Log.d("OLD", _noteClone.get_title());
            Log.d("NEW", _note.get_title());
            _repository.update(_note);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        _button_save.setVisibility(View.VISIBLE);
        _textView_updateTimestamp.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                onBackPressed();
                break;
            case R.id.linearLayout_content:
                _editText_content.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(_editText_content, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
