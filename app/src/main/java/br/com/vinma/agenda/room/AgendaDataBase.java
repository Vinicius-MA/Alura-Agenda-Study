package br.com.vinma.agenda.room;

import static br.com.vinma.agenda.room.AgendaMigrations.MIGRATIONS_ALL;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.room.converter.ConverterCalendar;
import br.com.vinma.agenda.room.dao.StudentDAO;

@Database(entities={Student.class}, version=5, exportSchema=false)
@TypeConverters({ConverterCalendar.class})
public abstract class AgendaDataBase extends RoomDatabase {

    private static final String NAME = "agenda.db";
    private static AgendaDataBase instance;

    public abstract StudentDAO getRoomStudentDao();

    public synchronized static AgendaDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, AgendaDataBase.class, NAME)
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATIONS_ALL)
                    .build();
        }
        return instance;
    }
}
