package com.daphne.finalprojectcooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.daphne.finalprojectcooking.databinding.ActivityKayitOlmaBinding;
import com.daphne.finalprojectcooking.databinding.ActivityMainBinding;

public class KayitOlma extends AppCompatActivity {

    private ActivityKayitOlmaBinding binding;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKayitOlmaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        database = this.openOrCreateDatabase("Kayit",MODE_PRIVATE,null);
    }

    public void uyeOlButton(View view){

        String kayitName = binding.kayitName.getText().toString();
        String kayitSurname = binding.kayitSurname.getText().toString();
        String editTextEmailAddress = binding.editTextEmailAddress.getText().toString();
        String kayitSifre = binding.kayitSifre.getText().toString();

      //  Toast.makeText(getApplicationContext(),"Başarıyla kayıt oldunuz, Hoşgeldiniz. ",Toast.LENGTH_LONG).show();

        try {

            database = this.openOrCreateDatabase("Kayit",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS kayit (id INTEGER PRIMARY KEY,kayitName VARCHAR, kayitSurname VARCHAR, editTextEmailAddress VARCHAR, kayitSifre VARCHAR)");

            String sqlString = "INSERT INTO kayit (kayitName, kayitSurname, editTextEmailAddress, kayitSifre) VALUES (?, ?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,kayitName);
            sqLiteStatement.bindString(2,kayitSurname);
            sqLiteStatement.bindString(3,editTextEmailAddress);
            sqLiteStatement.bindString(4,kayitSifre);
            sqLiteStatement.execute();


        } catch (Exception e) {

        }
         Toast.makeText(getApplicationContext(),"Başarıyla kayıt oldunuz, Hoşgeldiniz. ",Toast.LENGTH_LONG).show();
        startActivity(new Intent(KayitOlma.this,MainActivity.class));


    }
}