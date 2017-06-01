package com.hth.java.Lifecycle;

/**
 * Created by hth on 2017/3/2.
 * 优雅的实现
 到目前为止，我们已经定义了接口ILifeCycle，以及其骨架实现AbstractLifeCycle，并且增加了监听者机制。貌似我们可以开始写一个类来继承AbstractLifecycle，并重写其定义的抽象方法了，so far so good。

 但在开始之前，我们还需要考虑另外几个问题，

 我们的实现类是否对所有的抽象方法都感兴趣？
 是否每个实现累都需要实现init0, start0, suspend0, resume0, destroy0?
 是否有时候，我们的那些有生命的类或者模块并不支持暂停(suspend),恢复(resume)?
 直接继承AbstractLifeCycle，就意味着必须实现其全部的抽象方法。
 因此，我们还需要一个默认实现，DefaultLifeCycle，让它继承AbstractLifeCycle，并实现所有抽象方法，但它并不做任何实际的事情, do nothing。只是让我们真正的实现类来继承这个默认的实现类，并重写感兴趣的方法。

 于是，我们的DefaultLifeCycle就这么诞生了:
 */
public class DefaultLifecycle extends AbstractLifecycle {

    /*
     * @see AbstractLifecycle#init0()
     */
    @Override
    protected void init0() throws LifecycleException {
        // do nothing
    }

    /*
     * @see AbstractLifecycle#start0()
     */
    @Override
    protected void start0() throws LifecycleException {
        // do nothing
    }

    /*
     * @see AbstractLifecycle#suspend0()
     */
    @Override
    protected void suspend0() throws LifecycleException {
        // do nothing
    }

    /*
     * @see AbstractLifecycle#resume0()
     */
    @Override
    protected void resume0() throws LifecycleException {
        // do nothing
    }

    /*
     * @see AbstractLifecycle#destroy0()
     */
    @Override
    protected void destroy0() throws LifecycleException {
        // do nothing
    }

}