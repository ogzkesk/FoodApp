package com.example.foodapp.viewmodel

import android.telecom.Call
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel(){


    private var categoryNamesLiveData = MutableLiveData<CategoryList>()

    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<CategoryList>{
            override fun onResponse(
                call: retrofit2.Call<CategoryList>,
                response: Response<CategoryList>
            ) {
                if(response.body() != null){
                    categoryNamesLiveData.value = response.body()
                }
            }

            override fun onFailure(call: retrofit2.Call<CategoryList>, t: Throwable) {
                Log.e("CategoryViewModel",t.message.toString())
            }
        })
    }

    fun observeCategoryName() : LiveData<CategoryList>{
        return categoryNamesLiveData
    }
}