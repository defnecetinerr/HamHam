package com.daphne.finalprojectcooking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.daphne.finalprojectcooking.databinding.ActivityMainBinding;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    ArrayList<Food> foodArrayList;
     FoodAdapter foodAdapter;
    ArrayList<Kayit> kayitArrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        kayitArrayList = new ArrayList<>();

        getKayitData();
        getFoodData();
       // getDrinkData();



    }

    public void getFoodData() {

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Food",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM food", null);
            int nameIx = cursor.getColumnIndex("yemekAdiText");
            int idIx = cursor.getColumnIndex("id");
            int imageIx = cursor.getColumnIndex("image");
            int malzemeIx = cursor.getColumnIndex("malzemeText");
            int tarifIx = cursor.getColumnIndex("tarifText");

            while (cursor.moveToNext()) {
                String name = cursor.getString(nameIx);
                String malzeme = cursor.getString(malzemeIx);
                String tarif = cursor.getString(tarifIx);
                int id = cursor.getInt(idIx);
                byte[] bytes = cursor.getBlob(imageIx);
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                Food food = new Food(name,id,image,malzeme,tarif);
                foodArrayList.add(food);
            }

           foodAdapter.notifyDataSetChanged();
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
      //  System.out.println(foodArrayList.get(1).name);
      //  System.out.println(foodArrayList.get(1).id);
    }

    public void getKayitData() {
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Kayit",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM kayit", null);
            int mailIx = cursor.getColumnIndex("editTextEmailAddress");
            int sifreIx = cursor.getColumnIndex("kayitSifre");

            while (cursor.moveToNext()) {
                String mail = cursor.getString(mailIx);
                String sifre = cursor.getString(sifreIx);
                Kayit kayit = new Kayit(mail,sifre,0);
                kayitArrayList.add(kayit);
            }


            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       // System.out.println(kayitArrayList.get(1).mail);
        //System.out.println(kayitArrayList.get(1).sifre);

    }


    public void uyeOl (View view){

        startActivity(new Intent(MainActivity.this,KayitOlma.class));


    }



    public void giris(View view) {
       /* String mailTextGiris = binding.mailTextGiris.getText().toString();
        String siftreText = binding.siftreText.getText().toString();*/

        String mailTextGiris = binding.mailTextGiris.getText().toString();
        String siftreText = binding.sifreText.getText().toString();
        //SQLiteDatabase database = this.openOrCreateDatabase("Kayit", MODE_PRIVATE, null);
        for (int i =0; i<kayitArrayList.size();i++){
            if (mailTextGiris.equals(kayitArrayList.get(i).mail) && siftreText.equals(kayitArrayList.get(i).sifre)){
                System.out.println(true);
                Toast.makeText(this, "giriş başarılı", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,menuList.class));
            }else{
                System.out.println(false);
                Toast.makeText(this, "giriş başarısız", Toast.LENGTH_SHORT).show();
            }
        }


        //startActivity(new Intent(MainActivity.this,menuList.class));
    }


    }