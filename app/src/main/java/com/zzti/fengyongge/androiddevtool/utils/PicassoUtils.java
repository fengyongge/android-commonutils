package com.zzti.fengyongge.androiddevtool.utils;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.zzti.fengyongge.androiddevtool.R;

/**
 * @author fengyongge
 * @Description Picasso使用
 * @date 2017/7/7
 */
public class PicassoUtils {

    public static void showImage(Context context,String imageUrl, ImageView imageView){
        Picasso.with(context).load(imageUrl)
                .error(R.drawable.default_product)
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//设置无缓存
//                .resize(DbTOPxUtils.dip2px(context,250),DbTOPxUtils.dip2px(context,250))//设置裁剪
//                .centerCrop()
                .placeholder(R.drawable.test).into(imageView);
//        Picasso.with(this).setIndicatorsEnabled(true);//可以查看读取图片方式

    }

}
