package com.example.shaft.softwaredesign.ui.account;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shaft.softwaredesign.R;
import com.example.shaft.softwaredesign.firebase.workers.manager.AccountManager;
import com.example.shaft.softwaredesign.firebase.workers.manager.ContextManager;
import com.example.shaft.softwaredesign.databinding.FragmentEditAccountBinding;
import com.example.shaft.softwaredesign.firebase.workers.state.AccountState;
import com.example.shaft.softwaredesign.model.Account;
import com.example.shaft.softwaredesign.ui.MainActivity;
import com.example.shaft.softwaredesign.viewModels.ProfileViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditAccountFragment extends Fragment{

    private CircleImageView imageView;
    private FragmentEditAccountBinding binding;

    private Uri imageUri;

    private final int REQUEST_CODE = 1;
    private final int TAKE_PICTURE_CODE = 100;
    private final int SELECT_PICTURE_CODE = 101;
    private final String[] PERMISSIONS = new String[]{
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                        inflater,
                        R.layout.fragment_edit_account,
                        container,
                        false);

        View view = binding.getRoot();
        setData();

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener( (v) -> {
            switch (v.getId()){
                case R.id.floatingActionButton:
                    NavController navController = Navigation.findNavController(v);
                    ProgressBar bar = (ProgressBar) getView().findViewById(R.id.progressBar);
                    bar.setVisibility(View.VISIBLE);

                    ProfileViewModel model = binding.getModel();
                    if (imageUri != null) {
                        model.picture.set(imageUri.toString());
                    }

                    LiveData<AccountState> state = AccountManager.getInstance().
                            updateAccount(ProfileViewModel.castToAccount(model));
                    state.observe(this, new Observer<AccountState>() {
                        @Override
                        public void onChanged(AccountState state) {
                            if (state == null) {
                                return;
                            }
                            else if (state.isSuccess) {
                                navController.navigate(R.id.action_edit_account_fragment_to_account_fragment);
                                return;
                            }

                            Toast.makeText(getActivity().getApplicationContext(),
                                    state.error, Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.INVISIBLE);
                        }

                    });

                    break;
            }
        });

        imageView = view.findViewById(R.id.profile_image);
        imageView.setOnClickListener( (v) -> {onImageSelect(v);});

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case TAKE_PICTURE_CODE:
                if(resultCode == Activity.RESULT_OK){
                    Bitmap bitmap = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    imageUri = getImageUri(getActivity().getApplicationContext(), bitmap);
                    imageView.setImageURI(imageUri);
                    uploadImage();

                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            R.string.toast_img_updated, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case SELECT_PICTURE_CODE:
                if(resultCode == Activity.RESULT_OK){
                    imageUri = imageReturnedIntent.getData();
                    imageView.setImageURI(imageUri);
                    uploadImage();

                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            R.string.toast_img_updated, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }
    }

    // TODO: change in future
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                inContext.getContentResolver(),
                inImage,
                "Title",
                null);
        return Uri.parse(path);
    }

    public void onImageSelect(View view){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(PERMISSIONS, REQUEST_CODE);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.select_input))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.take),
                        (DialogInterface dialogInterface, int emptyArg) ->
                        {
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, TAKE_PICTURE_CODE);
                        })
                .setNegativeButton(getString(R.string.select),
                        (DialogInterface dialogInterface, int emptyArg) ->
                        {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto , SELECT_PICTURE_CODE);
                        }
                ).create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onImageSelect(getView());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getString(R.string.about_photo))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.clear),
                                    (DialogInterface dialogInterface, int emptyArg) ->
                                    {onImageSelect(getView());})
                            .setNegativeButton(getString(R.string.no),
                                    (DialogInterface dialogInterface, int emptyArg) -> {return;}
                            ).create().show();
                }
                break;
            default:
                throw new RuntimeException("No such request code!");
        }
    }

    public void setData(){
        LiveData<AccountState> liveData =
                AccountManager.getInstance().getCurrentAccount();

        liveData.observe(this, new Observer<AccountState>() {
            @Override
            public void onChanged(AccountState state) {

                if (state == null || state.data == null) {
                    return;
                }
                else if (state.isSuccess) {
                    binding.setModel(ProfileViewModel.castToProfileViewModel(state.data));
                    return;
                }

                Toast.makeText(getActivity().getApplicationContext(),
                        state.error, Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void uploadImage(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        String imageRefPath = UUID.randomUUID().toString();
        binding.getModel().picture.set(imageRefPath);

        StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child("images/" + imageRefPath);
        ref.putFile(imageUri)
                .addOnSuccessListener((taskSnapshot) -> {
                    progressDialog.dismiss();
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Uploaded",
                            Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener((e) -> {
                        progressDialog.dismiss();
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Failed " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                })
                .addOnProgressListener((taskSnapshot) -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+(int)progress + "%");
                });
    }
}
