package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.api.rest.MetromobiliteService
import com.example.myapplication.api.rest.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var adapter: Adapter? = null
    var metromobiliteService: MetromobiliteService =
        RetrofitManager.getInstance().create(MetromobiliteService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        setContentView(R.layout.activity_main)

        student_recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = initAdapter()
        initAllPointsVente()
    }

    private fun initAllPointsVente(): Disposable? {
        return metromobiliteService.getPointsVente("agenceM", null)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res ->
                    adapter?.features = res.featureList
                    adapter?.notifyDataSetChanged()
                },
                { error -> println("ERROR$error") }
            )
    }

    private fun initAdapter(): Adapter {
        val adapter = Adapter(listOf()) { feature ->
            run {
                val intent = Intent(this, AgenceDetailActivity::class.java)
                intent.putExtra("feature", feature)
                startActivity(intent)
            }
        }
        student_recycler_view.adapter = adapter
        return adapter
    }
}
