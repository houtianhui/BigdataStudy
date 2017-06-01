package com.hth.java.Lifecycle;

/**
 * Created by hth on 2017/3/2.
 * 自定义异常
 */
public class LifecycleException extends Exception
{
    public LifecycleException(Exception msg)
    {
        super(msg);
    }

    public LifecycleException(String s, Throwable t) {
    }
}