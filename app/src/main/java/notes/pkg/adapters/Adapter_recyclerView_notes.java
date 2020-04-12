package notes.pkg.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import notes.pkg.R;
import notes.pkg.models.Note;
import notes.pkg.util.Utility;

public class Adapter_recyclerView_notes extends RecyclerView.Adapter<Adapter_recyclerView_notes.ViewHolder> {

    private ArrayList<Note> _notes;
    private OnNoteListener _onNoteListener;

    public Adapter_recyclerView_notes(ArrayList<Note> notes, OnNoteListener onNoteListener) {
        this._notes = notes;
        this._onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item, parent, false);
        return new ViewHolder(view, _onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(_notes.get(position).get_title());
        holder.timestamp.setText(Utility.ezDate(_notes.get(position).get_updated_timestamp()));
        holder.content.setText(_notes.get(position).get_content());
    }

    @Override
    public int getItemCount() {
        return _notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, timestamp, content;

        OnNoteListener onNoteListener;

        ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            title = itemView.findViewById(R.id.note_title);
            timestamp = itemView.findViewById(R.id.note_timestamp);
            content = itemView.findViewById(R.id.note_content);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick (int position);
    }
}
