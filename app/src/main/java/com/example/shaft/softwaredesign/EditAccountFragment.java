package com.example.shaft.softwaredesign;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shaft.softwaredesign.databinding.FragmentEditAccountBinding;
import com.example.shaft.softwaredesign.databaseWorkers.ContextManager;
import com.example.shaft.softwaredesign.model.Account;
import com.example.shaft.softwaredesign.model.Picture;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditAccountFragment extends Fragment{

    private CircleImageView imageView;
    private FragmentEditAccountBinding binding;

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
        initData();
        View view = binding.getRoot();

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener( (v) -> {
            switch (v.getId()){
                case R.id.floatingActionButton:
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_edit_account_fragment_to_account_fragment);

                    // TODO: all change on LiveData arch component
                    setData();
                    break;
            }
        });

        imageView = view.findViewById(R.id.profile_image);
        imageView.setOnClickListener( (v) -> {onImageSelect(v);});

        return view;
    }

    public void initData(){
        binding.addressTe.setText();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case TAKE_PICTURE_CODE:
                if(resultCode == Activity.RESULT_OK){
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);

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

    private void setData(){
        Account account = new Account();
        Picture mPicture;
        String mFirstName;
        String mLastName;
        String mAddress;
        String mEmail;

        ContextManager.getInstance(getActivity().getApplicationContext()).setAccount(account);


    }
}
