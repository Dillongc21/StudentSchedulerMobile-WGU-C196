package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.Course;
import com.studentscheduler.entity.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CourseDetails extends AppCompatActivity {

    private Repository repo;
    private List<Term> terms;

    private EditText titleField;
    private EditText startField;
    private EditText endField;
    private Spinner statusSpinner;
    private EditText iNameField;
    private EditText iPhoneField;
    private EditText iEmailField;
    private Spinner courseTermSpinner;

    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;

    private final Calendar startCalendar = Calendar.getInstance();
    private final Calendar endCalendar = Calendar.getInstance();

    private SimpleDateFormat sdf;

    private int defaultTermId;
    private int editCourseId;
    private Course courseToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        repo = new Repository(getApplication());

        String myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleField = findViewById(R.id.courseDetailsTitleField);
        startField = findViewById(R.id.courseDetailsStartField);
        endField = findViewById(R.id.courseDetailsEndField);
        statusSpinner = findViewById(R.id.courseDetailsStatusSpinner);
        iNameField = findViewById(R.id.courseDetailsInstructorNameField);
        iPhoneField = findViewById(R.id.courseDetailsInstructorPhoneField);
        iEmailField = findViewById(R.id.courseDetailsInstructorEmailField);
        courseTermSpinner =  findViewById(R.id.courseDetailsTermSpinner);

        defaultTermId = getIntent().getIntExtra("termId", -1);
        editCourseId = getIntent().getIntExtra("courseId", -1);

        iPhoneField.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        terms = repo.getAllTerms();
        List<String> termTitles = terms.stream()
                .map(Term::getTitle)
                .collect(Collectors.toList());
        ArrayAdapter<String> termAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, termTitles);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseTermSpinner.setAdapter(termAdapter);

        if (editCourseId == -1) {
            String selectTermTitle = terms.stream()
                    .filter(term -> term.getTermId() == defaultTermId)
                    .collect(Collectors.toList()).get(0).getTitle();
            courseTermSpinner.setSelection(termTitles.indexOf(selectTermTitle));
        }

        startField.setOnClickListener(view -> {
            Date date;
            String info = startField.getText().toString();
            try {
                startCalendar.setTime(sdf.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(CourseDetails.this, startDateListener, startCalendar.get(Calendar.YEAR),
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
            new DatePickerDialog(CourseDetails.this, endDateListener, endCalendar.get(Calendar.YEAR),
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
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

    public void cancelActivity(View view) {
        this.finish();
    }

    public void saveCourse(View view) {
        if (editCourseId == -1) {
            Course newCourse = new Course(titleField.getText().toString(), startCalendar.getTime(),
                    endCalendar.getTime(), statusSpinner.getSelectedItem().toString(),
                    iNameField.getText().toString(), iPhoneField.getText().toString(),
                    iEmailField.getText().toString(),
                    getTermIdFromTitle(courseTermSpinner.getSelectedItem().toString()));
            repo.insert(newCourse);

            Intent intent = new Intent(CourseDetails.this, CoursesList.class);
            intent.putExtra("id", newCourse.getTermId());
            startActivity(intent);
        }
    }

    private void updateStartField() {
        startField.setText(sdf.format(startCalendar.getTime()));
    }
    private void updateEndField() {
        endField.setText(sdf.format(endCalendar.getTime()));
    }

    private int getTermIdFromTitle(String title) {
        return terms.stream()
                .filter(term -> term.getTitle() == title)
                .collect(Collectors.toList()).get(0).getTermId();
    }
}