package com.xywg.admin.rest.modular.system;

import com.xywg.admin.modular.system.model.OrganizationalStructure;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hjy
 * @date 2018/5/31
 */
@RestController
@RequestMapping("enterprise")
@Api(description = "企业")
public class EnterpriseRestController {
    @Autowired
    private  IDeptService iDeptService;

    /**
     *获取企业结构组织
     * @param userId  企业组织结构代码
     */
    @ApiOperation(value = "获取企业结构组织", notes = "获取企业结构组织")
    @RequestMapping(value = "/getOrganizationalStructure", method = RequestMethod.GET)
    public R getOrganizationalStructure(@RequestParam String  userId) {
        OrganizationalStructure organizationalStructure= iDeptService.getOrganizationalStructure(userId);
        return R.ok(organizationalStructure);
    }
}
