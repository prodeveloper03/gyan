package com.example.gyan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class AnswerAct : AppCompatActivity() {

    lateinit var engtxt : TextView;
    lateinit var hintxt : TextView;

    lateinit var engWord: String;
    lateinit var dbHelp: DbHelp;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        engtxt = findViewById(R.id.txteng)
        hintxt = findViewById(R.id.txthind)


       engWord = intent.getStringExtra("engword")!!;

        dbHelp = DbHelp(this,"database.db",2)

        fetchAns()
    }

    fun fetchAns(){

        var ans =  dbHelp.getHindAns(engWord);
        hintxt.setText(ans);
        engtxt.setText(engWord);


    }



}