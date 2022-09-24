package com.example.foodapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapp.pojo.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var instance: MealDatabase? = null
        val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: getDatabase(context).also {
                instance = it
            }
        }

        fun getDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MealDatabase::class.java, "mydb.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}