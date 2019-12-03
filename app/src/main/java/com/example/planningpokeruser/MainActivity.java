package com.example.planningpokeruser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planningpokeruser.group.GroupFragment;
import com.example.planningpokeruser.group.GroupListFragment;
import com.example.planningpokeruser.group.model.Group;
import com.example.planningpokeruser.group.model.Question;
import com.example.planningpokeruser.login.LoginFragment;
import com.example.planningpokeruser.question.QuestionFragment;

public class MainActivity extends AppCompatActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, LoginFragment.newInstance(), "")
                .commit();
    }

    @Override
    public void showGroupListing() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, GroupListFragment.newInstance(), "")
                .commit();
    }

    @Override
    public void showGroup(Group group) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, GroupFragment.newInstance(group.getId()), "group")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showQuestion(Question question) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, QuestionFragment.newInstance(question), "question")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack();
    }
}
