package com.example.planningpokeruser.group;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planningpokeruser.MainView;
import com.example.planningpokeruser.R;
import com.example.planningpokeruser.group.model.Group;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupListFragment extends Fragment {

    private static final String TAG = GroupListFragment.class.getName();

    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private List<Group> list = new ArrayList<>();
    private MainView mainView;

    public static GroupListFragment newInstance() {
        Bundle args = new Bundle();
        GroupListFragment fragment = new GroupListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainView = (MainView) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        groupAdapter = new GroupAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(groupAdapter);

        groupAdapter.setListener(new GroupAdapter.ClickListener() {
            @Override
            public void onGroupClicked(Group group) {
                Log.d(TAG, "Group clicked");
                mainView.showGroup(group);
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("groups");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                Log.e(TAG, "onDataChange");
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String groupKey = child.getKey();
                    list.add(new Group(groupKey));
                }
                groupAdapter.setList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled");
            }
        });
    }
}
