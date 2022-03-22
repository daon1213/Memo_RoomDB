package com.daon.memo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_memo.view.*


class MyAdapter(val context : Context,
                var list : List<MemoEntity>,
                var onDeleteListener: OnDeleteListener) : RecyclerView.Adapter<MyAdapter.MyViewHolder> (){

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val memo = itemView.textview_memo
        val root = itemView.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_memo,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val memo = list[position]

        holder.memo.text = memo.memo
        holder.root.setOnClickListener(object : View.OnLongClickListener, View.OnClickListener {
            override fun onLongClick(p0: View?): Boolean {
                onDeleteListener.onDeleteListener(memo)
                return true
            }

            override fun onClick(p0: View?) {

            }
        })
    }

    override fun getItemCount(): Int {
       return list.size
    }
}