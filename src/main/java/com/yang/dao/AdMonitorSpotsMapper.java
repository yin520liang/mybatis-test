/**
 * 
 */
package com.yang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.yang.entity.AdMonitorSpots;

/**
 * @Title AdMonitorSpotsMapper
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月18日
 */
public interface AdMonitorSpotsMapper {
	
	@Select("select * from st_data_admonitor_spots limit 1, 200")
	List<AdMonitorSpots> list();

}
