package com.the12smb.submissionstoryapp.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.the12smb.submissionstoryapp.R
import com.the12smb.submissionstoryapp.data.local.model.UserPreference
import com.the12smb.submissionstoryapp.data.remote.response.ListStoryItem
import com.the12smb.submissionstoryapp.databinding.ActivityMainBinding
import com.the12smb.submissionstoryapp.view.ViewModelFactory
import com.the12smb.submissionstoryapp.view.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setToken()
        setupView()
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvContent.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvContent.addItemDecoration(itemDecoration)

        mainViewModel.listStories.observe(this) { listStory ->
            binding.rvContent.adapter = showRecyclerView(listStory)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        supportActionBar?.title = getString(R.string.story_app)
    }

    private fun setupViewModel() {
        val pref = UserPreference.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]
    }

    private fun setToken() {
        mainViewModel.getToken().observe(this) {
            mainViewModel.getStories(it)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            AlertDialog.Builder(this).apply {
                setTitle("Logout?")
                setMessage("You sure want to logout?")
                setPositiveButton("Yes") { _, _ ->
                    mainViewModel.logout()
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                create()
                show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerView(list: List<ListStoryItem>?): StoriesAdapter {
        val storyList = ArrayList<ListStoryItem>()

        list?.let {
            storyList.clear()
            storyList.addAll(it)
        }

        return StoriesAdapter(storyList)
    }

}