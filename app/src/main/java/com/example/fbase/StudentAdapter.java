package com.example.fbase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder>{

    List<Student> students;
    Context context;
    final String TAG = "StudentAdapter";
    public StudentAdapter(Context context, List<Student> students){
        this.context = context;
        this.students = students;
    }
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView name, course, email;

        Button editButton, deleteButton;
        Student student;
        private DatabaseReference dataBase;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            dataBase = FirebaseDatabase.getInstance().getReference(Student.db);

            this.img = itemView.findViewById(R.id.img);
            this.name = itemView.findViewById(R.id.nameText);
            this.course = itemView.findViewById(R.id.courseText);
            this.email = itemView.findViewById(R.id.emailText);
            this.editButton = itemView.findViewById(R.id.editButton);
            this.deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editButtonBind();
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteButtonBind();
                }
            });
        }

        private void deleteButtonBind(){
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Are you sure?")
                    .setMessage("Deleted data can't be undo.")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dataBase.child(student.id).removeValue();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            builder.show();
        }

        private void editButtonBind(){
            final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                    .setContentHolder(new ViewHolder(R.layout.update_pop_up))
                    .setExpanded(true, 1200)
                    .create();

            View view = dialogPlus.getHolderView();

            EditText Ename = view.findViewById(R.id.updateName);
            EditText Ecourse = view.findViewById(R.id.updateCourse);
            EditText Eemail = view.findViewById(R.id.updateEmail);
            EditText EimgUrl = view.findViewById(R.id.updateImg);
            Button updateButton = view.findViewById(R.id.updateButton);

            if(student != null){
                Ename.setText(student.getName());
                Ecourse.setText(student.getCourse());
                Eemail.setText(student.getEmail());
                Ecourse.setText(student.getCourse());
                EimgUrl.setText(student.getImgUrl());
            }
            dialogPlus.show();

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, Object> updateStudent = new HashMap<>();
                    updateStudent.put(Student.dbName, Ename.getText().toString());
                    updateStudent.put(Student.dbEmail, Eemail.getText().toString());
                    updateStudent.put(Student.dbCourse, Ecourse.getText().toString());
                    updateStudent.put(Student.dbImg, EimgUrl.getText().toString());
                    dataBase.child(student.getId()).updateChildren(updateStudent)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Fail to update", Toast.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();
                                }
                            });
                }
            });
        }




        public void bind(Student student){
            if(student != null){
                this.student = student;
                this.name.setText(student.getName());
                this.course.setText(student.getCourse());
                this.email.setText(student.getEmail());

                Glide.with(context).load(student.getImgUrl()).circleCrop().into(this.img);
            }
        }
    }
}
