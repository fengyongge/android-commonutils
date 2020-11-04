package com.fengyongge.androidcommonutils

import androidx.core.content.FileProvider

class InitProvider : FileProvider() {

    /**
     * 通过ContentProvider获取ApplicationContext
     */
    override fun onCreate(): Boolean {
        AndroidCommonUtils.init(context!!)
        return super.onCreate()
    }
}