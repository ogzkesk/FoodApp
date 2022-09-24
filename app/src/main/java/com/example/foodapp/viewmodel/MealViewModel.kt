package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    val mealDatabase: MealDatabase
) : ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()


    fun getMeal(id:String){
        RetrofitInstance.api.getMealFromId(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
               if(response.body() != null){
                   mealDetailsLiveData.value = response.body()!!.meals[0]
               }
                else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("MealViewModel Error",t.message.toString())
            }
        })
    }

    fun observeMealLiveData() : LiveData<Meal>{
        return mealDetailsLiveData
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }


    fun getAllMealFromDb() : LiveData<List<Meal>>{
        return mealDatabase.mealDao().getAllMeals()
    }

}