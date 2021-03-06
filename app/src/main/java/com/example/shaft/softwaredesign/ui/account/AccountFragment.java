package com.example.shaft.softwaredesign.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shaft.softwaredesign.GlideApp;
import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.firebase.workers.manager.AccountManager;
import com.example.shaft.softwaredesign.databinding.FragmentAccountBinding;
import com.example.shaft.softwaredesign.firebase.auth.AuthManager;
import com.example.shaft.softwaredesign.firebase.workers.state.AccountState;
import com.example.shaft.softwaredesign.ui.auth.AuthActivity;
import com.example.shaft.softwaredesign.viewModels.ProfileViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class AccountFragment extends Fragment{
    FragmentAccountBinding binding;
    View view;

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

        view = binding.getRoot();
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

        Button signOut = (Button) view.findViewById(R.id.signout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignOutButtonClicked(v);
            }
        });

        return view;
    }

    public void setData(){
        LiveData<AccountState> liveData = AccountManager.getInstance().getCurrentAccount();
        ProgressBar bar = (ProgressBar) view.findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);
        liveData.observe(this, (state) -> {
            if (state == null || state.data == null) {
                return;
            }
            else if (state.isSuccess) {
                binding.setModel(ProfileViewModel.castToProfileViewModel(state.data));
                String picUrl = state.data.getPicture();

                if (picUrl != null && picUrl.startsWith("content://media"))
                {
                    picUrl = picUrl.replace("content://media", "");
                }

                if (picUrl != null) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + picUrl);
                    ImageView imageView = getView().findViewById(R.id.profile_image);

                    GlideApp.with(requireActivity().getApplicationContext() /* context */)
                            .load(storageReference)
                            .into(imageView);

                }
                bar.setVisibility(View.INVISIBLE);
                return;
            }

            Toast.makeText(getActivity().getApplicationContext(),
                    state.error, Toast.LENGTH_SHORT).show();

        });

    }

    private void onSignOutButtonClicked(View v){
        AuthManager.getInstance().signOut();
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
