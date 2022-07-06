package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.studentscheduler.R;

public class AssessmentDisplay extends AppCompatActivity {

    private TextView titleView, startView, endView;
    private CheckBox startNotify, endNotify;
    private RadioButton performanceRB, objectiveRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_display);


    }
}