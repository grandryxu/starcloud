package com.xywg.admin.rest.modular.recruit.v120;

import com.xywg.admin.modular.recruit.dto.ApplyDto;
import com.xywg.admin.modular.recruit.dto.ApplyListPersonDto;
import com.xywg.admin.modular.recruit.dto.CallMobileDto;
import com.xywg.admin.modular.recruit.model.ApplyListPersonVo;
import com.xywg.admin.modular.recruit.model.ApplyVo;
import com.xywg.admin.modular.recruit.model.EvaluateVo;
import com.xywg.admin.modular.recruit.model.RecruitInfoVo;
import com.xywg.admin.modular.recruit.service.RecruitApplyService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: wangshibo
 * @Date: 2018/8/20/020 09:56
 * @Description:
 */
@RestController
@RequestMapping("/v120/recruitApply")
@Api(value = "RecruitApplyController",description = "申请控制类")
public class RecruitApplyController {
    @Autowired
    RecruitApplyService recruitApplyService;

    @ApiOperation(value = "申请接口", notes = "")
    @RequestMapping(value = "apply",method = RequestMethod.POST)
    public R apply(@RequestBody ApplyDto applyDto){
        recruitApplyService.apply(applyDto);
    return  R.ok();
    }

    @ApiOperation(value = "获取我的申请列表接口", notes = "")
    @RequestMapping(value = "getApplyListByPerson",method = RequestMethod.POST)
    public R getApplyListByPerson(@RequestBody ApplyListPersonDto applyListPersonDto){
        List<ApplyListPersonVo> applyListByPerson = recruitApplyService.getApplyListByPerson(applyListPersonDto);
        return R.ok(applyListByPerson);
    }

    @ApiOperation(value = "拨打电话接口", notes = "")
    @RequestMapping(value = "callMobile",method = RequestMethod.POST)
    public R callMobile(@RequestBody CallMobileDto callMobileDto){
        recruitApplyService.callMobile(callMobileDto);
        return R.ok();
    }

    @ApiOperation(value = "查询申请记录", notes = "")
    @RequestMapping(value = "getApplyListById",method = RequestMethod.GET)
    public R getApplyListById(@RequestParam Integer id,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
       List<ApplyVo> list= recruitApplyService.getApplyListById(id,pageNo,pageSize);
       return R.ok(list);
    }

    @ApiOperation(value = "查询评价记录", notes = "")
    @RequestMapping(value = "getEvaluateListById",method = RequestMethod.GET)
    public R getEvaluateListById(@RequestParam Integer id, @RequestParam Integer pageNo, @RequestParam Integer pageSize){
        List<EvaluateVo> list= recruitApplyService.getEvaluateListById(id,pageNo,pageSize);
        return R.ok(list);
    }

    @ApiOperation(value = "根据id查询招聘信息", notes = "")
    @RequestMapping(value = "getRecruitInfoById",method = RequestMethod.GET)
    public R getRecruitInfoById(@RequestParam Integer id){
        RecruitInfoVo recruitInfoVo =  recruitApplyService.getRecruitInfoById(id);
        return R.ok(recruitInfoVo);
    }

    @ApiOperation(value = "处理申请", notes = "")
    @RequestMapping(value = "disposeApply",method = RequestMethod.GET)
    public R disposeApply(@RequestParam Integer id){
          recruitApplyService.disposeApply(id);
        return R.ok();
    }

}
