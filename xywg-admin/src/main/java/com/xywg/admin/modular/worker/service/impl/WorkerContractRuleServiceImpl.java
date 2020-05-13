package com.xywg.admin.modular.worker.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.mina.Utils;
import com.xywg.admin.modular.smz.model.WorkerContractorRuleMo;
import com.xywg.admin.modular.smz.utils.HttpClientUtils;
import com.xywg.admin.modular.smz.utils.ImageUtil;
import com.xywg.admin.modular.worker.dao.WorkerContractRuleMapper;
import com.xywg.admin.modular.worker.model.WorkerContractRule;
import com.xywg.admin.modular.worker.service.IWorkerContractRuleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 项目中工人劳动合同信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-08
 */
@Service
public class WorkerContractRuleServiceImpl extends ServiceImpl<WorkerContractRuleMapper, WorkerContractRule> implements IWorkerContractRuleService {

    private static final Log LOG = LogFactory.getLog(WorkerContractRuleServiceImpl.class);
    private static Properties systemParams = new Properties();

    /**
     * 加载配置文件
     */
    static {
        try {
            systemParams.load(WorkerContractRuleServiceImpl.class.getClassLoader().getResourceAsStream("smz.properties"));
        } catch (IOException e) {
            LOG.error("smz.properties" + "配置文件加载失败");
        }
    }

    /**
     *
     * @description 获取配置文件具体信息
     * @author chupp
     * @date 2018年4月27日
     * @param key
     * @return
     *
     */
    protected String getSystemStringParam(String key) {
        return systemParams.getProperty(key);
    }

    /**
     *
     * @description 登录实名制获取token
     * @author chupp
     * @date 2018年4月27日
     * @return
     *
     */
    @SuppressWarnings("unchecked")
    protected Map<String,String> loginSMZ() {
        String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefix");
        String loginUrl = this.getSystemStringParam("loginUrl");
        String userName = this.getSystemStringParam("userName");
        String password = this.getSystemStringParam("password");
        Map<String,Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", password);
        String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
        Map<String,Object> m = (Map<String,Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
        Map<String,String> headers = new HashMap<>();
        headers.put("token", (String) m.get("token"));
        return headers;
    }

    /**
     *
     * @description 登录实名制获取token
     * @author chupp
     * @date 2018年4月27日
     * @return
     *
     */
    @SuppressWarnings("unchecked")
    protected Map<String,String> loginSMZYC() {
        String httpUrlPrefix = this.getSystemStringParam("httpUrlPrefixYC");
        String loginUrl = this.getSystemStringParam("loginUrlYC");
        String userName = this.getSystemStringParam("userNameYC");
        String password = this.getSystemStringParam("passwordYC");
        Map<String,Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", password);
        String result = HttpClientUtils.post(httpUrlPrefix + loginUrl, map);
        Map<String,Object> m = (Map<String,Object>) JSONObject.toBean(JSONObject.fromObject(result), Map.class);
        Map<String,String> headers = new HashMap<>();
        headers.put("token", (String) m.get("token"));
        return headers;
    }
    @Value("${spring.mvc.view.imageLocalPath}")
    private String imageLocalPath;
    @Override
    public boolean getWorkeContractorFromLabor(List<Long> ids, Map<String, String> m)throws Exception{
        List<WorkerContractorRuleMo> list=baseMapper.getWorkeContractorFromLabor(ids);
        if(list.size() > 0) {
        	 for(WorkerContractorRuleMo w:list){
                 String wPath=w.getAttement();
                 w.setAttement(ImageUtil.GetImageStr(imageLocalPath+wPath));
             }
             String json= JSONArray.fromObject(list).toString();
             Map<String, Object> map=new HashMap<String, Object>();
             map.put("laborContract", json);
             String jsonResult=HttpClientUtils.post(getSystemStringParam("httpUrlPrefix")+getSystemStringParam("saveWorkerContractorFromLabor"), map, m);
             System.out.println(jsonResult);
             Map<String, String> result= JSONUtil.toBean(jsonResult, Map.class);
             if("-2".equals(result.get("code"))){
                 return false;
             }else{
                 return true;
             }
        }
        return false;
    }
}
