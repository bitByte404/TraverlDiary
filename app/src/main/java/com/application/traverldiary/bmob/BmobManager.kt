package com.application.traverldiary.bmob

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.QueryListener
import com.application.traverldiary.models.Dynamic
import com.application.traverldiary.models.User

class BmobManager private constructor() {
    companion object {
        private  var instance: BmobManager? = null
        fun getInstance(): BmobManager {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = BmobManager()
                    }
                }
            }
            return instance!!
        }
    }

    /**
     * 是否登录
     */
    fun isLogin() = BmobUser.isLogin()


    /**
     * 获取当前用户
     */
    fun currentUser() = BmobUser.getCurrentUser(User::class.java)

    /**
     * 退出登录
     */
    fun logout() = BmobUser.logOut()

    /**
     * 请求短信验证码
     */
    fun requestSms(phone: String, callback: (Boolean) -> Unit = {}) {
        BmobSMS.requestSMSCode(phone, "diary", object : QueryListener<Int>() {
            override fun done(p0: Int?, p1: BmobException?) {
                callback(p1 == null)
            }
        })
    }

    /**
     * 一键登录和注册
     */
    fun signOrLoginByMobilePhone(
        phone: String,
        code: String,
        callback: (Boolean) -> Unit = {}
    ) {
        BmobUser.signOrLoginByMobilePhone(phone, code, object : LogInListener<BmobUser>() {
            override fun done(p0: BmobUser?, p1: BmobException?) {
                callback(p1 == null)
            }
        })
    }

    /**
     * 手机和密码登录
     */
    fun loginByPhone(
        phone: String,
        password: String,
        callback: (Boolean) -> Unit = {}
    ) {
        BmobUser.loginByAccount(phone, password, object : LogInListener<User>(){
            override fun done(p0: User?, p1: BmobException?) {
                callback(p1 == null)
            }
        })
    }

    /**
     * 查询Dynamic表中的所有数据
     */
    fun queryDynamic(onStart: () -> Unit = {}, onEnd: (List<Dynamic>?) -> Unit = {}) {
        onStart()
        val dynamicQuery = BmobQuery<Dynamic>()
        dynamicQuery.findObjects(object : FindListener<Dynamic>() {
            override fun done(p0: MutableList<Dynamic>?, p1: BmobException?) {
                onEnd(p0)
            }
        })
    }
}
