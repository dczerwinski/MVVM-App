package com.example.mvvmapp.simple_mvvm.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [MyRoom::class, MyThingsInRoom::class],version = 1)
abstract class EntityDatabase: RoomDatabase() {
    abstract fun entityDao(): EntityDao

    companion object{
        @Volatile
        private var INSTANCE: EntityDatabase? = null
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): EntityDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntityDatabase::class.java,
                    "my_database"
                ).addCallback(RoomDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }

        private class RoomDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database->
                    scope.launch{
                        val list: List<MyRoom> = listOf(
                            MyRoom("Living room"), MyRoom("Baby Room")
                        )

                        val list_things: List<MyThingsInRoom> = listOf(
                            MyThingsInRoom("SomeThing1",2), MyThingsInRoom("SomeThing2",2),
                            MyThingsInRoom("NoteBook",1), MyThingsInRoom("IDK",1)
                        )
                        Thread(Runnable {
                            database.entityDao().insertAllRooms(list)
                            database.entityDao().insertAllThings(list_things)

                        }).start()
                    }
                }
            }
        }

    }
}