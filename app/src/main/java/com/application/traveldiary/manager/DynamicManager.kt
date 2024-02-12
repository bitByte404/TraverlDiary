package com.application.traveldiary.manager

import com.application.traveldiary.models.Dynamic

class DynamicManager private constructor() {
    private val mBmobManager = BmobManager.getInstance()
    private var mDynamicList = emptyList<Dynamic>()

    companion object {
        val instance: DynamicManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            DynamicManager()
        }
    }
}