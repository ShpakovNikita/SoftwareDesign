package com.example.shaft.softwaredesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaft.softwaredesign.databaseWorkers.manager.ContextManager;
import com.example.shaft.softwaredesign.databinding.FragmentAccountBinding;
import com.example.shaft.softwaredesign.model.Account;
import com.example.shaft.softwaredesign.viewModels.ProfileViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class AccountFragment extends Fragment{
    FragmentAccountBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_account,
                container,
                false);

        View view = binding.getRoot();
        setData();

        FloatingActionButton floatingActionButton =
                (FloatingActionButton) view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener( (v) -> {
            switch (v.getId()){
                case R.id.floatingActionButton:
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_account_fragment_to_edit_account_fragment);
                    break;
            }
        });

        return view;
    }

    public void setData(){
        LiveData<Account> liveData =
                ContextManager.getInstance(getActivity().getApplicationContext()).getData();

        liveData.observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                binding.setModel(ProfileViewModel.castToProfileViewModel(account));
            }

        });

    }
}
