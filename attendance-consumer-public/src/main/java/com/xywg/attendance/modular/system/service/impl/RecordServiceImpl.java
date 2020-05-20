package com.xywg.attendance.modular.system.service.impl;

import com.xywg.attendance.modular.system.model.Record;
import com.xywg.attendance.modular.system.dao.RecordMapper;
import com.xywg.attendance.modular.system.service.IRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 考勤表 服务实现类
 * </p>
 *
 * @author z
 * @since 2019-02-25
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

}
