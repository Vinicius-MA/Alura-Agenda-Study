package br.com.vinma.agenda.ui.activity;

import static br.com.vinma.agenda.ui.activity.Constants.INTENT_KEY_STUDENT;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import br.com.vinma.agenda.R;
import br.com.vinma.agenda.dao.StudentDAO;
import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.room.AgendaDataBase;
import br.com.vinma.agenda.room.dao.RoomStudentDAO;

public class StudentsFormActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPhone;
    private EditText etEmail;
    private RoomStudentDAO dao;
    private Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_form);

        dao = Room.databaseBuilder(this, AgendaDataBase.class, AgendaDataBase.NAME)
                .allowMainThreadQueries()
                .build()
                .getRoomStudentDao();

        initData();
        loadStudent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_students_form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.activity_students_form_menu_save){
            finishForm();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadStudent() {
        int titleId;
        Intent editStudentIntent = getIntent();

        if (editStudentIntent.hasExtra(INTENT_KEY_STUDENT)) {
            selectedStudent = (Student) editStudentIntent.getSerializableExtra(INTENT_KEY_STUDENT);
            fulfillFields();
            titleId = R.string.act_std_form_stat_bar_title_edit;
        } else{
            selectedStudent = new Student();
            titleId = R.string.act_std_form_stat_bar_title_new;
        }
        // set status bar title accordingly to the calling mode (new/edit student)
        setTitle(titleId);
    }

    private void fulfillFields() {
        etName.setText(selectedStudent.getName());
        etEmail.setText(selectedStudent.getEmail());
        etPhone.setText(selectedStudent.getPhone());
    }

    private void initData() {
        etName = findViewById(R.id.activity_students_form_name);
        etPhone = findViewById(R.id.activity_students_form_phone);
        etEmail = findViewById(R.id.activity_students_form_email);
    }

    private void finishForm() {
        fulfillStudent();
        if(selectedStudent.hasValidId()) {
            dao.edit(selectedStudent);
        }else{
            dao.save(selectedStudent);
        }

        Toast.makeText(this, selectedStudent.toString(this), Toast.LENGTH_LONG).show();

        finish();
    }

    private void fulfillStudent() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();

        selectedStudent.edit(name, phone, email);
    }
}