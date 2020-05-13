package com.xywg.admin.modular.faceUtils;

import lombok.Data;

/**
 * 人脸录入model
 * 
 * @author duanfen
 * @date 2018年1月11日 下午1:51:29
 *
 */
@Data
public class FaceAddModel {

//	/**
//	 * 用户id
//	 */
//	private String uid;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户组id
	 */
	private String groupId;

	/**
	 * 图片地址
	 */
	private String image;

	/**

	 * 	用户资料
	 */
	private String userInfo;

//	/**
//	 * 参数包含append、replace。如果为“replace”，则每次注册时进行替换replace（新增或更新）操作，默认为append操作
//	 */
//	private String actionType;

	/**
	 * BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
	 * URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；FACE_TOKEN：
	 * 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
	 *
	 * 上海大学和百度都会用到这个字段，但是上海大学那边这个字段的意思是指当前上传的图片的类型，是身份证图片还是人脸模板图片
	 */
	private String imageType;
	
	public FaceAddModel(String userId, String groupId, String image,
                        String userInfo, String imageType) {
		this.userId = userId;
		this.groupId = groupId;
		this.image = image;
		this.userInfo = userInfo;
		this.imageType = imageType;
	}

}
