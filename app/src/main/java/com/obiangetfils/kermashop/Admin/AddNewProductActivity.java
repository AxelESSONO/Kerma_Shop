package com.obiangetfils.kermashop.Admin;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.GalleryAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AddNewProductActivity extends AppCompatActivity {

    private String categoryName, description, price, pName, saveCurrentDate, saveCurrentTime;
    private Button addNewProductButton;
    //private ImageView inputProductImage;
    private ImageView ret, no_images;
    private EditText inputProductCategory, inputProductName, inputProductDescription, inputProductPrice;
    private static final int GALLERY_PICK = 1;
    private Uri imageUri;
    private String productRandomKey;
    private String downloadImageUrl;
    Random randomNumber = new Random();
    int number = randomNumber.nextInt();
    private StorageReference productImagesRef;
    private DatabaseReference productsRef;
    private ProgressDialog loadingBar;
    private Button choiceButton;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    private GridView gvGallery;
    private GalleryAdapter galleryAdapter;
    private ClipData mClipData;
    private HashMap<String, Object> tmpProductImage = new HashMap<>();
    //private HashMap<String, Uri> productMapImageUri = new HashMap<>();
    private ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    private Uri uri;
    private int upLoad_Count = 0;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        productImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        addNewProductButton = (Button) findViewById(R.id.add_new_product);
        inputProductName = (EditText) findViewById(R.id.product_name);
        inputProductDescription = (EditText) findViewById(R.id.product_description);
        inputProductPrice = (EditText) findViewById(R.id.product_price);
        inputProductCategory = (EditText) findViewById(R.id.category_name);
        choiceButton = (Button) findViewById(R.id.images_choice);
        gvGallery = (GridView) findViewById(R.id.gv);
        loadingBar = new ProgressDialog(this);
        ret = (ImageView) findViewById(R.id.ret);
        no_images = (ImageView) findViewById(R.id.no_images);

        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewProductActivity.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

        choiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateProductData();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, GALLERY_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && data != null) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();
                    mArrayUri.add(mImageUri);
                    galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setHorizontalSpacing(2);
                    gvGallery.setVerticalSpacing(2);
                    gvGallery.setPadding(10, 10, 10, 10);
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery.getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                } else {
                    if (data.getClipData() != null) {
                        //ClipData
                        mClipData = data.getClipData();
                        Uri uri;
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            uri = item.getUri();
                            mArrayUri.add(uri);
                            /** Load tmpProductImage **/
                            tmpProductImage.put("image " + i, uri);
                            /** Load ends **/
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();
                            galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery.getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                        no_images.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "Selected Images " + mArrayUri.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                no_images.setVisibility(View.VISIBLE);
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        } finally {
            if (mArrayUri.size() >= 1) {
                no_images.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Selected Images " + mArrayUri.size(), Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void validateProductData() {
        description = inputProductDescription.getText().toString();
        price = inputProductPrice.getText().toString();
        pName = inputProductName.getText().toString();
        categoryName = inputProductCategory.getText().toString();
        if (mArrayUri.size() == 0) {
            Toast.makeText(this, "Image(s) non chargée(s)...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(categoryName)) {
            Toast.makeText(this, "Renseignez la catégorie SVP...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pName)) {
            Toast.makeText(this, "Renseignez le nom SVP...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Renseignez la description SVP...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Renseignez le prix SVP...", Toast.LENGTH_SHORT).show();
        } else {
            storeProductInformation();
        }
    }

    private void storeProductInformation() {

        loadingBar.setTitle("Ajout du produit");
        loadingBar.setMessage("Ajout du produit en cours.Patientez SVP");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        productRandomKey = String.valueOf(number);
        for (upLoad_Count = 0; upLoad_Count < mArrayUri.size(); upLoad_Count++) {
            Uri IndividualImage = mArrayUri.get(upLoad_Count);
            final StorageReference ImageName = productImagesRef.child("Image" + IndividualImage.getLastPathSegment());
            ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = String.valueOf(uri);
                            storeLink(url, upLoad_Count);
                        }
                    });
                }
            });
        }
        saveProductInfoToDatabase();
    }

    private void storeLink(String url, int a) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products").child(productRandomKey).child("ImagesProducts");
        HashMap<String, String> hashMap = new HashMap<>();
        a = a+1;
        hashMap.put("ProductLink_"+a, url);
        databaseReference.push().setValue(hashMap);
        mArrayUri.clear();
        loadingBar.dismiss();
    }

    private void saveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", description);
        productMap.put("category", categoryName);
        productMap.put("price", price);
        productMap.put("pname", pName);

        productsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(AddNewProductActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                    loadingBar.dismiss();
                    Toast.makeText(AddNewProductActivity.this, "Ajout reussi !!!..", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
