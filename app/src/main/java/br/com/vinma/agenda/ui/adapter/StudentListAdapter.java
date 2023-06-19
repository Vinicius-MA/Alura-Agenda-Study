package br.com.vinma.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.vinma.agenda.R;
import br.com.vinma.agenda.model.Student;

public class StudentListAdapter extends BaseAdapter {

    private final ArrayList<Student> students = new ArrayList<>();
    private final Context context;

    public StudentListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Student getItem(int pos) {
        return students.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return students.get(pos).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(viewGroup != null){
            View viewInflated = createInflatedView(viewGroup);
            Student student = students.get(i);
            linkStudentToView(student, viewInflated);
            return viewInflated;
        }
        return null;
    }

    private void linkStudentToView(Student student, View view) {
        TextView studentNameEt = view.findViewById(R.id.item_student_name);
        TextView studentPhoneEt = view.findViewById(R.id.item_student_phone);
        TextView studentDateEt = view.findViewById(R.id.item_student_date);

        studentNameEt.setText(student.getName());
        studentPhoneEt.setText(student.getPhone());
        studentDateEt.setText(context.getString(R.string.adap_std_list_created_on, student.getDateCreatedFormatted(context)));
    }

    private View createInflatedView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_student, viewGroup, false);
    }

    public void update(List<Student> students){
        this.students.clear();
        this.students.addAll(students);
        notifyDataSetChanged();
    }

    public void remove(Student student) {
        this.students.remove(student);
        notifyDataSetChanged();
    }
}
