package br.com.vinma.agenda.ui;

import static br.com.vinma.agenda.ui.application.AgendaApplication.bgExecutor;
import static br.com.vinma.agenda.ui.application.AgendaApplication.dummySleep;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.vinma.agenda.R;
import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.room.AgendaDataBase;
import br.com.vinma.agenda.room.dao.StudentDAO;
import br.com.vinma.agenda.ui.activity.StudentsListActivity;
import br.com.vinma.agenda.ui.adapter.StudentListAdapter;

public class StudentsListView extends ListView{

    private final Context mContext;
    private final StudentListAdapter studentsListAdapter;
    private final StudentDAO dao;
    private final Activity activity;
    private View itemProgressLayout;
    private View itemMainLayout;
    private View targetView;

    public StudentsListView(Context context) {
        super(context);
        this.mContext = context;
        this.activity = (Activity)context;

        this.studentsListAdapter = new StudentListAdapter(this.mContext);
        dao = AgendaDataBase.getInstance(this.mContext).getStudentDao();
    }

    public void confirmStudentRemoval(AdapterView.AdapterContextMenuInfo menuInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.act_std_list_con_menu_opt_remove_dial_title)
                .setMessage(R.string.act_std_list_con_menu_opt_remove_dial_msg)
                .setPositiveButton(R.string.act_std_list_con_menu_opt_remove_dial_pos, (dialogInterface, i) -> remove(menuInfo))
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

    private void remove(AdapterView.AdapterContextMenuInfo menuInfo) {
        // passing a MenuInfo so the UI can be updated
        Student student = studentsListAdapter.getItem(menuInfo.position);
        bgExecutor.execute(() -> onRemoveBackground(menuInfo, student));
    }

    private void onRemoveBackground(AdapterView.AdapterContextMenuInfo menuInfo, Student student) {
        activity.runOnUiThread(() -> updateUiOnBackgroundStart(menuInfo));
        dao.remove(student);
        dummySleep();
        activity.runOnUiThread(()-> updateUiOnBackgroundEnd(menuInfo));
    }

    private void updateUiOnBackgroundStart(AdapterView.AdapterContextMenuInfo menuInfo) {
        targetView = menuInfo.targetView;
        findMenuInfoViewsByIds();
        itemProgressLayout.setVisibility(View.VISIBLE);
    }

    private void updateUiOnBackgroundEnd(AdapterView.AdapterContextMenuInfo menuInfo) {
        itemProgressLayout.setVisibility(View.GONE);
        itemMainLayout.setClickable(true);
        removeStudentFromUi(menuInfo);
    }

    private void findMenuInfoViewsByIds() {
        itemProgressLayout = targetView.findViewById(R.id.item_student_progress_layout);
        itemMainLayout = targetView.findViewById(R.id.item_student_main);
    }

    private void removeStudentFromUi(AdapterView.AdapterContextMenuInfo menuInfo) {
        Student student = studentsListAdapter.getItem(menuInfo.position);
        studentsListAdapter.remove(student);
        Toast.makeText(activity, activity.getString(R.string.students_list_view_on_removed_toast, student.getName()), Toast.LENGTH_LONG).show();
    }
}
