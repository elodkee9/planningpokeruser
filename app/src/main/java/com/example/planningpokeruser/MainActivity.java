package com.example.planningpokeruser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.planningpokeruser.login.LoginFragment;

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
        // TODO fragment
    }
}
