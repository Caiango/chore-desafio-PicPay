package com.picpay.desafio.android.binding

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.domain.user.User
import com.squareup.picasso.Picasso
import desafio_android.R
import desafio_android.databinding.ListItemUserBinding

class UserListItemViewHolder(
    private val binding: ListItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {

        binding.user = user

        binding.progressBar.visibility = View.VISIBLE

        Picasso.get()
            .load(user.img)
            .error(R.drawable.ic_round_account_circle)
            .into(binding.picture, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    binding.progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    binding.progressBar.visibility = View.GONE
                }
            })
    }
}
