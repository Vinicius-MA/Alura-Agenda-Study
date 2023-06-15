package br.com.vinma.agenda.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.vinma.agenda.model.Student;

@Dao
public interface RoomStudentDAO {

    @Insert
    void save(Student student);

    @Query("SELECT * FROM student")
    List<Student> all();

    @Delete
    void remove(Student student);

    @Insert
    // TODO: Implement RoomStudentDao.edit
    void edit(Student selectedStudent);
}
