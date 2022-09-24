package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Categories
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularMealLiveData = MutableLiveData<CategoryList>()
    private var categoriesLiveData = MutableLiveData<Categories>()
    private var mealById = MutableLiveData<Meal>()
    private var searchMealsLiveData = MutableLiveData<List<Meal>>()
    private var favoritesMealLiveData = mealDatabase.mealDao().getAllMeals()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful && response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment Error", "${t.message}")
            }

        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getCategoryList("Seafood").enqueue(object: Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body() != null){
                    popularMealLiveData.value = response.body()
                } else {
                    Log.e("HomeViewModel",response.errorBody().toString())
                }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }
        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<Categories>{
            override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                response.body()?.let { categories ->
                    categoriesLiveData.postValue(categories)
                }
            }

            override fun onFailure(call: Call<Categories>, t: Throwable) {
               // todo
            }
        })
    }

    fun searchMealsByName(name:String) {
        RetrofitInstance.api.getMealsBySearch(name).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    searchMealsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                // todo nothing.
            }
        })
    }

    fun observeSearchMealList() : LiveData<List<Meal>>{
        return searchMealsLiveData
    }

    fun observePopularLiveData() : LiveData<CategoryList>{
        return popularMealLiveData
    }

    fun observeRandomMealLiveData() : LiveData<Meal>{
        return randomMealLiveData
    }

    fun observeCategoriesLiveData() : LiveData<Categories>{
        return categoriesLiveData
    }

    fun observeFavoriteMealsLiveData() : LiveData<List<Meal>>{
        return favoritesMealLiveData
    }


    // DB ->


    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun getMealById(id:String){
        RetrofitInstance.api.getMealFromId(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val meal = response.body()!!.meals.first()
                    mealById.value = meal
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                // todo NOTGHINGGG
            }
        })
    }

    fun observeMealById() : LiveData<Meal>{
        return mealById
    }
}