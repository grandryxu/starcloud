package com.xywg.admin.rest.modular.longxin;

import com.xywg.admin.modular.longxin.service.TenderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/tender")
public class TenderController {
    @Autowired
    private TenderingService tenderingService;

    /**
     * 获取招标信息列表
     */
    @RequestMapping(value = "/getAll")
    @ResponseBody
    public Object list(@RequestBody Map<String,Object> map ) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String,Object>> tenderList= tenderingService.getTenderList(map);
            result.put("code","200");
            result.put("success",true);
            result.put("message","查询成功");
            result.put("data",tenderList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code","500");
            result.put("success",false);
            result.put("message","查询失败");
            return result;
        }
    }

    /**
     * 获取招标信息详情
     */
    @RequestMapping(value = "/tenderDetails")
    @ResponseBody
    public Object tenderDetails(@RequestBody Map<String,Object> map){
        Map<String, Object> result = new HashMap<>();
        try {
            String tenderCode = String.valueOf(map.get("tenderCode"));
            Map<String,Object>tenderDetails = tenderingService.tenderDetails(tenderCode);
            List<Map<String,Object>> tenderFile=tenderingService.tenderFile(tenderCode);
            result.put("code","200");
            result.put("success",true);
            result.put("message","查询成功");
            tenderDetails.put("tenderFileList",tenderFile);
            result.put("data",tenderDetails);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code","500");
            result.put("success",false);
            result.put("message","查询失败");
            return result;
        }
    }

    /**
     * 获取中标公告列表
     */
   @RequestMapping(value = "/winningBidList")
    @ResponseBody
    public Object winningBidList(@RequestBody Map<String,Object> map){
       HashMap<String, Object> result = new HashMap<>();
       try {
           List<Map<String,Object>> winningBidList=tenderingService.winningBidList(map);
           result.put("code","200");
           result.put("success",true);
           result.put("message","查询成功");
           result.put("data",winningBidList);
           return result;
       } catch (Exception e) {
           e.printStackTrace();
           result.put("code","500");
           result.put("success",false);
           result.put("message","查询失败");
           return result;
       }
   }

    /**
     * 获取中标公告详情
     */
    @RequestMapping(value = "/winningBidDetails")
    @ResponseBody
    public Object winningBidDetails(@RequestBody Map<String,Object> map){
        Map<String,Object> result=tenderingService.winningBidDetails(map);
        return result;
    }

    /**
     * 获取定标管理列表
     *
     */
    @RequestMapping(value = "/selectionList")
    @ResponseBody
    public Object selectionList(@RequestBody Map<String,Object> map){
        Map<String,Object> result=  tenderingService.selectionList(map);
        return result;
    }
    /**
     * 获取中标/投标单位列表
     */
    @RequestMapping(value = "/candidateBidList")
    @ResponseBody
    public Object candidateBidList(@RequestBody Map<String,Object> map) {
        Map<String,Object> result=  tenderingService.candidateBidList(map);
        return result;
    }

    /**
     * 获取招标明细列表
     */
    @RequestMapping(value = "/tenderDetail")
    @ResponseBody
    public Object tenderDetail(@RequestBody Map<String,Object> map){
      Map<String,Object>result=  tenderingService.tenderDetail(map);
      return result;
    }

    /**
     * 获取代办任务列表
     * 1:招标审批列表   2：定标审批列表
     */
    @RequestMapping(value = "/toDoTasks")
    @ResponseBody
    public Object toDoTasks(@RequestBody Map<String,Object> map){
      Map<String,Object>result= tenderingService.toDoTasks(map);
       return  result;
    }

    /**
     * 评标
     */
    @RequestMapping(value = "/evaluationBid")
    @ResponseBody
    public Object updateEvaluationBid(@RequestBody Map<String,Object> map){
        Map<String,Object>result=tenderingService.updateEvaluationBid(map);
        return result;
    }
    /**
     * 定标提交审核
     */
    @RequestMapping(value = "/submitBid")
    @ResponseBody
    public Object submitBid(@RequestBody Map<String,Object> map) {
        Map<String, Object> result = tenderingService.submitbid(map);
        return result;
    }


    /**
     * /missionDetails
     * 代办任务详情
     */
/*    @RequestMapping(value = "/missionDetails")
    @ResponseBody
    public Object missionDetails(@RequestBody Map<String,Object>map){
       Map<String,Object> result= tenderingService.missionDetails(map);
        return result;
    }*/


    /**
     * 定标审核/招标审核
     */

    //Review calibration
    @RequestMapping(value = "/reviewCalibration")
    @ResponseBody
    public Object reviewCalibration(@RequestBody Map<String,Object> map){
        Map<String, Object> result=  tenderingService.reviewCalibration(map);
        return result;
    }


    /**
     * 流标废标
     * Current bid or rejected bid
     */
    @RequestMapping(value = "/currentBidOrRejectedBid")
    @ResponseBody
    public Object currentBidOrRejectedBid(@RequestBody Map<String,Object>map){
        Map<String, Object> result= tenderingService.currentBidOrRejectedBid(map);
        return result;
    }

    /**
     * 获取招标公告列表
     */
    @RequestMapping(value = "/announcementGetAll")
    @ResponseBody
    public Object announcementGetAll(@RequestBody Map<String,Object> map ) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String,Object>> tenderList= tenderingService.announcementGetAllGetTenderList(map);
            result.put("code","200");
            result.put("success",true);
            result.put("message","查询成功");
            result.put("data",tenderList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code","500");
            result.put("success",false);
            result.put("message","查询失败");
            return result;
        }
    }


}
