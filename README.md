# 介绍

## 控件和id命名规则
先写这个控件是什么,再加上作用,如登录按钮:btnLogin

控件做成全局变量,然后去initView函数中进行赋值
![命名](https://foruda.gitee.com/images/1692712664368267150/0f8ea8b4_10451072.png "屏幕截图")
![初始化](https://foruda.gitee.com/images/1692712685722552568/edf0189d_10451072.png "屏幕截图")


## 踩过的坑
* 调用数据库函数时,需要new Thread来使用,不然无法连接数据库

* 当做了数据库操作之后,需要展示Toast,setText这种修改UI的操作时,需要在runOnUiThread()中进行,不然会报错
就像这样![示例](https://foruda.gitee.com/images/1692712627045819405/05a871d4_10451072.png "屏幕截图")


## 数据库的表
account表

![](https://foruda.gitee.com/images/1692712778410419793/1a993d28_10451072.png "屏幕截图")

product表

![](https://foruda.gitee.com/images/1692712793896394640/08a7219d_10451072.png "屏幕截图")

![外键](https://foruda.gitee.com/images/1692712948886412070/2f8f9816_10451072.png "屏幕截图")

![只能保存0和1](https://foruda.gitee.com/images/1692712966277536316/dc9e275f_10451072.png "屏幕截图")

#### 参与贡献
梁雨佳：编写数据库操作,登录界面和发布界面及其操作

