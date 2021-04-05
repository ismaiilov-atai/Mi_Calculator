package com.example.calculator.ui.archive

import android.content.Intent
import com.example.calculator.MainActivity
import com.example.calculator.base.BaseActivity
import com.example.calculator.database.HistoryItem
import com.example.calculator.databinding.ActivityArchiveBinding


class ArchiveActivity : BaseActivity<ActivityArchiveBinding, ArchiveViewModel>(ActivityArchiveBinding::inflate, ArchiveViewModel::class.java),
    ArchiveAdapter.OnclickListener {

    companion object {
        val KEY_MATH = "key_math"
        val KEY_RESULT = "key_math"
    }

    private lateinit var adapter: ArchiveAdapter

    override fun setupView() {
        aViewmodel.loadHistory()

        adapter = ArchiveAdapter()

        binding.historyArrowBack.setOnClickListener{ finish() }
        binding.archiveClear.setOnClickListener{ clearArchive() }

        aViewmodel.historyListLiveData.observe(this) {
            binding.historyRecyclerview.adapter = adapter
            adapter.historyList = it
            adapter.notifyDataSetChanged()
            binding.historyRecyclerview.scrollToPosition(it.size)
        }

        adapter.addOnItemClick(this)
    }

    private fun clearArchive() {
        aViewmodel.clearArchive()
    }

    override fun onClickItem(item: HistoryItem) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra(KEY_MATH,item.math)
        intent.putExtra(KEY_RESULT,item.result)
        startActivity(intent)
    }

}

