package com.example.mvvmapp.simple_mvvm.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface EntityDao {
    //Lists
    @Query("select * from  rooms_table order by room_name asc")
    fun getAllRooms(): LiveData<List<MyRoom>>

    @Query("select * from things_table order by thing_name asc")
    fun getAllThings(): LiveData<List<MyThingsInRoom>>

    @Query("select * from things_table order by thing_name asc")
    fun getAllThingsRx(): Flowable<MutableList<MyThingsInRoom>>

    @Query("select * from things_table where id_room like :id_room order by thing_name asc")
    fun getAllThingsByRoomId(id_room: Int): LiveData<List<MyThingsInRoom>>

    //Update
    @Query("update rooms_table set room_name = :room_name where room_id like :room_id")
    fun updateRoomById(room_id: Int,room_name: String)

    @Query("update things_table set thing_name = :thing_name where thing_id like :thing_id")
    fun updateThingsById(thing_id: Int, thing_name: String)

    //Inserts
    @Insert
    fun insertRoom(room: MyRoom)

    @Insert
    fun insertAllRooms(room_list: List<MyRoom>)

    @Insert
    fun insertThing(device: MyThingsInRoom)

    @Insert
    fun insertAllThings(device_list: List<MyThingsInRoom>)

    //deleters
    @Query("delete from rooms_table")
    fun deleteAllRooms()

    @Query("delete from rooms_table where room_id like :room_id")
    fun deleteRoomById(room_id: Int)

    @Query("delete from things_table where thing_id like :thing_id")
    fun deleteThingById(thing_id: Int)

    @Query("delete from things_table")
    fun deleteAllThings()

    @Query("delete from things_table where id_room like :id_room")
    fun deleteThingsByRoomId(id_room: Int)
}