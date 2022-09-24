package com.example.foodapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.databinding.PopularItemsBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.fragments.HomeFragment.Companion.MEAL_ID
import com.example.foodapp.fragments.HomeFragment.Companion.MEAL_NAME
import com.example.foodapp.fragments.HomeFragment.Companion.MEAL_THUMB
import com.example.foodapp.fragments.bottomsheet.MealBottomSheetFragment
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.MealX

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.MostViewHolder>() {

    private var mealList = ArrayList<MealX>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostViewHolder {
        return MostViewHolder(
            PopularItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MostViewHolder, position: Int) {
        val currentItem = mealList[position]

        Glide.with(holder.itemView.context).load(currentItem.strMealThumb)
            .into(holder.binding.ivListPopular)

        holder.binding.ivListPopular.setOnClickListener {
            val intent = Intent(holder.itemView.context,MealActivity::class.java)
            intent.putExtra(MEAL_ID,currentItem.idMeal)
            intent.putExtra(MEAL_NAME,currentItem.strMeal)
            intent.putExtra(MEAL_THUMB,currentItem.strMealThumb)
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.ivListPopular.setOnLongClickListener {
            val fragmentManager = FragmentManager.findFragment<HomeFragment>(holder.itemView)
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(currentItem.idMeal)
            mealBottomSheetFragment.show(fragmentManager.childFragmentManager,"Meal info")
            true
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    fun getMealCategoryList(categoryList: List<MealX>) {
        mealList = categoryList as ArrayList<MealX>
        notifyDataSetChanged()
    }

    class MostViewHolder(val binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root)
}