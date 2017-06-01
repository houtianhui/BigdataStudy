package com.hth.java.httpclient;

/**
 * Created by hth on 2017/3/10.
 * http访问网络的代理ip和端口，还有使用用户及密码都可以在Spring容器中注入进来:

 复制代码
 <bean id="httpInvoker" class="HttpInvoker">
 <constructor-arg type="java.lang.String" value="gbk" /><!--useProxy-->
 <constructor-arg type="int" value="10000" /><!--useProxy-->
 <constructor-arg type="boolean" value="true" /><!--useProxy-->
 <!--代理地址 -->
 <constructor-arg type="java.lang.String" value="192.168.1.1" />
 <constructor-arg type="int" value="8080" />
 <constructor-arg type="java.lang.String" value="" /><!--用户名-->
 <constructor-arg type="java.lang.String" value="" /><!--密码-->
 </bean>
 7． 多线程模式下使用
 多线程同时访问httpclient，例如同时从一个站点上下载多个文件。对于同一个HttpConnection同一个时间只能有一个线程访问，为了保证多线程工作环境下不产生冲突，httpclient使用了一个多线程连接管理器的类：MultiThreadedHttpConnectionManager，要使用这个类很简单，只需要在构造HttpClient实例的时候传入即可，代码如下：

 MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

 HttpClient client = new HttpClient(connectionManager);

 以后尽管访问client实例即可。



 httpClient完整封装
 HttpInvoke.java:封装了HttpClient调度的必要参数设置,以及post,get等常用方法
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import java.util.Iterator;
import java.util.Map;
import java.net.SocketTimeoutException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpInvoker {
    private Log logger = LogFactory.getLog(HttpInvoker.class);
    private static HttpInvoker httpInvoker = new HttpInvoker();
    private HttpClient client = null;
    private String charset = "gbk";
    private int timeout = 10000;
    private boolean useProxy = false;
    private String proxyHost = null;
    private int proxyPort;
    private String proxyUsername = null;
    private String proxyPassword = null;
    private boolean initialized = false;

    public static HttpInvoker getInstance() {
        return httpInvoker;
    }

    private HttpInvoker() {
        client = new HttpClient(new MultiThreadedHttpConnectionManager());
        client.getParams().setParameter("http.protocol.content-charset", "gbk");
        client.getParams().setContentCharset("gbk");
        client.getParams().setSoTimeout(timeout);
    }

    public HttpInvoker(String charset, int timeout, boolean useProxy,
                       String proxyHost, int proxyPort, String proxyUsername,
                       String proxyPassword) {
        client = new HttpClient(new MultiThreadedHttpConnectionManager());
        if(charset != null && !charset.trim().equals("")) {
            this.charset = charset;
        }
        if(timeout > 0) {
            this.timeout = timeout;
        }
        client.getParams().setParameter("http.protocol.content-charset", charset);
        client.getParams().setContentCharset(charset);
        client.getParams().setSoTimeout(timeout);
        if(useProxy && proxyHost != null &&
                !proxyHost.trim().equals("") && proxyPort > 0) {
            HostConfiguration hc = new HostConfiguration();
            hc.setProxy(proxyHost, proxyPort);
            client.setHostConfiguration(hc);
            if (proxyUsername != null && !proxyUsername.trim().equals("") &&
                    proxyPassword != null && !proxyPassword.trim().equals("")) {
                client.getState().setProxyCredentials(AuthScope.ANY,
                        new UsernamePasswordCredentials(proxyUsername, proxyPassword));
            }
        }
        initialized = true;
        logger.debug("HttpInvoker初始化完成");
    }

    public synchronized void init() {
        if(charset != null && !charset.trim().equals("")) {
            client.getParams().setParameter("http.protocol.content-charset", charset);
            client.getParams().setContentCharset(charset);
        }
        if(timeout > 0) {
            client.getParams().setSoTimeout(timeout);
        }
        if(useProxy && proxyHost != null &&
                !proxyHost.trim().equals("") && proxyPort > 0) {
            HostConfiguration hc = new HostConfiguration();
            hc.setProxy(proxyHost, proxyPort);
            client.setHostConfiguration(hc);
            if (proxyUsername != null && !proxyUsername.trim().equals("") &&
                    proxyPassword != null && !proxyPassword.trim().equals("")) {
                client.getState().setProxyCredentials(AuthScope.ANY,
                        new UsernamePasswordCredentials(proxyUsername, proxyPassword));
            }
        }
        initialized = true;
        logger.debug("HttpInvoker初始化完成");
    }

    public String invoke(String url) throws Exception {
        return invoke(url, null, false);
    }

    public String invoke(String url, Map params, boolean isPost) throws Exception {
        logger.debug("HTTP调用[" + (isPost?"POST":"GET") + "][" + url + "][" + params + "]");
        HttpMethod httpMethod = null;
        String result = "";
        try {
            if(isPost && params != null && params.size() > 0) {
                Iterator paramKeys = params.keySet().iterator();
                httpMethod = new PostMethod(url);
                NameValuePair[] form = new NameValuePair[params.size()];
                int formIndex = 0;
                while(paramKeys.hasNext()) {
                    String key = (String)paramKeys.next();
                    Object value = params.get(key);
                    if(value != null && value instanceof String && !value.equals("")) {
                        form[formIndex] = new NameValuePair(key, (String)value);
                        formIndex++;
                    } else if(value != null && value instanceof String[] &&
                            ((String[])value).length > 0) {
                        NameValuePair[] tempForm =
                                new NameValuePair[form.length + ((String[])value).length - 1];
                        for(int i=0; i<formIndex; i++) {
                            tempForm[i] = form[i];
                        }
                        form = tempForm;
                        for(String v : (String[])value) {
                            form[formIndex] = new NameValuePair(key, (String)v);
                            formIndex++;
                        }
                    }
                }
                ((PostMethod)httpMethod).setRequestBody(form);
            } else {
                if(params != null && params.size() > 0) {
                    Iterator paramKeys = params.keySet().iterator();
                    StringBuffer getUrl = new StringBuffer(url.trim());
                    if(url.trim().indexOf("?") > -1) {
                        if(url.trim().indexOf("?") < url.trim().length()-1 &&
                                url.trim().indexOf("&")  < url.trim().length()-1) {
                            getUrl.append("&");
                        }
                    } else {
                        getUrl.append("?");
                    }
                    while(paramKeys.hasNext()) {
                        String key = (String)paramKeys.next();
                        Object value = params.get(key);
                        if(value != null && value instanceof String && !value.equals("")) {
                            getUrl.append(key).append("=").append(value).append("&");
                        } else if(value != null && value instanceof String[] &&
                                ((String[])value).length > 0) {
                            for(String v : (String[])value) {
                                getUrl.append(key).append("=").append(v).append("&");
                            }
                        }
                    }
                    if(getUrl.lastIndexOf("&") == getUrl.length()-1) {
                        httpMethod = new GetMethod(getUrl.substring(0, getUrl.length()-1));
                    } else {
                        httpMethod = new GetMethod(getUrl.toString());
                    }
                } else {
                    httpMethod = new GetMethod(url);
                }
            }
            client.executeMethod(httpMethod);
//            result = httpMethod.getResponseBodyAsString();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpMethod.getResponseBodyAsStream(),"ISO-8859-1"));
            String line = null;
            String html = null;
            while((line = reader.readLine()) != null){
                if(html == null) {
                    html = "";
                } else {
                    html += "\r\n";
                }
                html += line;
            }
            if(html != null) {
                result = new String(html.getBytes("ISO-8859-1"), charset);
            }
        } catch (SocketTimeoutException e) {
            logger.error("连接超时[" + url + "]");
            throw e;
        } catch (java.net.ConnectException e) {
            logger.error("连接失败[" + url + "]");
            throw e;
        } catch (Exception e) {
            logger.error("连接时出现异常[" + url + "]");
            throw e;
        } finally {
            if (httpMethod != null) {
                try {
                    httpMethod.releaseConnection();
                } catch (Exception e) {
                    logger.error("释放网络连接失败[" + url + "]");
                    throw e;
                }
            }
        }

        return result;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public synchronized boolean isInitialized() {
        return initialized;
    }
}