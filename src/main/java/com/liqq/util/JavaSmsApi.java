package com.liqq.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Short Messaging Service 短信服务
 * @author Administrator
 *
 */
public class JavaSmsApi {
	
	private static Logger log = LoggerFactory.getLogger(JavaSmsApi.class);

    // 查账户信息的http地址
    private static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

    // 通用发送接口的http地址
    private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";

    // 模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "http://yunpian.com/v1/sms/tpl_send.json";

    // 发送语音验证码接口的http地址
    private static String URI_SEND_VOICE = "http://yunpian.com/v1/voice/send.json";

    // 编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";
    
    private static final String apikey="c1c86fd7b57717e86f9cb3da25a0f5fd";

    public static void main(String[] args) throws IOException, URISyntaxException {

        // 修改为您的apikey.apikey可在官网（http://www.yuanpian.com)登录后用户中心首页看到
        String apikey = "c1c86fd7b57717e86f9cb3da25a0f5fd";

        // 修改为您要发送的手机号
        String mobile = "xxxxxxxxxxx";

        /**************** 查账户信息调用示例 *****************/
        System.out.println(JavaSmsApi.getUserInfo());

        /**************** 使用通用接口发短信(推荐) *****************/
        // 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        String text = "【云片网】您的验证码是1234";
        // 发短信调用示例
        System.out.println(JavaSmsApi.sendSms(text, mobile));

        /**************** 使用模板接口发短信(不推荐，建议使用通用接口) *****************/
        // 设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
        String tpl_id = "1";
        // 设置对应的模板变量值
        // 如果变量名或者变量值中带有#&=%中的任意一个特殊符号，需要先分别进行urlencode编码
        // 如code值是#1234#,需作如下编码转换
        String codeValue = URLEncoder.encode("#1234#", ENCODING);
        String tpl_value = "#code#=" + codeValue + "&#company#=云片网";
        // 模板发送的调用示例
        System.out.println(JavaSmsApi.tplSendSms(tpl_id, tpl_value, mobile));

        /**************** 使用接口发语音验证码 *****************/
        String code = "1234";
        System.out.println(JavaSmsApi.sendVoice(apikey, mobile, code));
    }

    /**
     * 取账户信息
     * 
     * @return json格式字符串
     * @throws java.io.IOException
     */

    public static String getUserInfo() throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        return post(URI_GET_USER_INFO, params);
    }

    /**
     * 通用接口发短信
     * 
     * @param apikey
     *            apikey
     * @param text
     *            　短信内容
     * @param mobile
     *            　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */

    public static String sendSms(String text, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        return post(URI_SEND_SMS, params);
    }

    /**
     * 通过模板发送短信(不推荐)
     * 
     * @param apikey
     *            apikey
     * @param tpl_id
     *            　模板id
     * @param tpl_value
     *            　模板变量值
     * @param mobile
     *            　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */

    public static String tplSendSms(String tpl_id, String tpl_value, String mobile){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("tpl_id", tpl_id);
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        //String post = "测试不发送";
        String post = post(URI_TPL_SEND_SMS, params);//生产发送
        log.info(mobile+"短信模板id" +tpl_id+ "；短信发送内容：" + tpl_value + "响应结果：" + post);
        return post;
    }

    /**
     * 通过接口发送语音验证码
     * 
     * @param apikey
     *            apikey
     * @param mobile
     *            接收的手机号
     * @param code
     *            验证码
     * @return
     */

    public static String sendVoice(String apikey, String mobile, String code) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("mobile", mobile);
        params.put("code", code);
        return post(URI_SEND_VOICE, params);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     * 
     * @param url
     *            提交的URL
     * @param paramsMap
     *            提交<参数，值>Map
     * @return 提交响应
     */

    private static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                response.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
