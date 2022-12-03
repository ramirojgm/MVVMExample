package com.example.mvvmexample

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.Visibility
import com.example.mvvmexample.databinding.ActivityMainBinding
import com.example.mvvmexample.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeData()

                binding.buttonAnother.setOnClickListener {
                    mainViewModel.queryAnother()
                }
            }
        }
    }

    private fun observeData() {
        mainViewModel.mainModel.observe(this) { model->
            if (model != null) {
                if (model.errorMessage.isNotEmpty())
                    Toast.makeText(applicationContext,model.errorMessage, Toast.LENGTH_SHORT).show()

                if (model.ready) {
                    binding.textActivity.visibility = View.VISIBLE
                    binding.buttonAnother.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.textActivity.text = model.activity?.activity
                } else {
                    binding.textActivity.visibility = View.GONE
                    binding.buttonAnother.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}