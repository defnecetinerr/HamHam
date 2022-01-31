package com.daphne.finalprojectcooking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.daphne.finalprojectcooking.databinding.ActivityFoodBinding;
import com.daphne.finalprojectcooking.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

public class FoodActivity extends AppCompatActivity {
    private ActivityFoodBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher; // galeriye gitmek için aktivite sonucu baslatıcı
    ActivityResultLauncher<String> permissionLuncher; // izini istemek için kullanıyoruz
    Bitmap selectedImage;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        registerLauncher();

        database = this.openOrCreateDatabase("Food",MODE_PRIVATE,null);
        String info = getIntent().getStringExtra("info");
        if (info.matches("new")) {
          /*  binding.artNameText.setText("");
            binding.painterNameText.setText("");
            binding.yearText.setText("");
            binding.button.setVisibility(View.VISIBLE);*/


            binding.resimSec.setImageResource(R.drawable.ic_baseline_collections_24);


        } else {
            int foodId = getIntent().getIntExtra("foodId",1);
          //  binding.button.setVisibility(View.INVISIBLE);

            try {

                Cursor cursor = database.rawQuery("SELECT * FROM food WHERE id = ?",new String[] {String.valueOf(foodId)});

                int nameIx = cursor.getColumnIndex("yemekAdiText");
                int idIx = cursor.getColumnIndex("id");
                int malzemeIx = cursor.getColumnIndex("malzemeText");
                int tarifIx = cursor.getColumnIndex("tarifText");
                int imageIx = cursor.getColumnIndex("image");

                while (cursor.moveToNext()) {
                    String name = cursor.getString(nameIx);
                    String malzeme = cursor.getString(malzemeIx);
                    String tarif = cursor.getString(tarifIx);
                    //string malzeme al
                    int id = cursor.getInt(idIx);
                    byte[] bytes = cursor.getBlob(imageIx);
                    Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    binding.resimSec.setImageBitmap(image);
                    binding.yemekAdiText.setText(name);
                    binding.malzemeText.setText(malzeme);
                    binding.tarifText.setText(tarif);
                    //binding malzeme ekle ama class a malzeme eklemeyi unutma


                }

                cursor.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public void yukleButton(View view) {
        String yemekAdiText = binding.yemekAdiText.getText().toString();
        String malzemeText = binding.malzemeText.getText().toString();
        String tarifText = binding.tarifText.getText().toString();

        Bitmap smallImage = makeSmallerImage(selectedImage,300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] byteArray = outputStream.toByteArray();

        //Toast.makeText(getApplicationContext(),"Tarifiniz kayıt edilmiştir. Afiyet Olsun. :) ",Toast.LENGTH_LONG).show();


        try {

            database = this.openOrCreateDatabase("Food",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS food (id INTEGER PRIMARY KEY,yemekAdiText VARCHAR, malzemeText VARCHAR, tarifText VARCHAR, image BLOB)");

            String sqlString = "INSERT INTO food (yemekAdiText, malzemeText, tarifText, image) VALUES (?, ?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,yemekAdiText);
            sqLiteStatement.bindString(2,malzemeText);
            sqLiteStatement.bindString(3,tarifText);
            sqLiteStatement.bindBlob(4,byteArray);
            sqLiteStatement.execute();


        } catch (Exception e) {

        }

        Intent intent = new Intent(FoodActivity.this,recList.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        //finish();

    }






    public Bitmap makeSmallerImage(Bitmap image, int maximumSize) {

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1 ) {
            // yatay fotoğraflarda
            width = maximumSize;
            height=(int) (width / bitmapRatio);
        } else {
            //dikey fotoğraflarda
            height = maximumSize;
            height = (int) (width * bitmapRatio);
        }

        return image.createScaledBitmap(image, width,height,true);
        /* createScaleBitmap yukarı ya da aşağı doğru ayarlanmış */
    }



    public void resimSec(View view) {
        // izinleri kontrol et daha önce izin almıs mıyız almamıs mıyız
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // izin verilmemiş mi izin else giriyorsa else girilmiş package Manager yardımcı paket sınıf
// her iznin farklı app versiyonları var o yüzden bunu kullanıyoruz
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,"galeriye ulaşmamız için izininiz gereklidir! ",Snackbar.LENGTH_INDEFINITE).setAction("izin ver", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        permissionLuncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                    }
                }).show();

            }else {
                permissionLuncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

            }

        } else {
            //galery
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // BEN GALERİYE GİDİCEM VE GÖRSEL ALIP GERİ GELECEĞİM DEMEK İSTEDİM
            //uri adres belirten bir metrik url gibi adres belirtiyo
            activityResultLauncher.launch(intentToGallery);

        }

    }


    private void registerLauncher(){
// burada  ActivityResultLauncher<Intent> tanımlamalarını ve işlemlerini yazdığım fonksiyon
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == RESULT_OK){
                    //kullanıcı bir şey seçti demek
                    Intent intentFromResult = result.getData();
                    if (intentFromResult != null){
                        Uri imageData = intentFromResult.getData();
                        // binding.imageView.setImageURI(imageData);

                        try {
                            if (Build.VERSION.SDK_INT>=28){

// decoder kaybağı bul resime çevir diyoruz
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(),imageData);

                                selectedImage =ImageDecoder.decodeBitmap(source);
                                binding.resimSec.setImageBitmap(selectedImage);  }
                            else {
                                selectedImage = MediaStore.Images.Media.getBitmap(FoodActivity.this.getContentResolver(),imageData);
                                binding.resimSec.setImageBitmap(selectedImage);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

            }
        });
// permissionLuncher sonunda cevap alacağımız bir işlem olacağını söylüyoruz.
        permissionLuncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result){
                    // izin verildi
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);
                }

                else {
                    //izin verilmedi
                    Toast.makeText(FoodActivity.this, "izin gerekli" , Toast.LENGTH_LONG).show();
                }
            }
        });
    } }