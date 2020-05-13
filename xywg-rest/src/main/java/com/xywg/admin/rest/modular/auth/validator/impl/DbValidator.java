package com.xywg.admin.rest.modular.auth.validator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xywg.admin.core.constant.HttpStatus;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.system.model.SysPath;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.ISysPathService;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.rest.common.persistence.model.R;
import com.xywg.admin.rest.modular.auth.validator.IReqValidator;

/**
 * 账号密码验证
 *
 * @author wangcw
 * @date 2017-08-23 12:34
 */
@Service
public class DbValidator implements IReqValidator {

    @Autowired
    private ISysPathService sysPathService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${jwt.auth-path}")
    private String serverAddress;
    
    @Autowired
    private AccountProjectService accountProjectService;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    private static Logger logger = LoggerFactory.getLogger(DbValidator.class);
    @Override
    public List<String> validate(User user) {
        User dbUser = iUserService.getByAccountAPP(user.getAccount(), user.getUserType().toString());
        List<String> resList = new ArrayList<>(2);
        if (dbUser == null) {
            resList.add("601");
            resList.add("用户不存在!");
            return resList;
        }
        //管理员类型的用户中,项目经理不允许登录移动端
		/*
		 * if (dbUser.getUserType() == 1 && dbUser.getIsEnterprise() == 0) {
		 * resList.add("604"); resList.add("此账号权限不足"); return resList; }
		 */
        //非企业级管理员判断是否有关联或默认项目
        if (dbUser.getUserType() == 1 && dbUser.getIsEnterprise() == 0) {
        	int count = accountProjectService.selectIsExistRelationProject(user.getAccount());
        	if(count == 0) {
        		resList.add("606");
                resList.add("没有关联项目，请关联!");
                return resList;
        	}
        }
        int loginStatus = 2;
        if (dbUser.getStatus() == loginStatus) {
            resList.add("602");
            resList.add("账号被冻结!");
            return resList;
        }
        if (dbUser.getUserType() == 1) {
            if (dbUser.getEndTime() == null || (dbUser.getEndTime() != null && System.currentTimeMillis() > dbUser.getEndTime().getTime())) {
                resList.add("605");
                resList.add("账号已过期!");
                return resList;
            }
        }
        String md5Password = ShiroKit.md5(user.getPassword(), dbUser.getSalt());
        if (md5Password.equals(dbUser.getPassword())) {
            resList.add("600");
            resList.add("登录成功!");
        } else {
            resList.add("603");
            resList.add("用户名或密码错误!");
        }
        return resList;
    }

    @Override
    public R standardLogin(User user) {
        List<SysPath> sysPathList = this.sysPathService.selectAllSysPaths();
        List<FutureTask<R>> futureTasks = new ArrayList<FutureTask<R>>();
        Map<Integer, R> resultMap = new HashMap<Integer, R>(16);
        Integer size = sysPathList.size();
        if (size > 0) {
            ExecutorService executorService = Executors.newFixedThreadPool(size);
            for (int i = 0; i < sysPathList.size(); i++) {
                int finalI = i;
                FutureTask<R> futureTask = new FutureTask<R>(new Callable<R>() {
                    @SuppressWarnings("unchecked")
					@Override
                    public R call() throws Exception {
                        R r = restTemplate.postForObject(sysPathList.get(finalI).getPath() + "/" + serverAddress, user, R.class);
                        Map<String,Object> data = (Map<String, Object>) r.get("data");
                        if(data != null){
                            data.put("path",sysPathList.get(finalI).getPath());
                            data.put("key",sysPathList.get(finalI).getKey());
                        }
                        System.out.println("请求地址" + sysPathList.get(finalI).getPath() + "/" + serverAddress + ",返回值" + r.toString() );
                        return r;
                    }
                });
                futureTasks.add(futureTask);
                executorService.submit(futureTask);
            }
            executorService.shutdown();
            while (true) {
                if (executorService.isTerminated()) {
                    for (int i = 0; i < futureTasks.size(); i ++) {
                        try {
                            R r = futureTasks.get(i).get();
                            resultMap.put(Integer.parseInt(r.get("code").toString()), r);
                            if (HttpStatus.SC_OK == Integer.parseInt(r.get("code").toString())) {
                                return r;
                            }
                        } catch (InterruptedException e) {
                            logger.info("InterruptedException", e);
                            Thread.currentThread().interrupt();
                        } catch (ExecutionException e) {
                            logger.error("ExecutionException", e);
                            resultMap.put(606,R.error(606,"连接超时"));
                        }
                    }
                    break;
                }
            }
        }
        if (resultMap.keySet().size() > 0) {
            if (resultMap.containsKey(603)) {
                return resultMap.get(603);
            } else if (resultMap.containsKey(602)) {
                return resultMap.get(602);
            } else if (resultMap.containsKey(604)) {
                return resultMap.get(604);
            } else if (resultMap.containsKey(605)) {
                return resultMap.get(605);
            }else if (resultMap.containsKey(606)) {
                return resultMap.get(606);
            }
        }
        return R.error(601, "用户不存在");
    }
}
