package com.studentscheduler.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studentscheduler.R;
import com.studentscheduler.entity.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courses;
    private final Context context;
    private final LayoutInflater inflater;

    public CourseAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseItemTextView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Course current = courses.get(position);
                Intent intent = new Intent(context, AssessmentsList.class);
                intent.putExtra("id", current.getCourseId());
                context.startActivity(intent);
            });
        }
    }
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (courses != null) {
            Course current = courses.get(position);
            String title = current.getTitle();
            holder.courseItemView.setText(title);
        } else {
            holder.courseItemView.setText("No courses available");
        }
    }

    @Override
    public int getItemCount() {
        if (courses != null)
            return courses.size();
        else
            return 0;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }
}
