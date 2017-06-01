package com.hth.java.httpclient;

/**
 * Created by hth on 2017/3/10.
 * 6． 访问启用认证的页面
 我们经常会碰到这样的页面，当访问它的时候会弹出一个浏览器的对话框要求输入用户名和密码后方可，这种用户认证的方式不同于我们在前面介绍的基于表单的用户身份验证。这是HTTP的认证策略，httpclient支持三种认证方式包括：
 基本、摘要以及NTLM认证。其中基本认证最简单、通用但也最不安全；
 摘要认证是在HTTP 1.1中加入的认证方式，而NTLM则是微软公司定义的而不是通用的规范，最新版本的NTLM是比摘要认证还要安全的一种方式。

 下面例子是从httpclient的CVS服务器中下载的，它简单演示如何访问一个认证保护的页面：
 */
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.GetMethod;

public class BasicAuthenticationExample {

    public BasicAuthenticationExample() {
    }

    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
        client.getState().setCredentials( "www.verisign.com" , "realm" , new UsernamePasswordCredentials( "username" , "password" ) );

        GetMethod get = new GetMethod( "https://www.verisign.com/products/index.html" );
        get.setDoAuthentication( true );
        int status = client.executeMethod( get );
        System.out.println(status+ "\n" + get.getResponseBodyAsString());
        get.releaseConnection();
    }
}