package com.example.planningpokeruser.question;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.planningpokeruser.R;
import com.example.planningpokeruser.group.model.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class QuestionFragment extends Fragment {

    private static final String TAG = QuestionFragment.class.getName();

    private Question question;
    private TextView questionTextView;
    private TextView averageAnswerTextView;
    private EditText answerEditText;
    private ImageView addAnswerImageView;

    public static QuestionFragment newInstance(Question question) {

        Bundle args = new Bundle();
        args.putSerializable("key_question", question);
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        question = (Question) getArguments().getSerializable("key_question");
        questionTextView = view.findViewById(R.id.tv_question);
        averageAnswerTextView = view.findViewById(R.id.tv_average_answer);
        answerEditText = view.findViewById(R.id.et_answer);
        addAnswerImageView = view.findViewById(R.id.img_add_answer);

        questionTextView.setText(question.getQuestion());

        addAnswerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = answerEditText.getText().toString();
                addAnswer(answer);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("answers/" + question.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");
                int count = 0;
                int sum = 0;

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    count++;
                    String valueString = (String) child.getValue();
                    if (valueString != null) {
                        sum += Integer.valueOf(valueString);
                    }
                }

                if (count == 0) {
                    averageAnswerTextView.setText("0");
                } else {
                    String average = String.valueOf(sum / count);
                    averageAnswerTextView.setText(average);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled");
            }
        });
    }

    private void addAnswer(String answer) {
        if (auth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "Not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = auth.getCurrentUser().getUid();
        String questionId = question.getId();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("answers/" + questionId);
        HashMap<String, Object> map = new HashMap<>();
        map.put(uid, answer);
        ref.updateChildren(map);
    }
}
