package com.xywg.admin.rest.modular.recruit.v120;

import com.xywg.admin.modular.workerEnshrine.model.PersonEnshrineDto;
import com.xywg.admin.modular.workerEnshrine.model.WorkerEnshrine;
import com.xywg.admin.modular.workerEnshrine.model.WorkerEnshrineDto;
import com.xywg.admin.modular.workerEnshrine.service.IWorkerEnshrineService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/21/021 09:24
 * @Description:
 */
@RestController
@RequestMapping("/v120/workerEnshrine")
@Api(value = "WorkerEnshrineController",description = "收藏控制类")
public class WorkerEnshrineController {
    @Autowired
    IWorkerEnshrineService workerEnshrineService;

    @ApiOperation(value = "收藏接口", notes = "")
    @RequestMapping(value = "enshrine",method = RequestMethod.POST)
    public R enshrine (@RequestBody WorkerEnshrineDto workerEnshrineDto){
        workerEnshrineService.enshrine(workerEnshrineDto);
        return  R.ok();
    }

    @ApiOperation(value = "获取我的收藏列表", notes = "")
    @RequestMapping(value = "getPersonEnshrineList",method = RequestMethod.POST)
    public R getPersonEnshrineList(@RequestBody PersonEnshrineDto personEnshrineDto){
        return R.ok(workerEnshrineService.getPersonEnshrineList (personEnshrineDto));
    }
}
