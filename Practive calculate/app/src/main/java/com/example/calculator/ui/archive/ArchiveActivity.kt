package com.example.calculator.ui.archive

import android.content.Intent
import com.example.calculator.MainActivity
import com.example.calculator.base.BaseActivity
import com.example.calculator.database.HistoryItem
import com.example.calculator.databinding.ActivityArchiveBinding
import com.example.calculator.utils.Constants.KEY_MATH
import com.example.calculator.utils.Constants.KEY_RESULT


class ArchiveActivity : BaseActivity<ActivityArchiveBinding, ArchiveViewModel>(ActivityArchiveBinding::inflate, ArchiveViewModel::class.java) {

    private lateinit var adapter: ArchiveAdapter

    override fun setupView() {
        viewModel.loadHistory()

        adapter = ArchiveAdapter()

        binding.historyArrowBack.setOnClickListener{ finish() }
        binding.archiveClear.setOnClickListener{ viewModel.clearArchive() }

        viewModel.historyListLiveData.observe(this) {
            binding.historyRecyclerview.adapter = adapter
            adapter.historyList = it
            adapter.notifyDataSetChanged()
            binding.historyRecyclerview.scrollToPosition(it.size)
        }

        adapter.addOnItemClick(object : ArchiveAdapter.OnClickListener {
            override fun onClickItem(item: HistoryItem) {
                sendDataFromFigures(item)
            }
        })
    }

    private fun sendDataFromFigures(item: HistoryItem) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(KEY_MATH, item.math)
        intent.putExtra(KEY_RESULT, item.result)
        startActivity(intent)
    }
}

