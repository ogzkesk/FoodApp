package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.adapters.CategoriesAdapter
import com.example.foodapp.adapters.MostPopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.fragments.bottomsheet.MealBottomSheetFragment
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.pojo.MealX
import com.example.foodapp.retrofit.RetrofitInstance
import com.example.foodapp.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var adapter : MostPopularAdapter
    private lateinit var categoryAdapter : CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.foodapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.foodapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
        viewModel.getPopularItems()
        observePopularItems()
        viewModel.getCategories()
        observeCategories()
        onSearchIcon()




    }

    private fun onSearchIcon() {
        binding.ivSearchHome.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun observeCategories() {

        categoryAdapter = CategoriesAdapter()
        binding.rvListCategory.layoutManager = GridLayoutManager(requireContext(),3)
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner){   categories ->

            categories.categories.forEach {
                categoryAdapter.setList(it)
                binding.rvListCategory.adapter = categoryAdapter

            }
        }
    }


    private fun observePopularItems() {
        viewModel.observePopularLiveData().observe(viewLifecycleOwner){ list ->
            if(list != null){
                println("${list.meals.get(0)}")
                adapter = MostPopularAdapter()
                adapter.getMealCategoryList(list.meals)
                binding.rvListPopular.adapter = adapter
                binding.rvListPopular.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            } else {
                println("List Boşa attı")
            }
        }
    }

    private fun onRandomMealClick() {
        binding.ivRandomMeal.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }

    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner){
            Glide.with(this@HomeFragment)
                .load(it.strMealThumb)
                .into(binding.ivRandomMeal)

            this.randomMeal = it

        }
    }

}