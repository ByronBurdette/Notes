package notes.pkg.persistence;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import notes.pkg.models.Note;

@androidx.room.Database(entities = {Note.class}, version = 3)
public abstract class Database extends RoomDatabase {
    public static final String DATABASE_NAME = "database";

    private static Database instance;

    static Database getInstance(final Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                Database.class,
                DATABASE_NAME
            ).fallbackToDestructiveMigration(
            ).build();

        return instance;
    }

    public abstract Dao_note getNoteDao();
}
