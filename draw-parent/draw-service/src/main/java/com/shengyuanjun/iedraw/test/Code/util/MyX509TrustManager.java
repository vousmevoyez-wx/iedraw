package com.shengyuanjun.iedraw.test.Code.util;

/**
 * @program: sendmessage20190803
 * @description:
 * @author: gq544
 * @create: 2019-08-03 18:07
 */

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 微信请求 - 信任管理器
 */
public class MyX509TrustManager implements X509TrustManager {
    public MyX509TrustManager() {
        // TODO Auto-generated constructor stub
    }
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}