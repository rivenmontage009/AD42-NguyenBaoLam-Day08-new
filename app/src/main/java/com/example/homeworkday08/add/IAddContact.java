package com.example.homeworkday08.add;

import com.example.homeworkday08.model.Contact;

public interface IAddContact {
    void onSuccessful(Contact contact);
    void onMessenger(String mes);
}
