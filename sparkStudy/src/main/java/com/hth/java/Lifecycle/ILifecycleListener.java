package com.hth.java.Lifecycle;

/**
 * Created by hth on 2017/3/2.
 * 发生生命周期状态转化时，可能需要触发对某类事件感兴趣的监听者，
 * 因此ILifeCycle也定义了两个方法可以添加和移除监听者。
 * 分别是：public void addLifecycleListener(ILifecycleListener listener);
 * 和 public void removeLifecycleListener(ILifecycleListener listener);

 监听者也由一个接口来定义其行为规范，如下所示:
 */
public interface ILifecycleListener {
    /**
     * 对生命周期事件进行处理
     *
     * @param event 生命周期事件
     */
    public void lifecycleEvent(LifecycleEvent event);
}
