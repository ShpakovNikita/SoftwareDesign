package com.example.shaft.softwaredesign.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shaft.softwaredesign.ui.MainActivity;
import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.databinding.FragmentLoginBinding;
import com.example.shaft.softwaredesign.firebase.auth.AuthManager;
import com.example.shaft.softwaredesign.firebase.auth.state.SignInState;
import com.example.shaft.softwaredesign.viewModels.SignInViewModel;
import com.google.android.gms.common.util.Strings;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false);
        binding.setModel(new SignInViewModel());
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
        String email = binding.getModel().email.get();
        String password = binding.getModel().password.get();

        if (Strings.isEmptyOrWhitespace(email) || Strings.isEmptyOrWhitespace(password)){
            Toast.makeText(getActivity().getApplicationContext(),
                    "Type email and password!", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<SignInState> stateLiveData = AuthManager.getInstance().signInUser(email, password);

        ProgressBar bar = (ProgressBar) getView().findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);

        stateLiveData.observe(this, new Observer<SignInState>(){
            @Override
            public void onChanged(SignInState state) {
                if (state == null) {
                    return;
                }
                else if (state.isSuccess) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else if (state.error != null) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            state.error, Toast.LENGTH_SHORT).show();
                }

                stateLiveData.removeObserver(this);
                bar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void onSignUpButtonClicked(View v){
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.action_loginFragment_to_registerFragment);
    }
}
