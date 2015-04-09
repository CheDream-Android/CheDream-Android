package org.chedream.android.application;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.chedream.android.R;

import static org.chedream.android.helpers.Const.IMAGELOADER;

public class DreamApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    private void initImageLoader() {
        if (IMAGELOADER == null) {
            IMAGELOADER = ImageLoader.getInstance();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageForEmptyUri(R.drawable.no_photo)
                    .showImageOnFail(R.drawable.no_photo)
                    .showImageOnLoading(R.drawable.no_photo)
                    .build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .diskCacheExtraOptions(1024, 1024, null)
                    .denyCacheImageMultipleSizesInMemory()
                    .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                    .memoryCacheSize(2 * 1024 * 1024)
                    .diskCacheSize(50 * 1024 * 1024)
                    .diskCacheFileCount(100)
                    .defaultDisplayImageOptions(options)
                    .build();
            IMAGELOADER.init(config);
        }
    }
}
