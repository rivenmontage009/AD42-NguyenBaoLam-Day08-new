package com.example.homeworkday08.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.homeworkday08.R;
import com.example.homeworkday08.add.AddContactPresenter;
import com.example.homeworkday08.add.IAddContact;
import com.example.homeworkday08.databinding.FragmentAddContactBinding;
import com.example.homeworkday08.model.Contact;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class AddContactFragment extends Fragment implements IAddContact {
    FragmentAddContactBinding binding;
    AddContactPresenter addPresenter;

    List<Contact> contactList;

    public static AddContactFragment newInstance() {

        Bundle args = new Bundle();

        AddContactFragment fragment = new AddContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact,container,false);
        addPresenter = new AddContactPresenter(this, getActivity().getBaseContext());
        binding.btnSave.setOnClickListener(v ->{
            String name = binding.etNameContact.getText().toString();
            String phone = binding.etPhone.getText().toString();
            addPresenter.onAdd(name,phone);
        });
        return binding.getRoot();
    }
    public void getFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.flMain, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onEventTakeList(List<Contact> list){
        contactList = new ArrayList<>(list);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccessful(Contact contact) {
        contactList.add(contact);
        EventBus.getDefault().postSticky(contactList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contactList.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
        }

        Toast.makeText(getContext(), getActivity().getBaseContext()
                .getResources().getString(R.string.add_successful), Toast.LENGTH_SHORT).show();
        getFragment(ListContactFragment.newInstance());
    }

    @Override
    public void onMessenger(String mes) {
        Toast.makeText(getContext(), mes, Toast.LENGTH_SHORT).show();
    }
}
