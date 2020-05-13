package com.xywg.admin.modular.project.model;

import com.xywg.admin.modular.system.model.FileInfo;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/20 17:02
 */
@Data
public class ProjectTrainingVo extends ProjectTraining{

    private List<Map<String,Object>> detailList;

    private List<WorkerMaster> workerList;

    private String filePath;

    private String fileName;

    private List<FileInfo> fileList;
}
