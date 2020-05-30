package com.example.mvvmapp.simple_mvvm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmapp.simple_mvvm.DataBase.MyThingsInRoom
import com.example.mvvmapp.simple_mvvm.DataBase.MyViewModel
import com.example.mvvmapp.R


class ThingsListActivity : AppCompatActivity() {

    private lateinit var deviceViewModel: MyViewModel
    private val mNewDeviceAddActivityCode = 2
    private var currentRoomId: Int = -1
    private val mEditDeviceActivityCode = 4
    private var TAG = "ThingsListActivity"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_things_list)

        Log.d(TAG,"onCreate")

        val mAddDeviceButton: Button = findViewById(R.id.dczerwinski_button2)
        mAddDeviceButton.setOnClickListener{
            Log.d(TAG,"mAddDeviceButton.setOnClickListener")
            val intent = Intent(this, AddActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivityForResult(intent,mNewDeviceAddActivityCode)
        }



        val title = intent.extras!!["room_name"].toString()
        currentRoomId = intent.extras!!["room_id"] as Int

        Log.d(TAG,currentRoomId.toString())

        val mTextView: TextView = findViewById(R.id.dczerwinski_tv_device_lsit)

        mTextView.text = "$title Things List"

        val mRecyclerView: RecyclerView = findViewById(R.id.dczerwinski_recycler_view2)
        val mRecyclerAdapter: RecyclerAdapter<MyThingsInRoom> =
            RecyclerAdapter()

        deviceViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        deviceViewModel.allDevices.observe(this, Observer { devices ->
            devices.let{
                mRecyclerAdapter.setList(it)
            }
        })
        mRecyclerAdapter.setRequestCode(mEditDeviceActivityCode)
        mRecyclerAdapter.setRoomId(currentRoomId)
        val mLinearLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mRecyclerAdapter
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        Log.d(TAG,"onActivityResult")

        if (resultCode == Activity.RESULT_OK && requestCode == mNewDeviceAddActivityCode) {
            val device = MyThingsInRoom(intentData!!.extras!!.get("new_name").toString(),currentRoomId)
            deviceViewModel.insertThing(device)
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == mEditDeviceActivityCode){
            val new_device_name = intentData!!.extras!!.get("new_name").toString()
            val device_id = intentData.extras!!.get("T_id") as Int
            if(intentData.extras!!.get("DELETETHIS") != null){
                deviceViewModel.deleteThing(device_id)
            }
            else {
                deviceViewModel.updateThing(device_id, new_device_name)
            }
        }
        super.onActivityResult(requestCode, resultCode, intentData)
    }
}
