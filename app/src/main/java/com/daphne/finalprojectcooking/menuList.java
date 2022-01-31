package com.daphne.finalprojectcooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.daphne.finalprojectcooking.databinding.ActivityMainBinding;
import com.daphne.finalprojectcooking.databinding.ActivityMenuListBinding;

import java.util.ArrayList;

public class menuList extends AppCompatActivity {
    private ActivityMenuListBinding binding;
    ArrayList<Food> foodList;
    FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        foodList = new ArrayList<>();


       //binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));


        foodAdapter = new FoodAdapter(foodList);
      // binding.recyclerView.setAdapter(foodAdapter);


    }



    public void secenekButton (View view) {


       PopupMenu popup = new PopupMenu(menuList.this,binding.secenekButton);

        popup.getMenuInflater().inflate(R.menu.menu,popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.addFood:
                        Intent intent = new Intent(menuList.this, FoodActivity.class);
                        intent.putExtra("info", "new");
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Sil Seçildi",Toast.LENGTH_SHORT).show();
                        return true;

                  //  case
                            //Toast.makeText(getApplicationContext(),"lütfen seçim yapınız",Toast.LENGTH_SHORT).show();

                        //return true;
                    default:
                       return false;

                }
            }
        });
        popup.show();
    }

    public void kayitliTarifButton(View view) {

        startActivity(new Intent(menuList.this,recList.class));

    }

    public void cikis(View view) {
        startActivity(new Intent(menuList.this,MainActivity.class));
    }

}