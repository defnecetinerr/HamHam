package com.daphne.finalprojectcooking;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.daphne.finalprojectcooking.databinding.ActivityRecListBinding;

import java.util.ArrayList;

public class recList extends AppCompatActivity {

    private ActivityRecListBinding binding;
    ArrayList<Food> foodArrayList;
    FoodAdapter foodAdapter;
    static Food secilen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        foodArrayList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(foodArrayList);
        binding.recyclerView.setAdapter(foodAdapter);
        getFoodData();



        /*binding.recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
        foodAdapter = new FoodAdapter(foodArrayList);
        binding.recyclerView.setAdapter(foodAdapter);*/
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
               // Food food = new Food(name,id,image,malzeme,tarif);
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
}