package com.example.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.di.net.main.ApiExecutor
import com.example.livescoredemo.R
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     *
     *
     *  Just For Testing
     *
     * */

    @Inject
    lateinit var apiExecutor: ApiExecutor

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn).setOnClickListener {
            disposables.add(
                apiExecutor.getStatus()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Timber.d(it.toString())
                    }, {
                        Timber.d(it)
                    })
            )
        }
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}

