package com.yechtech.dac.report.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 
 *
 * @author dou
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_report_log")
public class ReportLog extends Model<ReportLog> {

    private static final long serialVersionUID = 1L;


    /**
     * 访问用户的adAccount
     */
        @TableField("adAccount")
    private String adAccount;

    /**
     * 访问用户的psid
     */
    private String psid;

    /**
     * 访问tableau报表所使用的账号
     */
    private Long accountId;

    /**
     * 用户访问的报表
     */
    private Long reportId;

    /**
     * 用户访问的时间
     */
    private LocalDateTime createTime;

    private String reportName;

    private String reportUid;

    /**
     * 报表访问路径
     */
    private String url;

    private String accountName;

    /**
     * 日志类型
     */
    private Integer type;



}
