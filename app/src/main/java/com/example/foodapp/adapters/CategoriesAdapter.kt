package com.example.foodapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.databinding.CategoryItemBinding
import com.example.foodapp.fragments.HomeFragment.Companion.CATEGORY_NAME
import com.example.foodapp.pojo.Categories
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryList

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var categoriesList = ArrayList<Category>()

    class CategoriesViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val currentItem = categoriesList[position]
        Glide.with(holder.itemView.context).load(currentItem.strCategoryThumb)
            .into(holder.binding.ivCategory)
        holder.binding.tvCategoryNameHome.text = currentItem.strCategory

        holder.binding.ivCategory.setOnClickListener {
            val intent = Intent(holder.itemView.context,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,currentItem.strCategory)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun setList(listItem : Category){
        categoriesList.add(listItem)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}