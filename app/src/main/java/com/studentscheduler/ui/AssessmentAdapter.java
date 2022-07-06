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
import com.studentscheduler.entity.Assessment;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment> assessments;
    private final Context context;
    private final LayoutInflater inflater;

    AssessmentAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentItemTextView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Assessment current = assessments.get(position);
                Intent intent = new Intent(context, AssessmentDisplay.class);
                intent.putExtra("id", current.getAssessmentId());
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (assessments != null) {
            Assessment current = assessments.get(position);
            String title = current.getTitle();
            holder.assessmentItemView.setText(title);
        } else {
            holder.assessmentItemView.setText("No Assessments Available");
        }
    }

    @Override
    public int getItemCount() {
        if (assessments != null)
            return assessments.size();
        else
            return 0;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

}
