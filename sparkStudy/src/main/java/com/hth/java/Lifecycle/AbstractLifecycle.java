package com.hth.java.Lifecycle;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hth on 2017/3/2.
 * 骨架实现
 有了ILifeCycle接口以后，任何实现了这个接口的类将会被作为一个生命周期管理对象，这个类可以是一个socket监听服务，也可以代表一个特定的模块，等等。
 那我们是不是只要实现ILifeCycle就可以了?
 可以这么说，但考虑到各个生命周期管理对象在生命周期的各个阶段会有一些共同的行为，比如说：

 设置自身的生命周期状态
 检查状态的转换是否符合逻辑
 通知监听者生命周期状态发生了变化
 因此，提供一个抽象类AbstractLifeCycle，作为ILifeCycle的骨架实现是有重要意义的，这样避免了很多的重复代码，使得架构更加清晰。
 这个抽象类会实现ILifeCycle中定义的所有接口方法，并添加对应的抽象方法，供子类实现。
 AbstractLifeCycle可以这么实现：



 可以看到，抽象类的骨架实现中做了几件生命周期管理中通用的事情，检查状态之间的转换是否合法(比如说start之前必须要init)，设置内部状态，以及触发相应的监听者。

 抽象类实现了ILifeCycle定义的方法后，又留出了相应的抽象方法供其子类实现，如上面的代码所示，其留出来的抽象方法有以下这些:

 protected abstract void init0() throws LifecycleException;
 protected abstract void start0() throws LifecycleException;
 protected abstract void suspend0() throws LifecycleException;
 protected abstract void resume0() throws LifecycleException;
 protected abstract void destroy0() throws LifecycleException;
 */
public abstract class AbstractLifecycle implements ILifecycle {

    private List<ILifecycleListener> listeners = new CopyOnWriteArrayList<ILifecycleListener>();

    /**
     * state 代表当前生命周期状态
     */
    private LifecycleState state = LifecycleState.NEW;

    /*
     * @see ILifecycle#init()
     */
    @Override
    public final synchronized void init() throws LifecycleException {
        if (state != LifecycleState.NEW) {
            return;
        }

        setStateAndFireEvent(LifecycleState.INITIALIZING);
        try {
            init0();
        } catch (Throwable t) {
            setStateAndFireEvent(LifecycleState.FAILED);
            if (t instanceof LifecycleException) {
                throw (LifecycleException) t;
            } else {
                throw new LifecycleException(formatString(
                        "Failed to initialize {0}, Error Msg: {1}", toString(), t.getMessage()), t);
            }
        }
        setStateAndFireEvent(LifecycleState.INITIALIZED);
    }

    protected abstract void init0() throws LifecycleException;

    /*
     * @see ILifecycle#start()
     */
    @Override
    public final synchronized void start() throws LifecycleException {
        if (state == LifecycleState.NEW) {
            init();
        }

        if (state != LifecycleState.INITIALIZED) {
            return;
        }

        setStateAndFireEvent(LifecycleState.STARTING);
        try {
            start0();
        } catch (Throwable t) {
            setStateAndFireEvent(LifecycleState.FAILED);
            if (t instanceof LifecycleException) {
                throw (LifecycleException) t;
            } else {
                throw new LifecycleException(formatString("Failed to start {0}, Error Msg: {1}",
                        toString(), t.getMessage()), t);
            }
        }
        setStateAndFireEvent(LifecycleState.STARTED);
    }

    protected abstract void start0() throws LifecycleException;

    /*
     * @see ILifecycle#suspend()
     */
    @Override
    public final synchronized void suspend() throws LifecycleException {
        if (state == LifecycleState.SUSPENDING || state == LifecycleState.SUSPENDED) {
            return;
        }

        if (state != LifecycleState.STARTED) {
            return;
        }

        setStateAndFireEvent(LifecycleState.SUSPENDING);
        try {
            suspend0();
        } catch (Throwable t) {
            setStateAndFireEvent(LifecycleState.FAILED);
            if (t instanceof LifecycleException) {
                throw (LifecycleException) t;
            } else {
                throw new LifecycleException(formatString("Failed to suspend {0}, Error Msg: {1}",
                        toString(), t.getMessage()), t);
            }
        }
        setStateAndFireEvent(LifecycleState.SUSPENDED);
    }

    protected abstract void suspend0() throws LifecycleException;

    /*
     * @see ILifecycle#resume()
     */
    @Override
    public final synchronized void resume() throws LifecycleException {
        if (state != LifecycleState.SUSPENDED) {
            return;
        }

        setStateAndFireEvent(LifecycleState.RESUMING);
        try {
            resume0();
        } catch (Throwable t) {
            setStateAndFireEvent(LifecycleState.FAILED);
            if (t instanceof LifecycleException) {
                throw (LifecycleException) t;
            } else {
                throw new LifecycleException(formatString("Failed to resume {0}, Error Msg: {1}",
                        toString(), t.getMessage()), t);
            }
        }
        setStateAndFireEvent(LifecycleState.RESUMED);
    }

    protected abstract void resume0() throws LifecycleException;

    /*
     * @see ILifecycle#destroy()
     */
    @Override
    public final synchronized void destroy() throws LifecycleException {
        if (state == LifecycleState.DESTROYING || state == LifecycleState.DESTROYED) {
            return;
        }

        setStateAndFireEvent(LifecycleState.DESTROYING);
        try {
            destroy0();
        } catch (Throwable t) {
            setStateAndFireEvent(LifecycleState.FAILED);
            if (t instanceof LifecycleException) {
                throw (LifecycleException) t;
            } else {
                throw new LifecycleException(formatString("Failed to destroy {0}, Error Msg: {1}",
                        toString(), t.getMessage()), t);
            }
        }
        setStateAndFireEvent(LifecycleState.DESTROYED);
    }

    protected abstract void destroy0() throws LifecycleException;

    /*
     * @see
     * ILifecycle#addLifecycleListener(ILifecycleListener)
     */
    @Override
    public void addLifecycleListener(ILifecycleListener listener) {
        listeners.add(listener);
    }

    /*
     * @see
     * ILifecycle#removeLifecycleListener(ILifecycleListener)
     */
    @Override
    public void removeLifecycleListener(ILifecycleListener listener) {
        listeners.remove(listener);
    }

    private void fireLifecycleEvent(LifecycleEvent event) {
        for (Iterator<ILifecycleListener> it = listeners.iterator(); it.hasNext();) {
            ILifecycleListener listener = it.next();
            listener.lifecycleEvent(event);
        }
    }

    protected synchronized LifecycleState getState() {
        return state;
    }

    private synchronized void setStateAndFireEvent(LifecycleState newState) throws LifecycleException {
        state = newState;
        fireLifecycleEvent(new LifecycleEvent(state));
    }

    private String formatString(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

