package com.daon.memo

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity(), OnDeleteListener{

    lateinit var db : MemoDatabase
    var memoList = listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MemoDatabase.getInstance(this)!!

        button_add.setOnClickListener {
            val memo = MemoEntity(null,edittext_memo.text.toString())
            edittext_memo.setText("")
            insertMemo(memo)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        getAllMemos()

    }

    // 1. Insert Data
    // 2. Get Data
    // 3. Delete Data
    // 4. Set RecyclerView

    fun insertMemo(memo : MemoEntity){

        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg p0: Unit?) {
                db.memoDAO().insert(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }
        insertTask.execute()
    }

    fun getAllMemos(){
        val getTask = (object : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg p0: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                SetRecyclerView(memoList)
            }
        }).execute()
    }

    fun deleteMemo(memo: MemoEntity){
        val deleteTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg p0: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }
    }

    fun SetRecyclerView(memoList: List<MemoEntity>){
        recyclerView.adapter = MyAdapter(this,memoList, this)
    }

    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
    }
}