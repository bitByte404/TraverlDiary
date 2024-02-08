package com.application.traverldiary.bmob

import com.application.traverldiary.models.Dynamic

class DynamicManager private constructor() {
    private val mBmobManager = BmobManager.getInstance()
    private var mDynamicList = emptyList<Dynamic>()

    companion object {
        val instance: DynamicManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            DynamicManager()
        }
    }
}