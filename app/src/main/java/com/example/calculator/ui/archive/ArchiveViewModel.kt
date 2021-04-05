package com.example.calculator.ui.archive

import androidx.lifecycle.MutableLiveData
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.database.HistoryDatabase
import com.example.calculator.database.HistoryItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArchiveViewModel(event: BaseViewModelEventListener) : BaseViewModel(event){

    var historyListLiveData: MutableLiveData<List<HistoryItem>> = MutableLiveData()

    fun loadHistory(){
        GlobalScope.launch {
            val history = HistoryDatabase.instance?.historyDao()?.getAll()
            uiScope.launch {
                historyListLiveData.value = history
            }
        }
    }

    fun clearArchive() {
        GlobalScope.launch {
           HistoryDatabase.instance?.historyDao()?.deleteAll()
            uiScope.launch {
                historyListLiveData.value = emptyList()
            }
        }
    }
}