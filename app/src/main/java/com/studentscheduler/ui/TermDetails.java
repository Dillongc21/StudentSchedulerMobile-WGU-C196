package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TermDetails extends AppCompatActivity {

    private Repository repo;

    private EditText titleField;
    private EditText startField;
    private EditText endField;

    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;

    private final Calendar startCalendar = Calendar.getInstance();
    private final Calendar endCalendar = Calendar.getInstance();

    private SimpleDateFormat sdf;

    public static int editTermId;
    private Term termToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        repo = new Repository(getApplication());

        titleField = findViewById(R.id.termDetailsTitleField);
        startField = findViewById(R.id.termDetailsStartField);
        endField = findViewById(R.id.termDetailsEndField);

        String myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (editTermId == -1) {                         // Add Term
            getSupportActionBar().setTitle("Add Term");
            String currentDate = sdf.format(new Date());
            startField.setText(currentDate);
            endField.setText(currentDate);
        } else {                                        // Edit Term
            getSupportActionBar().setTitle("Edit Term");
            List<Term> termList = repo.getAllTerms();
            termToEdit = termList.stream()
                    .filter(term -> term.getTermId() == editTermId)
                    .collect(Collectors.toList()).get(0);
            titleField.setText(termToEdit.getTitle());
            startField.setText(sdf.format(termToEdit.getStart()));
            endField.setText(sdf.format(termToEdit.getEnd()));
        }

        startField.setOnClickListener(view -> {
            Date date;
            String info = startField.getText().toString();
            try {
                startCalendar.setTime(sdf.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(TermDetails.this, startDateListener, startCalendar.get(Calendar.YEAR),
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
            new DatePickerDialog(TermDetails.this, endDateListener, endCalendar.get(Calendar.YEAR),
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
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
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

    private void updateStartField() {
        startField.setText(sdf.format(startCalendar.getTime()));
    }
    private void updateEndField() {
        endField.setText(sdf.format(endCalendar.getTime()));
    }

    public void cancelActivity(View view) {
        this.finish();
    }

    public void saveTerm(View view) {
        if (startCalendar.after(endCalendar)) {
            CharSequence toastText = "\"Start Date\" must come before \"End Date\"";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, toastText, duration);
            toast.show();
        }
        // Add New Term
        else if(editTermId == -1) {
            // Check for Duplicate Title
            if (termTitleIsDuplicate()){
                CharSequence toastText = "This title: \"" + titleField.getText().toString() + "\" "
                        + "already exists. Please choose a different title.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(this, toastText, duration);
                toast.show();
            } else {
                Term newTerm = new Term(titleField.getText().toString(), startCalendar.getTime(),
                        endCalendar.getTime());
                repo.insert(newTerm);
                Intent intent = new Intent(TermDetails.this, TermsList.class);
                startActivity(intent);
            }
        }
        // Edit term
        else {
            Term termCopy = new Term(termToEdit);
            termCopy.setTitle(titleField.getText().toString());
            termCopy.setStart(startCalendar.getTime());
            termCopy.setEnd(endCalendar.getTime());
            repo.update(termCopy);
            List<Term> updatedTerms = repo.getAllTerms();
            int queryId = termCopy.getTermId();
            Term updated = updatedTerms.stream()
                    .filter(term -> term.getTermId() == queryId)
                    .collect(Collectors.toList()).get(0);
            Intent intent = new Intent(TermDetails.this, CoursesList.class);
            intent.putExtra("id", updated.getTermId());
            intent.putExtra("title", updated.getTitle());
            intent.putExtra("start", updated.getStart());
            intent.putExtra("end", updated.getEnd());
            startActivity(intent);
        }
    }

    private boolean termTitleIsDuplicate() {
        List<String> termTitles = repo.getAllTerms()
                .stream().map(Term::getTitle)
                .collect(Collectors.toList());
        return  termTitles.contains(titleField.getText().toString());
    }

}