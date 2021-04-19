package com.example.homeworkday08.add;

import android.content.Context;

import com.example.homeworkday08.R;
import com.example.homeworkday08.model.Contact;

public class AddContactPresenter {
    IAddContact iAddContact;
    Context context;

    public AddContactPresenter(IAddContact iAddContact,Context context) {
        this.iAddContact = iAddContact;
        this.context = context;
    }

    public void onAdd(String name, String phone) {
        if (name.isEmpty() || phone.isEmpty())
            iAddContact.onMessenger(context.getResources().getString(R.string.add_error));
        else if(phone.length()<10)
            iAddContact.onMessenger(context.getResources().getString(R.string.add_error_phone_length));
        else
            iAddContact.onSuccessful(new Contact(name,phone));
    }
}
