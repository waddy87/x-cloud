package org.waddys.xcloud.monitor.serviceImpl.impl;
/*package org.waddys.xcloud.monitor.serviceImpl.impl;

import java.io.File;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.waddys.xcloud.monitor.serviceImpl.entity.AdminUser;
import org.waddys.xcloud.monitor.serviceImpl.service.LoginServiceI;
import org.waddys.xcloud.monitor.serviceImpl.util.Connection;


@Service("monitor-loginServiceImpl")
public class LoginServiceImpl implements LoginServiceI {

    public static String LOGIN_ERROR_A = "登录失败，请核对SSO服务器时间与本地时间是否一致";
    public static String LOGIN_ERROR_B = "用户名或密码错误";
    public static String LOGIN_SUCCESS = "登录成功";
    public static String LGOUT_SUCCESS = "注销成功";
    public static Element token = null;
     @Value("${vcenter.ssoip}")
    private String ssoIp;
    @Qualifier("monitor-connection")
    @Autowired
    private Connection connection;


    @Override
    public String login(String userName, String pwd, Boolean isSSO) {
        if (isSSO.booleanValue()) {
            synchronized (this) {
                // sso登录
                //System.out.println("sso登录....");
                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timeString = time.format(new java.util.Date());
                //System.out.print("开始时间" + timeString);
                String SSO_URL = "https://" + this.ssoIp + "/sts/STSService";
                String[] param = { SSO_URL, userName, pwd };
                HostnameVerifier hv = new HostnameVerifier() {
                    @Override
                    public boolean verify(String urlHostName, SSLSession session) {
                        return true;
                    }
                };
                HttpsURLConnection.setDefaultHostnameVerifier(hv);
                // 暂时不做登录xuby 20160326
                // Utils.trustAllHttpsCertificates();
                //
                //
                // SecurityUtil userCert = SecurityUtil.generateKeyCertPair();
                //
                // try {
                // token = AcquireHoKTokenByUserCredentialSample.getToken(param,
                // userCert.getPrivateKey(),
                // userCert.getUserCert());
                //
                // } catch (DatatypeConfigurationException e) {
                // // TODO Auto-generated catch block
                // e.printStackTrace();
                // return LOGIN_ERROR_A;
                // } catch (Exception e) {
                // e.printStackTrace();
                // return LOGIN_ERROR_B;
                // }
                // Utils.printToken(token);

                timeString = time.format(new java.util.Date());
                //System.out.print("结束时间" + timeString);

            }


        } else {
            // 内建用户登录
            //System.out.println("内建用户登录....");
            List<AdminUser> userList = this.getAdminInfo();
            if (!validUserInfo(userName, pwd, userList)) {
                return LOGIN_ERROR_B;
            }
        }
        return LOGIN_SUCCESS;

    }

    public boolean validUserInfo(String name, String pwd, List<AdminUser> userList) {
        
        if (name.isEmpty() || name == "" || pwd.isEmpty() || pwd == "") {
            // 用户名密码不能为空
            return false;
        }
        
        if (null != userList && userList.size() > 0) {
            
            for (AdminUser user : userList) {
                if (name.equals(user.getUserName())) {
                    // 用户名匹配成功(大小写敏感)
                    String pass = this.string2MD5(pwd);
                    // 用加密后的MD5来判断
                    if (pass.equals(user.getPassword())) {
                        // 密码匹配成功
                        return true;
                    }
                }
            }

        }

        return false;
    }

    public List<AdminUser> getAdminInfo() {

        Element element = null;
        List<AdminUser> userList = new ArrayList<AdminUser>();
        String path = System.getProperty("evan.webapp") + "/WEB-INF/userInfo.xml";

        File f = new File(path);
        // documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = null;
        try {
            // 返回documentBuilderFactory对象
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            Document dt = db.parse(f);
            element = dt.getDocumentElement();
            NodeList childNodes = element.getChildNodes();
            // 遍历这些子节点
            for (int i = 0; i < childNodes.getLength(); i++) {
                // 获得每个对应位置i的结点
                Node node1 = childNodes.item(i);
                if ("Account".equals(node1.getNodeName())) {
                    // 如果节点的名称为"Account"，则输出Account元素属性type
                    // 获得<Accounts>下的节点
                    NodeList nodeDetail = node1.getChildNodes();
                    AdminUser adminUser = new AdminUser();
                    // 遍历<Accounts>下的节点
                    for (int j = 0; j < nodeDetail.getLength(); j++) {

                        // 获得<Accounts>元素每一个节点
                        Node detail = nodeDetail.item(j);
                        if ("username".equals(detail.getNodeName())) {

                            adminUser.setUserName(detail.getTextContent());
                        } else if ("password".equals(detail.getNodeName())) {
                            adminUser.setPassword(detail.getTextContent());
                        }

                    }
                    userList.add(adminUser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    // 加密算法
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            //System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = (md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }



}
*/