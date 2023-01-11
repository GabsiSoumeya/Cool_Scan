package com.example.mypfaproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class FirstFragment extends Fragment implements OnClickListener {
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_first, container,
                false);

        Button register = rootView.findViewById(R.id.button4);
        register.setOnClickListener(this);

        return rootView;
    }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_first);

        }

    private void setContentView(int fragment_first) {
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.button4:
                startActivity(new Intent(this.getContext(),RegisterUser.class));
                break;
            }

    }
}
