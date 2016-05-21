package org.waddys.xcloud.vijava.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadSystemProperty {

	@SuppressWarnings("unchecked")
	public static CloudVMConfig loadFromJsonFileUnchecked(InputStream is) throws IOException {

		CloudVMConfig cloudVMConfig = new CloudVMConfig();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(is);

//		rootNode.fields().forEachRemaining(node -> {
//
//			String key = node.getKey();
//			if ("vcenter".equals(key)) {
//
//				List<CloudVMConfig.ConnectionInfo> lsConnInfo = new LinkedList<CloudVMConfig.ConnectionInfo>();
//
//				try {
//					lsConnInfo = (List<CloudVMConfig.ConnectionInfo>)objectMapper
//							.readValue(node.getValue().toString(), lsConnInfo.getClass());
//				} catch (Exception e) {
//				}
//				
//				CloudVMConfig.ConnectionInfo arrayConnectionInfo[lsConnInfo.l];
//				cloudVMConfig.setVcenterInfo(lsConnInfo.toArray());
//			}
//
//		});
		return cloudVMConfig;
	}
	
	public static CloudVMConfig loadFromJsonFile(String file)  {

		CloudVMConfig cloudVMConfig = null;
		try {
			URI uri = new URI(file);
			InputStream inputStream = uri.toURL().openStream();
			cloudVMConfig = loadFromJsonFileUnchecked(inputStream);
		} catch (URISyntaxException e) {
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}

//		System.out.println(cloudVMConfig);
		return cloudVMConfig;
		
	}

	public static void main(String[] args) {
		String file = "file:/D:/work/java-properties-loader/src/main/resources/config.json";
		loadFromJsonFile(file);

	}
}