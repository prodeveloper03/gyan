package com.example.gyan

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream
import java.lang.Exception

class DbHelp(context: Context,name: String, version: Int) :
    SQLiteOpenHelper(context,name,null,version) {

        var mcontext: Context;
        var dbName : String;
        var dbPath: String;


    init {
        mcontext = context
        dbName = name
        dbPath = " /data/data/" + " com.example.gyan" + "/database/";
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        //TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
       // TODO("Not yet implemented")
    }

    fun checkDb() {
        try {
            var filePath = mcontext.getDatabasePath(dbName);

            if (!filePath.exists()) copyDatabase()
            else Log.d("check the database","database alreary avialable")
    }catch (e: Exception){
        e.stackTrace
        }

    }

    fun copyDatabase() {

        this.readableDatabase;
        Log.d("copydb","creating a new database from the asset folder");
        //create a folder asset in the main diectory and copy the db file in that
        //db copied

        try {
            var ios = mcontext.assets.open(dbName);
            var os = FileOutputStream(dbPath+dbName);

            var buffer  = ByteArray(1024)
            var len = ios.read(buffer);

            while ((len)>0){
                os.write(buffer,0,len)
                len  = ios.read(buffer);

            }
            ios.close()
            os.close()
            os.flush()

        }catch (e: Exception){
            e.stackTrace
        }
        Log.d("copydb","database copied");
    }

    fun searchsugg(query : String): ArrayList<String>{

        var tableName = "primary_word"
        var colName = "_from"

        var list = ArrayList<String>();
        var db = this.readableDatabase;

        var cursor = db.query(

                tableName,
                arrayOf(colName),
                colName + "LIKE ?",
                arrayOf(query + "%"),
                null,null,
                colName


        )

        var index = cursor.getColumnIndex(colName)

        while (cursor.moveToNext()){

            list.add(cursor.getString(index))
        }

        cursor.close()
        return list;
        
    }

    fun getHindAns (query: String):String{

        var db = this.readableDatabase;
        var hindiWord = "";
        var cursor = db.rawQuery("select * from primary_word where _from  = '${query}",null,null);

        while (cursor.moveToNext()){

            hindiWord = cursor.getString(cursor.getColumnIndex("_to"));

        }
        return hindiWord;
    }




}