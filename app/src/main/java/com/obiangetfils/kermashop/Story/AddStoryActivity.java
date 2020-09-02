package com.obiangetfils.kermashop.Story;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.obiangetfils.kermashop.Prevalent.Prevalent;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.Sellers.SellerHomeActivity;
import com.obiangetfils.kermashop.adapters.GalleryAdapter;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AddStoryActivity extends AppCompatActivity {

    private String saveCurrentDate, saveCurrentTime;
    private Button addNewProductButton;
    private ImageView ret, no_images;
    private static final int GALLERY_PICK = 1;
    private String productRandomKey;
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
    private ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    private int upLoad_Count = 0;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        productImagesRef = FirebaseStorage.getInstance().getReference().child("Ads");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Story");
        addNewProductButton = (Button) findViewById(R.id.add_new_product);

        choiceButton = (Button) findViewById(R.id.images_choice);
        gvGallery = (GridView) findViewById(R.id.gv);
        loadingBar = new ProgressDialog(this);
        ret = (ImageView) findViewById(R.id.ret);
        no_images = (ImageView) findViewById(R.id.no_images);

        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddStoryActivity.this, SellerHomeActivity.class);
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
                startActivityForResult(Intent.createChooser(intent, "Sélection"), PICK_IMAGE_MULTIPLE);
            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateProductData();
            }
        });
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
                        Log.v("LOG_TAG", "Images sélectinnée(s)" + mArrayUri.size());
                        no_images.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "Images sélectinnée(s) " + mArrayUri.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                no_images.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Quelque chose a mal tourné", Toast.LENGTH_LONG).show();
        } finally {
            if (mArrayUri.size() >= 1) {
                no_images.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Images sélectinnée(s) " + mArrayUri.size(), Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void validateProductData() {

        if (mArrayUri.size() == 0) {
            Toast.makeText(this, "Image(s) non chargée(s)...", Toast.LENGTH_SHORT).show();
        } else {
            storeProductInformation();
        }
    }

    private void storeProductInformation() {

        loadingBar.setTitle("Enregistrement de la publicité");
        loadingBar.setMessage("Ajout en cours d'exécution. Patientez SVP");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        productRandomKey = Prevalent.currentOnLineUser.getUser_id();
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
                            storeLink(url);
                        }
                    });
                }
            });
        }
        saveProductInfoToDatabase();
    }

    private void storeLink(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Story").child(productRandomKey).child("ImagesProducts");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("StoryLink", url);
        databaseReference.push().setValue(hashMap);
        mArrayUri.clear();
        loadingBar.dismiss();
    }

    private void saveProductInfoToDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("storyId", productRandomKey);
        productMap.put("storyDate", saveCurrentDate);
        productMap.put("storyTime", saveCurrentTime);
        productMap.put("storyNameSender", Prevalent.currentOnLineUser.getUser_lastName());

        productsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(AddStoryActivity.this, SellerHomeActivity.class);
                    startActivity(intent);
                    loadingBar.dismiss();
                    Toast.makeText(AddStoryActivity.this, "Publicité enregistrée !!!..", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AddStoryActivity.this, "Erreur: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}