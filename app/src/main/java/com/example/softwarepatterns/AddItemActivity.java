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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class AddItemActivity extends AppCompatActivity {
    EditText titleText, categoryText, manufacturerText,priceText, numText;
    ImageView imageView;
    Button addButton;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    StorageReference mStorageRef;
    private Uri imageUri;
    Uri downloadUri;
    RecyclerView.Adapter adapter;
    ArrayList<StockItem> stockItems = new ArrayList<StockItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("StockItem").push();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        numText = findViewById(R.id.numText);
        titleText = findViewById(R.id.titleText);
        categoryText = findViewById(R.id.categoryText);
        manufacturerText = findViewById(R.id.manufacturerText);
        priceText = findViewById(R.id.priceText);
        imageView = findViewById(R.id.imageView);
        addButton = findViewById(R.id.addButton);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String title = titleText.getText().toString();
                String category = categoryText.getText().toString();
                String manufacturer = manufacturerText.getText().toString();
                int price = Integer.parseInt(priceText.getText().toString());
                String uri = downloadUri.toString();
                int num = Integer.parseInt(numText.getText().toString());
                String key =  reference.getKey();

                if (title.equals("") || category.equals("")||manufacturer.equals("")||uri.equals("")){
                    Toast.makeText(AddItemActivity.this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
                } else {
                    final StockItem stock = new StockItem(title, manufacturer, category, uri, price, num,key);

                    stockItems.add(stock);


                    reference.setValue(stock).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddItemActivity.this, "Your Item Has Been Created", Toast.LENGTH_LONG).show();
                                adapter = new Adapter(stockItems);
                                Intent i = new Intent(AddItemActivity.this, AdminHomeActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(AddItemActivity.this, "Your Item Has Not Been Created", Toast.LENGTH_LONG).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddItemActivity.this, "Insert item failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                    ((EditText) findViewById(R.id.titleText)).setText("");
                    ((EditText) findViewById(R.id.priceText)).setText("");
                    ((EditText) findViewById(R.id.categoryText)).setText("");
                    ((EditText) findViewById(R.id.manufacturerText)).setText("");

                }
            }


        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotos();
            }


        }); }
    private void choosePhotos() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null&& data.getData()!=null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploadPhoto();
        }
    }

    private void uploadPhoto() {
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
                    downloadUri = task.getResult();
                    Log.i("url", downloadUri.toString());

                } else {
                    Toast.makeText(AddItemActivity.this, "upload image failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }




}