package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class EditActivity extends AppCompatActivity {
    EditText updateTitleText, updateCategoryText, updateManufacturerText,updatePriceText, updateNumText;
    ImageView imageView2;
    Button updateButton;
    DatabaseReference reference,fireDB;
    FirebaseAuth mAuth;
    StorageReference mStorageRef;
    String title,manufacturer,category;
    int numStock,price;
    Uri uri;
    String key;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("StockItem");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        updateTitleText = findViewById(R.id.updateTitleText);
        updateCategoryText = findViewById(R.id.updateCategoryText);
        updateManufacturerText = findViewById(R.id.updateManufacturerText);
        updatePriceText = findViewById(R.id.updatePriceText);
        updateNumText = findViewById(R.id.updateNumText);
        imageView2 = findViewById(R.id.imageView2);
        updateButton = findViewById(R.id.updateButton);


        title = getIntent().getExtras().getString("title");
        price = getIntent().getExtras().getInt("price");
        category = getIntent().getExtras().getString("cat");
        manufacturer = getIntent().getExtras().getString("man");
        numStock = getIntent().getExtras().getInt("numStock");
        uri = getIntent().getExtras().getParcelable("uri");
        key = getIntent().getExtras().getString("key");

        updateTitleText.setText(title);
        updateCategoryText.setText(category);
        updateManufacturerText.setText(manufacturer);
        updatePriceText.setText(String.valueOf(price));
        updateNumText.setText(String.valueOf(numStock));

        Picasso.get().load(uri).into(imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotos();
            }
        });
    }
        private void choosePhotos () {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(i, 1);
        }


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();
                imageView2.setImageURI(imageUri);
                uploadPhoto();
            }
        }

        private void uploadPhoto () {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("uploading Image...");
            pd.show();
            final String randomKey = UUID.randomUUID().toString();
            final StorageReference riversRef = mStorageRef.child("images/" + randomKey);

            riversRef.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        final Uri downloadUri = task.getResult();
                        Log.i("url", downloadUri.toString());

                        updateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final String title = updateTitleText.getText().toString();
                                final int price = Integer.parseInt(String.valueOf(updatePriceText.getText()));
                                final String cat = updateCategoryText.getText().toString();
                                final String man = updateManufacturerText.getText().toString();
                                final int num =Integer.parseInt(String.valueOf(updateNumText.getText()));
                                final String uri = downloadUri.toString();

                                fireDB = FirebaseDatabase.getInstance().getReference("StockItem");

                                fireDB.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                            StockItem stockItem = snapshot.getValue(StockItem.class);

                                            String key1 = snapshot1.getKey();

                                            if (key.equals(key1)) {


                                                fireDB.child(key).child("title").setValue(title);
                                                fireDB.child(key).child("category").setValue(cat);
                                                fireDB.child(key).child("manufacturer").setValue(man);
                                                fireDB.child(key).child("numStock").setValue(num);
                                                fireDB.child(key).child("price").setValue(price);
                                                fireDB.child(key).child("url").setValue(uri);


                                                Toast.makeText(EditActivity.this, "Item Updated!", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        });


                    } else {
                        Toast.makeText(EditActivity.this, "upload image failed", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }

    }
