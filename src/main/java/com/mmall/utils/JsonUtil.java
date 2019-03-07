package com.mmall.utils;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有字段全列入ALWAYS
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        //取消默认转化timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        //忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        //所有日期格式统一为以下样式
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.DEFAULTTIMEFORMAT));

        //忽律在json字符串中存在，但是在java对象中不存在对于属性的请，防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static <T> String obj2String(T obj){
        if (null==obj){
            return null;
        }

        try {
            return obj instanceof String ?(String)obj:objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("parse object to String error",e);
            return null;
        }
    }


    public static <T> String obj2StringPretty(T obj){
        if (null==obj){
            return null;
        }

        try {
            return obj instanceof String ?(String)obj:objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("parse object to String error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str,Class<T> clazz){
        if (StringUtils.isEmpty(str) || null ==clazz){
            return null;
        }

        try {
            return clazz.equals(String.class)?(T)str:objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            log.warn("parse String to object  error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if (StringUtils.isEmpty(str) || null ==typeReference){
            return null;
        }

        try {
            return (T)(typeReference.getType().equals(String.class)?str:objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("parse String to object  error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClass,Class<?>...elementClasses){

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            log.warn("parse String to object  error",e);
            return null;
        }
    }


    public static void main(String[] args) {
        User user1 = new User();
        user1.setUsername("zouxinqi");
        user1.setQuestion("who am i");

        User user2 = new User();
        user2.setUsername("shenjie");
        user2.setQuestion("who am i");

        String user1Json = obj2String(user1);
        String user1JsonPretty = obj2StringPretty(user1);
        log.info("user1Json:{}          user1JsonPretty{}",user1Json,user1JsonPretty);

        log.info("user:{}",string2Obj(user1Json,User.class));
        log.info("===========================");

        List<User> userList = Lists.newArrayList();
        userList.add(user1);
        userList.add(user2);
        String userListStr =obj2StringPretty(userList);
        log.info("userListStr:{}",userListStr);

        List<User> userListObj1 = string2Obj(userListStr, new TypeReference<List<User>>() {});

        List<User> userListObj2 = string2Obj(userListStr,List.class,User.class);
        log.info("userListObj1:{}           userListObj2:{}",userListObj1,userListObj2);
    }
}
