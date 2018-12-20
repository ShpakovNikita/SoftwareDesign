package com.example.shaft.softwaredesign.ui.auth;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shaft.softwaredesign.firebase.auth.state.SignInState;
import com.example.shaft.softwaredesign.firebase.workers.manager.AccountManager;
import com.example.shaft.softwaredesign.firebase.workers.state.AccountState;
import com.example.shaft.softwaredesign.model.Account;
import com.example.shaft.softwaredesign.ui.MainActivity;
import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.databinding.FragmentRegisterBinding;
import com.example.shaft.softwaredesign.firebase.auth.AuthManager;
import com.example.shaft.softwaredesign.firebase.auth.state.SignUpState;
import com.example.shaft.softwaredesign.utils.NetworkUtils;
import com.example.shaft.softwaredesign.viewModels.ProfileViewModel;
import com.example.shaft.softwaredesign.viewModels.SignUpViewModel;
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
        binding.setModel(new SignUpViewModel());
        View view = binding.getRoot();

        // Inflate the layout for this fragment
        Button signup =
                (Button) view.findViewById(R.id.signup);

        signup.setOnClickListener((v)->{
            if(NetworkUtils.isNetworkConnected(getActivity().getApplicationContext())) {
                onSignUpButtonClicked(v);
            }else{
                Toast.makeText(getActivity().getApplicationContext(),
                        "No internet connection", Toast.LENGTH_SHORT).show();
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void onSignInButtonClicked(View v){
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.action_registerFragment_to_loginFragment);
    }

    private void onSignUpButtonClicked(View v){
        String email = binding.getModel().email.get();
        String password = binding.getModel().password.get();
        String passwordConfirm = binding.getModel().passwordConfirm.get();

        if (Strings.isEmptyOrWhitespace(email) || Strings.isEmptyOrWhitespace(password)){
            Toast.makeText(getActivity().getApplicationContext(),
                    "Type all data!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordConfirm)){
            Toast.makeText(getActivity().getApplicationContext(),
                    "Passwords doesn't match!", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<SignUpState> stateLiveData = AuthManager.getInstance().signUpUser(email, password);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Signing up...");
        progressDialog.show();

        // TODO: refactor this horrible monster!
        stateLiveData.observe(this, new Observer<SignUpState>(){
            @Override
            public void onChanged(SignUpState state) {
                if (state == null) {
                    return;
                }
                else if (state.isSuccess) {

                    LiveData<SignInState> signInState = AuthManager.getInstance().signInUser(email, password);
                    signInState.observe(getActivity(), new Observer<SignInState>(){
                        @Override
                        public void onChanged(SignInState state) {
                            if (state == null) {
                                return;
                            }
                            else if (state.isSuccess) {
                                Account account = new Account();
                                account.setEmail(email);

                                LiveData<AccountState> createState =
                                        AccountManager.getInstance().createAccount(account);
                                createState.observe(getActivity(), new Observer<AccountState>() {
                                    @Override
                                    public void onChanged(AccountState state) {

                                        if (state == null) {
                                            return;
                                        }
                                        else if (state.isSuccess) {
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                            createState.removeObserver(this);
                                            return;
                                        }

                                        Toast.makeText(getActivity().getApplicationContext(),
                                                state.error, Toast.LENGTH_SHORT).show();
                                    }

                                });

                            }
                            else if (state.error != null) {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        state.error, Toast.LENGTH_SHORT).show();
                            }

                            signInState.removeObserver(this);
                            progressDialog.dismiss();
                        }
                    });
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
