package com.xywg.admin.modular.zr.service;


/**
 * <p>
 * 中如接口
 * </p>
 *
 * @author jln
 */
public interface IZrInterfaceService {

	boolean SendCompanyInfo(String stoken);

	boolean SendProjectInfo(String stoken);

	boolean SendDeviceInfo(String stoken);

	boolean SendUserInfo(String stoken) throws Exception ;

	boolean SendAttendance(String stoken) throws Exception;

	boolean SendInjuryInfo(String stoken) throws Exception;

   
}
