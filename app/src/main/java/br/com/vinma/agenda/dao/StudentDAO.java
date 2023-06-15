package br.com.vinma.agenda.dao;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.vinma.agenda.model.Student;

public class StudentDAO {

    private final static List<Student> students = new ArrayList<>();
    private static int countStudents = 0;

    public void save(Student student) {
        updateIds();
        student.setId(countStudents);
        students.add(student);
    }

    public void remove(Student student){
        Student studentFound = findStudentById(student);
        if(studentFound != null){
            students.remove(studentFound);
        }
    }

    private void updateIds() {
        countStudents++;
    }

    public void edit(Student student){
        Student studentFound = findStudentById(student);
        if(studentFound != null){
            int index = students.indexOf(studentFound);
            students.set(index, student);
        }
    }

    @Nullable
    private Student findStudentById(Student student) {
        for(Student s: students){
            if(s.getId() == student.getId()){
                return s;
            }
        }
        return null;
    }

    public List<Student> all() {
        return new ArrayList<>(students);
    }
}
