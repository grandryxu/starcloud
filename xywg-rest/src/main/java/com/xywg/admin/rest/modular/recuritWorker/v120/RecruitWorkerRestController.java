package com.xywg.admin.rest.modular.recuritWorker.v120;

import com.xywg.admin.core.base.tips.SuccessTip;
import com.xywg.admin.modular.recuritWorker.model.RecruitWorker;
import com.xywg.admin.modular.recuritWorker.model.dto.GetBrowseHistoryAppParam;
import com.xywg.admin.modular.recuritWorker.model.dto.GetMyRecruitAppParam;
import com.xywg.admin.modular.recuritWorker.model.dto.RecuritWorkerDto;
import com.xywg.admin.modular.recuritWorker.service.IRecruitWorkerService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * * @Package com.xywg.admin.rest.modular.system
 * * @Description: 招聘信息
 * * @author caiwei
 * * @date 2018/8/17
 **/
@RestController
@RequestMapping("/v120/recruit")
@Api(description = "招聘信息")
public class RecruitWorkerRestController {

    @Autowired
    private IRecruitWorkerService recruitWorkerService;

    @ApiOperation(value = "保存招聘信息", notes = "保存招聘信息")
    @RequestMapping( value = "/saveRecruitInfoApp"   ,method = RequestMethod.POST)
    @ResponseBody
    public R saveRecruitInfoApp(@RequestBody RecruitWorker recruitWorker) {
        Integer count = recruitWorkerService.saveRecruitInfoApp(recruitWorker);
        return R.ok(count);
    }

    @ApiOperation(value = "查询我发布的职位信息", notes = "查询我发布的职位信息")
    @RequestMapping( value = "/getMyRecruitApp"   ,method = RequestMethod.POST)
    @ResponseBody
    public R getMyRecuritApp(@RequestBody GetMyRecruitAppParam getMyRecruitAppParam)  {
        return R.ok(recruitWorkerService.getMyRecruitApp(getMyRecruitAppParam));
    }

    @ApiOperation(value = "刷新发布的时间", notes = "刷新发布的时间")
    @RequestMapping( value = "/refreshRecruitApp"   ,method = RequestMethod.POST)
    @ResponseBody
    public R refreshRecruitApp(@RequestBody RecuritWorkerDto recuritWorkerDto)  {
        Integer count = recruitWorkerService.refreshRecruitApp(recuritWorkerDto);
        return R.ok(count);
    }

    @ApiOperation(value = "删除当前职位", notes = "删除当前职位")
    @RequestMapping( value = "/deleteRecruitApp"   ,method = RequestMethod.POST)
    @ResponseBody
    public R deleteRecruitApp(@RequestBody RecuritWorkerDto recuritWorkerDto)  {
        Integer count = recruitWorkerService.deleteRecruitApp(recuritWorkerDto);
        return R.ok(count);
    }

    @ApiOperation(value = "统计招聘的浏览和申请数量", notes = "统计招聘的浏览和申请数量")
    @RequestMapping( value = "/getRecruitNumsApp"   ,method = RequestMethod.GET)
    @ResponseBody
    public R getRecruitNumsApp(@RequestParam Long id)  {
        return R.ok(recruitWorkerService.getRecruitNumsApp(id));
    }

    @ApiOperation(value = "查询浏览记录", notes = "查询浏览记录")
    @RequestMapping( value = "/getBrowseHistoryApp"   ,method = RequestMethod.POST)
    @ResponseBody
    public R getBrowseHistoryApp(@RequestBody GetBrowseHistoryAppParam getBrowseHistoryAppParam )  {
        return R.ok(recruitWorkerService.getBrowseHistoryApp(getBrowseHistoryAppParam));
    }
}
