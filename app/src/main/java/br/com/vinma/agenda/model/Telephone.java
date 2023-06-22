package br.com.vinma.agenda.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Student.class, parentColumns = "id", childColumns = "studentId", onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE))
public class Telephone {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String number;
    private TelephoneType type;
    private int studentId;

    public Telephone(String number, TelephoneType type) {
        this.number = number;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        if(number == null){return "";}
        return number;
    }

    public TelephoneType getType() {
        return type;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setType(TelephoneType type) {
        this.type = type;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
