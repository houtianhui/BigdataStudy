package com.hth.java.Lifecycle;

/**
 * Created by hth on 2017/3/2.
 *对于DefaultLifeCycle来说，do nothing就是其职责。
 因此接下来我们可以写一个自己的实现类，继承DefaultLifeCycle，并重写那些感兴趣的生命周期方法。

 例如，我有一个类只需要在初始化，启动，和销毁时做一些任务，那么可以这么写:


 这里的ServerSocket中，init0初始化socket监听，start0开始获取socket连接, destroy0销毁socket监听。在这套生命周期管理机制下，我们将会很容易地对资源进行管理，不会发生资源未关闭的情况，架构和模块化更加清晰。
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends DefaultLifecycle {

    private ServerSocket acceptor = null;
    private int port = 9527;
    /*
     * @see DefaultLifecycle#init0()
     */
    @Override
    protected void init0() throws LifecycleException {
        try {
            acceptor = new ServerSocket(port);
        } catch (IOException e) {
            throw new LifecycleException(e);
        }
    }

    /*
     * @see DefaultLifecycle#start0()
     */
    @Override
    protected void start0() throws LifecycleException {
        Socket socket = null;
        try {
            socket = acceptor.accept();
            //do something with socket


        } catch (IOException e) {
            throw new LifecycleException(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * @see DefaultLifecycle#destroy0()
     */
    @Override
    protected void destroy0() throws LifecycleException {
        if (acceptor != null) {
            try {
                acceptor.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
