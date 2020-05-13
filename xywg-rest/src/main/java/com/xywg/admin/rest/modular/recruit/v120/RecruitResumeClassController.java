package com.xywg.admin.rest.modular.recruit.v120;

import com.xywg.admin.modular.recruit.dto.RecruitPageDto;
import com.xywg.admin.modular.recruit.dto.ResumeClassDto;
import com.xywg.admin.modular.recruit.model.RecruitResumeClassVo;
import com.xywg.admin.modular.recruit.model.ResumeClassListVo;
import com.xywg.admin.modular.recruit.service.IResumeClassService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wangshibo
 * @Date: 2018/8/22/022 10:42
 * @Description:
 */
@RestController
@RequestMapping("/v120/recruitResumeClass")
@Api(value = "RecruitResumeClassController",description = "班组简历控制类")
public class RecruitResumeClassController {

    @Autowired
    IResumeClassService resumeClassService;

    @ApiOperation(value = "保存班组简历", notes = "")
    @RequestMapping(value = "keepResumeClass",method = RequestMethod.POST)
    public R keepResumeClass(@RequestBody ResumeClassDto resumeClassDto){
        //判断是新增还是修改
        if (resumeClassDto.getId() == null || resumeClassDto.getId() == 0){
            return R.ok(resumeClassService.addResumeClass(resumeClassDto));
        }else {
            return R.ok(resumeClassService.updateResumeClass(resumeClassDto));
        }
    }

    @ApiOperation(value = "获取班组简历列表", notes = "")
    @RequestMapping(value = "getResumeClassList",method = RequestMethod.POST)
    public R getResumeClassList(@RequestBody RecruitPageDto recruitPageDto){
      List<ResumeClassListVo> list= resumeClassService.getResumeClassList(recruitPageDto);
        return R.ok(list);
    }

    @ApiOperation(value = "获取班组简历详情", notes = "")
    @RequestMapping(value = "getResumeClass",method = RequestMethod.POST)
    public R getResumeClass(@RequestBody ResumeClassDto resumeClassDto){
       RecruitResumeClassVo recruitResumeClassVo = resumeClassService.getResumeClass(resumeClassDto.getId().intValue());
        return R.ok(recruitResumeClassVo);
    }

}
