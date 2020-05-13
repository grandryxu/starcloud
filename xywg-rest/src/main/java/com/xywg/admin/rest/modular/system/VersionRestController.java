package com.xywg.admin.rest.modular.system;

import com.xywg.admin.modular.system.service.VersionService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hjy
 * @date 2018/5/31
 */
@RestController
@RequestMapping("version")
@Api(description = "版本信息")
public class VersionRestController {
    @Autowired
    private  VersionService  versionService;

    @ApiOperation(value = "获取最新版本信息", notes = "获取最新版本信息")
    @RequestMapping( value = "/getVersion"   ,method = RequestMethod.GET)
    @ResponseBody
    public R getLatestVersionInfo(@RequestParam Integer phoneType ,@RequestParam Integer  kind) {

        return R.ok(versionService.getLatestVersionInfo(phoneType,kind));
    }

}
