package com.websarva.wings.android.asynctestcoroui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.eclipsesource.json.Json
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    val URL =
        "http://weather.livedoor.com/forecast/webservice/json/v1?city=400040" //サンプルとしてライブドアのお天気Webサービスを利用します
    var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getButton = findViewById(R.id.getButton) as Button
        getButton.setOnClickListener(object : View.OnClickListener {
            override
            fun onClick(view: View) {
                onParallelGetButtonClick()
            }
        })
    }

    //非同期処理でHTTP GETを実行します。
    fun onParallelGetButtonClick() = GlobalScope.launch(Dispatchers.Main) {
        val http = HttpUtil()
        //Mainスレッドでネットワーク関連処理を実行するとエラーになるためBackgroundで実行
        async(Dispatchers.Default) { http.httpGet(URL) }.await().let {
            //minimal-jsonを使って　jsonをパース
            val result = Json.parse(it).asObject()
            val textView = findViewById(R.id.text) as TextView
            textView.setText(result.get("description").asObject().get("text").asString())
        }
    }
}