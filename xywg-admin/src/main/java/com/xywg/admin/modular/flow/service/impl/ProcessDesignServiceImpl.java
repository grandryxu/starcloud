package com.xywg.admin.modular.flow.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.flow.entity.Process;
import com.xywg.admin.flow.mapper.ProcessMapper;
import com.xywg.admin.flow.service.IFlowService;
import com.xywg.admin.flow.service.ProcessService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IRoleService;
import com.xywg.admin.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 严鹏
 * @date 2019/7/17
 */
@Service
public class ProcessDesignServiceImpl extends IFlowService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ProcessService processService;
    @Autowired
    private ProcessMapper processMapper;


    @Override
    public List<Map<String, Object>> getAllUser() {
        return userService.selectUsers(null,null,null,null,null);
    }

    @Override
    public List<Map<String, Object>> getAllDept() {
        return deptService.list(null,null);
    }

    @Override
    public List<Map<String, Object>> getAllRole() {
        return roleService.selectRoles(null,null);
    }

    @Override
    public Object insert1(String displayName, String lists) {
        JSONArray jsonArray = JSONArray.parseArray(lists);
        Process p = new Process();
        p.setId(UUID.randomUUID().toString());
        p.setDisplayName(displayName);
        p.setProcessName(UUID.randomUUID().toString());
        p.setVersion(1);
        p.setCreateTime(new Date());
        p.setCreateUser(ShiroKit.getUser().getId());
        p.setIsDel(0);
        JSONObject result = new JSONObject();

        if(jsonArray.size()==1){
            JSONObject jo = new JSONObject();
            jo.put("prev","");
            jo.put("next","");
            JSONObject parObject = new JSONObject();
            List  l = new ArrayList<>();
            l.add(Integer.parseInt(((JSONObject)jsonArray.get(0)).get("user").toString()));
            parObject.put("user",l);
            parObject.put("role",new ArrayList<>());
            parObject.put("dept",new ArrayList<>());
            jo.put("par",parObject);
            jo.put("type",1);
            result.put(((JSONObject)jsonArray.get(0)).get("name").toString(),jo );
        }else{
             for(int i=0;i<jsonArray.size();i++){
                JSONObject jo = new JSONObject();

                if(i==0){
                    jo.put("prev","");
                }else{
                    jo.put("prev",((JSONObject)jsonArray.get(i-1)).get("name") );
                }
                if(i==jsonArray.size()-1){
                    jo.put("next","");
                }else{
                    jo.put("next",((JSONObject)jsonArray.get(i+1)).get("name"));
                }

                 JSONObject parObject = new JSONObject();
                 List  l = new ArrayList<>();
                 l.add(Integer.parseInt(((JSONObject)jsonArray.get(i)).get("user").toString()));
                 parObject.put("user",l);
                 parObject.put("role",new ArrayList<>());
                 parObject.put("dept",new ArrayList<>());

                 jo.put("par",parObject);
                 jo.put("type",1);
                result.put(((JSONObject)jsonArray.get(i)).get("name").toString(),jo);
            }
        }

        p.setVars(result.toString());
        p.setType(0);
        try {
            processService.insert(p,null,null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "" ;
    }

    @Override
    public Object xiugai(String displayName, String lists,String id) {
        Process processold= new Process();
        processold.setId(id);
        processold= processService.getOne(processold,null,null,null);

        JSONArray jsonArray = JSONArray.parseArray(lists);
        Process p = new Process();
        p.setId(UUID.randomUUID().toString());
        p.setDisplayName(displayName);
//        p.setProcessName(UUID.randomUUID().toString());
        p.setVersion(processold.getVersion()+1);
        p.setCreateTime(new Date());
        p.setCreateUser(ShiroKit.getUser().getId());
        p.setIsDel(0);
        p.setProcessName(processold.getProcessName());

        JSONObject result = new JSONObject();

        if(jsonArray.size()==1){
            JSONObject jo = new JSONObject();
            jo.put("prev","");
            jo.put("next","");
            JSONObject parObject = new JSONObject();
            List<Integer>  l = new ArrayList<Integer>();
            l.add( Integer.parseInt(((JSONObject)jsonArray.get(0)).get("user").toString().replace("[","").replace("]","")));
            parObject.put("user",l);
            parObject.put("role",new ArrayList<>());
            parObject.put("dept",new ArrayList<>());
            jo.put("par",parObject);
            jo.put("type",1);
            result.put(((JSONObject)jsonArray.get(0)).get("name").toString(),jo );
        }else{
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jo = new JSONObject();

                if(i==0){
                    jo.put("prev","");
                }else{
                    jo.put("prev",((JSONObject)jsonArray.get(i-1)).get("name") );
                }
                if(i==jsonArray.size()-1){
                    jo.put("next","");
                }else{
                    jo.put("next",((JSONObject)jsonArray.get(i+1)).get("name"));
                }

                JSONObject parObject = new JSONObject();
                List<Integer>  l = new ArrayList<Integer>();
                l.add( Integer.parseInt(((JSONObject)jsonArray.get(i)).get("user").toString().replace("[","").replace("]","")) );
                parObject.put("user",l);
                parObject.put("role",new ArrayList<>());
                parObject.put("dept",new ArrayList<>());

                jo.put("par",parObject);
                jo.put("type",1);
                result.put(((JSONObject)jsonArray.get(i)).get("name").toString(),jo);
            }
        }

        p.setVars(result.toString());
        p.setType(0);
        try {
            processService.update(p,null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "" ;
    }

    @Override
    public Object delete(String id) {
        Process  p = new Process ();
        p.setId(id);
        p = processMapper.getOne(p);


        processMapper.delete(p.getProcessName());
        return null;
    }

}
