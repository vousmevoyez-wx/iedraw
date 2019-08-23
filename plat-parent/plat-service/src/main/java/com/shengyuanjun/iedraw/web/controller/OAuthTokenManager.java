//package com.shengyuanjun.iedraw.web.controller;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtBuilder;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.apache.commons.lang.StringUtils;
//import org.apache.tomcat.util.codec.binary.Base64;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.HashMap;
//
///**
// * OAuthTokenUtils
// * Token管理*/
//public class OAuthTokenManager {
//    private String APP_ID = "";
//    private String APP_SECRET = "";
//    private String KEY_SING =  ""; //用於存放TOKEN的標誌,Redis
//    private LinkedHashMap<string, object=""> pairs = new LinkedHashMap();//封装json的map
//    private CommonService service;
//    public static final int MINUTE_TTL = 60*1000;  //millisecond
//    public static final int HOURS_TTL = 60*60*1000;  //millisecond
//    public static final int DAY_TTL = 12*60*60*1000;  //millisecond
//
//
//
//    private OAuthTokenManager() {}
//    private static OAuthTokenManager single=null;
//    public static OAuthTokenManager getInstance() {
//            if (single == null) {
//                single = new OAuthTokenManager();
//            }
//            return single;
//        }
//
//    public String getKEY_SING() {
//        return KEY_SING;
//    }
//
//    public void setPairs(LinkedHashMap<string, object=""> pairs) {
//        this.pairs = pairs;
//    }
//    public LinkedHashMap<string, object=""> getPairs() {
//        return pairs;
//    }
//
//    public void put(String key, Object value){//向json中添加属性，在js中访问，请调用data.map.key
//        pairs.put(key, value);
//    }
//
//    public void remove(String key){
//        pairs.remove(key);
//    }
//
//    /**
//     */
//    public String token(String appid,String secret,LogicInterface logicInterface){
//        //获取appid和secret
//        this.accessPairs(appid,secret);
//        //验证appid和secretS，获取对象载体
//        Object subject = this.loginAuthentication(logicInterface);
//        //生成JWT签名数据ToKen
//        String token = this.createToken(this.generalSubject(subject),this.MINUTE_TTL);
//        return token;
//    }
//
//    public void accessPairs(String APP_ID, String APP_SECRET) {
//        this.APP_ID = APP_ID;
//        this.APP_SECRET = APP_SECRET;
//        //this.KEY_SING = MD5Util.MD5Encode(APP_ID+"_"+APP_SECRET, "UTF-8").toUpperCase();//要用到的时候才用
//    }
//
//    public Object loginAuthentication(LogicInterface logicInterface){
//        if (StringUtils.isNotBlank(APP_ID) && StringUtils.isNotBlank(APP_SECRET)) {
//                Map<string, object=""> map = new HashMap<>();
//                map.put("APP_ID",APP_ID);
//                map.put("APP_SECRET",APP_SECRET);
//                if(logicInterface == null || logicInterface.handler(map) == null){
//                    return map;
//                }else {
//                    return logicInterface.handler(map);
//                }
//        } else {
//            return null;
//        }
//    }
//    /**
//     * 由字符串生成加密key
//     * @return
//     */
//    public SecretKey generalKey(){
//        String stringKey = APP_ID+APP_SECRET;
//        byte[] encodedKey = Base64.decodeBase64(stringKey);
//        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//        return key;
//    }
//    /**
//     * 生成subject信息
//     * @param obj
//     * @return
//     */
//    public static String generalSubject(Object obj){
//        if(obj != null ) {
//            JSONObject json = JSONObject.fromObject(obj);
//            return json.toString();
//        }else{
//            return "{}";
//        }
//
//    }
//
//    /**
//     * 创建token
//     * @param subject
//     * @param ttlMillis
//     * @return
//     * @throws Exception
//     */
//    public String createToken(String subject, long ttlMillis) {
//
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//        SecretKey key = generalKey();
//        JwtBuilder builder = Jwts.builder()
//                .setId(APP_ID)
//                .setIssuedAt(now)
//                .setSubject(subject)
//                .signWith(signatureAlgorithm, key);
//        if (ttlMillis >= 0) {
//            long expMillis = nowMillis + ttlMillis;
//            Date exp = new Date(expMillis);
//            builder.setExpiration(exp);
//        }
//        return builder.compact();
//    }
//
//    /**
//     * 解密token
//     * @param token
//     * @return
//     * @throws Exception
//     */
//    public Claims validateToken(String token) throws Exception{
//        Claims claims = Jwts.parser()
//                .setSigningKey(generalKey())
//                .parseClaimsJws(token).getBody();
//        /*System.out.println("ID: " + claims.getId());
//        System.out.println("Subject: " + claims.getSubject());
//        System.out.println("Issuer: " + claims.getIssuer());
//        System.out.println("Expiration: " + claims.getExpiration());*/
//        return claims;
//    }
//}