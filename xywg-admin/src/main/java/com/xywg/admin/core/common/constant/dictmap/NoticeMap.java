package com.xywg.admin.core.common.constant.dictmap;

import com.xywg.admin.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * 通知的映射
 *
 * @author wangcw
 * @date 2017-05-06 15:01
 */
public class NoticeMap extends AbstractDictMap {

    @Override
    public void init() {
        put("title", "标题");
        put("content", "内容");
    }

    @Override
    protected void initBeWrapped() {
    }
}
