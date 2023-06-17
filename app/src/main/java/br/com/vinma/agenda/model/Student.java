package br.com.vinma.agenda.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import br.com.vinma.agenda.R;

@Entity
public class Student implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String name;
    private String surname;
    private String phone;
    private String email;

    @Ignore
    public Student(String name, String surname, String phone, String email) {
        setName(name);
        setSurname(surname);
        setPhone(phone);
        setEmail(email);
    }

    public Student() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        if( name != null) {
            this.name = name;
        }
    }

    public void setSurname(String surname) {
        if( surname != null) {
            this.surname = surname;
        }
    }

    public void setPhone(String phone) {
        if( phone != null) {
            this.phone = phone;
        }
    }

    public void setEmail(String email) {
        if( email != null) {
            this.email = email;
        }
    }

    public void edit(String name, String surname, String phone, String email) {
        this.setName(name);
        this.setSurname(surname);
        this.setPhone(phone);
        this.setEmail(email);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        if(name == null){return "";}
        return name;
    }

    public String getSurname() {
        if(surname == null){return "";}
        return surname;
    }

    public String getFullName(Context context){
        return context.getString(R.string.model_student_fullname,
                getName(), getSurname());
    }

    public String getPhone() {
        if(phone == null){return "";}
        return phone;
    }

    public String getEmail() {
        if(email == null){return "";}
        return email;
    }

    public boolean hasValidId() {
        return this.getId() > 0;
    }

}
