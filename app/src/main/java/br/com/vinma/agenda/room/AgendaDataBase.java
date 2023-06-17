package br.com.vinma.agenda.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.room.dao.StudentDAO;

@Database(entities={Student.class}, version=2, exportSchema=false)
public abstract class AgendaDataBase extends RoomDatabase {

    private static final String NAME = "agenda.db";
    private static AgendaDataBase instance;

    public abstract StudentDAO getRoomStudentDao();

    public synchronized static AgendaDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, AgendaDataBase.class, NAME)
                    .allowMainThreadQueries()
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("ALTER TABLE student ADD COLUMN surname TEXT");
                        }
                    })
                    .build();
        }
        return instance;
    }
}
