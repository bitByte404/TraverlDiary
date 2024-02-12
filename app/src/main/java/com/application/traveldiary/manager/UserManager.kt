package com.application.traveldiary.manager

import com.application.traveldiary.models.User

class UserManager private constructor() {
    private var mUser: User? = null
    private val mBomb = BmobManager.getInstance()

    companion object {
        private var instance: UserManager? = null
        fun getInstance(): UserManager {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = UserManager()
                    }
                }
            }
            return instance!!
        }
    }

    /**
     * 是否登录
     */
    fun isLogin(): Boolean {
        return mBomb.isLogin()
    }

    /**
     * 请求验证码
     */
    fun loginBySmaCode(
        phone: String,
        code: String,
        onStart: () -> Unit = {},
        onEnd: (Boolean) -> Unit = {}
    ) {
        mBomb.signOrLoginByMobilePhone(phone, code) {
            if (it) { //成功获取当前登录的用户信息
                mUser = mBomb.currentUser()
            }
            onEnd(it)
        }
    }

    /**
     * 手机和密码登录
     * 前提：用户存在，手机验证过
     */
    fun loginByPhone(
        phone: String,
        password: String,
        onStart: () -> Unit = {},
        onEnd: (Boolean) -> Unit = {}
    ) {
        mBomb.loginByPhone(phone, password) {
            if (it) {
                mUser = mBomb.currentUser()
            }
            onEnd(it)
        }
    }

    /**
     * 退出登录
     */
    fun logout() {
        mBomb.logout()
        mUser = null
    }
}