package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.Assessment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class AssessmentDetails extends AppCompatActivity {

    private Repository repo;

    private EditText titleField;
    private EditText startField;
    private EditText endField;
    private CheckBox startNotify, endNotify;
    private RadioButton performanceRB, objectiveRB;

    private int courseId;
    private int assessmentId;

    private Assessment toEdit;

    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;

    private final Calendar startCalendar = Calendar.getInstance();
    private final Calendar endCalendar = Calendar.getInstance();

    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        repo = new Repository(getApplication());

        titleField = findViewById(R.id.assessmentDetailsTitle);
        startField = findViewById(R.id.assessmentDetailsStart);
        endField = findViewById(R.id.assessmentDetailsEnd);
        startNotify = (CheckBox) findViewById(R.id.assessmentDetailsStartCheckBox);
        endNotify = (CheckBox) findViewById(R.id.assessmentDetailsEndCheckBox);
        performanceRB = (RadioButton) findViewById(R.id.assessmentDetailsPerformanceRB);
        objectiveRB = (RadioButton) findViewById(R.id.assessmentDetailsObjectiveRB);

        courseId = getIntent().getIntExtra("courseId", -1);
        assessmentId = getIntent().getIntExtra("assessmentId", -1);

        String myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (assessmentId == -1) {
            getSupportActionBar().setTitle("Add Assessment");
            String currentDate = sdf.format(new Date());
            startField.setText(currentDate);
            endField.setText(currentDate);
        } else {
            getSupportActionBar().setTitle("Edit Assessment");
            toEdit = repo.getAllAssessments().stream()
                    .filter(assessment -> assessment.getAssessmentId() == assessmentId)
                    .collect(Collectors.toList()).get(0);
            titleField.setText(toEdit.getTitle());
            startField.setText(sdf.format(toEdit.getStart()));
            endField.setText(sdf.format(toEdit.getEnd()));

            if (Objects.equals(toEdit.getType(), "Performance"))
                performanceRB.setChecked(true);
            else if (Objects.equals(toEdit.getType(), "Objective"))
                objectiveRB.setChecked(true);
        }

        startField.setOnClickListener(view -> {
            Date date;
            String info = startField.getText().toString();
            try {
                startCalendar.setTime(sdf.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AssessmentDetails.this, startDateListener, startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartField();
            }
        };
        endField.setOnClickListener(view -> {
            Date date;
            String info = endField.getText().toString();
            try {
                endCalendar.setTime(sdf.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AssessmentDetails.this, endDateListener, endCalendar.get(Calendar.YEAR),
                    endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, monthOfYear);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndField();
            }
        };
    }

    private void updateStartField() {
        startField.setText(sdf.format(startCalendar.getTime()));
    }
    private void updateEndField() {
        endField.setText(sdf.format(endCalendar.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
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

    public void saveAssessment(View view) {
        RadioButton selected = getRBSelection();
        if (startCalendar.after(endCalendar)) {
            CharSequence toastText = "\"Start Date\" must come before \"End Date\"";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, toastText, duration);
            toast.show();
        } else if (selected == null) {
            CharSequence toastText = "Please select an option for \"Type\"";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(AssessmentDetails.this, toastText, duration);
            toast.show();
        }
        else if (assessmentId == -1) {
            Assessment newAssessment = new Assessment(titleField.getText().toString(),
                    startCalendar.getTime(), endCalendar.getTime(), selected.getText().toString(),
                    courseId);
            repo.insert(newAssessment);
            Intent intent = new Intent(AssessmentDetails.this, AssessmentsList.class);
            intent.putExtra("id", courseId);
            startActivity(intent);
        } else {
            Assessment assessmentCopy = new Assessment(toEdit);
            assessmentCopy.setTitle(titleField.getText().toString());
            assessmentCopy.setStart(startCalendar.getTime());
            assessmentCopy.setEnd(endCalendar.getTime());
            assessmentCopy.setType(selected.getText().toString());
            repo.update(assessmentCopy);
            Intent intent = new Intent(AssessmentDetails.this, AssessmentsList.class);
            intent.putExtra("id", assessmentCopy.getCourseId());
            startActivity(intent);
            this.finish();
        }
    }

    private RadioButton getRBSelection() {
        if (performanceRB.isChecked())
            return performanceRB;
        else if (objectiveRB.isChecked())
            return objectiveRB;
        else
            return null;
    }
}