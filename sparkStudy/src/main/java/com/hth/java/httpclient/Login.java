package com.hth.java.httpclient;

/**
 * Created by hth on 2017/3/10.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 4． 模拟登录开心网
 本小节应该说是HTTP客户端编程中最常碰见的问题，很多网站的内容都只是对注册用户可见的，这种情况下就必须要求使用正确的用户名和口令登录成功后，方可浏览到想要的页面。因为HTTP协议是无状态的，也就是连接的有效期只限于当前请求，请求内容结束后连接就关闭了。在这种情况下为了保存用户的登录信息必须使用到Cookie机制。以JSP/Servlet为例，当浏览器请求一个JSP或者是Servlet的页面时，应用服务器会返回一个参数，名为jsessionid（因不同应用服务器而异），值是一个较长的唯一字符串的Cookie，这个字符串值也就是当前访问该站点的会话标识。浏览器在每访问该站点的其他页面时候都要带上jsessionid这样的Cookie信息，应用服务器根据读取这个会话标识来获取对应的会话信息。

 对于需要用户登录的网站，一般在用户登录成功后会将用户资料保存在服务器的会话中，这样当访问到其他的页面时候，应用服务器根据浏览器送上的Cookie中读取当前请求对应的会话标识以获得对应的会话信息，然后就可以判断用户资料是否存在于会话信息中，如果存在则允许访问页面，否则跳转到登录页面中要求用户输入帐号和口令进行登录。这就是一般使用JSP开发网站在处理用户登录的比较通用的方法。

 这样一来，对于HTTP的客户端来讲，如果要访问一个受保护的页面时就必须模拟浏览器所做的工作，首先就是请求登录页面，然后读取Cookie值；再次请求登录页面并加入登录页所需的每个参数；最后就是请求最终所需的页面。当然在除第一次请求外其他的请求都需要附带上Cookie信息以便服务器能判断当前请求是否已经通过验证。说了这么多，可是如果你使用httpclient的话，你甚至连一行代码都无需增加，你只需要先传递登录信息执行登录过程，然后直接访问想要的页面，跟访问一个普通的页面没有任何区别，因为类HttpClient已经帮你做了所有该做的事情了，太棒了！下面的例子实现了模拟登陆开心网并向自己好友发送消息的功能。
 */
class Login {
    public static String loginurl = "https://security.kaixin001.com/login/login_post.php";
    static Cookie[] cookies = {};

    static HttpClient httpClient = new HttpClient();

    static String email = "xxx@qq.com";//你的email
    static String psw = "xxx";//你的密码
    // 消息发送的action
    String url = "http://www.kaixin001.com/home/";

    public static void getUrlContent()
            throws Exception {

        HttpClientParams httparams = new HttpClientParams();
        httparams.setSoTimeout(30000);
        httpClient.setParams(httparams);

        httpClient.getHostConfiguration().setHost("www.kaixin001.com", 80);

        httpClient.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        PostMethod login = new PostMethod(loginurl);
        login.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");

        NameValuePair Email = new NameValuePair("loginemail", email);// 邮箱
        NameValuePair password = new NameValuePair("password", psw);// 密码
        // NameValuePair code = new NameValuePair( "code"
        // ,"????");//有时候需要验证码，暂时未解决

        NameValuePair[] data = { Email, password };
        login.setRequestBody(data);

        httpClient.executeMethod(login);
        int statuscode = login.getStatusCode();
        System.out.println(statuscode + "-----------");
        String result = login.getResponseBodyAsString();
        System.out.println(result+"++++++++++++");

        cookies = httpClient.getState().getCookies();
        System.out.println("==========Cookies============");
        int i = 0;
        for (Cookie c : cookies) {
            System.out.println(++i + ":   " + c);
        }
        httpClient.getState().addCookies(cookies);

        // 当state为301或者302说明登陆页面跳转了，登陆成功了
        if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY)
                || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
                || (statuscode == HttpStatus.SC_SEE_OTHER)
                || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
            // 读取新的 URL 地址
            Header header = login.getResponseHeader("location");
            // 释放连接
            login.releaseConnection();
            System.out.println("获取到跳转header>>>" + header);
            if (header != null) {
                String newuri = header.getValue();
                if ((newuri == null) || (newuri.equals("")))
                    newuri = "/";
                GetMethod redirect = new GetMethod(newuri);
                // ////////////
                redirect.setRequestHeader("Cookie", cookies.toString());
                httpClient.executeMethod(redirect);
                System.out.println("Redirect:"
                        + redirect.getStatusLine().toString());
                redirect.releaseConnection();

            } else
                System.out.println("Invalid redirect");
        } else {
            // 用户名和密码没有被提交，当登陆多次后需要验证码的时候会出现这种未提交情况
            System.out.println("用户没登陆");
            System.exit(1);
        }

    }

    public static void sendMsg() throws Exception {
        // 登录后发消息
        System.out.println("*************发消息***********");

        String posturl = "http://www.kaixin001.com/msg/post.php";
        PostMethod poster = new PostMethod(posturl);

        poster.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");
        poster.setRequestHeader("Cookie", cookies.toString());

        NameValuePair uids = new NameValuePair("uids", "89600585");// 发送的好友对象的id,此处换成你的好友id
        NameValuePair content = new NameValuePair("content", "你好啊!");// 需要发送的信息的内容
        NameValuePair liteeditor_0 = new NameValuePair("liteeditor_0", "你好啊!");// 需要发送的信息的内容
        NameValuePair texttype = new NameValuePair("texttype", "plain");
        NameValuePair send_separate = new NameValuePair("send_separate", "0");
        NameValuePair service = new NameValuePair("service", "0");
        NameValuePair[] msg = { uids, content, texttype, send_separate, service,liteeditor_0 };

        poster.setRequestBody(msg);
        httpClient.executeMethod(poster);

        String result = poster.getResponseBodyAsString();
        System.out.println(result+"++++++++++++");
        //System.out.println(StreamOut(result, "iso8859-1"));
        int statuscode = poster.getStatusCode();
        System.out.println(statuscode + "-----------");
        if(statuscode == 301 || statuscode == 302){
            // 读取新的 URL 地址
            Header header = poster.getResponseHeader("location");
            System.out.println("获取到跳转header>>>" + header);
            if (header != null) {
                String newuri = header.getValue();
                if ((newuri == null) || (newuri.equals("")))
                    newuri = "/";
                GetMethod redirect = new GetMethod(newuri);
                // ////////////
                redirect.setRequestHeader("Cookie", cookies.toString());
                httpClient.executeMethod(redirect);
                System.out.println("Redirect:"
                        + redirect.getStatusLine().toString());
                redirect.releaseConnection();

            } else
                System.out.println("Invalid redirect");
        }

        poster.releaseConnection();
    }

    public static String StreamOut(InputStream txtis, String code)
            throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(txtis,
                code));
        String tempbf;
        StringBuffer html = new StringBuffer(100);
        while ((tempbf = br.readLine()) != null) {
            html.append(tempbf + "\n");
        }
        return html.toString();

    }
}
