package com.example.shaft.softwaredesign.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.shaft.softwaredesign.model.Account;
import com.example.shaft.softwaredesign.model.Picture;
import com.example.shaft.softwaredesign.viewmodels.EditAccountViewModel;
import com.example.shaft.softwaredesign.databinding.EditAccountFragmentBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class EditAccountFragment extends Fragment {
    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        EditAccountFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.edit_account_fragment, parent, false);


        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbarLayout.toolbar);

        mGallerViewModel = ViewModelProviders.of(getActivity()).get(GalleryViewModel.class);
        mGallerViewModel.getPath().observe(this, path -> {
            Glide.with(EditAccountFragment.this)
                    .load(path)
                    .into(binding.editAccountPicture);
        });

        final EditAccountViewModel model = ViewModelProviders.of(this).get(EditAccountViewModel.class);
        Account initialAccount = model.getAccount().getValue();
        if (initialAccount != null) {
            if (mGallerViewModel.getPath().getValue() == null) {
                mGallerViewModel.setPath(initialAccount.getPicture().getPath());
            }
            setDate(initialAccount, binding);
        }

        binding.editAccountSaveFab.setOnClickListener(v -> {
            saveData(initialAccount, model, binding);

            final NavController navController =
                    Navigation.findNavController(v);
            navController.navigateUp();
        });

        binding.editAccountPicture.setOnClickListener(v -> {

            final NavController navController =
                    Navigation.findNavController(v);
            navController.navigate(R.id.action_edit_account_to_gallery);
        });


        return binding.getRoot();
    }
    */

    /*
    private void setDate(Account account, EditAccountFragmentBinding binding) {
        binding.editAccountFirstName.setText(account.getFirstName());
        binding.editAccountSecondName.setText(account.getLastName());
        binding.editAccountEmail.setText(account.getEmail());
        binding.editAccountLocation.setText(account.getAddress());
        binding.editAccountTelephone.setText(account.getTelephone());
    }
    */

    /*
    private void saveData(Account initialAccount, EditAccountViewModel model, EditAccountFragmentBinding binding) {

        String firstName = binding.editAccountFirstName.getText().toString();
        String lastName = binding.editAccountSecondName.getText().toString();
        String email = binding.editAccountEmail.getText().toString();
        String telephone = binding.editAccountTelephone.getText().toString();
        String location = binding.editAccountLocation.getText().toString();
        String photo = mGallerViewModel.getPath().getValue();

        Account account = new Account();
        if (initialAccount == null) {
            account.setMain(true);
        } else {
            account = initialAccount;
        }

        if (!firstName.isEmpty()) {
            account.setFirstName(firstName);
        }
        if (!lastName.isEmpty()) {
            account.setLastName(lastName);
        }
        if (!email.isEmpty()) {
            account.setEmail(email);
        }
        if (!telephone.isEmpty()) {
            account.setTelephone(telephone);
        }
        if (!location.isEmpty()) {
            account.setAddress(location);
        }
        if (photo != null && !photo.isEmpty()) {
            Picture picture = new Picture();
            picture.setPath(photo);
            account.setPicrute(picture);
        }

        model.setAccount(account);
        model.saveAccount();
    }
    */
}
