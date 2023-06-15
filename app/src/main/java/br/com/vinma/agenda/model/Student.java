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
    private String phone;
    private String email;

    @Ignore
    public Student(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Student() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void edit(String name, String phone, String email) {
        this.setName(name);
        this.setPhone(phone);
        this.setEmail(email);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean hasValidId() {
        return this.getId() > 0;
    }

    @NonNull
    public String toString(Context mContext) {

        return mContext.getString(R.string.model_student_to_string,
            this.getName(), this.getPhone(), this.getEmail());

    }
}
