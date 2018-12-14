package com.example.shaft.softwaredesign.ui.auth;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shaft.softwaredesign.R;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button floatingActionButton =
                (Button) view.findViewById(R.id.signin);

        floatingActionButton.setOnClickListener( (v) -> {
            switch (v.getId()){
                case R.id.signin:
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                    break;
            }
        });

        // TODO: on register listener

        return view;
    }

}
