package com.application.traveldiary.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.traveldiary.models.Dynamic
import com.application.traveldiary.models.User
import com.application.traveldiary.utils.CommunityTest
import com.application.traveldiary.utils.ImageUploader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class CommunityViewModel : ViewModel() {

    val dynamics: MutableLiveData<ArrayList<Dynamic>?> = MutableLiveData()
    val currentUser: MutableLiveData<User>  by lazy {
        MutableLiveData(CommunityTest.getUser())
    }

    //加载数据
    fun loadDynamic(): ArrayList<Dynamic> {
        //TODO 这是使用的还是预设数据
        dynamics.value = arrayListOf(CommunityTest.getDynamic(0), CommunityTest.getDynamic(1))
        return dynamics.value!!
    }

    //
    fun addDynamic(dynamic: Dynamic) {
        val newDynamics = dynamics.value?.let { ArrayList(it) }
        newDynamics?.add(dynamic)
        // 将新的集合赋值给LiveData
        dynamics.value = newDynamics
    }

    fun uploadPic(uris: List<Uri>, onStart: () -> Unit = {}, onEnd: () -> Unit = {}): ArrayList<String> {
        val list = arrayListOf<String>()
        viewModelScope.launch(Dispatchers.Main) {
            onStart()
            withContext(Dispatchers.IO) {
                val imageUploader = ImageUploader()
                imageUploader.setSuccessCallback {
                    list.add(it)
                }
                uris.forEach {
                    imageUploader.uploadByUri(it)
                }
            }
            onEnd()
        }
        return list
    }

    fun uploadAndGetDynamic(
        uris: List<Uri>,
        title: String,
        content: String,
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {}
    ): Dynamic {
        val pics = uploadPic(uris, onStart, onEnd)
        return Dynamic(
            "",
            title,
            content,
            pics,
            0,
            currentUser.value!!,
            Date(),
            comments = arrayListOf()
        )
    }
}