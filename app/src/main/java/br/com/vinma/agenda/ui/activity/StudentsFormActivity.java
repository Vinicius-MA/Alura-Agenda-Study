package br.com.vinma.agenda.ui.activity;

import static br.com.vinma.agenda.model.TelephoneType.LANDLINE;
import static br.com.vinma.agenda.model.TelephoneType.MOBILE;
import static br.com.vinma.agenda.ui.activity.Constants.INTENT_KEY_STUDENT;
import static br.com.vinma.agenda.ui.application.AgendaApplication.bgExecutor;
import static br.com.vinma.agenda.ui.application.AgendaApplication.dummySleep;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.vinma.agenda.R;
import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.model.Telephone;
import br.com.vinma.agenda.model.TelephoneType;
import br.com.vinma.agenda.room.AgendaDataBase;
import br.com.vinma.agenda.room.dao.StudentDAO;
import br.com.vinma.agenda.room.dao.TelephoneDAO;

public class StudentsFormActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPhoneLandline;
    private EditText etPhoneMobile;
    private EditText etEmail;
    private View progressView;

    private Student selectedStudent;

    private StudentDAO studentDAO;
    private TelephoneDAO telephoneDAO;
    private List<Telephone> telephonesFromStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_form);

        AgendaDataBase database = AgendaDataBase.getInstance(this);
        studentDAO = database.getStudentDao();
        telephoneDAO = database.getTelephoneDao();

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
        fulfillTelephoneFields();
    }

    private void fulfillTelephoneFields() {
        try {
            telephonesFromStudent = bgExecutor.submit(() -> telephoneDAO.getTelephonesFromStudent(selectedStudent.getId())).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(Telephone telephone: this.telephonesFromStudent){
            if(telephone.getType().equals(LANDLINE)){
                etPhoneLandline.setText(telephone.getNumber());
            } else if(telephone.getType().equals(TelephoneType.MOBILE)){
                etPhoneMobile.setText(telephone.getNumber());
            }
        }
    }

    private void initData() {
        progressView = findViewById(R.id.activity_students_form_progress_layout);
        etName = findViewById(R.id.activity_students_form_name);
        etPhoneLandline = findViewById(R.id.activity_students_form_phone_landline);
        etPhoneMobile = findViewById(R.id.activity_students_form_phone_mobile);
        etEmail = findViewById(R.id.activity_students_form_email);
    }

    private void finishForm() {
        fulfillStudent();

        Telephone landline = createTelephone(etPhoneLandline, LANDLINE);
        Telephone mobile = createTelephone(etPhoneMobile, MOBILE);

        if(selectedStudent.hasValidId()) {
            editStudent(landline, mobile);
        }else {
            saveStudent(landline, mobile);
        }
    }

    private void makeStudentToast(boolean isEdit) {
        String string = getString(isEdit ? R.string.act_std_form_edit_toast : R.string.act_std_form_save_toast, selectedStudent.getName());
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
    }

    private void saveStudent(Telephone landline, Telephone mobile) {
        int studentId;
        try {
            studentId = bgExecutor.submit(() -> {
                runOnUiThread(()-> progressView.setVisibility(View.VISIBLE));
                return studentDAO.save(selectedStudent).intValue();
            }).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        assert studentId >= 0;
        bgExecutor.execute(() -> {
            bindStudentToTelephones(studentId, landline, mobile);
            telephoneDAO.save(landline, mobile);
            dummySleep();
            finish();
            runOnUiThread(()-> makeStudentToast(false));
        });
    }

    private void editStudent(Telephone landline, Telephone mobile) {
        bgExecutor.execute(() -> {
            runOnUiThread(()-> progressView.setVisibility(View.VISIBLE));
            studentDAO.edit(selectedStudent);
            bindStudentToTelephones(selectedStudent.getId(), landline, mobile);
            updateTelephoneIds(landline, mobile);
            dummySleep();
            telephoneDAO.update(landline, mobile);
            finish();
            runOnUiThread(()-> makeStudentToast(true));
        });
    }

    private void updateTelephoneIds(Telephone landline, Telephone mobile){
        for(Telephone telephone: telephonesFromStudent){
            if(telephone.getType().equals(LANDLINE)){
                landline.setId(telephone.getId());
            } else if(telephone.getType().equals(MOBILE)){
                mobile.setId(telephone.getId());
            }
        }
    }

    private void bindStudentToTelephones(int studentId, Telephone... telephones){
        for(Telephone telephone: telephones){
            telephone.setStudentId(studentId);
        }
    }

    @NonNull
    private Telephone createTelephone(EditText editText, TelephoneType type){
        return new Telephone(editText.getText().toString(), type);
    }

    private void fulfillStudent() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        selectedStudent.edit(name, email);
    }
}