package com.xywg.admin.core.beetl;

import com.xywg.admin.core.util.KaptchaUtil;
import com.xywg.admin.core.util.ToolUtil;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
/**
 * beetl拓展配置,绑定一些工具类,方便在模板中直接调用
 *
 * @author wangcw
 * @Date 2018/2/22 21:03
 */
public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Value("${spring.mvc.view.imagePath}")
    private String imagePath;

    @Override
    public void initOther() {
        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
        groupTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());
        Map<String,Object> shared = new HashMap<String,Object>();
        shared.put("imagePath", imagePath);
        groupTemplate.setSharedVars(shared);
    }
}
