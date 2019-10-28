package com.example.mvvmapp.simple_mvvm.DataBase

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class Repository(private val entityDao: EntityDao) {

    private lateinit var dao: EntityDao

    val allRooms: LiveData<List<MyRoom>> = entityDao.getAllRooms()
    val allDevices: LiveData<List<MyThingsInRoom>> = entityDao.getAllThings()

    @WorkerThread
    fun insertRoom(room: MyRoom){
        Thread(Runnable {
            entityDao.insertRoom(room)
        }).start()
    }

    @WorkerThread
    fun insertThing(thing: MyThingsInRoom){
        Thread(Runnable {
            entityDao.insertThing(thing)
        }).start()
    }

    @WorkerThread
    fun deleteRoom(room_id: Int){
        Thread(Runnable {
            entityDao.deleteRoomById(room_id)
        }).start()
    }

    @WorkerThread
    fun updateThing(thing_id: Int, thing_name: String){
        Thread(Runnable {
            entityDao.updateThingsById(thing_id,thing_name)
        }).start()
    }

    @WorkerThread
    fun updateRoom(room_id: Int,room_name: String){
        Thread(Runnable {
            entityDao.updateRoomById(room_id,room_name)
        }).start()
    }

    @WorkerThread
    fun deleteThing(thing_id: Int){
        Thread(Runnable {
            entityDao.deleteThingById(thing_id)
        }).start()
    }

    @WorkerThread
    fun deleteThingsByRoomId(id_room: Int){
        Thread(Runnable {
            entityDao.deleteThingsByRoomId(id_room)
        }).start()
    }

    fun getAllThingsRx() = dao.getAllThingsRx()
}