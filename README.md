# LearnDemo
该项目中目前实现：
1.recyclerview 图片浏览器（功能不完善）
2.ndk编译

recyclerview 图片浏览器
本意是想仿照微信的图片浏览，所以界面都是类似微信的图片选择。暂时没有做选择目录的功能，只能查看全部图片。
图片浏览有缓存，不会造成oom，浏览顺畅，主要归功于AlbumBitmapCacheHelper——从网上找到的一个开源代码，原本是用来让ImageView加载网络图片的，
经过改动可以加载本地的图片。

项目中还用到了recyclerview加载每一项时候的一些动画效果：https://github.com/gabrielemariotti/RecyclerViewItemAnimators
