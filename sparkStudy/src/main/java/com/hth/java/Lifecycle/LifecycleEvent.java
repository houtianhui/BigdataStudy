package com.hth.java.Lifecycle;

/**
 * Created by hth on 2017/3/2.
 * 生命周期事件由LifecycleEvent来表示，如下所示:
 */
public final class LifecycleEvent {

    private LifecycleState state;

    public LifecycleEvent(LifecycleState state) {
        this.state = state;
    }

    /**
     * @return the state
     */
    public LifecycleState getState() {
        return state;
    }

}