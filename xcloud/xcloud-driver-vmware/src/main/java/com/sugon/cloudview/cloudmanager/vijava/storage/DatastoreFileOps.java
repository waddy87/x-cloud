package com.sugon.cloudview.cloudmanager.vijava.storage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

import com.sugon.cloudview.cloudmanager.vijava.impl.Session;
import com.sugon.cloudview.cloudmanager.vijava.util.HttpsConnectionUtil;
import com.sugon.vim25.VimPortType;
import com.sugon.vim25.mo.*;

public class DatastoreFileOps { 
	 
	 
 /***
  * 已提供 1.文件名处理 2.本地路径处理 
  * 需要补充
  * 1. 服务器端同名检查
  * 2. 进度
  * 3. 服务器端路径（已确认）
  * 
  * */
    public void uploadFileToStore(String localFilePath,String remoteFileName,String datacenterName,String datastoreName,String token) throws Exception { 
 
 
       ServiceInstance si = Session.getInstanceByToken(token);
       Folder rootFolder = si.getRootFolder(); 
 
 
       InventoryNavigator inv = new InventoryNavigator(rootFolder); 
 

      // Datacenter dc = retrieveDatacenter(inv); 
       //String datacenter = dc.getName(); 
       String    datacenter = datacenterName;
       String    dsName = datastoreName;
      // String localFilePath = "E://cloudmanager_2.ova"; 
       String split[] = localFilePath.replaceAll("\\\\", "/").replaceAll("//", "/").split("/");
       String filename = remoteFileName;
    		   if(null == filename || "".equals(filename))
    			   filename = split[split.length-1];
       
       String url = si.getServerConnection().getUrl().toString(); 
       String serviceUrl =  url.substring(0, url.lastIndexOf("sdk") - 1); 
       String httpUrl = serviceUrl + "/folder/111111/"+filename+"?dcPath=" + datacenter + "&dsName="+dsName; 
       httpUrl = httpUrl.replaceAll("\\ ", "%20"); 
       System.out.println("Uploading local file to " + httpUrl); 
       URL fileURL = new URL(httpUrl); 
       HttpsConnectionUtil.trustAllHosts(); 
       HttpsURLConnection conn = (HttpsURLConnection) fileURL.openConnection(); 
       conn.setHostnameVerifier(HttpsConnectionUtil.DO_NOT_VERIFY); 
       conn.setDoInput(true); 
       conn.setDoOutput(true); 
       conn.setAllowUserInteraction(true); 
 
 
       // Extract cookie from ServiceInstance connection. 
       VimPortType vimPort = si.getServerConnection().getVimService(); 
       String cookieValue = vimPort.getWsc().getCookie(); 
       StringTokenizer tokenizer = new StringTokenizer(cookieValue, ";"); 
       cookieValue = tokenizer.nextToken(); 
       String path = "$" + tokenizer.nextToken(); 
       String cookie = "$Version=\"1\"; " + cookieValue + "; " + path; 
 
 

       conn.setRequestProperty("Cookie", cookie); 
       conn.setRequestProperty("Content-Type", "application/octet-stream"); 
       conn.setRequestMethod("PUT"); 
       conn.setRequestProperty("Content-Length", "1024"); 
       long fileLen = new File(localFilePath).length(); 
       System.out.println("File size is: " + fileLen); 
       conn.setChunkedStreamingMode((int) fileLen); 
       OutputStream out = conn.getOutputStream(); 
       InputStream in = new BufferedInputStream(new FileInputStream(localFilePath)); 
       int bufLen = 9 * 1024; 
       byte[] buf = new byte[bufLen]; 
       byte[] tmp = null; 
       int len = 0; 
       while ((len = in.read(buf, 0, bufLen)) != -1) { 
          tmp = new byte[len]; 
          System.arraycopy(buf, 0, tmp, 0, len); 
          out.write(tmp, 0, len); 
       } 
       in.close(); 
       out.close(); 
       System.out.println(conn.getConnectTimeout());
       System.out.println(conn.getResponseCode());
       System.out.println(conn.getResponseMessage()); 
       conn.disconnect(); 
    } 
 
 
    public static void main(String[] args) throws Exception { 
       DatastoreFileOps obj = new DatastoreFileOps(); 
       String localFilePath = "E://cloudmanager_2.ova"; 
       String remoteFileName = "test.ova";
       String datacenterName = "Datacenter";
       String datastoreName = "ipsan3669";
       obj.uploadFileToStore(localFilePath, remoteFileName, datacenterName, datastoreName,"");
    } 
 
 
 } 
