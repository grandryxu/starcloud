package com.xywg.admin.modular.project.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.project.model.ProjectTraining;
import com.xywg.admin.modular.project.model.ProjectTrainingVo;
import com.xywg.admin.modular.smz.model.ProjectTrainFileMo;
import com.xywg.admin.modular.smz.model.ProjectTrainMo;
import com.xywg.admin.modular.smz.model.ProjectTrainWorkerMo;
import com.xywg.admin.modular.system.model.SwitchType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目中的培训记录 Mapper 接口
 * </p>
 *
 * @author wangcw123ss
 * @since 2018-06-14
 */
public interface ProjectTrainingMapper extends BaseMapper<ProjectTraining> {

    int insertProjectTrain(ProjectTraining projectTrainingVo);

    List<Map<String,Object>> list(@Param("Page") Page page, @Param("map") Map<String, Object> map,@Param("switchType") SwitchType switchType);

    ProjectTrainingVo getOneById(@Param("id") Integer id);

    List<Map<String,Object>> getWorkerList(@Param("id") Integer id,@Param("page") Page page);

    List<ProjectTrainMo> getProjectTrainFromLabor(List<Long> ids);

    List<ProjectTrainFileMo> getProjectTrainFileFromLabor(List<Long> ids);

    List<ProjectTrainWorkerMo> getProjectTrainWorkerFromLabor(List<Long> ids);

    void deleteByIds(@Param("map") Map<String, Object> map);

    List<Map<String,Object>> getProjectFileList(@Param("id") Long id);

}
