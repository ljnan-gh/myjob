package com.bd.mapper;

import com.bd.entity.AppJob;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LJnan
 * @since 2022-01-07
 */
public interface AppJobMapper extends BaseMapper<AppJob> {
	@Select("select * from app_job")
	public List<AppJob> loadJobs();
	
}
