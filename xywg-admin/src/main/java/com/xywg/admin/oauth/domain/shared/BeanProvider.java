package com.xywg.admin.oauth.domain.shared;

import com.xywg.admin.core.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;

/**
 * @author Shengzhao Li
 */
public abstract class BeanProvider {

    private static ApplicationContext applicationContext;

    private BeanProvider() {
    }

    public static void initialize(ApplicationContext applicationContext) {
        BeanProvider.applicationContext = applicationContext;
    }

    /**
     * Get Bean by clazz.
     *
     * @param clazz Class
     * @param <T>   class type
     * @return Bean instance
     */
    public static <T> T getBean(Class<T> clazz) {
//        if (applicationContext == null) {
//            return null;
//        }
        return SpringContextHolder.getBean(clazz);
//        return applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        if (applicationContext == null) {
            return null;
        }
        return (T) applicationContext.getBean(beanId);
    }

}