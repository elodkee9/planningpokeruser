package com.example.planningpokeruser;

import com.example.planningpokeruser.group.model.Group;
import com.example.planningpokeruser.group.model.Question;

public interface MainView {
    void showGroupListing();

    void showGroup(Group group);

    void showQuestion(Question question);
}
