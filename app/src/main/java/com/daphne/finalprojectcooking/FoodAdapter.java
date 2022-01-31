package com.daphne.finalprojectcooking;

import static com.daphne.finalprojectcooking.recList.secilen;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daphne.finalprojectcooking.databinding.RecyclerRowFoodBinding;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder>{

    ArrayList<Food> foodArrayList;
    // ArrayList<Drink>drinkArrayList;

    public FoodAdapter(ArrayList<Food>foodArrayList) {
        this.foodArrayList = foodArrayList;
    }
   /* public FoodAdapter(ArrayList<Drink> drinkArrayList){
        this.drinkArrayList = drinkArrayList;
    }*/


    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowFoodBinding recyclerRowFoodBinding = RecyclerRowFoodBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FoodHolder(recyclerRowFoodBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        holder.binding.recyclerViewText.setText(foodArrayList.get(position).name);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),FoodActivity.class);
                intent.putExtra("foodId",foodArrayList.get(holder.getAdapterPosition()).id);
                intent.putExtra("info","old");
                holder.itemView.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return foodArrayList.size(); // kaç tane eleman varsa listte onu göstericem
    }

    public class FoodHolder extends RecyclerView.ViewHolder{

        private RecyclerRowFoodBinding binding;

        public FoodHolder(RecyclerRowFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
