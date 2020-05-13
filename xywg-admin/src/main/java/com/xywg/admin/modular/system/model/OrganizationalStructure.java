package com.xywg.admin.modular.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hjy
 * @date 2018/5/31
 * 企业组织结构
 */
@Data
public class OrganizationalStructure implements Serializable {

    private Long id;

    private Long pid;

    private String companyName;

    private String pids;

    private Integer num;

    private String socialCreditNumber;

    private List<OrganizationalStructure> subList;
}
