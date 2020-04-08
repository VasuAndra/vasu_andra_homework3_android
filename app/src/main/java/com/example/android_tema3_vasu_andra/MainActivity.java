package com.example.android_tema3_vasu_andra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private UsersFragment usersFragment  = new UsersFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(usersFragment);
    }




    public  void addFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.FragmentMain,fragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.FragmentMain, fragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
