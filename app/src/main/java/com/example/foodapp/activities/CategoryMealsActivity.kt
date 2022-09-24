package com.example.foodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapters.CategoryMealsAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.fragments.HomeFragment.Companion.CATEGORY_NAME
import com.example.foodapp.viewmodel.CategoryMealsViewModel
import java.util.*

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel : CategoryMealsViewModel
    private var categoryName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(CATEGORY_NAME)){
            categoryName = intent.getStringExtra(CATEGORY_NAME)!!
        }

        binding.tvCategoryCount.text = categoryName
        categoryMealsViewModel = ViewModelProvider(this).get(CategoryMealsViewModel::class.java)
        categoryMealsViewModel.getMealsByCategory(categoryName)
        observeCategoryMeals()

    }

    private fun observeCategoryMeals() {
        categoryMealsViewModel.observeCategoryName().observe(this){
            categoryList ->
            categoryList?.let {
                val listOfMeals = categoryList.meals
                val adapter = CategoryMealsAdapter()
                binding.rvMeals.layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
                adapter.setMealsList(listOfMeals)
                binding.rvMeals.adapter = adapter

            }

        }
    }
}