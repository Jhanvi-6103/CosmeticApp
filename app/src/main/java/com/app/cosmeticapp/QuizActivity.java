package com.app.cosmeticapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.*;

import java.util.*;

public class QuizActivity extends AppCompatActivity {

    private ImageView imageQuestion;
    private TextView textQuestion;
    private RadioGroup optionsGroup;
    private Button btnNext;

    private List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;
    private Map<String, Integer> scoreMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        imageQuestion = findViewById(R.id.imageQuestion);
        textQuestion = findViewById(R.id.textQuestion);
        optionsGroup = findViewById(R.id.optionsGroup);
        btnNext = findViewById(R.id.btnNext);

        // Handle button click only once
        btnNext.setOnClickListener(v -> {
            int selectedId = optionsGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selected = findViewById(selectedId);
            String type = (String) selected.getTag();

            scoreMap.put(type, scoreMap.getOrDefault(type, 0) + 1);
            currentIndex++;
            showNextQuestion();
        });

        loadQuestions(); // Load quiz from Firebase
    }

    private void loadQuestions() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("skinTypeQuiz");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Question q = ds.getValue(Question.class);
                    if (q != null) questions.add(q);
                }
                if (questions.isEmpty()) {
                    Toast.makeText(QuizActivity.this, "No questions found", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    showNextQuestion();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuizActivity.this, "Failed to load quiz", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNextQuestion() {
        if (currentIndex >= questions.size()) {
            showResult();
            return;
        }

        Question q = questions.get(currentIndex);
        textQuestion.setText(q.getText());
        Glide.with(this).load(q.getImageUrl()).into(imageQuestion);
        optionsGroup.removeAllViews();

        for (Option opt : q.getOptions()) {
            RadioButton rb = new RadioButton(this);
            rb.setText(opt.getText());
            rb.setTag(opt.getType());
            rb.setTextSize(16f);
            optionsGroup.addView(rb);
        }
    }

    private void showResult() {
        if (scoreMap.isEmpty()) {
            Toast.makeText(this, "No answers selected. Please try again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Find the most selected type
        String topType = Collections.max(scoreMap.entrySet(), Map.Entry.comparingByValue()).getKey();

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("skinType", topType);
        startActivity(intent);
        finish();
    }
}