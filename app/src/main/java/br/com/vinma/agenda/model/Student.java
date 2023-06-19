package br.com.vinma.agenda.model;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.com.vinma.agenda.R;

@Entity
public class Student implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String name;
    private String phone;
    private String email;
    private Calendar dateCreated = Calendar.getInstance();

    @Ignore
    public Student(String name, String phone, String email) {
        setName(name);
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

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
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
        if(name == null){return "";}
        return name;
    }

    public String getPhone() {
        if(phone == null){return "";}
        return phone;
    }

    public String getEmail() {
        if(email == null){return "";}
        return email;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public String getDateCreatedFormatted(Context context){
        SimpleDateFormat format = new SimpleDateFormat(context.getString(R.string.student_date_format), Locale.getDefault());
        return format.format(dateCreated.getTime());
    }

    public boolean hasValidId() {
        return this.getId() > 0;
    }

}
