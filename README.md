项目说明:
1、此项目是以 MVP 设计模式进行开发以及模块化开发（app 和 commonframework）
2、commonframework 是做为底层网络库以及公共库的模块，以RxJava+Retrofit2 作为底层网络库的开发，如果是多人多组多项目开发的话，
   可以在公司内网配置maven,以 maven 的形式集成到项目当中。可以保持项目内底层网络以及风格一致
3、app 模块作为应用需求的开发，用到了如下技术:
   #用到了 今日头条的 AutoSize 做为屏幕适配 （720x1280）
   #Room 数据库数据的存储
   #AsyncTask 异步操作
   #RecycleView 嵌套滑动冲突处理
4、运行项目:可以直接运行。
   如果需要看layout布局，则需要在 AVD Manager 内配置 Virtual Devices。配置如下:
   #Devices Type: Phone/Tablet
   #Screen size: 57.82
   #Resolution: 720x1280





