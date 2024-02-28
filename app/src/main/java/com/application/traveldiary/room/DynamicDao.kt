package com.application.traveldiary.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.application.traveldiary.models.Dynamic
import com.application.traveldiary.models.User

@Dao
interface DynamicDao {
    // 获取所有动态
    @Query("SELECT * FROM dynamic")
    suspend fun getAll(): List<Dynamic>

    // 根据标题或者内容检索动态
    @Query("SELECT * FROM dynamic WHERE title LIKE :keywoard OR content LIKE :keyword")
    suspend fun searchByTitleOrContent(keyword: String): List<Dynamic>

    // 根据postTime获取查询一定范围的动态
    @Query("SELECT * FROM dynamic WHERE postTime BETWEEN :from AND :to")
    suspend fun getDynamicsInDateRange(from: Long, to: Long): List<Dynamic>

    // 依据likes 数目，获得热门动态
    @Query("SELECT * FROM dynamic ORDER BY likes DESC LIMIT :limit")
    suspend fun getTopLikedDynamics(limit: Int): List<Dynamic>

    // 添加所有动态
    @Insert
    suspend fun insertAll(vararg dynamic: Dynamic)

    //添加一个动态
    @Insert
    suspend fun addDynamic(dynamic: Dynamic)

    // 删除动态
    @Delete
    suspend fun delete(dynamic: Dynamic)

    // 查询指定用户的动态
    @Query("SELECT * FROM dynamic WHERE postUser = :user")
    suspend fun getDynamicsByUser(user: User): List<Dynamic>
}
