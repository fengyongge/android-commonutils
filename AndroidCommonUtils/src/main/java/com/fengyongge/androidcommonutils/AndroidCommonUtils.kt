package com.fengyongge.androidcommonutils

import android.content.Context

/**
 * describe
 * 对外开放API
 * @author fengyongge(fengyongge98@gmail.com)
 * GitHub: https://github.com/fengyongge/android-commonutils
 * @date 2020/09/08
 */
class AndroidCommonUtils(context: Context) {
    companion object {
        private var instance: AndroidCommonUtils?= null
        private lateinit var context:Context

        /**
         * 初始化
         */
        internal fun init(context: Context): AndroidCommonUtils {
            Companion.context = context
            instance =
                    AndroidCommonUtils(context)
            return instance!!
        }

        internal fun getAppContext(): Context{
            return context
        }
    }

}