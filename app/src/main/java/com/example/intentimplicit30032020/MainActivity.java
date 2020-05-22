package com.example.intentimplicit30032020;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.security.Permission;
import java.security.Permissions;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.buttonCamera)
    Button mBtnCamera;
    @BindView(R.id.buttonGallery)
    Button mBtnGallery;
    @BindView(R.id.imageview)
    ImageView mImage;
    Integer REQUEST_CODE_CAMERA = 123;
    Integer REQUEST_CODE_GALLEY = 456;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBtnCamera.setOnClickListener(v -> {
            // Tao ra request permission
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_CAMERA
            );
        });
        mBtnGallery.setOnClickListener(v -> {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLEY
            );
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_CAMERA){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent , REQUEST_CODE_CAMERA);
            }
        }
        if (requestCode == REQUEST_CODE_GALLEY){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent , REQUEST_CODE_GALLEY);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap  bitmap  = (Bitmap) data.getExtras().get("data");
            mImage.setImageBitmap(bitmap);
        }
        if (requestCode ==  REQUEST_CODE_GALLEY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            mImage.setImageURI(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
