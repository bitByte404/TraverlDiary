package com.application.traveldiary.viewModel

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.traveldiary.application.BmobApp
import com.application.traveldiary.models.Dynamic
import com.application.traveldiary.models.User
import com.application.traveldiary.utils.CommunityTest
import com.application.traveldiary.utils.ImageUploader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.Date

class CommunityViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        Toast.makeText(BmobApp.getContext(), "被清除", Toast.LENGTH_LONG).show()
    }


    val dynamics: MutableLiveData<ArrayList<Dynamic>?> = MutableLiveData()
    val currentUser: MutableLiveData<User>  by lazy {
        MutableLiveData(CommunityTest.getUser())
    }

    //加载数据
    fun loadDynamic(): ArrayList<Dynamic> {
        //TODO 这是使用的还是预设数据
        if (dynamics.value == null) {
            dynamics.value = arrayListOf(CommunityTest.getDynamic(0), CommunityTest.getDynamic(1))
        }
        return dynamics.value!!
    }

    //
    fun addDynamic(dynamic: Dynamic) {
        dynamics.value?.add(dynamic)
    }

    fun addDynamicFromTop(dynamic: Dynamic) {
        dynamics.value?.add(0, dynamic)
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

                runBlocking {
                    uris.forEach {uri ->
                        launch {
                            imageUploader.uploadByUri(uri)
                        }
                    }
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