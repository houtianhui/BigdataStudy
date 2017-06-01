package com.hth.java.httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang.StringUtils;

import java.io.*;

/**
 *最简单的HTTP客户端,用来演示通过GET或者POST方式访问某个页面
 *@authorLiudong
 * 在这个例子中首先创建一个HTTP客户端(HttpClient)的实例，然后选择提交的方法是GET或者POST，最后在HttpClient实例上执行提交的方法，最后从所选择的提交方法中读取服务器反馈回来的结果。
 * 这就是使用HttpClient的基本流程。
 * 其实用一行代码也就可以搞定整个请求的过程，非常的简单！
 */
public class SimpleClient {
    public static void main(String[] args) throws IOException
    {
        HttpClient client = new HttpClient();
        // 设置代理服务器地址和端口
        //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
        HttpMethod method=new GetMethod("http://java.sun.com");
        //使用POST方法
        //HttpMethod method = new PostMethod("http://java.sun.com");
        client.executeMethod(method);

        //打印服务器返回的状态
        System.out.println(method.getStatusLine());
        //打印返回的信息
        System.out.println(method.getResponseBodyAsString());
        //释放连接
        method.releaseConnection();
    }


    /**
     * 上传url文件到指定URL
     * @param fileUrl 上传图片url
     * @param postUrl 上传路径及参数,注意有些中文参数需要使用预先编码 eg : URLEncoder.encode(appName, "UTF-8")
     * @return
     * @throws IOException
     */
    public static String doUploadFile(String postUrl) throws IOException {
        if(StringUtils.isEmpty(postUrl))
            return null;
        String response = "";
        PostMethod postMethod = new PostMethod(postUrl);
        try {
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams()
                    .setConnectionTimeout(50000);// 设置连接时间
            int status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                response = stringBuffer.toString();
            } else {
                response = "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return response;
    }

    /**
     * 上传文件到指定URL
     * @param file
     * @param url
     * @return
     * @throws IOException
     */
    public static String doUploadFile(File file, String url) throws IOException {
        String response = "";
        if (!file.exists()) {
            return "file not exists";
        }
        PostMethod postMethod = new PostMethod(url);
        try {
            //----------------------------------------------
            // FilePart：用来上传文件的类,file即要上传的文件
            FilePart fp = new FilePart("file", file);
            Part[] parts = { fp };

            // 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
            MultipartRequestEntity mre = new MultipartRequestEntity(parts,
                    postMethod.getParams());
            postMethod.setRequestEntity(mre);
            //---------------------------------------------
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams()
                    .setConnectionTimeout(50000);// 由于要上传的文件可能比较大 , 因此在此设置最大的连接超时时间
            int status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                response = stringBuffer.toString();
            } else {
                response = "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return response;
    }
    /**
     * 在JSP/Servlet编程中response.sendRedirect方法就是使用HTTP协议中的重定向机制。它与JSP中的<jsp:forward …>的区别在于后者是在服务器中实现页面的跳转，也就是说应用容器加载了所要跳转的页面的内容并返回给客户端；
     * 而前者是返回一个状态码，这些状态码的可能值见下表，然后客户端读取需要跳转到的页面的URL并重新加载新的页面。就是这样一个过程，所以我们编程的时候就要通过HttpMethod.getStatusCode()方法判断返回值是否为下表中的某个值来判断是否需要跳转。
     * 如果已经确认需要进行页面跳转了，那么可以通过读取HTTP头中的location属性来获取新的地址。

     下面的代码片段演示如何处理页面的重定向
     */
    public static  void forword(){
//        client.executeMethod(post);
//        System.out.println(post.getStatusLine().toString());
//        post.releaseConnection();
//// 检查是否重定向
//        int statuscode = post.getStatusCode();
//        if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
//                (statuscode ==HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
//// 读取新的 URL 地址
//            Header header=post.getResponseHeader("location");
//            if (header!=null){
//                Stringnewuri=header.getValue();
//                if((newuri==null)||(newuri.equals("")))
//                    newuri="/";
//                GetMethodredirect=newGetMethod(newuri);
//                client.executeMethod(redirect);
//                System.out.println("Redirect:"+redirect.getStatusLine().toString());
//                redirect.releaseConnection();
//            }else
//                System.out.println("Invalid redirect");
//        }
    }

}
