package com.xywg.admin.rest.modular.system;

import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * * @Package com.xywg.admin.rest.modular.system
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2018/8/17
 **/
@RestController
@RequestMapping("/welFare")
@Api(description = "福利")
public class WelFareRestController {

    @Autowired
    private IDictService dictService;

    @ApiOperation(value = "获取福利接口", notes = "获取福利接口")
    @RequestMapping( value = "/getWelFaresApp"   ,method = RequestMethod.GET)
    @ResponseBody
    public R getWelFaresApp(){
        List<Dict> dictList = dictService.getWelFaresApp();
        return R.ok(dictList);
    }
}
