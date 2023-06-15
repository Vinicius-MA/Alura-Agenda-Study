package br.com.vinma.agenda.ui.activity;

import static br.com.vinma.agenda.ui.activity.Constants.INTENT_KEY_STUDENT;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.vinma.agenda.R;
import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.ui.StudentsListView;

public class StudentsListActivity extends AppCompatActivity {

    private StudentsListView studentsListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        studentsListView = new StudentsListView(this);

        configAddStudentButton();
        configActionBar();
        configListViewStudents();

    }

    @Override
    protected void onResume() {
        super.onResume();
        studentsListView.update();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_students_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getItemId() == R.id.activity_students_list_menu_remove) {
            studentsListView.confirmStudentRemoval(menuInfo);
        }
        return super.onContextItemSelected(item);
    }

    private void configActionBar() {
        setTitle(R.string.act_std_list_stat_bar_title);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.purple_500)));
    }

    private void configAddStudentButton() {
        FloatingActionButton addStudentButton = findViewById(R.id.activity_students_list_fab_add_students);
        addStudentButton.setOnClickListener(view -> startStudentFormActivityInCreateMode());
    }

    public void startStudentFormActivityInCreateMode() {
        startActivity(new Intent(this, StudentsFormActivity.class));
    }

    private void configListViewStudents() {
        ListView listViewStudents = findViewById(R.id.activity_students_list_listView_students);
        studentsListView.configAdapter(listViewStudents);
        configItemListener(listViewStudents);
        registerForContextMenu(listViewStudents);
    }

    private void configItemListener(ListView listViewStudents) {
        listViewStudents.setOnItemClickListener((adapterView, view, pos, id) -> {
            Student selectedStudent = (Student) adapterView.getItemAtPosition(pos);
            startStudentFormActivityInEditMode(selectedStudent);
        });
    }

    private void startStudentFormActivityInEditMode(Student student) {
        Intent studentIntent = new Intent(StudentsListActivity.this, StudentsFormActivity.class);
        studentIntent.putExtra(INTENT_KEY_STUDENT, student);
        startActivity(studentIntent);
    }
}
