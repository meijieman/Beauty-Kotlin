Material design



## 参考资料
* ui 设计
http://www.uisdc.com/design-better-forms#
http://www.uisdc.com/ui-form-design-beautiful
http://www.sj33.cn/digital/uisj/201712/48286_2.html
http://www.sj33.cn/digital/uisj/201712/48272.html

* 参考界面
https://www.uisdc.com/comprehensive-material-design-note
http://colachan.com/post/3416


* https://github.com/futuresimple/android-floating-action-button

* 配色、图标
 https://www.materialpalette.com/


* 自定义表格
https://github.com/RmondJone/LockTableView

* https://github.com/huangyanbin/smartTable

* 客户端同步服务器端表中数据架构分析
https://a52071453.iteye.com/blog/1978498

* 常用的图表库
https://blog.csdn.net/u014133119/article/details/80923327

* Android 手写和笔锋研究资料
https://blog.csdn.net/bglg2000/article/details/89429400

---

MaterialCardView
android.support.design.chip.ChipGroup
android.support.design.chip.Chip

BottomAppBar

Design Support Library v28


搜索框

Material Spinner

Palette

-----------
## 问题
```xml
    <TextView
        android:id="@+id/tvContent"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:paddingBottom="2dp"
        android:background="#f00"
        android:singleLine="true"
        android:layout_centerInParent="true"
        android:text="2"
        android:textAppearance="?android:attr/textAppearanceSmall"
        android:textColor="@android:color/holo_blue_bright"
        android:textSize="12dp" />
```
如果 `android:text="2"`
没有设置值的话，不会显示出数据，即`android:text=""`界面就不会显示数据了

```
        // 是否绘制数据值
        barDataSet.setDrawValues(true);
```
