package com.xywg.admin.modular.worker.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 用户导出列表转换
 *
 * @author xuehao.shi
 * @date 2019年7月4日 下午10:47:03
 */
public class WorkerColUtil {

	
/**
 * 单个处理
 * @param map
 * @param colStr
 * @param showCols
 * @return
 */
    public static Map<String, Object> getColsByIf(Map<String, Object> pMap,String colStr, List<String> showCols) {
    	Map<String, Object> newMap = null;
    	for (Iterator<String> iterator = pMap.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if(!showCols.contains(key)) {
				newMap = getInstance(pMap,newMap);
				newMap.remove(key);
    	    }
		}
    	return newMap;
    }
    

	private static Map<String, Object> getInstance(Map<String, Object> pMap, Map<String, Object> newMap) {
    	if(null == newMap) {
    		newMap = new HashMap<String, Object>();
    		newMap.putAll(pMap);
    	}
    	return newMap;
	}

    /**
     * 
     * @param colStr 字段str
     * @return
     */
	public static List<String> getShowCols(String colStr) {
    	List<String> showCols = new ArrayList<String>();
        if(!StringUtils.isEmpty(colStr)) {
        	String[] colArr = colStr.split(",");
        	showCols = Arrays.asList(colArr);
        }
        if(showCols.contains("joinStatus")) {
        	showCols.set(showCols.indexOf("joinStatus"), "joinStatusName");
        }
        return showCols;
    }

	/**
	 * @param workers需要处理的list
	 * @param colStr条件
	 */
	public static List<Map<String, Object>> getColsByIf(List<Map<String, Object>> workers, String colStr) {
		List<String> showCols = getShowCols(colStr);
		
		List<Map<String, Object>> retList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : workers) {
			retList.add(getColsByIf(map, colStr, showCols));
		}
		return retList;
	}
}
