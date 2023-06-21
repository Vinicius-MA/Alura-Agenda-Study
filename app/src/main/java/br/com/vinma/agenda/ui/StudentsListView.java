package br.com.vinma.agenda.ui;

import static br.com.vinma.agenda.ui.application.AgendaApplication.bgExecutor;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.vinma.agenda.R;
import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.room.AgendaDataBase;
import br.com.vinma.agenda.room.dao.StudentDAO;
import br.com.vinma.agenda.ui.adapter.StudentListAdapter;

public class StudentsListView {

    private final Context mContext;
    private final StudentListAdapter studentsListAdapter;
    private final StudentDAO dao;

    public StudentsListView(Context mContext) {
        this.mContext = mContext;
        this.studentsListAdapter = new StudentListAdapter(this.mContext);
        dao = AgendaDataBase.getInstance(this.mContext).getStudentDao();
    }

    public void confirmStudentRemoval(AdapterView.AdapterContextMenuInfo menuInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.act_std_list_con_menu_opt_remove_dial_title)
                .setMessage(R.string.act_std_list_con_menu_opt_remove_dial_msg)
                .setPositiveButton(R.string.act_std_list_con_menu_opt_remove_dial_pos,
                        (dialogInterface, i) -> {
                            Student studentChosen = studentsListAdapter.getItem(menuInfo.position);
                            remove(studentChosen);
                        })
                .setNegativeButton(R.string.act_std_list_con_menu_opt_remove_dial_neg, null)
                .show();
    }

    public void configAdapter(ListView listViewStudents) {
        listViewStudents.setAdapter(studentsListAdapter);
    }

    public void update() {
        List<Student> students;
        try {
            students = bgExecutor.submit(dao::all).get();
            studentsListAdapter.update(students);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void remove(Student student) {
        bgExecutor.execute(() -> dao.remove(student));
        studentsListAdapter.remove(student);
    }
}
