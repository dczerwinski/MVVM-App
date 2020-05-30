package com.example.mvvmapp.simple_mvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmapp.R
import com.example.mvvmapp.simple_mvvm.DataBase.MyRoom
import com.example.mvvmapp.simple_mvvm.DataBase.MyViewModel

class RoomListActivity : AppCompatActivity(){

    private lateinit var myViewModel: MyViewModel
    private val mNewRoomAddActivityCode = 1
    private val mEditRoomActivityCode = 3
    private var TAG = "RoomListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)

        Log.d(TAG,"onCreate")

        val mRecyclerView: RecyclerView = findViewById(R.id.dczerwinski_recycler_view)
        val mAddRoomButton: Button = findViewById(R.id.dczerwinski_add_room_btn)
        mAddRoomButton.setOnClickListener{
            Log.d(TAG,"mAddRoomButton.setOnClickListener")
            val intent = Intent(this, AddActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivityForResult(intent,mNewRoomAddActivityCode)
        }


        val mRecyclerAdapter: RecyclerAdapter<MyRoom> =
            RecyclerAdapter()

        myViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)

        val mLinearLayoutManager = LinearLayoutManager(this)
        mRecyclerAdapter.setRequestCode(mEditRoomActivityCode)
        myViewModel.allRooms.observe(this, Observer { rooms ->
            rooms?.let {
                mRecyclerAdapter.setList(it)
            }
        })

        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mRecyclerAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        Log.d(TAG,"onActivityResult")

        if (resultCode == Activity.RESULT_OK && requestCode == mNewRoomAddActivityCode) {
            val room = MyRoom(intentData!!.extras!!.get("new_name").toString())
            myViewModel.insertRoom(room)
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == mEditRoomActivityCode){
            val new_room_name = intentData!!.extras!!.get("new_name").toString()
            val room_id = intentData.extras!!.get("T_id") as Int
            if(intentData.extras!!.get("DELETETHIS") != null){
                myViewModel.deleteRoom(room_id)
            }
            else {
                myViewModel.updateRoom(room_id, new_room_name)
            }
        }
        super.onActivityResult(requestCode, resultCode, intentData)
    }

}
