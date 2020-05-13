package com.xywg.admin.modular.faceUtils;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author wangpeixu
 * @date 20180529
 */
public class JsonUtil {

    public static Map<String, Object> jsonToMap(JSONObject jsonObject) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Iterator<String> iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext())
        {
            key = iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;
    }
}
