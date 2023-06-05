package com.the12smb.submissionstoryapp.view.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.the12smb.submissionstoryapp.R
import com.the12smb.submissionstoryapp.data.local.model.UserPreference
import com.the12smb.submissionstoryapp.databinding.ActivityDetailBinding
import com.the12smb.submissionstoryapp.view.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
    }

    private fun setupView() {
        supportActionBar?.title = getString(R.string.detail)

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setIdAndToken()

        detailViewModel.detail.observe(this) {
            binding.apply {
                tvDetailName.text = it.name
                tvDetailDescription.text = it.description
                Glide.with(this@DetailActivity)
                    .load(it.photoUrl)
                    .into(ivDetailPhoto)
            }
        }


    }

    private fun setupViewModel() {
        val pref = UserPreference.getInstance(dataStore)
        detailViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[DetailViewModel::class.java]
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setIdAndToken() {
        id = intent.getStringExtra(EXTRA_ID) as String
        detailViewModel.getToken().observe(this) {
            detailViewModel.getDetail(id, it)
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"

    }
}