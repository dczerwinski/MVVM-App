package com.example.mvvmapp.simple_mvvm

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmapp.R
import com.example.mvvmapp.simple_mvvm.DataBase.MyThingsInRoom
import com.example.mvvmapp.simple_mvvm.DataBase.MyRoom

class RecyclerAdapter<T> : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    private var list: ArrayList<T> = arrayListOf()
    private var room_id: Int = -1
    private var requestCode: Int = -1
    private var TAG = "RecyclerAdapter"

    fun setRequestCode(requestCode: Int){
        Log.d(TAG,"setRequestCode")
        this.requestCode = requestCode
    }

    fun setList(list: List<T>){
        Log.d(TAG,"setList")
        this.list = arrayListOf()
        if(room_id > 0){
            val filter = list.filter {
                (it as MyThingsInRoom).id_room == room_id
            }
            this.list.addAll(filter)
        }
        else{
            this.list.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun setRoomId(room_id: Int){
        Log.d(TAG,"setRoomId")
        this.room_id = room_id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d(TAG,"onCreateViewHolder")
        val inflater: LayoutInflater? = LayoutInflater.from(parent.context)
        val view: View = inflater!!.inflate(R.layout.item_recylcer_view_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG,"getItemCount")
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG,"onBindViewHolder")
        when(list[position]){
            is MyThingsInRoom -> {
                holder.mTextView.text = (list[position] as MyThingsInRoom).thing_name
                holder.mButton.setOnClickListener { v ->
                    val intent = Intent(v!!.context, EditActivity::class.java)
                    intent.putExtra("room_name",holder.mTextView.text.toString())
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    v.context.startActivity(intent)
                }
            }
            is MyRoom -> {
                holder.mTextView.text = (list[position] as MyRoom).room_name
                holder.mButton.setOnClickListener { v->
                    val intent = Intent(v!!.context, EditActivity::class.java)
                    intent.putExtra("room_name",(list[position] as MyRoom).room_name)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    v.context.startActivity(intent)
                }
            }
        }

        holder.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"holder.setOnClickListener")
            val intent = Intent(it!!.context, EditActivity::class.java)
            intent.putExtra("name",holder.mTextView.text)
            if(list[position] is MyRoom)intent.putExtra("T_id",(list[position] as MyRoom).room_id)
            if(list[position] is MyThingsInRoom)intent.putExtra("T_id",(list[position] as MyThingsInRoom).thing_id)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            (it.context as Activity).startActivityForResult(intent,requestCode)
        })


        if(list[position] is MyRoom){
            holder.getItemView().setOnClickListener { v ->
                Log.d(TAG,"holder.getItemView().setOnClickListener ")
                val intent = Intent(v!!.context, ThingsListActivity::class.java)
                intent.putExtra("room_name",holder.mTextView.text)
                intent.putExtra("room_id",(list[position] as MyRoom).room_id)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                v.context.startActivity(intent)
            }
        }
    }

    class MyViewHolder : RecyclerView.ViewHolder{
        private var view: View
        var mTextView: TextView
        var mButton: Button
        private var TAG = "MyViewHolder"

        constructor(itemView: View) : super(itemView) {
            Log.d(TAG,"constructor")
            this.view = itemView
            mTextView = itemView.findViewById(R.id.dczerwinski_name_of_item)
            mButton = itemView.findViewById(R.id.dczerwinski_edit_button)
        }

        fun setOnClickListener(listener: View.OnClickListener){
            Log.d(TAG,"setOnClickListener")
            mButton.setOnClickListener(listener)
        }

        fun getItemView(): View {
            Log.d(TAG,"getItemView")
            return this.view
        }
    }
}