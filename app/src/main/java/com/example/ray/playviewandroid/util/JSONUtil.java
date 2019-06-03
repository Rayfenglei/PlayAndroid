package com.example.ray.playviewandroid.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * FastJSON封装类 防止解析时异常
 */
public class JSONUtil {
    private static final String TAG = "JSONUtil";

    /**
     * 判断json格式是否正确
     *
     * @param s
     * @return
     */
    public static boolean isJsonCorrect(String s) {
//		LogUtil.i(TAG, "isJsonCorrect  <<<<     " + s + "     >>>>>>>");
        return s != null && !s.equals("[]")
                && !s.equals("{}") && !s.equals("") && !s.equals("[null]") && !s.equals("{null}") && !s.equals("null");
    }

    /**
     * 获取有效的json
     *
     * @param json
     * @return
     */
    public static String getCorrectJson(String json) {
        return isJsonCorrect(json) ? json : "";
    }

    /**
     * @param obj
     * @return
     */
    public static JSONObject parseObject(Object obj) {
        return parseObject(toJSONString(obj));
    }

    /**
     * json转JSONObject
     *
     * @param json
     * @return
     */
    public static JSONObject parseObject(String json) {
        try {
            return JSON.parseObject(getCorrectJson(json));
        } catch (Exception e) {
            LogUtil.e(TAG, "parseObject  catch \n" + e.getMessage());
        }
        return null;
    }

    /**
     * JSONObject转实体类
     *
     * @param object
     * @param clazz
     * @return
     */
    public static <T> T parseObject(JSONObject object, Class<T> clazz) {
        return parseObject(toJSONString(object), clazz);
    }

    /**
     * json转实体类
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return JSON.parseObject(getCorrectJson(json), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseObject(String json, TypeReference<T> type) {
        try {
            return JSON.parseObject(getCorrectJson(json), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param obj
     * @return
     */
    public static String toJSONString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            LogUtil.e(TAG, "toJSONString  catch \n" + e.getMessage());
        }
        return null;
    }

    /**
     * @param json
     * @return
     */
    public static JSONArray parseArray(String json) {
        try {
            return JSON.parseArray(getCorrectJson(json));
        } catch (Exception e) {
            LogUtil.e(TAG, "parseArray  catch \n" + e.getMessage());
        }
        return null;
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            return JSON.parseArray(getCorrectJson(json), clazz);
        } catch (Exception e) {
            LogUtil.e(TAG, "parseArray  catch \n" + e.getMessage());
        }
        return null;
    }

    /**
     * 格式化，显示更好看
     *
     * @param object
     * @return
     */
    public static String format(Object object) {
        try {
            return JSON.toJSONString(object, true);
        } catch (Exception e) {
            LogUtil.e(TAG, "format  catch \n" + e.getMessage());
        }
        return null;
    }

    /**
     * 判断是否为JSONObject
     *
     * @param obj instanceof String ? parseObject
     * @return
     */
    public static boolean isJSONObject(Object obj) {
        if (obj instanceof JSONObject) {
            return true;
        }
        if (obj instanceof String) {
            try {
                JSONObject json = parseObject((String) obj);
                return json != null && !json.isEmpty();
            } catch (Exception e) {
                LogUtil.e(TAG, "isJSONObject  catch \n" + e.getMessage());
            }
        }

        return false;
    }

    /**
     * 判断是否为JSONArray
     *
     * @param obj instanceof String ? parseArray
     * @return
     */
    public static boolean isJSONArray(Object obj) {
        if (obj instanceof JSONArray) {
            return true;
        }
        if (obj instanceof String) {
            try {
                JSONArray json = parseArray((String) obj);
                return json != null && !json.isEmpty();
            } catch (Exception e) {
                LogUtil.e(TAG, "isJSONArray  catch \n" + e.getMessage());
            }
        }

        return false;
    }

}
