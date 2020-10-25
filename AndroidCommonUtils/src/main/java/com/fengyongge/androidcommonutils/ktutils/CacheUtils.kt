package com.fengyongge.androidutils.utils

import com.fengyongge.androidcommonutils.AndroidCommonUtils
import java.io.File

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * GitHub: https://github.com/fengyongge/android-commonutils
 * @date 2020/09/08
 */

object CacheUtils {

    /**
     * 获取系统默认缓存文件夹
     * 优先返回SD卡中的缓存文件夹
     */
    fun cacheDir(): String {
        var cacheDir = ""
        var cacheFile: File? = null

        if (FileUtils.isSDCardAlive) {
            cacheFile = AndroidCommonUtils.getAppContext().externalCacheDir
        }
        cacheFile?.let {
            cacheFile = AndroidCommonUtils.getAppContext().cacheDir
            cacheDir = it.absolutePath
        }
        return cacheDir
    }

    /**
     * 获取系统默认缓存文件夹内的缓存大小
     */
    fun totalCacheSize(): String {
        var cacheSize: Long = FileUtils.getSize(AndroidCommonUtils.getAppContext().cacheDir)
        if (FileUtils.isSDCardAlive) {
            var cacheFile = AndroidCommonUtils.getAppContext().externalCacheDir
            cacheFile?.let {
                cacheSize += FileUtils.getSize(it)
            }
        }
        return FileUtils.formatSize(cacheSize.toDouble())
    }

    /**
     * 清除系统默认缓存文件夹内的缓存
     */
    fun clearAllCache() {
        FileUtils.delete(AndroidCommonUtils.getAppContext().cacheDir)
        if (FileUtils.isSDCardAlive) {
            FileUtils.delete(AndroidCommonUtils.getAppContext().externalCacheDir)
        }
    }
}