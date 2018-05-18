/**
 * 
 */
package com.yang.entity;

import java.util.Date;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

/**
 * @title
 * @description 广告位
 *              <p>
 *              关联表：st_relation_monitor_spots_celebrity
 *              st_relation_monitor_spots_play
 * @author lvzhaoyang
 * @date 2017年8月23日上午11:05:30
 */
@Data
public class AdMonitorSpots {

	private Long id;

	private String spotsStr;

	private Long campaignId;

	private Date startTime;

	private Date endTime;

	private String name;

	private String description;

	private String frequency;

	private String locale;

	private String regOrientation;
	//频道

	private Long channelId;
	//媒体

	private Long mediaId;
	//广告素材类型

	private Long displayTypeId;
	//广告位类型

	private Long spotsTypeId;
	//采购方式

	private Long purchaseTypeId;
	//落地页类型

	private Long landingPageTypeId;
	//内容采买类型

	private Long playPurchaseTypeId;
	

	private Date createTime;

	private Date updateTime;

	private Date deleteTime;
	
	
	public boolean isRegionalBuying(){
		return StringUtils.isNotBlank(this.regOrientation);
	}
	
	public void fillDefaultRelation(){
		if(this.channelId == null){
			this.channelId = DBConstant.DEFAULT_LONG;
		}
		if(this.mediaId == null){
			this.mediaId = DBConstant.DEFAULT_LONG;
		}
		if(this.displayTypeId == null){
			this.displayTypeId = DBConstant.DEFAULT_LONG;
		}
		if(this.spotsTypeId == null){
			this.spotsTypeId = DBConstant.DEFAULT_LONG;
		}
		if(this.purchaseTypeId == null){
			this.purchaseTypeId = DBConstant.DEFAULT_LONG;
		}
		if(this.landingPageTypeId == null){
			this.landingPageTypeId = DBConstant.DEFAULT_LONG;
		}
		if(this.playPurchaseTypeId == null){
			this.playPurchaseTypeId = DBConstant.DEFAULT_LONG;
		}
	}
	
	private static class DBConstant {
		static final Long DEFAULT_LONG = -1L;
	}
}
