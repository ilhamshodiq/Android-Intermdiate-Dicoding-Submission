package com.the12smb.submissionstoryapp.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.the12smb.submissionstoryapp.R
import com.the12smb.submissionstoryapp.databinding.ActivityMainBinding
import com.the12smb.submissionstoryapp.view.add.AddStoryActivity
import com.the12smb.submissionstoryapp.view.login.LoginActivity
import com.the12smb.submissionstoryapp.view.main.MainViewModel.Companion.TAG
import com.the12smb.submissionstoryapp.view.maps.MapsActivity

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactoryMain(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        getData()
        setupAction()
    }

    private fun setupAction() {
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvContent.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvContent.addItemDecoration(itemDecoration)

        mainViewModel.getName().observe(this) {
            supportActionBar?.title = "Hii, $it"
        }

    }

    private fun getData() {
        val adapter = StoriesAdapter()
        binding.rvContent.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.getToken().observe(this) { token ->
            mainViewModel.getStory(token).observe(this) {
                adapter.submitData(lifecycle, it)
                Log.d(TAG, "jumlah paging adapternya ada = " + adapter.itemCount.toString())
            }
        }
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
        } else if (item.itemId == R.id.map) {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}