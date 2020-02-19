package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.api.rest.MetromobiliteService
import com.example.myapplication.api.rest.RetrofitManager
import fr.clerc.myapplication.kotlin.StudentContent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var metromobiliteService: MetromobiliteService?

    init {
        metromobiliteService =
            RetrofitManager.getInstance()?.create(MetromobiliteService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        student_recycler_view.layoutManager = LinearLayoutManager(this)

        val adapter = Adapter(StudentContent.ITEMS) { student ->
            run {
                val intent = Intent(this, StudentDetailActivity::class.java)
                intent.putExtra("student", student)
                startActivity(intent)
            }
        }
        student_recycler_view.adapter = adapter

        val res =
            metromobiliteService?.getPointsVente("agenceM", "Agence de Mobilité StationMobile")
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ res -> println(res) },
                    { error -> println(error.stackTrace) })


    }
}
