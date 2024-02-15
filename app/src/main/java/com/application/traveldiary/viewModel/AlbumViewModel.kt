package com.application.traveldiary.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.application.traveldiary.models.Picture

class AlbumViewModel:ViewModel(){

    // 创建一个MutableLiveData对象来存储你的数据
    var albumList: MutableLiveData<List<Any>> = MutableLiveData()
    //传入到pictureFragment的uri
    var picture: Picture? = null

    // 更新数据
    fun updateData(newData: List<Any>) {
        albumList.value = newData
    }
}

