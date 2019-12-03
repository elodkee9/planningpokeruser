package com.example.planningpokeruser.question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.planningpokeruser.R;
import com.example.planningpokeruser.group.model.Question;

public class QuestionFragment extends Fragment {

    public static QuestionFragment newInstance(Question question) {

        Bundle args = new Bundle();
        args.putSerializable("key_question", question);
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Question question;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        question = (Question) getArguments().getSerializable("key_question");
        TextView questionTextView = view.findViewById(R.id.tv_question);
        EditText answerEditText = view.findViewById(R.id.et_answer);
        ImageView addAnswerImageView = view.findViewById(R.id.img_add_answer);

        questionTextView.setText(question.getQuestion());
    }
}
