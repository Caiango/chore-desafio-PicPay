package com.picpay.desafio.android

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.binding.UserListAdapter
import com.picpay.desafio.android.compose.UsersScreenComposable
import desafio_android.databinding.ActivityMainBinding
import desafio_android.databinding.ActivityMainComposeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent, StatusListener {

    private var composeBinding: ActivityMainComposeBinding? = null
    private var binding: ActivityMainBinding? = null

    private var recyclerView: RecyclerView? = null
    private var adapter: UserListAdapter? = null
    private var feedbackButton: Button? = null

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        composeBinding = ActivityMainComposeBinding.inflate(layoutInflater)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding?.viewModel = this@MainActivity.viewModel

        setContentView(binding?.root)

        initObservers()
        binding?.let { initViews(it) }
    }

    private fun initViews(binding: ActivityMainBinding) {
        binding.lifecycleOwner = this

        recyclerView = binding.recyclerView
        feedbackButton = binding.feedback.feedbackButton

        adapter = UserListAdapter()
        recyclerView?.adapter = adapter
        feedbackButton?.setOnClickListener {
            lifecycleScope.launch {
                onButtonClick()
            }
        }

    }

    private fun initObservers() {
        with(viewModel) {
            screen.observe(this@MainActivity) {
                composeBinding?.composeView?.setContent {
                    UsersScreenComposable(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                            .background(color = Color.Black),
                        screen = it,
                        listener = this@MainActivity
                    )
                }

                it.userList?.let { list ->
                    adapter?.users = list
                }
            }
        }
    }


    override suspend fun onButtonClick() {
        viewModel.getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        composeBinding = null
        binding = null
    }

}