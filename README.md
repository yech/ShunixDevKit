ShunixDevKit
============

Shunix的Android开发工具包，简化了开发流程
------------

目前实现的功能如下：
***
1. 实现了缓存类的功能（暂时只支持缓存全部成员为String类型的类，类成员可访问性没关系)
使用时需要缓存的类需要继承Cachable接口：
<pre><code>
public interface Cachable {
	public String getName();
}
</code></pre>
这里的接口定义了一个方法，getName()这个方法将返回缓存的SQLite数据库的表名，通常在这个方法里只需要返回类的名字即可，如下实现即可：
<pre><code>
@Override
	public String getName() {
		return getClass().getSimpleName();
	}
</code></pre>
使用时先初始化一个CacheManager类，然后调用cacheObject()方法即可：
<pre><code>
cacheManager = CacheManager.getInstance(getActivity());
cacheManager.cacheObject(Cachable);
</code></pre>


2. 实现了使用annotation初始化View对象，具体使用方式如下，比如原来要这么初始化View对象：
<pre><code>
private Button button;
View view = inflater.inflate(R.layout.layout, container, false);
button = (Button) view.findViewById(R.id.button);
</code></pre>
现在初始化方式是：
<pre><code>
@ViewComponent(R.id.button)
private Button button;
AnnotationParser parser = AnnotationParser.getInstance();
View view = inflater.inflate(R.layout.layout, container, false);
parser.initAllComponent(this, view);
</code></pre>
支持Activity和Fragment中的View初始化，如果是Fragment，需要传入一个参数为扩张后的布局，View类型，如果是Activity则不需要此参数。


3. 封装了AsyncTask，以前写AsyncTask每次需要实现继承自AsyncTask的一个子类，现在需要实现一个接口：
<pre><code>
public abstract class AsyncTaskEntity {
	public abstract void OnPreExecute();
	public abstract void OnExecute();
	public abstract void OnPostExecute();
}
</code></pre>
然后新建一个ShunixAsyncTask类型实例，构造函数传入之前实现了AsyncTaskEntity的类即可，这个纯粹是为了少写几行代码，没做什么封装，嘿嘿，就是因为懒。


4. 实现了HTTP的POST和GET操作的封装，目前仅支持JSON格式的请求和返回，这个需要在单独线程里执行，我没有把这些操作线程化。


5. 实现了一个网络相关工具类，可以检测当前的网络是否可用，检测Wifi的IP地址或者是ISP的IP地址。


6. 实现了文件相关的工具类，处理文件相关的操作（目前只实现了检测SD卡是否可用）


7. 实现了图片相关的操作，包括缩放Drawable对象，drawable对象到Bitmap对象转换，dp转换为px，合并两张图片（中心对齐）


8. 实现了将记录写入文件的Log类


9. 封装了SharedPreference相关操作


10. 封装了执行Shell命令相关的方法（目前实现了执行Root权限的命令)


11. 实现了异常信息自动写入文件的ExceptionHandler，并对输出细节做了增强，可以用来替换系统默认的ExceptionHandler，使用时需要将默认的Application替换成ShunixApplication
