package com.fengyongge.androidutils.utils

import android.content.Context
import android.widget.Toast
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * GitHub: https://github.com/fengyongge/android-commonutils
 * @date 2020/09/08
 */
class ToastUtils {

    companion object{
        fun showToast(context: Context?,messsage: String?){
            Toast.makeText(context,messsage,Toast.LENGTH_SHORT).show()
        }
    }

}