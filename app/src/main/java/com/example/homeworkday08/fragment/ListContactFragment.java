package com.example.homeworkday08.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkday08.R;
import com.example.homeworkday08.adapter.ContactAdapter;
import com.example.homeworkday08.adapter.IOnClickItem;
import com.example.homeworkday08.databinding.FragmentListContactBinding;
import com.example.homeworkday08.model.Contact;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ListContactFragment extends Fragment {
    FragmentListContactBinding binding;
    List<Contact> contactList;
    ContactAdapter contactAdapter;

    public static ListContactFragment newInstance() {

        Bundle args = new Bundle();

        ListContactFragment fragment = new ListContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_contact, container, false);
        getContactList();

        setContactAdapter();

        return binding.getRoot();
    }

    public List<Contact> getContactList() {
        contactList = new ArrayList<>();

        ContentResolver cr = getContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String name = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String image = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                Contact c = new Contact(name, phone);
                // lấy image contact
                if (image != null) {
                    c.setImgUri(image);
                    c.setIsImage(true);
                } else {
                    c.setIsImage(false);
//                    c.setImgDefault(R.drawable.ic_baseline_account_circle_24);
                }
                contactList.add(c);
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contactList.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
        }

        return contactList;
    }

    public void setContactAdapter() {
        contactAdapter = new ContactAdapter(contactList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.rvContactList.setAdapter(contactAdapter);
        binding.rvContactList.setLayoutManager(layoutManager);

        binding.btnAddContact.setOnClickListener(v -> {
                    getFragment(AddContactFragment.newInstance());
                    EventBus.getDefault().postSticky(contactList);
                }
        );

        contactAdapter.setIOnClickItem(new IOnClickItem() {
            @Override
            public void onClickItem(Contact contact) {

                EventBus.getDefault().postSticky(contact);
                Toast.makeText(getContext(), contact.getName(), Toast.LENGTH_SHORT).show();
                getFragment(DetailContactFragment.newInstance());
            }
        });


    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onEventTakeNewList(List<Contact> list){
        contactList = new ArrayList<>(list);

        setContactAdapter();
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


    public void getFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.flMain, fragment)
                .addToBackStack(null)
                .commit();
    }
}
