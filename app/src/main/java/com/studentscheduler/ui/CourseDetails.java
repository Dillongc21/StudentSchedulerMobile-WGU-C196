package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.Objects;
import java.util.stream.Collectors;

public class CourseDetails extends AppCompatActivity {

    private Repository repo;
    private List<Term> terms;

    private EditText titleField;
    private EditText startField;
    private EditText endField;
    private CheckBox startNotify;
    private CheckBox endNotify;
    private Spinner statusSpinner;
    private EditText iNameField;
    private EditText iPhoneField;
    private EditText iEmailField;
    private Spinner courseTermSpinner;

    private int startNotifyId;
    private int endNotifyId;

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

        startNotify = (CheckBox) findViewById(R.id.courseDetailsStartNotify);
        endNotify = (CheckBox) findViewById(R.id.courseDetailsEndNotify);

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

        String selectTermTitle;

        if (editCourseId == -1) {
            getSupportActionBar().setTitle("Add Course");
            startNotifyId = -1;
            endNotifyId = -1;
            selectTermTitle = terms.stream()
                    .filter(term -> term.getTermId() == defaultTermId)
                    .collect(Collectors.toList()).get(0).getTitle();
            String currentDate = sdf.format(new Date());
            startField.setText(currentDate);
            endField.setText(currentDate);
        } else {
            getSupportActionBar().setTitle("Edit Course");

            courseToEdit = repo.getAllCourses().stream()
                    .filter(course -> course.getCourseId() == editCourseId)
                    .collect(Collectors.toList()).get(0);
            selectTermTitle = terms.stream()
                    .filter(term -> term.getTermId() == courseToEdit.getTermId())
                    .collect(Collectors.toList()).get(0).getTitle();

            int statusSpinnerPosition = statusAdapter.getPosition(courseToEdit.getStatus());

            titleField.setText(courseToEdit.getTitle());
            startField.setText(sdf.format(courseToEdit.getStart()));
            endField.setText(sdf.format(courseToEdit.getEnd()));
            statusSpinner.setSelection(statusSpinnerPosition);
            iNameField.setText(courseToEdit.getInstructorName());
            iPhoneField.setText(courseToEdit.getInstructorPhone());
            iEmailField.setText(courseToEdit.getInstructorEmail());

            if (courseToEdit.getStartNotifyId() != -1)
                startNotify.setChecked(true);
            if (courseToEdit.getEndNotifyId() != -1)
                endNotify.setChecked(true);

        }

        courseTermSpinner.setSelection(termTitles.indexOf(selectTermTitle));

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
        if (startCalendar.after(endCalendar)) {
            CharSequence toastText = "\"Start Date\" must come before \"End Date\"";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, toastText, duration);
            toast.show();
        } else if (editCourseId == -1) {

            if (startNotify.isChecked())
                setStartNotification();
            if (endNotify.isChecked())
                setEndNotification();

            Course newCourse = new Course(titleField.getText().toString(), startCalendar.getTime(),
                    endCalendar.getTime(), startNotifyId, endNotifyId,
                    statusSpinner.getSelectedItem().toString(), iNameField.getText().toString(),
                    iPhoneField.getText().toString(), iEmailField.getText().toString(),
                    getTermIdFromTitle(courseTermSpinner.getSelectedItem().toString()));
            repo.insert(newCourse);

            Intent intent = new Intent(CourseDetails.this, CoursesList.class);
            intent.putExtra("id", newCourse.getTermId());
            startActivity(intent);
        } else {

            if (startNotify.isChecked() && courseToEdit.getStartNotifyId() == -1)
                setStartNotification();
            if (endNotify.isChecked() && courseToEdit.getEndNotifyId() == -1)
                setEndNotification();

            String newTermTitle = courseTermSpinner.getSelectedItem().toString();
            int newTermId = terms.stream()
                    .filter(term -> Objects.equals(term.getTitle(), newTermTitle))
                    .collect(Collectors.toList()).get(0).getTermId();
            Course courseCopy = new Course(courseToEdit);
            courseCopy.setTitle(titleField.getText().toString());
            courseCopy.setStart(startCalendar.getTime());
            courseCopy.setEnd(endCalendar.getTime());
            courseCopy.setStatus(statusSpinner.getSelectedItem().toString());
            courseCopy.setInstructorName(iNameField.getText().toString());
            courseCopy.setInstructorPhone(iPhoneField.getText().toString());
            courseCopy.setInstructorEmail(iEmailField.getText().toString());
            courseCopy.setTermId(newTermId);
            repo.update(courseCopy);
            Intent intent = new Intent(CourseDetails.this, CoursesList.class);
            intent.putExtra("id", courseCopy.getTermId());
            startActivity(intent);
            this.finish();
        }
    }

    private void setStartNotification() {
        Long trigger = startCalendar.getTime().getTime();
        startNotifyId = Home.numAlert;
        Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
        intent.putExtra("key", "Course \"" + titleField.getText().toString() + "\" "
                + "starts today!");
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, Home.numAlert++,
                intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
    }

    private void setEndNotification() {
        Long trigger = endCalendar.getTime().getTime();
        endNotifyId = Home.numAlert;
        Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
        intent.putExtra("key", "Course \"" + titleField.getText().toString() + "\" "
                + "ends today!");
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, Home.numAlert++,
                intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
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