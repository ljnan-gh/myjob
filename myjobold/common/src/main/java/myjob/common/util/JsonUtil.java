package myjob.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

public class JsonUtil {
    private static final ThreadLocal<Gson> gsons = new ThreadLocal<>();

    public static Gson getGson() {
        Gson gson = gsons.get();
        if (gson == null) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            gsons.set(gson);
        }
        return gson;
    }

    private static final ThreadLocal<Gson> resultGsons = new ThreadLocal<>();

    public static Gson getResultGson() {
        Gson gson = resultGsons.get();
        if (gson == null) {
            gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            resultGsons.set(gson);
        }
        return gson;
    }

    public static Type SList = new TypeToken<List<String>>() {
    }.getType();
    public static Type LList = new TypeToken<List<Long>>() {
    }.getType();
    public static Type MapSO = new TypeToken<Map<String, Object>>() {
    }.getType();
    public static Type MapSS = new TypeToken<Map<String, String>>() {
    }.getType();
    public static Type MapSL = new TypeToken<Map<String, Long>>() {
    }.getType();
    public static Type ListMapSO = new TypeToken<List<Map<String, Object>>>() {
    }.getType();
    public static Type ListMapSS = new TypeToken<List<Map<String, String>>>() {
    }.getType();

    public static Object fromJson(String str, Type t) {
        return getGson().fromJson(str, t);
    }

    public static String getJsonStr(Object obj) {
        Gson gson = getGson();
        return gson.toJson(obj);
    }

    public static String getResultJsonStr(Object obj) {
        Gson gson = getResultGson();
        return gson.toJson(obj);
    }

    public static String getJsonStr2(Object obj) {
        //添加显示空值功能
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(obj);
    }

    public static Map<String, Object> getObj(String paramJson) {
        Gson gson = getGson();
        return gson.fromJson(paramJson, HashMap.class);
    }

    public static Map<String, String> getMap(String paramJson) {
        Gson gson = getGson();
        return gson.fromJson(paramJson, HashMap.class);
    }

    /**
     * 将字符串转化成List数组
     *
     * @param paramJson
     * @return
     */
    public static List<Object> getList(String paramJson) {
        Gson gson = getGson();
        return gson.fromJson(paramJson, List.class);
    }

    /**
     * 将字符串转化成List数组
     *
     * @param paramJson
     * @return
     */
    public static <T> List<T> getList2(String paramJson, Type type) {
        Gson gson = getGson();
        ArrayList al = gson.fromJson(paramJson, type);
        return (List) al;
    }

}
