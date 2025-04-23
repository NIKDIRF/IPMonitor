package com.example.ipmonitor.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.ipmonitor.R
import com.example.ipmonitor.databinding.ActivityMainBinding
import com.example.ipmonitor.viewModel.IpViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: IpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.ipButton.setOnClickListener {
            viewModel.fetchIpAddress()
        }

        binding.ipText.setOnClickListener {
            val state = viewModel.uiState.value
            if (state is IpViewModel.UiState.Success) copyToClipboard(state.ip)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is IpViewModel.UiState.Default -> setDefaultState()
                    is IpViewModel.UiState.Success -> setSuccessState(state.ip)
                    is IpViewModel.UiState.Error -> setErrorState(state.message)
                }
            }
        }
    }

    private fun setDefaultState() {
        binding.ipButton.setTextColor(ContextCompat.getColor(this, R.color.blue))

        binding.ipText.visibility = View.GONE
    }

    private fun setSuccessState(ip: String) {
        binding.ipButton.setTextColor(ContextCompat.getColor(this, R.color.green))

        binding.ipText.text = ip
        binding.ipText.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.ipText.visibility = View.VISIBLE
    }

    private fun setErrorState(error: String) {
        binding.ipButton.setTextColor(ContextCompat.getColor(this, R.color.red))

        binding.ipText.text = error
        binding.ipText.setTextColor(ContextCompat.getColor(this, R.color.red))
        binding.ipText.visibility = View.VISIBLE
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("IP Address", text)
        clipboard.setPrimaryClip(clip)
        Snackbar.make(binding.root, "IP скопирован", Snackbar.LENGTH_SHORT).show()
    }
}