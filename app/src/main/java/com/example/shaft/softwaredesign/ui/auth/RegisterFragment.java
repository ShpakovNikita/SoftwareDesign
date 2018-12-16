package com.example.shaft.softwaredesign.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shaft.softwaredesign.MainActivity;
import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.databinding.FragmentRegisterBinding;
import com.example.shaft.softwaredesign.firebaseAuth.AuthManager;
import com.example.shaft.softwaredesign.firebaseAuth.state.SignInState;
import com.example.shaft.softwaredesign.firebaseAuth.state.SignUpState;
import com.google.firebase.FirebaseException;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_register,
                container,
                false);

        View view = binding.getRoot();

        // Inflate the layout for this fragment
        Button signup =
                (Button) view.findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpButtonClicked(v);
            }
        });

        Button signInButton = (Button) view.findViewById(R.id.signin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInButtonClicked(v);
            }
        });

        return view;
    }

    private void onSignInButtonClicked(View v){
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.action_registerFragment_to_loginFragment);
    }

    private void onSignUpButtonClicked(View v){
        String email = binding.getModel().email.get();
        String password = binding.getModel().password.get();
        String passwordConfirm = binding.getModel().passwordConfirm.get();

        if (password != passwordConfirm){
            Toast.makeText(getActivity().getApplicationContext(),
                    "Passwords doesn't match!", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<SignUpState> stateLiveData = AuthManager.getInstance().signUpUser(email, password);

        stateLiveData.observe(this, new Observer<SignUpState>(){
            @Override
            public void onChanged(SignUpState state) {
                if (state == null) {
                    return;
                }
                else if (state.isSuccess) {
                    AuthManager.getInstance().signInUser(email, password);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else if (state.error != null) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            state.error, Toast.LENGTH_SHORT).show();
                }

                stateLiveData.removeObserver(this);
            }
        });
    }

}
