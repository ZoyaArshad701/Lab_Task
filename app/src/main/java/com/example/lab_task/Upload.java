package com.example.lab_task;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class Upload extends AppCompatActivity {
    private ImageView imageToUploadIV;
    private Uri imageDataInUriForm;
    private static final int REQUEST_CODE = 123;
    private EditText imageNameET;
    private StorageReference objectStorageReference;

    private FirebaseFirestore objectFirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        imageToUploadIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();

            }
        });
    }

    private void openGallery() {
        try {
            Intent objectIntent = new Intent(); //Step 1:create the object of intent
            objectIntent.setAction(Intent.ACTION_GET_CONTENT); //Step 2: You want to get some data

            objectIntent.setType("image/*");//Step 3: Images of all type
            startActivityForResult(objectIntent, REQUEST_CODE);

        } catch (Exception e) {
            Toast.makeText(this, "openGallery:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                imageDataInUriForm = data.getData();
                Bitmap objectBitmap;

                objectBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageDataInUriForm);
                imageToUploadIV.setImageBitmap(objectBitmap);

//                isImageSelected = true;

            } else if (requestCode != REQUEST_CODE) {
                Toast.makeText(this, "Request code doesn't match", Toast.LENGTH_SHORT).show();
            } else if (resultCode != RESULT_OK) {
                Toast.makeText(this, "Fails to get image", Toast.LENGTH_SHORT).show();
            } else if (data == null) {
                Toast.makeText(this, "No image was selected", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Toast.makeText(this, "onActivityResult:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
