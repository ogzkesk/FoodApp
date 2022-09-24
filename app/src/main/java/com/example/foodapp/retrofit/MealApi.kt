package com.example.foodapp.retrofit

import com.example.foodapp.pojo.Categories
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<MealList>

    @GET("lookup.php")
    fun getMealFromId(
        @Query("i")id:String
    ) : Call<MealList>

    @GET("filter.php")
    fun getCategoryList(
        @Query("c")category: String
    ) : Call<CategoryList>

    @GET("categories.php")
    fun getCategories() : Call<Categories>

    @GET("filter.php")
    fun getMealsByCategory(
        @Query("c") categoryName:String
    ) : Call<CategoryList>

    @GET("search.php")
    fun getMealsBySearch(
        @Query("s")name:String
    ) : Call<MealList>
}