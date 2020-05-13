package com.xywg.admin.modular.recuritWorker.model.dto;

import com.xywg.admin.modular.recuritWorker.model.RecruitWorker;
import lombok.Data;

/**
 * * @Package com.xywg.admin.modular.recuritWorker.model.dto
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2018/8/20
 **/
@Data
public class RecuritWorkerDto extends RecruitWorker{

    private String workerTypeNames;

    private String salaryTypeName;

    private String wealName;
}
