package notes.pkg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import notes.pkg.fragments.DialogFragment;
import notes.pkg.models.Note;
import notes.pkg.persistence.Repository;
import notes.pkg.util.Constants;
import notes.pkg.util.Utility;

public class Activity_note
        extends
            AppCompatActivity
        implements
            View.OnFocusChangeListener,
            View.OnClickListener,
            View.OnTouchListener,
            DialogFragment.DialogListener {

    // UI COMPONENTS
    private EditText _editText_title;
    private EditText _editText_content;
    private ImageButton _button_save;
    private TextView _textView_updateTimestamp;
    private LinearLayout _linearLayout_content;

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
        _linearLayout_content = findViewById(R.id.linearLayout_content);

        // set event listeners
        _editText_title.setOnFocusChangeListener(this);
        _editText_content.setOnFocusChangeListener(this);
        _button_save.setOnClickListener(this);
        _linearLayout_content.setOnClickListener(this);

        // initialize note
        _note = getIntent().getParcelableExtra(Constants.Extra.selectedNote);
//        _noteClone = _note.clone();
        _editText_title.setText(_note.get_title());
        _editText_content.setText(_note.get_content());
        _textView_updateTimestamp.setText(_note.get_updated_timestamp() == null ? "" : "Last modified: " + Utility.ezDate(_note.get_updated_timestamp()));

        if (_note.isNew())
            focusEditText(_editText_content);

        Log.d("BYRON", _note.toString());
    }

    private void syncNoteAndView() {
        _note.set_title(_editText_title.getText().toString());
        _note.set_content(_editText_content.getText().toString());
    }

    @Override
    public void onBackPressed() {
        syncNoteAndView();

        if (_note.changed()) {
            DialogFragment dialog = new DialogFragment();
            dialog.show(getSupportFragmentManager(), "SDF");
        } else
            super.onBackPressed();


    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    private void save() {
        syncNoteAndView();

        if (_note.canSave()) {
            if (_note.isNew()) {
                _repository.insert(_note);

                // set this note == inserted note
//            _note = _repository.select_last_insert_note();
//            _noteClone = _note.clone();
            } else {
                _repository.update(_note);
            }
        } else if(_note.isNew()) {
            Toast toast = Toast.makeText(getApplicationContext(), "No content to save. Note discarded.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        super.onBackPressed();
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
                save();
                break;
            case R.id.linearLayout_content:
                focusEditText(_editText_content);
        }
    }

    private void focusEditText(TextView textView) {
        textView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        Log.d("BYORN", "Positive");
        save();
//        super.onBackPressed();
    }

    @Override
    public void onNegativeClick(DialogFragment dialog) {
        Log.d("BYORN", "Negative");
        super.onBackPressed();
    }

    @Override
    public void onNeutralClick(DialogFragment dialog) {
        Log.d("BYORN", "Neutral");
    }
}
