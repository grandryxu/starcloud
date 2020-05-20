package com.xywg.attendance.task;

import com.xywg.attendance.common.model.TransmissionMessageTemplateSubclass;
import com.xywg.attendance.modular.handler.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.xywg.attendance.common.global.GlobalStaticConstant.MONGODB_ORIGINAL_DATA;

/**
 * @author caobinbin
 * @Date 2019年6月24日16:39:19
 */
@Component
@Configurable
@EnableScheduling
public class InsertPicTask {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MethodService methodService;
    /**
     * 跳转到代码生成主页
     */
    @Scheduled(cron="0 50 15 * * ?" )
    public String onlyInsert() {
        Criteria criteria=new Criteria();
        criteria.andOperator(
              Criteria.where("date").gte("2019-06-22 10:27:53"),
                 Criteria.where("date").lte("2019-06-24 10:43:07")
        );
     Query query=new Query(criteria);
     List<TransmissionMessageTemplateSubclass> allTMTs= mongoTemplate.find(query,TransmissionMessageTemplateSubclass.class, MONGODB_ORIGINAL_DATA);
        //字节码转化为对象
        try {
            if(null!=allTMTs){
                for(TransmissionMessageTemplateSubclass message:allTMTs){
                    methodService.runMethod(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "thanks";
    }
}
