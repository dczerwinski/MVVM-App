package com.example.mvvmapp.simple_mvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mvvmapp.R

class EditActivity : AppCompatActivity(){

    private var TAG = "EditActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_edit)
        super.onCreate(savedInstanceState)

        Log.d(TAG,"onCreate")

        val title = intent.extras!!["name"].toString()
        val T_id = intent.extras!!["T_id"] as Int

        val mTextView: TextView = findViewById(R.id.dczerwinski_tv_edit)
        val mSaveButton: Button = findViewById(R.id.dczerwinski_button_edit)
        val mEditText: EditText = findViewById(R.id.dczerwinski_et_edit)
        val mDeleteButton: Button = findViewById(R.id.dczerwinski_delete_button)


        mTextView.text = "$title Edit room"
        mEditText.setText(title)
        val intent = Intent()

        mDeleteButton.setOnClickListener{v->
            Log.d(TAG,"mDeleteButton.setOnClickListener")
            intent.putExtra("ew_name",mEditText.text)
            intent.putExtra("T_id",T_id)
            intent.putExtra("DELETETHIS",1)
            setResult(Activity.RESULT_OK,intent)
            Toast.makeText(v.context,"Deleted",Toast.LENGTH_SHORT).show()
            finish()
        }

        mSaveButton.setOnClickListener{v->
            Log.d(TAG,"mSaveButton.setOnClickListener")
            intent.putExtra("new_name",mEditText.text)
            intent.putExtra("T_id",T_id)
            setResult(Activity.RESULT_OK,intent)
            Toast.makeText(v.context,"Saved",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
