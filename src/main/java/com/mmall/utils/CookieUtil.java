package com.mmall.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = ".izou.com";
    private final static String COOKIE_NAME = "login_token";

    public static void wirteLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        //代表设置在跟目录
        ck.setPath("/");

        //单位是秒
        //如果maxage不设置的花，cookie酒不会写入硬盘，而是写在内存，只在当前页面有效
        //-1为永久有效，0为删除（设置到response中后浏览器检测cookie为0后会将起移除）
        ck.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(ck);
        log.info("write cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
    }


    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (null != cks) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(COOKIE_NAME, ck.getName())) {
                    log.info("return cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if (null != cks) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(COOKIE_NAME, ck.getName())) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    //设置为0，代表删除此cookie
                    ck.setMaxAge(0);
                    log.info("del cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
