package br.com.vinma.agenda.ui;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import br.com.vinma.agenda.R;
import br.com.vinma.agenda.dao.StudentDAO;
import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.ui.adapter.StudentListAdapter;

public class StudentsListView {

    private final Context mContext;
    private final StudentListAdapter studentsListAdapter;
    private final StudentDAO dao;

    public StudentsListView(Context mContext) {
        this.mContext = mContext;
        this.studentsListAdapter = new StudentListAdapter(this.mContext);
        this.dao = new StudentDAO();
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
        studentsListAdapter.update(dao.all());
    }

    private void remove(Student student) {
        dao.remove(student);
        studentsListAdapter.remove(student);
    }
}
