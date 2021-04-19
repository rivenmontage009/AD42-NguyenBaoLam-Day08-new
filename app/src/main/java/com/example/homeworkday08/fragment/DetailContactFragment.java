package com.example.homeworkday08.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.homeworkday08.R;
import com.example.homeworkday08.databinding.FragmentDetailContactBinding;
import com.example.homeworkday08.model.Contact;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DetailContactFragment extends Fragment {
    FragmentDetailContactBinding binding;

    public static DetailContactFragment newInstance() {

        Bundle args = new Bundle();

        DetailContactFragment fragment = new DetailContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_contact, container, false);
        return binding.getRoot();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEventItem(Contact contact){
        String name = contact.getName();
        String phone = contact.getPhone();
        String image = contact.getImgUri();
        binding.tvGetName.setText(name);
        binding.tvGetPhone.setText(phone);
        if(contact.isImage())
            binding.ivGetImage.setImageURI(Uri.parse(image));
        else
            binding.ivGetImage.setImageResource(R.drawable.ic_baseline_account_circle_24);
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
}
