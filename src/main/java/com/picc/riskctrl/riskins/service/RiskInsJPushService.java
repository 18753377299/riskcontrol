package com.picc.riskctrl.riskins.service;

import com.alibaba.fastjson.JSONArray;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.riskins.po.RiskReportMessageSend;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class RiskInsJPushService{

	@Autowired
	private DataSourcesService dataSourcesService;

	// 极光推送常用常量
	private static final String APPKEY = "9bdd55ad77fa056972f3ae8c";
	private static final String MASTER_SECRET = "779de320a3f259634bf395e1";
	private static final int TIME_TO_LIVE = 864000;// 离线消息保存时间:10天
	private static final boolean APNS_PRODUCTION = true;// true:生产环境,false:开发环境
	
	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

	/**
	 * @功能：向单个账户发送通知消息(別名) @param messageSend
	 * @return String
	 * @throws @作者：王亚军
	 * 			@日期：2018-02-05 @修改记录：
	 */
	public String singleAccountPush(RiskReportMessageSend messageSend) {
		String result = "";
		String pushUrl = "";
		ResourceBundle bundle = ResourceBundle.getBundle("config.jPush", Locale.getDefault());
		pushUrl = bundle.getString("pushUrl");// 极光服务器请求地址

		try {
			System.out.println("开始推送了......");
			result = jPush(pushUrl, messageSend);
			JSONObject resData = JSONObject.fromObject(result);
			if (resData.has("error")) {
				System.out.println("针对别名为" + messageSend.getUserCode() + "的信息推送失败！");
				JSONObject error = JSONObject.fromObject(resData.get("error"));
				System.out.println("错误信息为：" + error.get("message").toString());
			} else {
				System.out.println("针对别名为" + messageSend.getUserCode() + "的信息推送成功！");
			}
		} catch (Exception e) {
			LOGGER.info("消息推送异常：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("消息推送异常:"+e);
		}
		return result;
	}

	/**
	 * @功能：极光推送-调用极光api
	 * @param pushUrl,messageSend
	 * @return String
	 * @throws @作者：王亚军
	 * 			@日期：2018-02-05 @修改记录：
	 */
	public String jPush(String pushUrl, RiskReportMessageSend messageSend) {
		//!!String base64_auth_string = encryptBASE64(APPKEY + ":" + MASTER_SECRET);
		//String authorization = "Basic " + base64_auth_string;
		String authorization = "Basic ";
		return sendPostRequest(pushUrl, generateJson(messageSend).toString(), "UTF-8", authorization);
	}

	/**
	 * @功能：组装极光推送专用json串
	 * @param messageSend
	 * @return JSONObject
	 * @throws @作者：王亚军
	 * 			@日期：2018-02-05 @修改记录：
	 */
	public JSONObject generateJson(RiskReportMessageSend messageSend) {

		JSONObject json = new JSONObject();
		// 声明推送平台
		try {
			JSONArray platform = new JSONArray();
			if ("1".equals(messageSend.getMobileFlag())) {
				platform.add("android");
			} else {
				platform.add("ios");
			}
			// 推送全部平台
			// platform.add("all");

			// 推送目标
			JSONObject audience = new JSONObject();
			JSONArray alias1 = new JSONArray();
			alias1.add(messageSend.getUserCode());
			audience.put("alias", alias1);

			// 通知内容
			JSONObject notification = new JSONObject();
			if ("1".equals(messageSend.getMobileFlag())) {
				// android通知内容
				JSONObject android = new JSONObject();
				android.put("alert", messageSend.getMessage());
				android.put("title", messageSend.getTitle());
				android.put("builder_id", 1);
				JSONObject android_extras = new JSONObject();// android额外参数
				android_extras = getExtraData(messageSend);
				android.put("extras", android_extras);
				notification.put("android", android);
			} else {
				// ios通知内容
				JSONObject ios = new JSONObject();
				ios.put("alert", messageSend.getMessage());
				ios.put("sound", "default");
				ios.put("badge", "+1");
				JSONObject ios_extras = new JSONObject();// ios额外参数
				ios_extras = getExtraData(messageSend);
				ios.put("extras", ios_extras);
				notification.put("ios", ios);
			}

			// 消息内容
			JSONObject message = new JSONObject();
			message.put("msg_content", messageSend.getMessage());
			message.put("title", messageSend.getTitle());
			message.put("content_type", "text");
			JSONObject msg_extras = new JSONObject();// 消息额外参数
			msg_extras = getExtraData(messageSend);
			message.put("extras", msg_extras);

			// 设置参数
			JSONObject options = new JSONObject();
			options.put("time_to_live", Integer.valueOf(TIME_TO_LIVE));
			options.put("apns_production", APNS_PRODUCTION);

			json.put("platform", platform);
			json.put("audience", audience);
			json.put("notification", notification);
			json.put("message", message);
			json.put("options", options);
		} catch (Exception e) {
			LOGGER.info("组装极光推送专用json串失败：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("组装极光推送专用json串失败:"+e);
		}
		return json;

	}

	/**
	 * @功能：发送Post请求（json格式） @param pushUrl,data,encodeCharset,authorization
	 * @return String
	 * @throws @作者：王亚军
	 * 			@日期：2018-02-05 @修改记录：
	 */
	public String sendPostRequest(String pushUrl, String data, String encodeCharset, String authorization) {
		HttpClient client = new DefaultHttpClient();
		client = getHttpClient();
		HttpPost httpPost = new HttpPost(pushUrl);
		HttpResponse response = null;
		String result = "";
		System.out.println("json串------" + data);
		try {
			StringEntity entity = new StringEntity(data, encodeCharset);
			entity.setContentType("application/json");
			httpPost.setHeader("Authorization", authorization.trim());
			httpPost.setEntity(entity);
			response = client.execute(httpPost);
			// 获取返回结果
			HttpEntity entry = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			result = EntityUtils.toString(entry, encodeCharset);
			System.out.println("返回结果：" + result);
		} catch (ClientProtocolException e) {
			LOGGER.info("发送Post请求失败：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("发送Post请求失败:"+e);
		} catch (IOException e) {
			LOGGER.info("发送Post请求失败：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("发送Post请求失败:"+e);
		} catch (Exception e) {
			LOGGER.info("发送Post请求失败：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("发送Post请求失败:"+e);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return result;
	}

	/**
	 * @功能：组装自定义数据
	 * @param messageSend
	 * @return JSONObject
	 * @throws @作者：王亚军
	 * 			@日期：2018-02-06 @修改记录：
	 */
	public JSONObject getExtraData(RiskReportMessageSend messageSend) {
		JSONObject extras = new JSONObject();
		Map<String, Object> map = new TreeMap<String, Object>();

		String businessNo = messageSend.getBusinessNo();
		String[] businessNoList = businessNo.split("_");
		map.put("archives_no", businessNoList[0]);
		map.put("image_name", businessNoList[1]);
		map.put("serial_no", businessNoList[2]);
		// RiskDcode riskDcode =
		// dataSourcesService.translateImageCategory("riskReportTree002",
		// businessNoList[1].substring(0, businessNoList[1].lastIndexOf(".")));
		// if(riskDcode != null) {
		// map.put("image_type", riskDcode.getCodeCname());
		// }
		map.put("image_type", "jknjk");
		Set<String> keySet = map.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			extras.put(key, (String) map.get(key));
		}
		return extras;
	}

	/**
	 * @功能：BASE64加密工具
	 * @param str
	 * @return String
	 * @throws @作者：王亚军
	 * 			@日期：2018-02-05 @修改记录：
	 */
	public static String encryptBASE64(String str) {
		/*
		byte[] key = str.getBytes();
		BASE64Encoder base64Encoder = new BASE64Encoder();
		String strNew = base64Encoder.encodeBuffer(key);
		return strNew;
		*/
		return null;
	}

	/**
	 * @功能：设置代理
	 * @param
	 * @return HttpClient
	 * @throws @作者：王亚军
	 * 			@日期：2018-02-05 @修改记录：
	 */
	public static HttpClient getHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// String proxyHost = "Proxy.piccnet.com.cn";
		// int proxyPort = 3128;
		String proxyHost = "10.130.67.170";
		int proxyPort = 10011;		
		
		// String userName = "71012993";
		// String password = "77CD8DE7";
		// httpClient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost,
		// proxyPort),
		// new UsernamePasswordCredentials(userName, password));
		HttpHost proxy = new HttpHost(proxyHost, proxyPort);
		httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		return httpClient;
	}

}
