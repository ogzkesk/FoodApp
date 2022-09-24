package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.MealX

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.MealsViewHolder>() {

    private var list = ArrayList<MealX>()

    class MealsViewHolder(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val binding = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        val currentMeal = list[position]

        Glide.with(holder.itemView.context).load(currentMeal.strMealThumb)
            .into(holder.binding.ivMealsPic)
        holder.binding.tvMaelName.text = currentMeal.strMeal

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setMealsList(newList: List<MealX>) {
        list = newList as ArrayList<MealX>
        notifyDataSetChanged()
    }
}