package com.xywg.admin.rest.modular.example;

import com.xywg.admin.modular.worker.service.IWorkerMasterService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 常规控制器
 *
 * @author wangcw
 * @date 2017-08-23 16:02
 */
@RestController
@RequestMapping("/hello")
@Api(tags = "hello测试")
public class ExampleController {


    @Autowired
    private IWorkerMasterService workerMasterService;

    @ApiOperation(value="测试标题", notes="这是一个备注")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public R hello() {
        workerMasterService.getWorkerByIdCard("1",1);

        return  R.ok();
    }
}
