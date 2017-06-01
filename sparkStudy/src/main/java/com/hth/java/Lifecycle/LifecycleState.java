package com.hth.java.Lifecycle;

/**
 * Created by hth on 2017/3/2.
 * 生命周期状态
 一个模块的生命周期状态一般有以下几个：

 新生 -> 初始化中 -> 初始化完成 -> 启动中 -> 启动完成 -> 正在暂停 -> 已经暂停 -> 正在恢复 -> 已经恢复 -> 正在销毁 -> 已经销毁

 其中，任何一个状态之间的转化如果失败，那么就会进入另外一种状态：失败。

 为此，可以用一个枚举类来枚举出这几个状态，如下所示：
 */
public enum LifecycleState {

    NEW, //新生

    INITIALIZING, INITIALIZED, //初始化

    STARTING, STARTED, //启动

    SUSPENDING, SUSPENDED, //暂停

    RESUMING, RESUMED,//恢复

    DESTROYING, DESTROYED,//销毁

    FAILED;//失败
}
