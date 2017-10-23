package utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Created by Sam.fu on 2017/2/16.
 */
public class HTTPCLIENT {


    private static final Log logger = LogFactory.getLog(HTTPCLIENT.class);
    private final static Object syncLock = new Object();
    private static CloseableHttpClient client;
    private static BasicCookieStore cookieStore;
    /**
     * 获取httpclient 实例
     * @return
     */
    public static CloseableHttpClient getHttpClient (){
        if (client == null) {
            synchronized (syncLock) {
                if (client == null) {
                    client = createHttpClient();
                }
            }
        }
        return client;
    }

    private static CloseableHttpClient createHttpClient(){

        // 配置连接参数
        ConnectionConfig connConfig = ConnectionConfig.custom()
                .setCharset(Charset.forName("UTF-8"))
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .build();

        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(50000)
                .setTcpNoDelay(true)
                .build();

        try{
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            // 用SSLContexts().custom() 代替 过时的方法 SSLContextBuilder()
            SSLContext sslContext = new SSLContexts().custom().loadTrustMaterial(trustStore,anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext,new NoopHostnameVerifier());
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory> create();
            registryBuilder.register("http", new PlainConnectionSocketFactory());
            registryBuilder.register("https", sslSF);
            Registry<ConnectionSocketFactory> registry = registryBuilder.build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
            // 注册connection
            connManager.setDefaultConnectionConfig(connConfig);
            connManager.setDefaultSocketConfig(socketConfig);
            connManager.setMaxTotal(10); // 连接池最大并发连接数
            connManager.setDefaultMaxPerRoute(10);// 单路由最大并发数
            // 请求重试处理
            HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest(IOException exception,
                                            int executionCount, HttpContext context) {
                    if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                        return false;
                    }
                    if (exception instanceof InterruptedIOException) {// 超时
                        return false;
                    }
                    if (exception instanceof UnknownHostException) {// 目标服务器不可达
                        return false;
                    }
                    if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                        return false;
                    }
                    if (exception instanceof SSLException) {// SSL握手异常
                        return false;
                    }

                    HttpClientContext clientContext = HttpClientContext .adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    // 如果请求是幂等的，就再次尝试
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }
            };
            // 实例化cookie
            cookieStore = new BasicCookieStore();

            // 创建httpclient对象 ,使用连接池
            client = HttpClientBuilder.create()
                    .setDefaultCookieStore(cookieStore)
                    .setConnectionManager(connManager)
                    .setRetryHandler(httpRequestRetryHandler)
                    .build();

            return client;

        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }


    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        url = gethURL(url, params);
        HttpGet method = new HttpGet(url);
        config(method); // *请求的配置项，构造器可以动态配置
        return getMethod(method, headers);
    }

    public static String delete(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        url = gethURL(url, params);
        HttpDelete method = new HttpDelete(url);
        config(method); // *请求的配置项，构造器可以动态配置
        return getMethod(method, headers);
    }

    public static String options(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        url = gethURL(url, params);
        HttpOptions method = new HttpOptions(url);
        config(method); // *请求的配置项，构造器可以动态配置
        return getMethod(method, headers);
    }

    public static String trace(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        url = gethURL(url, params);

        HttpTrace method = new HttpTrace(url);
        config(method); // *请求的配置项，构造器可以动态配置

        return getMethod(method, headers);
    }

    public static String head(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        url = gethURL(url, params);
        HttpHead method = new HttpHead(url);
        config(method); // *请求的配置项，构造器可以动态配置
        return getHead(method, headers);
    }

    public static String put(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        HttpPut method = new HttpPut(url);
        config(method); // *请求的配置项，构造器可以动态配置

        // 请求的参数信息传递
        List<NameValuePair> paires = new ArrayList<NameValuePair>();
        if (params != null) {
            Set<String> keys = params.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                paires.add(new BasicNameValuePair(key,params.get(key) )); //URLDecoder.decode(params.get(key), "UTF-8"))
            }
        }
        if (paires.size() > 0) {
            HttpEntity entity = new UrlEncodedFormEntity(paires, "utf-8"); //编码方式
            method.setEntity(entity);
        }
        return postMethod(method,params, headers);
    }

    /**
     * POST 请求方法
     * @param url url地址
     * @param params 参数
     * @param headers 头信息
     * @return String
     * @throws Exception
     */
    public static String post(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        HttpPost method = new HttpPost(url);
        config(method); // *请求的配置项，构造器可以动态配置
        // 请求的参数信息传递
        List<NameValuePair> paires = new ArrayList<NameValuePair>();
        if (params != null) {
            Set<String> keys = params.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                paires.add(new BasicNameValuePair(key, params.get(key))); //URLDecoder.decode(params.get(key), "UTF-8"))
            }
        }
        if (paires.size() > 0) {
            HttpEntity entity = new UrlEncodedFormEntity(paires, "utf-8"); //编码方式
            method.setEntity(entity);
        }
        logger.info("URL:"+url+"\n"+"params="+params+"\n"+"head="+headers.toString());
        return postMethod(method,params, headers);
    }


    private static String postMethod(HttpRequestBase method,Map<String,String> params,  Map<String, String> headers)  throws Exception {
        HttpClient client = HttpClients.createDefault();
        method.setHeader("charset", "utf-8");
        String body=null;
        HttpEntity entity=null;
        CloseableHttpResponse response=null;
        try {
            if (headers != null) {
                Set<String> keys = headers.keySet();
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    method.setHeader(key, headers.get(key));
            }
        }
            response= (CloseableHttpResponse) client.execute(method);
            logger.info(response.getEntity());
        int status = response.getStatusLine().getStatusCode();
        if (status < 200 || status >= 300) {
              throw new ClientProtocolException("url:" + method.getURI() + "-Unexpected response status: " + status);
        }
             entity= response.getEntity();
             body = EntityUtils.toString(entity, "UTF-8");
        }
        finally {
           close(entity, method, response);
          // logger.info("Release all connections resources.");
        }
        return body;
    }

    public static BasicCookieStore getCookie(String url, Map<String, String> params, Map<String, String> headers) throws  Exception{
        post( url,  params,  headers);
        return cookieStore;
    }

    // 获取页面代码
    private static String getMethod(HttpRequestBase method, Map<String, String> headers) throws Exception {

        HttpClient client = HTTPCLIENT.getHttpClient(); //创建httpClient对象
        HttpEntity entity=null;
        CloseableHttpResponse response=null;
        String body=null;
        method.setHeader("charset", "utf-8");
       try {
           // 默认超时时间为15s。
           if (headers != null) {
               Set<String> keys = headers.keySet();
               Iterator<String> iterator = keys.iterator();
               while (iterator.hasNext()) {
                   String key = iterator.next();
                   method.setHeader(key, headers.get(key));
               }
           }
           response  = (CloseableHttpResponse) client.execute(method);
           int status = response.getStatusLine().getStatusCode();
           if (status < 200 || status >= 300) {
               throw new ClientProtocolException("Path:" + method.getURI() + "-Unexpected response status: " + status);
           }
           entity= response.getEntity();
           body= EntityUtils.toString(entity, "UTF-8");
       }finally {
          // logger.info("Release all connections resources.");
           close(entity,method,response);
       }
        return body;
    }

    // 获取页面代码
    private static String getHead(HttpRequestBase method, Map<String, String> headers) throws Exception {
        HttpClient client = HTTPCLIENT.getHttpClient(); //创建httpClient对象
        CloseableHttpResponse response=null;
        HttpEntity entity=null;
        StringBuilder sb=null;
        try {
            method.setHeader("charset", "utf-8");
            // 默认超时时间为15s。
            if (headers != null) {
                Set<String> keys = headers.keySet();
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    method.setHeader(key, headers.get(key));
                }
            }
            response = (CloseableHttpResponse) client.execute(method);
            Header[] responseHeaders = response.getAllHeaders();
            sb= new StringBuilder("");
            for (Header h : responseHeaders) {
                sb.append(h.getName() + ":" + h.getValue() + "\r\n");
            }
        }finally {
            close(entity,method,response);
        }
        return sb.toString();
    }



    public static String postBody(String url, String body, Map<String, String> headers) throws Exception {
        HttpClient client = HTTPCLIENT.getHttpClient(); //创建httpClient对象

        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("charset", "utf-8");
        if (headers != null) {
            Set<String> keys = headers.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key =  iterator.next();
                httppost.setHeader(key, headers.get(key));
            }
        }

        BasicHttpEntity requestBody = new BasicHttpEntity();
        requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
        requestBody.setContentLength(body.getBytes("UTF-8").length);
        httppost.setEntity(requestBody);
        // 执行客户端请求
        CloseableHttpResponse response = (CloseableHttpResponse)client.execute(httppost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "UTF-8");
    }

    /**
     *
     * @param url
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String gethURL(String url, Map<String, String> params) throws UnsupportedEncodingException {

       return (null == params ? url : url + "?" + parseParam(params));

    }


    public static String getUrl(String host,String url) {
        return "http://"+host+url;
    }
    /**
     * // 配置请求的超时
     * @param httpRequestBase
     */
    private static void config(HttpRequestBase httpRequestBase) {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(50000)
                .setConnectTimeout(30000)
                .setSocketTimeout(50000)
                .build();
        httpRequestBase.setConfig(requestConfig);
    }
    /**
     * 参数拼接
     *
     * @param params
     * @return
     */
    public static String parseParam(Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (String key : params.keySet()) {
            sb.append(key + "=" + params.get(key) + "&");
        }
        return sb.substring(0, sb.length() - 1);
    }


    /**
     * 关闭http
     *
     * @param entity
     * @param request
     * @param response
     * @throws IOException
     */
    private static void close(HttpEntity entity, HttpRequestBase request, CloseableHttpResponse response) throws IOException {
        if (request != null)
            request.releaseConnection();
        if (entity != null)
            entity.getContent().close();
        if (response != null)
            response.close();

    }



    public static InputStream getInputStream(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3 * 1000);
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("User-Agent",
                "User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setUseCaches(false);// 不进行缓存
        // 头部必须设置不缓存，否则第二次获取不到sessionID
        conn.setUseCaches(false);
        if (conn.getResponseCode() == 200) {
            return conn.getInputStream();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String url="http://launcher-test.tclclouds.com/tlauncher-api/api/kvp";
        Map<String,String> parms=new HashMap<>();
        Map<String,String> head=new HashMap<>();
        parms.put("key","");
        String res=post(url,parms,head);
        System.out.println(res);

    }
}
