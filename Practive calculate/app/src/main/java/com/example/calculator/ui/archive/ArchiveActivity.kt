package com.example.calculator.ui.archive

import com.example.calculator.base.BaseActivity
import com.example.calculator.databinding.ActivityArchiveBinding


class ArchiveActivity : BaseActivity<ActivityArchiveBinding, ArchiveViewModel>(ActivityArchiveBinding::inflate, ArchiveViewModel::class.java) {

    private lateinit var adapter: ArchiveAdapter

    override fun setupView() {
        activityViewModel.loadHistory()

        adapter = ArchiveAdapter()

        binding.historyArrowBack.setOnClickListener{ finish() }

        activityViewModel.historyListLiveData.observe(this) {
            binding.historyRecyclerview.adapter = adapter
            adapter.historyList = it
            adapter.notifyDataSetChanged()
            binding.historyRecyclerview.scrollToPosition(it.size)
        }
    }
}

