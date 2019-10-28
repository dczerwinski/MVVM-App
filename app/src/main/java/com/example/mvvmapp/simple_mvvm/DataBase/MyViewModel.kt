package com.example.mvvmapp.simple_mvvm.DataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val allRooms: LiveData<List<MyRoom>>
    val allDevices: LiveData<List<MyThingsInRoom>>
    private val TAG = "MyViewModel"

    init {
        val entityDao = EntityDatabase.getDatabase(application,viewModelScope).entityDao()
        repository = Repository(entityDao)
        allRooms = repository.allRooms
        allDevices = repository.allDevices
    }

    fun insertRoom(room: MyRoom) = viewModelScope.launch {
        repository.insertRoom(room)
    }

    fun insertThing(thing: MyThingsInRoom) = viewModelScope.launch {
        repository.insertThing(thing)
    }

    fun deleteRoom(room_id: Int) = viewModelScope.launch {
        repository.deleteThingsByRoomId(room_id)
        repository.deleteRoom(room_id)
    }

    fun updateRoom(room_id: Int,room_name: String) = viewModelScope.launch {
        repository.updateRoom(room_id, room_name)
    }

    fun updateThing(thing_id: Int, thing_name: String) = viewModelScope.launch {
        repository.updateThing(thing_id, thing_name)
    }

    fun deleteThing(thing_id: Int) = viewModelScope.launch {
        repository.deleteThing(thing_id)
    }
}
