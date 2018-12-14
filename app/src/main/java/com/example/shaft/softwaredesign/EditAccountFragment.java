package com.example.shaft.softwaredesign;

import android.Manifest;
import android.app.Activity;
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
import android.widget.Toast;

import com.example.shaft.softwaredesign.databaseWorkers.ContextManager;
import com.example.shaft.softwaredesign.databinding.FragmentEditAccountBinding;
import com.example.shaft.softwaredesign.model.Account;
import com.example.shaft.softwaredesign.viewModels.ProfileViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

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
                    navController.navigate(R.id.action_edit_account_fragment_to_account_fragment);

                    ProfileViewModel model = binding.getModel();
                    if (imageUri != null) {
                        model.picture.set(imageUri.toString());
                    }

                    ContextManager.getInstance(getActivity().getApplicationContext()).
                            setData(ProfileViewModel.castToAccount(model));
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

                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            R.string.toast_img_updated, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case SELECT_PICTURE_CODE:
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);

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
