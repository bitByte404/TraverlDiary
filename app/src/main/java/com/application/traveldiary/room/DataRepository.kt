package com.application.traveldiary.room

import androidx.room.Room
import com.application.traveldiary.application.BmobApp
import com.application.traveldiary.models.Dynamic
import com.application.traveldiary.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataRepository {
    //数据库对象
    private val database: DynamicDatabase by lazy {
        Room.databaseBuilder(
            BmobApp.getContext(),
            DynamicDatabase::class.java,
            "dynamicdb"
        ).build()
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    companion object {
        val instance = DataRepository()
    }

    //添加一个动态
    fun addDynamic(dynamic: Dynamic) {
        scope.launch {
            database.getDynamicDao().addDynamic(dynamic)
        }
    }

    // 获得所有动态
    fun getAllDynamic(callback: (Boolean, List<Dynamic>) -> Unit) {
        scope.launch {
             val result = database.getDynamicDao().getAll()
            withContext(Dispatchers.Main) {
                callback(result.isEmpty(), result)
            }
        }
    }

    // 获得指定用户的动态
    fun getUserDynamic(user: User, callback: (Boolean, List<Dynamic>) -> Unit) {
        scope.launch {
            val result = database.getDynamicDao().getDynamicsByUser(user)
            withContext(Dispatchers.Main) {
                callback(result.isEmpty(), result)
            }
        }
    }

    // 获得热门动态
    fun getTopLikedDynamics(limit: Int = 5, callback: (Boolean, List<Dynamic>) -> Unit) {
        scope.launch {
            val result = database.getDynamicDao().getTopLikedDynamics(limit)
            withContext(Dispatchers.Main) {
                callback(result.isEmpty(), result)
            }
        }
    }

    // 根据标题或内容检索动态
    fun searchByTitleOrContent(keyword: String, callback: (Boolean, List<Dynamic>) -> Unit) {
        scope.launch {
            val result = database.getDynamicDao().searchByTitleOrContent(keyword)
            withContext(Dispatchers.Main) {
                callback(result.isEmpty(), result)
            }
        }
    }

    // 获取一段时间的动态
    fun getDynamicsInDateRange(
        from: Long,
        to: Long,
        callback: (Boolean, List<Dynamic>) -> Unit
    ) {
        scope.launch {
            val result = database.getDynamicDao().getDynamicsInDateRange(from, to)
            withContext(Dispatchers.Main) {
                callback(result.isEmpty(), result)
            }
        }
    }


    // 删除指定动态
    fun deleteDynamic(dynamic: Dynamic, onEnd:  () -> Unit = {}) {
        scope.launch {
            database.getDynamicDao().delete(dynamic)
            withContext(Dispatchers.Main) {
                onEnd()
            }
        }
    }

    // 插入所有动态
    fun insertAllDynamics(vararg dynamics: Dynamic, callback: () -> Unit = {}) {
        scope.launch {
            database.getDynamicDao().insertAll(*dynamics)
            withContext(Dispatchers.Main) {
                callback()
            }
        }
    }
}