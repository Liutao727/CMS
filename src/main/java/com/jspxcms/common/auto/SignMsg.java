package com.jspxcms.common.auto;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


/**
 * uc info
 * @author git
 *
 */
public class SignMsg {

	// 存url
	private static List urlList = new ArrayList();
	private static WebClient webClient;
	// 循环次数控制器
	private static int index = 0;

	public static List<Article> msgList = new ArrayList<Article>();

	// 循环采集链接
	public static void findMsgDetile(String url) {

		Document doc;
		try {
			if (webClient == null) {
				webClient = creatClient();
			}
      
		  
			com.gargoylesoftware.htmlunit.UnexpectedPage page=webClient.getPage(url); 
			// 等待JS驱动dom完成获得还原后的网页
			webClient.waitForBackgroundJavaScript(3000);
			WebResponse pageRes=page.getWebResponse();
			String xmlValue=pageRes.getContentAsString();
		 
			// 用jsoupup 操作更好操作
			Map mapValue=JSON.parseObject(xmlValue) ;
	 
			Map dataMap =JSON.parseObject(mapValue.get("data").toString());
		    System.err.println(dataMap.get("title"));
		   //body层
		    Map bodyMap =JSON.parseObject(dataMap.get("body").toString());
		    //获取图片
		    List imgList =   JSON.parseArray(bodyMap.get("inner_imgs").toString(), Map.class);
		    if(imgList!=null&&imgList.size()>0) {
		    	for (Object strObj : imgList) {  
		    		Map imgMap=  (Map)strObj;
				    System.err.println(imgMap.get("srcUrl")); 
				    System.err.println(imgMap.get("format")); 
				    Date date =new Date();
				    
				    DownloadImgUtil.downloadImages(imgMap.get("srcUrl").toString(),   date.getTime()+"" , "uc", imgMap.get("format").toString() );
				}
		    	 
		    }
		    System.err.println();
			// webClient.close();//关闭窗口

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 
	/**
	 * 创建一个工人
	 */
	public static WebClient creatClient() {

		try {
			WebClient webClient1 = new WebClient(BrowserVersion.CHROME);// 创建对象
			webClient1.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true

			webClient1.getOptions().setCssEnabled(false); // 禁用css支持

			webClient1.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常

			webClient1.getOptions().setTimeout(3000); // 设置连接超时时间
			return webClient1;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
													// ，这里是10S。如果为0，则无限期等待

		
	}

 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 自动播报
		// findMsgDetile("https://www.cnblogs.com/evangline/p/8350825.html") ;
		
		  for (int i = 0; i < 1; i++) { 
			  findMsgDetile(
		  "https://ff.dayu.com/contents/origin/ee71c20a00344e1e9b23d03aa629866c?biz_id=1002&_fetch_author=1&_incr_fields=click1,click2,click3,click_total,play,like"
		  ); }
		 
		
		 
		
	}

}
