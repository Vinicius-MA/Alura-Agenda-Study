package br.com.vinma.agenda;

import android.app.Application;

import br.com.vinma.agenda.dao.StudentDAO;
import br.com.vinma.agenda.model.Student;

public class AgendaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        insertTestingData();
    }

    private void insertTestingData() {
        StudentDAO dao = new StudentDAO();
        dao.save(new Student("Vinícius", "(16)99746-4813", "vinicius@gmail.com"));
        dao.save(new Student("Mônica", "(16)99714-4871", "monica@outlook.com"));
        dao.save(new Student("Gabriel", "(16)99991-2884", "gabriel@yahoo.com.br"));
    }
}
