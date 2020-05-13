package com.xywg.admin.modular.system.service.impl;

import cn.hutool.http.HttpException;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.xywg.admin.modular.faceUtils.HttpUtil;
import com.xywg.admin.modular.system.service.BaiduFaceService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class BaiduFaceServiceImpl implements BaiduFaceService {

    private final String checkUrl = "http://61.147.204.98:9500/faceDetect/";

  /*  @Override
    public boolean checkFace(MultipartFile file, ByteArrayOutputStream bo) {
        try {
            if(file.isEmpty()) {
                return false;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("image",Base64.encode(bo.toByteArray()));
            String paramsStr = JSONObject.fromObject(params).toString();
            String result = HttpUtil.postLocal(checkUrl, paramsStr);
            JSONObject jObject = JSONObject.fromObject(result);
            if(null == jObject || !jObject.has("code")) {
                return false;
            }
            if(jObject.getString("code").equals("success")) {
                int faces = jObject.getInt("faces");
                if(faces == 1) {
                    return true;
                }else{
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }*/

    @Override
    public boolean checkFace(MultipartFile file) {
        try {
            if(file.isEmpty()) {
                return false;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("image", this.fileToBase64(file));
            String paramsStr = JSONObject.fromObject(params).toString();
            String result = HttpUtil.postLocal(checkUrl, paramsStr);
            JSONObject jObject = JSONObject.fromObject(result);
            if(null == jObject || !jObject.has("code")) {
                return false;
            }
            if(jObject.getString("code").equals("success")) {
                int faces = jObject.getInt("faces");
                if(faces == 1) {
                    return true;
                }else{
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    /**
     * file转base64
     * @param bo
     * @return
     */
 /*   private String fileToBase64(ByteArrayOutputStream bo) {
        File f = null;
        FileInputStream inputFile = null;
        try {
            //转base64
            String base64=null;
            base64 = Base64.encode(bo.toByteArray());
            return base64;
        } catch (HttpException e) {
            e.printStackTrace();
        } finally {
            try {
                inputFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            f.deleteOnExit();     //使用完成删除文件
        }
        return  null;
        *//*String base64 = null;
        InputStream in = null;
        try {
            in = file.getInputStream();
            byte[] bytes = new byte[in.available()];
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;*//*
    }*/



    /**
     * file转base64
     * @param file
     * @return
     */
    private String fileToBase64(MultipartFile file) {
        File f = null;
        FileInputStream inputFile = null;
        try {
            f=File.createTempFile("tmp", null);
            file.transferTo(f);
            //转base64
            inputFile = new FileInputStream(f);
            String base64=null;
            byte[] buffer = new byte[(int) f.length()];
            inputFile.read(buffer);
            base64 = file2Base64(f);
            return base64;
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            f.deleteOnExit();     //使用完成删除文件
        }
        return  null;
        /*String base64 = null;
        InputStream in = null;
        try {
            in = file.getInputStream();
            byte[] bytes = new byte[in.available()];
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;*/
    }

    public static String file2Base64(File file) {
        if(file==null) {
            return null;
        }
        String base64 = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] buff = new byte[fin.available()];
            fin.read(buff);
            base64 = Base64.encode(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }
}
