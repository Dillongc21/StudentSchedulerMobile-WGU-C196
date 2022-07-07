package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.Assessment;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class AssessmentDisplay extends AppCompatActivity {

    private int id;

    Repository repo;

    private TextView titleView, startView, endView;
    private CheckBox startNotify, endNotify;
    private RadioButton performanceRB, objectiveRB;

    private Assessment displayAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_display);

        repo = new Repository(getApplication());

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        titleView = findViewById(R.id.assessmentDisplayTitle);
        startView = findViewById(R.id.assessmentDisplayStart);
        endView = findViewById(R.id.assessmentDisplayEnd);
        startNotify = (CheckBox) findViewById(R.id.assessmentDisplayStartNotify);
        endNotify = (CheckBox) findViewById(R.id.assessmentDisplayEndNotify);
        performanceRB = (RadioButton) findViewById(R.id.assessmentDisplayPerformanceRB);
        objectiveRB = (RadioButton) findViewById(R.id.assessmentDisplayObjectiveRB);

        id = getIntent().getIntExtra("assessmentId", -1);

        displayAssessment = repo.getAllAssessments().stream()
                .filter(assessment -> assessment.getAssessmentId() == id)
                .collect(Collectors.toList()).get(0);

        String startFormatted = sdf.format(displayAssessment.getStart());
        String endFormatted = sdf.format(displayAssessment.getEnd());

        titleView.setText(displayAssessment.getTitle());
        startView.setText(startFormatted);
        endView.setText(endFormatted);

        if (Objects.equals(displayAssessment.getType(), "Performance"))
            performanceRB.setChecked(true);
        else if (Objects.equals(displayAssessment.getType(), "Objective"))
            objectiveRB.setChecked(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdisplay, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToEditAssessment(View view) {
        Intent intent = new Intent(AssessmentDisplay.this, AssessmentDetails.class);
        intent.putExtra("assessmentId", id);
        startActivity(intent);
    }

    public void deleteAssessment(View view) {
        int courseId = displayAssessment.getCourseId();
        repo.delete(displayAssessment);
        Intent intent = new Intent(AssessmentDisplay.this, AssessmentsList.class);
        intent.putExtra("id", courseId);
        startActivity(intent);
    }
}