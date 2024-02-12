package com.application.traverldiary.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlbumViewModel:ViewModel(){

    // 创建一个MutableLiveData对象来存储你的数据
    var albumList: MutableLiveData<List<Any>> = MutableLiveData()

    // 更新数据
    fun updateData(newData: List<Any>) {
        albumList.value = newData
    }
}