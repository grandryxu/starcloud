package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.system.model.Sign;


/**
 * @author hjy
 * 验证码Mapper
 */
public interface SignMapper extends BaseMapper<Sign> {

    Sign getSign(String signId);


    void  update(Sign sign);

}