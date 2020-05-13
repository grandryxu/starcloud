package com.xywg.admin.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-07
 */
@TableName("buss_file_info")
public class FileInfo extends Model<FileInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关联id
     */
    @TableField("relevance_id")
    private Long relevanceId;
    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 附件地址
     */
    private String path;
    /**
     * 文件所属模块
     */
    private String type;
    
    public FileInfo() {
    	super();
    }

    public FileInfo(Long relevanceId,String fileName,String path,String type) {
    	this.relevanceId = relevanceId;
    	this.fileName = fileName;
    	this.path = path;
    	this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Long relevanceId) {
        this.relevanceId = relevanceId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
        "id=" + id +
        ", relevanceId=" + relevanceId +
        ", fileName=" + fileName +
        ", path=" + path +
        ", type=" + type +
        "}";
    }
}
