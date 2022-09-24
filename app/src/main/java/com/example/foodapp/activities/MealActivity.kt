package com.example.foodapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.fragments.HomeFragment.Companion.MEAL_ID
import com.example.foodapp.fragments.HomeFragment.Companion.MEAL_NAME
import com.example.foodapp.fragments.HomeFragment.Companion.MEAL_THUMB
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewmodel.MealViewModel
import com.example.foodapp.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMealBinding
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var mealMvvm : MealViewModel
    private lateinit var mealUrl : String
    private var mealToSave : Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingCase()
        if(intent.hasExtra(MEAL_ID)){
            getMealInformationFromIntent()
        }


        val mealDatabase = MealDatabase.getDatabase(this)
        val mealViewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this,mealViewModelFactory).get(MealViewModel::class.java)


        setInformationViews()
        mealMvvm.getMeal(mealId)
        mealMvvm.observeMealLiveData().observe(this){
            it?.let {
                mealToSave = it
                onResponseCase()
                binding.tvCategoryDetails.text = "Category : ${it.strCategory}"
                binding.tvAreaDetails.text = "Area : ${it.strArea}"
                binding.tvInstructions.text = it.strInstructions
                mealUrl = it.strYoutube!!
            }
        }

        onYoutubeImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.fabFav.setOnClickListener {
            if(mealToSave != null){
                mealMvvm.insertMeal(mealToSave!!)
                Toast.makeText(this,"Meal Saved",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun onYoutubeImageClick() {
        binding.ivVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealUrl))
            startActivity(intent)
        }
    }

    private fun setInformationViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding.ivCollapsing)
        binding.collapsingToolbar.title = mealName

    }


    private fun getMealInformationFromIntent() {
        mealId = intent.getStringExtra(MEAL_ID) as String
        mealName = intent.getStringExtra(MEAL_NAME) as String
        mealThumb = intent.getStringExtra(MEAL_THUMB) as String
    }

    private fun loadingCase(){
        binding.fabFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvAreaDetails.visibility = View.INVISIBLE
        binding.tvCategoryDetails.visibility = View.INVISIBLE
        binding.ivVideo.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun onResponseCase(){
        binding.fabFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvAreaDetails.visibility = View.VISIBLE
        binding.tvCategoryDetails.visibility = View.VISIBLE
        binding.ivVideo.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE

    }
}