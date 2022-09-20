package com.bd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LJnan
 * @since 2022-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Dataanalysishistory extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    @TableField("CreateTime")
    private LocalDateTime CreateTime;

    private String dataTime;

    private String key;

    private String value;

    @TableField("Enable")
    private Integer Enable;


}
