package com.example.shaft.softwaredesign.viewModels;

import android.widget.ImageView;

import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.model.Account;
import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

public class ProfileViewModel {

    public ObservableField<String> picture = new ObservableField<>();
    public ObservableField<String> firstName = new ObservableField<>();
    public ObservableField<String> lastName = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> email = new ObservableField<>();

    public void initValues(Account account){
        firstName.set(account.getFirstName());
        lastName.set(account.getLastName());
        address.set(account.getAddress());
        email.set(account.getEmail());
    }

    public static Account castToAccount(ProfileViewModel model){
        Account account = new Account();
        account.setPicture(model.picture.get());
        account.setFirstName(model.firstName.get());
        account.setLastName(model.lastName.get());
        account.setAddress(model.address.get());
        account.setEmail(model.email.get());

        return account;
    }

    public static ProfileViewModel castToProfileViewModel(Account account){
        ProfileViewModel model = new ProfileViewModel();
        model.picture.set(account.getPicture());
        model.email.set(account.getEmail());
        model.address.set(account.getAddress());
        model.lastName.set(account.getLastName());
        model.firstName.set(account.getFirstName());

        return model;
    }

    public String getImageUrl() {
        // The URL will usually come from a model (i.e Profile)
        return "http://cdn.meme.am/instances/60677654.jpg";
    }

    @BindingAdapter("android:src")
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.account)
                .into(view);
    }


}
