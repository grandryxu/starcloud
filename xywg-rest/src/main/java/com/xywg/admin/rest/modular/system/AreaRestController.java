package com.xywg.admin.rest.modular.system;

import com.alibaba.fastjson.JSON;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.system.model.dto.AreaDto;
import com.xywg.admin.modular.system.service.IAreaService;
import com.xywg.admin.modular.system.service.IDictService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * * @Package com.xywg.admin.rest.modular.system
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2018/8/17
 **/
@RestController
@RequestMapping("/area")
@Api(description = "地区")
public class AreaRestController {

    @Autowired
    private IAreaService areaService;

    @ApiOperation(value = "获取省、市、区接口", notes = "获取省、市、区接口")
    @RequestMapping( value = "/getAreasApp"   ,method = RequestMethod.GET)
    @ResponseBody
    public R getAreasApp() throws IOException {
        List<AreaDto> areaDtoList = areaService.getAreasApp();
        return R.ok(areaDtoList);
    }
}
