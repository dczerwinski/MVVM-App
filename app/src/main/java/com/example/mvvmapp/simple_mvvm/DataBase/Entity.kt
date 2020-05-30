package com.example.mvvmapp.simple_mvvm.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "rooms_table")
data class MyRoom(
    @ColumnInfo(name = "room_name") var room_name: String
){
    @PrimaryKey(autoGenerate = true)
    var room_id: Int = 0
}

@Entity(tableName = "things_table",foreignKeys = arrayOf(
    ForeignKey(
        entity = MyRoom::class,
        parentColumns = arrayOf("room_id"),
        childColumns = arrayOf("id_room"),
        onDelete = ForeignKey.CASCADE
    )
))
data class MyThingsInRoom(
    @ColumnInfo(name = "thing_name") var thing_name: String,
    @ColumnInfo(name = "id_room") var id_room: Int
){
    @PrimaryKey(autoGenerate = true)
    var thing_id: Int = 0
}

