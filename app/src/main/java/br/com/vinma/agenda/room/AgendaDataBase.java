package br.com.vinma.agenda.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.room.dao.RoomStudentDAO;

@Database(entities={Student.class}, version=1, exportSchema=false)
public abstract class AgendaDataBase extends RoomDatabase {

    public static final String NAME = "agenda.db";

    public abstract RoomStudentDAO getRoomStudentDao();
}
