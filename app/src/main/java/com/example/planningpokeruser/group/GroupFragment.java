package com.example.planningpokeruser.group;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planningpokeruser.MainView;
import com.example.planningpokeruser.R;
import com.example.planningpokeruser.group.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupFragment extends Fragment {
    private static final String TAG = GroupFragment.class.getName();

    public static GroupFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("key_id", id);
        GroupFragment fragment = new GroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String groupId;
    private TextView groupNameTextView;
    private RecyclerView recyclerView;
    private MainView mainView;
    private QuestionAdapter questionAdapter;
    private List<Question> questionList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_detail, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainView = (MainView) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        groupNameTextView = view.findViewById(R.id.tv_group_name);
        recyclerView = view.findViewById(R.id.recycler_view_questions);

        groupId = getArguments().getString("key_id");
        groupNameTextView.setText(groupId);

        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(questionAdapter);

        questionAdapter.setListener(new QuestionAdapter.ClickListener() {
            @Override
            public void onQuestionClicked(Question question) {
                mainView.showQuestion(question);
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("groups/" + groupId + "/questions");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionList.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String questionId = child.getKey();
                    String question = (String) child.getValue();
                    questionList.add(new Question(questionId, question));
                }

                questionAdapter.setList(questionList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled");
            }
        });
    }
}
