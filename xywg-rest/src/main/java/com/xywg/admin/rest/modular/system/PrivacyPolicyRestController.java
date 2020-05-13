package com.xywg.admin.rest.modular.system;

import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hjy
 * @date 2018/5/31
 */
@RestController
@Api(description = "隐私条款")
public class PrivacyPolicyRestController {


    @ApiOperation(value = "获取隐私条款路径", notes = "获取隐私条款路径")
    @RequestMapping(value = "/user/private", method = RequestMethod.GET)
    @ResponseBody
    public R getPrivacyPolicyURL() {
        //固定地址做nginx 跳转 ,后期可能需要修改
        String url = "private.html";
        Map<String,String> map = new HashMap<>(1);
        map.put("url", url);
        return R.ok().put("data", map);

    }

}
