package com.xywg.admin.rest.modular.system;

import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.system.service.BannerService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hjy
 * @date 2018/5/29
 */
@RestController
@RequestMapping("appBanner")
@Api(description = "鸡汤")
public class BannerRestController {
    @Autowired
    private BannerService bannerService;

    @ApiOperation(value = "获取座右铭", notes = "获取座右铭")
    @RequestMapping(value = "/getMottos", method = RequestMethod.GET)
    public R getMottos() {

        return R.ok(bannerService.getBanner(2,null));
    }


    /**
     *
     * @param type  客户端类型(移动端,PC端)
     * @return
     */
    @ApiOperation(value = "获取轮播图", notes = "获取轮播图")
    @RequestMapping(value = "/getBanner", method = RequestMethod.GET)
    public R getBanner(@RequestParam Integer type) {
        if(type==null){
            throw new XywgException(600,"参数type不能为空");
        }
        return R.ok(bannerService.getBanner(1,type));
    }
}
