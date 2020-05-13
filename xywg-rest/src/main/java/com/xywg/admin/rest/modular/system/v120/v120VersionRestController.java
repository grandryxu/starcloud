package com.xywg.admin.rest.modular.system.v120;

import com.xywg.admin.modular.system.service.IVersionService;
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
@RequestMapping("/v120/version")
@Api(description = "版本信息")
public class v120VersionRestController {
    @Autowired
    private IVersionService versionService;

    @ApiOperation(value = "获取最新版本信息", notes = "获取最新版本信息")
    @RequestMapping( value = "/getVersionByPhone"   ,method = RequestMethod.GET)
    @ResponseBody
    public R getVersionByPhone(@RequestParam Integer phoneType ,@RequestParam Integer  kind,@RequestParam String version) {
        return R.ok(versionService.getVersionByPhone(phoneType,kind,version));
    }

}
