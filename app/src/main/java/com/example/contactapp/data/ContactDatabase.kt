package com.example.contactapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database( entities =  [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object{

        @Volatile
        private var _INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            return _INSTANCE ?: synchronized(lock = this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact_database"

                ).build()

                _INSTANCE = instance

                instance
            }
        }


    }
}
