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
import com.studentscheduler.entity.CourseNote;

import java.util.List;

public class CourseNoteAdapter extends RecyclerView.Adapter<CourseNoteAdapter.CourseNoteViewHolder> {

    private List<CourseNote> courseNotes;
    private final Context context;
    private final LayoutInflater inflater;

    CourseNoteAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    class CourseNoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNoteItemView;
        private CourseNoteViewHolder(View itemView) {
            super(itemView);
            courseNoteItemView = itemView.findViewById(R.id.courseNoteItemTextView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final CourseNote current = courseNotes.get(position);
                Intent intent = new Intent(context, CourseNoteDisplay.class);
                intent.putExtra("courseNoteId", current.getCourseNoteId());
                intent.putExtra("courseId", current.getCourseId());
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public CourseNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.course_note_list_item, parent, false);
        return new CourseNoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseNoteViewHolder holder, int position) {
        if (courseNotes != null) {
            CourseNote current = courseNotes.get(position);
            String note = current.getNote();
            String teaserText = (note.length() >= 11) ? note.substring(0, 10) + "..." : note;
            holder.courseNoteItemView.setText(teaserText);
        } else {
            holder.courseNoteItemView.setText("No Notes Available");
        }
    }

    @Override
    public int getItemCount() {
        if (courseNotes != null)
            return courseNotes.size();
        else
            return 0;
    }

    public void setCourseNotes(List<CourseNote> courseNotes) {
        this.courseNotes = courseNotes;
        notifyDataSetChanged();
    }
}
