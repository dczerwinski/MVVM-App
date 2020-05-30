package com.example.mvvmapp.simple_mvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mvvmapp.R

class AddActivity : AppCompatActivity() {

    private var TAG = "AddActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)


        Log.d(TAG,"onCreate")

        val mAddButton: Button = findViewById(R.id.dczerwinski_button_add)
        val mName: EditText = findViewById(R.id.dczerwinski_et_add)

        mAddButton.setOnClickListener{
            Log.d(TAG,"mAddButton.setOnClickListener")
            val intent = Intent()
            if(mName.text.isEmpty()){
                Toast.makeText(it.context,"Name is empty!",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(it.context,"New Room Added!",Toast.LENGTH_SHORT).show()
                intent.putExtra("new_name",mName.text)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}
