package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.Course;
import com.studentscheduler.entity.Term;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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



    }

    public void goToCourseListActivity(View view) {
    }

    public void saveCourse(View view) {
    }
}