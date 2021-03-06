package com.studentscheduler.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.studentscheduler.R;
import com.studentscheduler.db.Repository;
import com.studentscheduler.entity.Term;

import java.util.ArrayList;
import java.util.List;

public class TermsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);

        RecyclerView recyclerView = findViewById(R.id.termsListRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Term> terms = new ArrayList<>();
        terms = repo.getAllTerms();
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);
    }

    public void goToAddTerm(View view) {
        TermDetails.editTermId = -1;
        Intent intent = new Intent(TermsList.this, TermDetails.class);
        startActivity(intent);
    }
}