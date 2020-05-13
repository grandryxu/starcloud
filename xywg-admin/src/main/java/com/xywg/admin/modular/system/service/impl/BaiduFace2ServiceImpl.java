package com.xywg.admin.modular.system.service.impl;

import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.modular.faceUtils.Base64Util;
import com.xywg.admin.modular.faceUtils.Constant;
import com.xywg.admin.modular.faceUtils.HttpUtil;
import com.xywg.admin.modular.faceUtils.TokenUtil;
import com.xywg.admin.modular.system.service.BaiduFaceService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Primary
@Service
public class BaiduFace2ServiceImpl implements BaiduFaceService {

	private static final Logger logger = LoggerFactory.getLogger(BaiduFace2ServiceImpl.class);

	@Override
	public boolean checkFace(MultipartFile file) {
		try {
			return detect(file);
		} catch (Exception e) {
			throw new XywgException(500, e.getMessage());
		}
	}

	/**
	 * 人脸检测V3
	 *
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	public static boolean detect(MultipartFile multipartFile) throws Exception {
		boolean flag = false;
		// String faceToken = null;
		// byte[] imgData = FileUtil.getFile(filePath);
		// if (imgData.length == 0) {
		// throw new Exception("找不到图片");
		// }
		byte[] imgData;
		try (InputStream inputStream = multipartFile.getInputStream()) {
			imgData = StreamUtils.copyToByteArray(inputStream);
		}

		// getFileByBytes(imgData,"D://","test.jpg");

		String imgStr = Base64Util.encode(imgData);
		// 构造参数
		Map<String, String> map = new HashMap<>(10);
		map.put("image", imgStr);
		map.put("max_face_num", "2");
		map.put("image_type", Constant.IMAGE_TYPE);
		map.put("face_field", Constant.FACE_DETECT_FACE_FIELD);
		JSONObject paramJson = new JSONObject(map);
		String param = paramJson.toString();
		// 获取token，拼接url
		String accessToken = TokenUtil.getToken();
		String requestUrl = Constant.BAIDU_DETECT_URL_V3 + "?access_token=" + accessToken;
		// 发送请求
		String result = null;
		try {
			result = HttpUtil.postLocal(requestUrl, param);
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		JSONObject json = new JSONObject(result);
		// 如果请求成功，获取face_token（当前图片人脸的唯一的标识）
		String code = json.get("error_code") + "";
		if ("0".equals(code)) {
			JSONObject resultJson = (JSONObject) json.get("result");
			// 人脸个数比较
			String faceNum = resultJson.getString("face_num");
			if (Integer.parseInt(faceNum) > 1) {
				throw new XywgException(500, "请保持只有一张人脸！");
			}

			JSONArray faceList = resultJson.getJSONArray("face_list");
			JSONObject face = (JSONObject) faceList.get(0);
			// 人脸质量信息
			String quality = face.getString("quality");
			JSONObject qualityJson = new JSONObject(quality);
			// 人脸模糊比较
			BigDecimal blur = new BigDecimal(qualityJson.getString("blur"));
			// 0.7作为阈值，大于阈值才能算清晰照片 0.7
			if (blur.compareTo(Constant.BLUR_SCORE) > 0) {
				throw new XywgException(500, "人脸模糊,拍摄时不要晃动！");
			}
			// 脸部区域的光照程度
			BigDecimal illumination = new BigDecimal(qualityJson.getString("illumination"));
			// 脸部区域的光照程度和阈值比较 40
			if (illumination.compareTo(Constant.ILLUMINATION_SCORE) < 0) {
				throw new XywgException(500, "请到光线适宜的地方拍摄！");
			}
			// 人脸完整度 0
			BigDecimal completeness = new BigDecimal(qualityJson.getString("completeness"));
			if (completeness.compareTo(BigDecimal.ZERO) == 0) {
				throw new XywgException(500, "人脸图片不清晰，请重新录入！");
			}

			// 人脸质量信息
			String occlusionJson = qualityJson.getString("occlusion");
			JSONObject occlusion = new JSONObject(occlusionJson);
			// 左眼遮挡比例
			BigDecimal leftEye = new BigDecimal(occlusion.getString("left_eye"));
			// 左眼遮挡比例比较0.6
			if (leftEye.compareTo(Constant.LEFT_EYE_SCORE) > 0) {
				throw new XywgException(500, "左眼被遮挡，请重新录入！");
			}

			// 右眼遮挡比例
			BigDecimal rightEye = new BigDecimal(occlusion.getString("right_eye"));
			// 右眼遮挡比例比较0.6
			if (rightEye.compareTo(Constant.RIGHT_EYE_SCORE) > 0) {
				throw new XywgException(500, "右眼被遮挡，请重新录入！");
			}

			// 嘴巴遮挡比例
			BigDecimal mouth = new BigDecimal(occlusion.getString("mouth"));
			// 嘴巴遮挡比例比较0.6
			if (mouth.compareTo(Constant.MOUTH_SCORE) > 0) {
				throw new XywgException(500, "嘴巴被遮挡，请重新录入！");
			}

			// 左脸遮挡比例
			BigDecimal leftCheck = new BigDecimal(occlusion.getString("left_cheek"));
			// 左脸遮挡比例比较0.6
			if (leftCheck.compareTo(Constant.LEFT_FACE_SCORE) > 0) {
				throw new XywgException(500, "左脸颊被遮挡，请重新录入！");
			}

			// 右脸遮挡比例
			BigDecimal rightCheck = new BigDecimal(occlusion.getString("right_cheek"));
			// 右脸遮挡比例比较0.6
			if (rightCheck.compareTo(Constant.RIGHT_FACE_SCORE) > 0) {
				throw new XywgException(500, "右脸颊被遮挡，请重新录入！");
			}

			// 下巴遮挡比例
			BigDecimal chinContour = new BigDecimal(occlusion.getString("chin_contour"));
			// 下巴遮挡比例比较0.7
			if (chinContour.compareTo(Constant.CHIN_CONTOUR_SCORE) > 0) {
				throw new XywgException(500, "下巴被遮挡，请重新录入！");
			}
			// faceToken = ((JSONObject) faceList.get(0)).getString("face_token");
			flag = true;
		} else if (code.equals("222202")) {
			throw new XywgException(500, "图片中没有人脸，请重新录入！");
		} else if (code.equals("222203")) {
			throw new XywgException(500, "无法解析人脸，请重新录入！");
		} else if (code.equals("222207")) {
			throw new XywgException(500, "未找到匹配的用户，请重新录入！");
		} else {
			throw new XywgException(500, "图片不能识别，请重新录入！");
		}
		return flag;
	}

	// public static void getFileByBytes(byte[] bytes, String filePath, String
	// fileName) {
	// BufferedOutputStream bos = null;
	// FileOutputStream fos = null;
	// File file = null;
	// try {
	// File dir = new File(filePath);
	// // 判断文件目录是否存在
	// if (!dir.exists() && dir.isDirectory()) {
	// dir.mkdirs();
	// }
	// file = new File(filePath + "\\" + fileName);
	//
	// //输出流
	// fos = new FileOutputStream(file);
	//
	// //缓冲流
	// bos = new BufferedOutputStream(fos);
	//
	// //将字节数组写出
	// bos.write(bytes);
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (bos != null) {
	// try {
	// bos.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// if (fos != null) {
	// try {
	// fos.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

}
