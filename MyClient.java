package com.ocr.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
// <dependency>
// 	<groupId>org.apache.httpcomponents</groupId>
// 	<artifactId>httpclient</artifactId>
// 	<version>4.4</version>
// </dependency>
@SuppressWarnings("deprecation")
public class MyClient {
	private HttpClient client = new DefaultHttpClient(new ThreadSafeClientConnManager());
	private HttpPost httpPost = null;
	private HttpResponse response = null;
	private HttpGet httpGet;
	private String userAgent="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36";
	public List<Cookie> getCookies(){
		List<Cookie> cookies = ((AbstractHttpClient) client).getCookieStore().getCookies();
		if (cookies.isEmpty()) {  
            return null;
        } else {
        	return cookies;
        }
	}
	public String doGetSaveByImage(String path,String url) throws ClientProtocolException, IOException{
		httpGet=new HttpGet(url);
        response=client.execute(httpGet);
        HttpEntity entity=response.getEntity();
		byte[] allbuf=EntityUtils.toByteArray(entity);
		if(allbuf.length==0){
			return "error";
		}
		else{
	        InputStream sbs = new ByteArrayInputStream(allbuf); 
	        BufferedInputStream in1=new BufferedInputStream(sbs);
	        String s1=path+"/"+UUID.randomUUID().toString()+".jpg";
			File img=new File(s1);
			BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(img));
			byte[] buf=new byte[1024];
			int length=in1.read(buf);
			while(length!=-1)
			{
				out.write(buf, 0, length);
				length=in1.read(buf);
			}
			in1.close();
			out.close();
			return s1;
		}
	}
	private List<NameValuePair> mapToList(Map<String,String> map){
		List<NameValuePair> nvps=null;
		if(map!=null&&map.size()!=0){
			nvps=new ArrayList<NameValuePair>();
			Set<String> key = map.keySet();
			String s=null;
	        for (Iterator<String> it = key.iterator(); it.hasNext();) {
	            s=it.next();
	    		nvps.add(new BasicNameValuePair(s,map.get(s)));
	        }
		}
		return nvps;
	}
	public String doPostNeedLocation(String url,Map<String,String> map,String prefixUrl) throws ParseException, IOException{
		List<NameValuePair> nvps=mapToList(map);
		return doPostNeedLocation(url,nvps,prefixUrl);
	}
	public String doPostNeedLocation(String url,List<NameValuePair> nvps,String prefixUrl) throws ParseException, IOException{
		httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		response = client.execute(httpPost);
		Header locationHeader = response.getFirstHeader("Location");
		return getHtml(prefixUrl+locationHeader.getValue());
	}
	public String doPost(String url,Map<String,String> map) throws ParseException, IOException{
		List<NameValuePair> nvps=mapToList(map);
		return doPost(url,nvps);
	}
	public String doPost(String url, List<NameValuePair> nvps)
			throws ParseException, IOException {
		httpPost = new HttpPost(url);
		httpPost.setHeader("User-Agent", userAgent);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity);
		return html;
	}

	public String getHtml(String URL) {
		httpGet= new HttpGet(URL);
		httpGet.setHeader("User-Agent",userAgent);
		String res = "";
		try {
			response = client.execute(httpGet);
			if (response.getEntity()!= null)
				res = EntityUtils.toString(response.getEntity(), "utf-8");
			else
				res = "";
			return res;
		} catch (IOException e) {
			return (e.toString());
		} catch (ParseException e) {
			return (e.toString());
		}
	}
	public HttpResponse getResponse() {
		return response;
	}
	public void setResponse(HttpResponse response) {
		this.response = response;
	}
	public HttpClient getClient() {
		return client;
	}
	public void setClient(HttpClient client) {
		this.client = client;
	}
}
