package notes.pkg.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note implements Parcelable {
    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate=true)
    private Integer _id;

    @ColumnInfo(name = "title")
    @NonNull
    private String _title = "";

    @ColumnInfo(name = "content")
    @NonNull
    private String _content = "";

    @ColumnInfo(name = "created_timestamp")
    @NonNull
    private String _created_timestamp;

    @ColumnInfo(name = "updated_timestamp")
    @NonNull
    private String _updated_timestamp;

    public Note(Integer id, String title, String content, String created_timestamp, String updated_timestamp) {
        this._id = id;
        this._title = title;
        this._content = content;
        this._created_timestamp = created_timestamp;
        this._updated_timestamp = updated_timestamp;
    }

    @Ignore
    public Note() {
    }

    protected Note(Parcel in) {
        if (in.readByte() == 0) {
            _id = null;
        } else {
            _id = in.readInt();
        }
        _title = in.readString();
        _content = in.readString();
        _created_timestamp = in.readString();
        _updated_timestamp = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", _title='" + _title + '\'' +
                ", _content='" + _content + '\'' +
                ", _created_timestamp='" + _created_timestamp + '\'' +
                ", _updated_timestamp='" + _updated_timestamp + '\'' +
                '}';
    }

    //setters
    public void set_id(Integer _id) { this._id = _id; }
    public void set_title(String _title) { this._title = _title; }
    public void set_content(String _content) { this._content = _content; }
    public void set_created_timestamp(@NonNull String _created_timestamp) { this._created_timestamp = _created_timestamp; }
    public void set_updated_timestamp(@NonNull String _updated_timestamp) { this._updated_timestamp = _updated_timestamp; }

    // getters
    public Integer get_id() { return _id; }
    public String get_title() { return _title; }
    public String get_content() { return _content; }
    @NonNull
    public String get_created_timestamp() { return _created_timestamp; }
    @NonNull
    public String get_updated_timestamp() { return _updated_timestamp; }

    public Note clone() {
        return new Note(_id, _title, _content, _created_timestamp, _updated_timestamp);
    }

    public boolean equals(Note note) { return _title.equals(note._title) && _content.equals(note._content); }

    public boolean isNew() { return _id == null; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(_id);
        }
        dest.writeString(_title);
        dest.writeString(_content);
        dest.writeString(_created_timestamp);
        dest.writeString(_updated_timestamp);
    }
}
