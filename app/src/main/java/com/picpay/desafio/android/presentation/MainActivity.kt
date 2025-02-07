package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.presentation.binding.UserListAdapter
import com.picpay.desafio.android.presentation.compose.UsersScreenComposable
import com.picpay.domain.screen.ScreenStatus
import desafio_android.databinding.ActivityMainBinding
import desafio_android.databinding.ActivityMainComposeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var composeBinding: ActivityMainComposeBinding
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView

    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        composeBinding = ActivityMainComposeBinding.inflate(layoutInflater)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = this@MainActivity.viewModel

        setContentView(composeBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initObservers()
        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding) {
        recyclerView = binding.recyclerView
        progressBar = binding.userListProgressBar

        adapter = UserListAdapter()
        recyclerView.adapter = adapter

    }

    private fun initObservers() {
        with(viewModel) {
            screen.observe(this@MainActivity) {
                composeBinding.cv.setContent {
                    UsersScreenComposable(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                            .background(color = Color.Black),
                        screen = it
                    )
                }

                it.userList?.let { list ->
                    adapter.users = list
                }

                progressBarVisibility(it.status)
            }
        }
    }

    private fun progressBarVisibility(status: ScreenStatus) {
        progressBar.visibility = if (status.isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
