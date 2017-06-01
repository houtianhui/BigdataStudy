package com.hth.java.Lifecycle;

/**
 * Created by hth on 2017/3/2.
 * 接口
 生命周期中的各种行为规范，也需要一个接口来定义，如下所示:
 */
public interface ILifecycle {

    /**
     * 初始化
     *
     * @throws LifecycleException
     */
    public void init() throws LifecycleException;

    /**
     * 启动
     *
     * @throws LifecycleException
     */
    public void start() throws LifecycleException;

    /**
     * 暂停
     *
     * @throws LifecycleException
     */
    public void suspend() throws LifecycleException;

    /**
     * 恢复
     *
     * @throws LifecycleException
     */
    public void resume() throws LifecycleException;

    /**
     * 销毁
     *
     * @throws LifecycleException
     */
    public void destroy() throws LifecycleException;

    /**
     * 添加生命周期监听器
     *
     * @param listener
     */
    public void addLifecycleListener(ILifecycleListener listener);

    /**
     * 删除生命周期监听器
     *
     * @param listener
     */
    public void removeLifecycleListener(ILifecycleListener listener);
}
