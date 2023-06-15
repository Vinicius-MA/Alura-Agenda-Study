package br.com.vinma.agenda;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import br.com.vinma.agenda.dao.StudentDAO;
import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.room.AgendaDataBase;
import br.com.vinma.agenda.room.dao.RoomStudentDAO;

public class AgendaApplication extends Application {

    private RoomStudentDAO dao;

    @Override
    public void onCreate() {
        super.onCreate();

        dao = Room.databaseBuilder(this, AgendaDataBase.class, AgendaDataBase.NAME)
            .allowMainThreadQueries()
            .build()
            .getRoomStudentDao();

        insertTestingData();
    }

    private void insertTestingData() {
        if(dao==null || dao.all().size()==0) {
            dao.save(new Student("Vinícius", "(16)99746-4813", "vinicius@gmail.com"));
            dao.save(new Student("Mônica", "(16)99714-4871", "monica@outlook.com"));
            dao.save(new Student("Gabriel", "(16)99991-2884", "gabriel@yahoo.com.br"));
        }
    }
}
