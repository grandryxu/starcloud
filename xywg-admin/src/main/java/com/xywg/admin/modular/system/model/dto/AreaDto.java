package com.xywg.admin.modular.system.model.dto;

import com.xywg.admin.modular.system.model.Area;
import lombok.Data;

import java.util.List;

/**
 * * @Package com.xywg.admin.modular.system.model.dto
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2018/8/17
 **/
@Data
public class AreaDto extends Area {

    private List<Area> areaList;
}
