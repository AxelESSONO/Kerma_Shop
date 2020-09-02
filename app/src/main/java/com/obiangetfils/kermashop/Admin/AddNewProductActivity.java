package com.obiangetfils.kermashop.Admin;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.adapters.AddProductAdapter;
import com.obiangetfils.kermashop.models.CategoryOBJ;
import com.obiangetfils.kermashop.models.ProductOBJ;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddNewProductActivity extends AppCompatActivity {

    private List<CategoryOBJ> categoryOBJArrayList = new ArrayList<>();
    private ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    private HashMap<String, Object> tmpProductImage = new HashMap<>();
    List<String> imagesEncodedList;
    String imageEncoded;

    private SmartMaterialSpinner productTypeSpinner, categorySearch;
    private List<String> productTypeList, categoryList;

    private ImageView noImage;
    private RecyclerView recyclerViewImage;

    private Button selectButton;
    int PICK_IMAGE_MULTIPLE = 1;

    private AddProductAdapter galleryAdapter;
    private ClipData mClipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        productTypeSpinner = (SmartMaterialSpinner) findViewById(R.id.SearchableProductType);
        initSpinner1();
        initSpinner2();

        selectButton = (Button) findViewById(R.id.select_image);
        recyclerViewImage = (RecyclerView) findViewById(R.id.recyclerView_add_new_product);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        String uri = "edhjfjfj";
        ProductOBJ productOBJ = new ProductOBJ(1002, "Black Scirt", uri, "$192", "$358", false, false, true, false, 1, 10, "Single");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

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
                    galleryAdapter = new AddProductAdapter(getApplicationContext(), mArrayUri);
                    recyclerViewImage.setAdapter(galleryAdapter);


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

                            galleryAdapter = new AddProductAdapter(getApplicationContext(), mArrayUri);
                            recyclerViewImage.setAdapter(galleryAdapter);
                            recyclerViewImage.setLayoutManager(new GridLayoutManager(this, 2));

                          /*  recyclerViewImage.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) recyclerViewImage.getLayoutParams();
                            mlp.setMargins(0, recyclerViewImage.getHorizontalSpacing(), 0, 0);*/
                        }
                        Log.v("LOG_TAG", "Images sélectinnée(s)" + mArrayUri.size());
                        Toast.makeText(this, "Images sélectinnée(s) " + mArrayUri.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Quelque chose a mal tourné", Toast.LENGTH_LONG).show();
        } finally {
            if (mArrayUri.size() >= 1) {
                Toast.makeText(this, "Images sélectinnée(s) " + mArrayUri.size(), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void pickImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Sélection"), PICK_IMAGE_MULTIPLE);
    }

    private void initSpinner1() {


        //spEmptyItem = findViewById(R.id.sp_empty_item);
        categoryList = new ArrayList<>();

        categoryList.add("Kampong Thom");
        categoryList.add("Kampong Cham");
        categoryList.add("Kampong Chhnang");
        categoryList.add("Phnom Penh");
        categoryList.add("Kandal");
        productTypeList.add("Kampot");

        productTypeSpinner.setItem(categoryList);

        productTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(AddNewProductActivity.this, categoryList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        productTypeSpinner.getSelectedItem().toString();

    }

    private void initSpinner2()
    {
        productTypeSpinner = (SmartMaterialSpinner) findViewById(R.id.SearchableProductType);
        //spEmptyItem = findViewById(R.id.sp_empty_item);
        productTypeList = new ArrayList<>();

        productTypeList.add("Kampong Thom");
        productTypeList.add("Kampong Cham");
        productTypeList.add("Kampong Chhnang");
        productTypeList.add("Phnom Penh");
        productTypeList.add("Kandal");
        productTypeList.add("Kampot");

        productTypeSpinner.setItem(productTypeList);

        productTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(AddNewProductActivity.this, productTypeList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
