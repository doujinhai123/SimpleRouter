# 小型路由 SimpleRouter

闲来无事，利用 APT 写了个类似 ARouter 的小型路由框架，方便组件化跳转。

## 功能说明

### 基本路由跳转

路由跳转当然是 `SimpleRouter` 最基本的功能。

1、使用 `SimpleRouter` 注解标注路由名。

```java
@SimpleRouter(value = "main_page", interceptor = "")
public class MainActivity extends AppCompatActivity {
    ...
}

@SimpleRouter(value = "home_page", interceptor = "")
public class HomeActivity extends AppCompatActivity {
    ...
}
```

2、使用 `SRouter` 类进行跳转。

```java
@SimpleRouter(value = "main_page", interceptor = "")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 示例路由跳转
        SRouter.build(this, "home_page").execute();
    }
}
```

综上就完成了 `Activity` 之间的跳转。

### 带参路由跳转

1、传递参数

```java
SRouter.build(this, "home_page")
        .addParams("title", "This is title")
        .addParams("book", new Book("书名", 99.9))
        .execute();
```

2、解析参数

```java
@SimpleRouter(value = "home_page", interceptor = "")
public class HomeActivity extends AppCompatActivity {

    @SimpleParam("title")
    String mTitle;

    @SimpleParam("book")
    Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SRouter.initParams(this);
    }
}
```

调用 `@SimpleParam` 注解指定要解析的参数， 调用 `SRouter.initParams(this)` 以解析参数。

> `SRouter.initParams(this)` 可以写在基类中，即使没有参数可解析也没影响。


### 路由监控和拦截

`SimpleRouter` 包含了简单的路由监控和拦截机制。

#### 监控

```java
SRouter.build(this, "home_page")
       .callback(new RouterCallback() {
            @Override
            public void onFound() {}

            @Override
            public void onLost() {}

            @Override
            public void onArrival() {}

            @Override
            public void onIntercept(Interceptor interceptor) {}
       })
       .execute();
```

它们触发的时机分别是：

onFound：找到路由，跳转前。
onLost：未找到路由。
onArrival：找到路由，跳转后。
onIntercept：找到路由，跳转被拦截后。

#### 拦截

拦截支持俩种方式，分别是通用拦截器和临时拦截器。

**通用拦截器**

先说通用拦截器，这种拦截方式会在跳转到目标 Activity 之前，先执行其拦截器。因为任何 Activity 跳转至目标 Activity 都会先触发该拦截器，所以称为通用拦截器。

首先定义拦截器

```java
...
import com.dixon.simple.router.guide.Interceptor;

public class HomeInterceptor implements Interceptor {
    @Override
    public boolean onIntercept(Context context) {
        // 示例代码 未登录则跳转登录页
        if (Constant.isLogin()) {
            return false;
        }
        SRouter.build(context, "login_page").execute();
        // true 表示拦截
        return true;
    }
}
```

然后给目标 `Activity` 关联该拦截器，`interceptor` 值为上述拦截器的全限定名。

```java
@SimpleRouter(value = "home_page", interceptor = "com.dixon.comp.home.HomeInterceptor")
public class HomeActivity extends AppCompatActivity {
    ...
}
```

这样任意 `Activity` 跳转至 `HomeActivity` 前，都会触发 `HomeInterceptor` 的逻辑。

> interceptor 可以为空字符串，找不到拦截器不会影响正常跳转。


**临时拦截器**

故名思义，只在本次跳转生效。使用方式如下：

```java
SRouter.build(this, "home_page")
       .interceptor(new Interceptor() {
            @Override
            public boolean onIntercept(Context context) {
                // false 表示不拦截 your logic
                return false;
            }
       })
       .execute();
```

优先级上，临时拦截器 > 通用拦截器。

## 项目配置

要使用 `SimpleRouter`，需要如下配置：

1、在需要使用到 `SimpleRouter` 的 `module` 的 `build.gradle` 文件中，如下配置：

```java
android {
    ...
    defaultConfig {
        ...
        // 配置 moduleName 用于生成特殊文件
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [moduleName: project.getName()]
            }
        }
    }
}

dependencies {
    ...
    implementation 'com.dixon.srouter:simple_router:1.0.1'
    annotationProcessor 'com.dixon.srouter:simple_router_processor:1.0.1'
}
```

2、在你的 `Application` 中完成初始化。

```java
public class XXApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SRouter.init(this);
    }
}
```

如上，配置完毕。

## 源码地址

[源码及Demo Github](https://github.com/zhxyComing/SimpleRouter)

## 备注说明

很简单的小项目，没有复杂的功能，也没有 ARouter 强大，小型项目可用，简单易上手。

如果有功能需要或提 bug，可以 Github 或者直接下边提需求，后续会逐步更新优化。


