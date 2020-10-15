package com.yechtech.dac.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpClient4.3工具类
 *
 * @author hang.luo
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    //    private static CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";
    private static ThreadLocal<Map<String,String>> httpHeader = new ThreadLocal<Map<String,String>>();
    private static ThreadLocal<Map<String,Object>> httpClientConfig = new ThreadLocal<Map<String,Object>>();

    //连接超时时间
    public static final String CONNECT_TIMEOUT= "connect_timeout";
    //socket超时时间
    public static final String SOCKET_TIMEOUT= "socket_timeout";
    public static final Integer DEFAULT_CONNECT_TIMEOUT = 600000;
    public static final Integer DEFAULT_SOCKET_TIMEOUT = 600000;

    public static CloseableHttpClient buildHttpClient(String accessToken){
        Map<String,Object> configSetting = new HashMap<String,Object>();
        if(httpClientConfig!=null && null!=httpClientConfig.get()){
            configSetting = httpClientConfig.get();
        }
        RequestConfig.Builder builder = RequestConfig.custom();
        Integer connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        if(configSetting.get(CONNECT_TIMEOUT)!=null){
            try{
                connectTimeout = Integer.valueOf(configSetting.get(CONNECT_TIMEOUT).toString());
            }catch(Exception e){
                logger.warn("class covert error!",e);
                connectTimeout = DEFAULT_CONNECT_TIMEOUT;
            }
        }
        builder.setConnectTimeout(connectTimeout);
        Integer socketTimeout = DEFAULT_SOCKET_TIMEOUT;
        if(configSetting.get(SOCKET_TIMEOUT)!=null){
            try{
                socketTimeout = Integer.valueOf(configSetting.get(SOCKET_TIMEOUT).toString());
            }catch(Exception e){
                logger.warn("class covert error!",e);
                socketTimeout = DEFAULT_SOCKET_TIMEOUT;
            }
        }
        builder.setSocketTimeout(socketTimeout);
        RequestConfig config = builder.build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        if (StringUtils.isNotEmpty(accessToken)){
            // 设置BasicAuth
            CredentialsProvider provider = new BasicCredentialsProvider();
            // Create the authentication scope
            AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
            // Create credential pair，在此处填写用户名和密码
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("Token",accessToken);
            // Inject the credentials
            provider.setCredentials(scope, credentials);
            // Set the default credentials provider
            httpClientBuilder.setDefaultCredentialsProvider(provider);
        }
        return httpClientBuilder.setDefaultRequestConfig(config).build();
    }

    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, CHARSET);
    }
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doGet(String url,Map<String,String> params,String charset) throws IOException{
        String encoding = CHARSET;
        if(StringUtils.isNotBlank(charset)){
            encoding = charset;
        }
        return doGet(url,params,encoding,encoding);
    }

    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param reqcharset     请求编码格式
     * @param respCharset    返回编码格式
     * @return    页面内容
     */
    public static String doGet(String url,Map<String,String> params,String reqcharset,String respCharset) throws IOException{
        if(StringUtils.isBlank(url)){
            return null;
        }
        String requestCharset = CHARSET;
        if(StringUtils.isNotBlank(reqcharset)){
            requestCharset = reqcharset;
        }
        String responseCharset = CHARSET;
        if(StringUtils.isNotBlank(respCharset)){
            responseCharset = respCharset;
        }
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null ;
        HttpGet httpGet = null;
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, requestCharset));
            }
            httpGet = new HttpGet(url);
            handlerHeader(httpGet);

            httpClient = buildHttpClient(null);
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, responseCharset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            throw e;
        }finally{
            if(response!=null){
                response.close();
            }
            if(httpGet!=null){
                httpGet.releaseConnection();
            }
            if(httpClient!=null){
                httpClient.close();
            }
        }
    }




    private static void handlerHeader(HttpRequestBase requestBase) {
        if(httpHeader!=null&&httpHeader.get()!=null){
            Map<String,String> map = httpHeader.get();
            for(String key:map.keySet()){
                requestBase.addHeader(key,map.get(key));
            }
        }
    }


    public static String doPost(String url, Map<String, Object> params) throws IOException {
        return doPost(url, params, CHARSET);
    }

    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doPost(String url,Map<String,Object> params,String charset) throws IOException {
        String encoding = CHARSET;
        if(StringUtils.isNotBlank(charset)){
            encoding = charset;
        }
        return doPost(url,params,encoding,encoding);
    }

    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param reqCharset    请求编码格式
     * @param respCharset    返回编码格式
     * @return    页面内容
     */
    public static String doPost(String url,Map<String,Object> params,String reqCharset,String respCharset) throws IOException {
        if(StringUtils.isBlank(url)){
            return null;
        }
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null ;
        HttpPost httpPost = null;
        String requestCharset = CHARSET;
        if(StringUtils.isNotBlank(reqCharset)){
            requestCharset = reqCharset;
        }
        String responseCharset = CHARSET;
        if(StringUtils.isNotBlank(respCharset)){
            responseCharset = respCharset;
        }
        try {
            List<NameValuePair> pairs = null;
            if(params != null && !params.isEmpty()){
                pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,Object> entry : params.entrySet()){
                    String value = entry.getValue().toString();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
            }
            httpPost = new HttpPost(url);
            handlerHeader(httpPost);
            if(pairs != null && pairs.size() > 0){
                httpPost.setEntity(new UrlEncodedFormEntity(pairs,requestCharset));
            }
            httpClient = buildHttpClient(params.get("accessToken") == null ? null : params.get("accessToken").toString());
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity,responseCharset);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            throw e;
        }finally{
            if(response!=null){
                response.close();
            }
            if(httpPost!=null){
                httpPost.releaseConnection();
            }
            if(httpClient!=null){
                httpClient.close();
            }
        }
    }


    /**
     *  HTTP Post 获取内容
     * @param url  请求的url地址
     * @param jsonParam 请求的JSON参数
     * @param reqCharset    请求编码格式
     * @param respCharset    返回编码格式
     *
     * @return
     */
    public static String doPost(String url,String jsonParam,String reqCharset,String respCharset) throws IOException {
        if(StringUtils.isBlank(url)){
            return null;
        }
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpPost httpPost = null ;
        String requestCharset = CHARSET;
        if(StringUtils.isNotBlank(reqCharset)){
            requestCharset = reqCharset;
        }
        String responseCharset = CHARSET;
        if(StringUtils.isNotBlank(respCharset)){
            responseCharset = respCharset;
        }
        try {
            httpPost = new HttpPost(url);
            handlerHeader(httpPost);
            if(StringUtils.isNotBlank(jsonParam)){
                StringEntity entity = new StringEntity(jsonParam,CHARSET);
                entity.setContentEncoding(requestCharset);
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            httpClient = buildHttpClient(null);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, responseCharset);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            throw e;
        }finally{
            if(response!=null){
                response.close();
            }
            if(httpPost!=null){
                httpPost.releaseConnection();
            }
            if(httpClient!=null){
                httpClient.close();
            }
        }
    }



    /**
     *  HTTP Post 获取内容
     * @param url  请求的url地址
     * @param jsonParam 请求的JSON参数
     * @param charset   编码格式
     *
     * @return
     */
    public static String doPost(String url,String jsonParam,String charset ) throws IOException {
        String encoding = CHARSET;
        if(StringUtils.isNotBlank(charset)){
            encoding = charset;
        }
        return doPost(url,jsonParam,encoding,encoding);
    }


    /**
     *  HTTP Post 获取内容
     * @param url  请求的url地址
     * @param jsonParam 请求的JSON参数
     *
     * @return
     */
    public static String doPost(String url,String jsonParam) throws IOException {
        return doPost(url,jsonParam,CHARSET);
    }


    /**
     * post发送json数据
     * @param url
     * @param param
     * @return
     */
    public static String doPostJson(String url, JSONObject param) {
        HttpPost httpPost = null;
        String result = null;
        try {
            HttpClient client = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            if (param != null) {
                StringEntity se = new StringEntity(param.toString(), "utf-8");
                // post方法中，加入json数据
                httpPost.setEntity(se);
                httpPost.setHeader("Content-Type", "application/json");
            }

            HttpResponse response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }

        } catch (Exception ex) {
            logger.error("发送到接口出错", ex);
        }
        return result;
    }


    public static void setHeader(Map<String,String> header){
        if(header!=null) {
            httpHeader.set(header);
        }
    }

    public static void setConfig(Map<String,Object> config){
        if(config!=null) {
            httpClientConfig.set(config);
        }
    }

}