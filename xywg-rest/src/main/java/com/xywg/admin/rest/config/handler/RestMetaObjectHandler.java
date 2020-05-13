package com.xywg.admin.rest.config.handler;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.xywg.admin.core.support.HttpKit;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Date;


/**
 * @author jingyun_hu
 * @date 2018/5/22
 * 公共字段自动注入
 */
//@Component
public class RestMetaObjectHandler extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object token = HttpKit.getRequest().getSession().getAttribute("token");

        Object username =  HttpKit.getRequest().getSession().getAttribute("username");
//        Object username =  "admin";

        if (username == null) {
            return;
        }
        Object createDate = getFieldValByName("createDate", metaObject);
        Object createUser = getFieldValByName("createUser", metaObject);
        if (null == createDate) {
            setFieldValByName("createDate", new Date(System.currentTimeMillis()), metaObject);

        }

        if (null == createUser) {
            setFieldValByName("createUser", username, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        Object username =  "admin";
        Object token = HttpKit.getRequest().getSession().getAttribute("token");
        Object username =  HttpKit.getRequest().getSession().getAttribute("username");
        if (username == null) {
            return;
        }

        Object updateDate = getFieldValByName("updateDate", metaObject);
        Object updateUser = getFieldValByName("updateUser", metaObject);


        if (null == updateDate) {
            setFieldValByName("updateDate", new Date(System.currentTimeMillis()), metaObject);
        }
        if (null == updateUser) {
            setFieldValByName("updateUser", username, metaObject);
        }
    }
}
