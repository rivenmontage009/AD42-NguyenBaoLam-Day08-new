package com.example.homeworkday08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.homeworkday08.databinding.ActivityMainBinding;
import com.example.homeworkday08.fragment.ListContactFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            getFragment(ListContactFragment.newInstance());
        }
    }
    public void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flMain,fragment)
                .commit();
    }
}