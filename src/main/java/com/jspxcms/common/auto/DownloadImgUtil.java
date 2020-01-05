package com.jspxcms.common.auto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DownloadImgUtil {

	/**
	 * 
	 * @param url  图片路径
	 * @param fileName 文件名称
	 * @param fileResuce 图片来源
	 * @param imageFormat  图片类型
	 * @param headerType
	 */
	public static void downloadImages(String url, String fileName,String fileResuce,String imageFormat ) {
        // 创建httpclient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
       
        String path=DownloadImgUtil.class.getResource("/").getPath();
		path=path.replaceFirst("/", "").replace("target/classes/", "");
		path=path+"src/main/webapp/data/"+fileResuce+"/";
		
		 File myPath = new File(path);
		   if ( !myPath.exists()){//若此目录不存在，则创建之// 这个东西只能简历一级文件夹，两级是无法建立的。。。。。
		           myPath.mkdir();
		           System.out.println("创建文件夹路径为："+ path);
		}
        
        // Http请求
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse pictureResponse = httpclient.execute(httpGet);
            HttpEntity pictureEntity = pictureResponse.getEntity();
            InputStream inputStream = pictureEntity.getContent();

            // 使用 common-io 下载图片到本地，注意图片名不能重复  
            FileUtils.copyToFile(inputStream, new File( path+ fileName +"."+ imageFormat));
             pictureResponse.close(); // pictureResponse关闭

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
