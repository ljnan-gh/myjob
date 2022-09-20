package com.bd.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LJnan
 * @since 2022-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AppJob extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    
    private Long jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务状态
     */
    private Integer jobStatus;

    /**
     * 任务执行规则（多久执行一次）
     */
    private String cronExpression;

    /**
     * 失败次数（只记录最后一次成功到目前失败状态下的失败次数）
     */
    private Integer errorTimes;

    /**
     * 最后一次执行时间
     */
    private Long lastExecutionTime;

    /**
     * 最后一次任务的状态(启动，暂定)
     */
    private Integer lastExecutionStatus;

    /**
     * 任务创建时间
     */
    private Long createTime;

    /**
     * 任务跟新时间
     */
    private Long updateTime;

    /**
     * 任务说明
     */
    private String description;
    /**
     * 任务路径
     */
    private String jobPath;


}
