package com.xywg.admin.core.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * 图片压缩
 * @author hh cao
 * @date 2019/4/1
 */
public class ImageUtils {
    /**
     * 传入mulitipartFile实现图片压缩
     * @param multipartFile
     * @param outputStream
     */
    public static void compressImage(MultipartFile multipartFile, FileOutputStream outputStream)throws Exception {
        File file = mutipartFile2File(multipartFile);
        compressImage(new FileInputStream(file), outputStream);
    }

    /**
     * 传入图片流，实现图片压缩
     * @param inputStream
     * @param outputStream
     */
    public static void compressImage(FileInputStream inputStream, FileOutputStream outputStream)throws IOException {
        Thumbnails.of(inputStream).scale(getScaleValue(inputStream)).outputQuality(getQualityValue(inputStream)).toOutputStream(outputStream);
    }

    /**
     * 传入inputstream类的子类，返回压缩过的base64字符串，和實名制對接使用
     * @param inputStream
     * @return
     */
    public static String compressImage(InputStream inputStream, long size)throws IOException {
        //构建可以读取多次的inputstream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > -1 ) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        //获取两个inputstream
        InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
        InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
        //用来给芬姐那边多种图片情况做计算
        BufferedImage img = ImageIO.read(stream1);
        float h = img.getHeight();
        float w = img.getWidth();
        float height = Float.parseFloat(new DecimalFormat("#.00").format(400/h));
        float weight = Float.parseFloat(new DecimalFormat("#.00").format(400/w));
        float scale = height>weight?weight:height;
        BufferedImage image = Thumbnails.of(stream2).scale(h>400||w>400?scale:getScaleValue(size)).outputQuality(getQualityValue(size)).asBufferedImage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage newBufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        ImageIO.write(newBufferedImage, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Img = encoder.encode(outputStream.toByteArray());
        return base64Img;
    }

    /**
     * 压缩输出到
     * @param file
     * @param outputPath
     */
    public static void compressImage(File file, String outputPath)throws IOException {
        Thumbnails.of(file).scale(getScaleValue(new FileInputStream(file))).outputQuality(getQualityValue(new FileInputStream(file))).toFile(outputPath);
    }

    /**
     * 增加文字水印
     * @param inputStream  文件流
     * @param logoText  需要添加的水印字
     * @param degree  字旋转角度
     * @param clarity  透明度 <1
     * @return
     */
    public static byte[] addWatermark(InputStream inputStream, String logoText, Integer degree, Float clarity) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            Image srcImg = ImageIO.read(inputStream);
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);

            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            // 设置颜色
            g.setColor(Color.red);
            // 设置 Font
            g.setFont(new Font("宋体", Font.BOLD, 20));
            float alpha = clarity;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .
            int width = ((BufferedImage) srcImg).getWidth()-230;
            int height = ((BufferedImage) srcImg).getHeight()-20;

            g.drawString(logoText, width, height);
            g.dispose();
            // 生成图片
            ImageIO.write(buffImg, "JPG", os);
           //测试用生成base64然后看水印效果
            /*BASE64Encoder encoder = new BASE64Encoder();
            String base64Img = encoder.encode(os.toByteArray());
            System.out.println(base64Img);*/
            return os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取需要压缩的图片带下比例
     * @param inputStream
     * @return
     */
    private static float getScaleValue(FileInputStream inputStream)throws IOException {
        FileChannel fc = inputStream.getChannel();
        return getScaleValue(fc.size());
    }

    /**
     * 获取需要压缩的图片带下比例
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static float getScaleValue(InputStream inputStream)throws IOException {
        long size = inputStream.available();
        return getScaleValue(size);
    }

    /**
     * 返回需要压缩的图片比例
     * @param size
     * @return
     */
    private static float getScaleValue(long size) {
        float scaleValue = 1f;
        if(size>3*1024*1024) {
            scaleValue = 0f;
        }else if(size>1024*1024) {
            scaleValue = 0.1f;
        }else if(size>800*1024) {
            scaleValue = 0.15f;
        }else if(size>700*1024) {
            scaleValue = 0.2f;
        }else if(size>600*1024) {
            scaleValue = 0.25f;
        }else if(size>400*1024) {
            scaleValue = 0.3f;
        }else if(size>300*1024) {
            scaleValue = 0.4f;
        }else if(size>200*1024) {
            scaleValue = 0.8f;
        }
        return scaleValue;
    }

    /**
     * 获取压缩质量比例
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static float getQualityValue(FileInputStream inputStream)throws IOException {
        FileChannel fc = inputStream.getChannel();
        return getQualityValue(fc.size());
    }

    /**
     * 获取压缩质量比例
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static float getQualityValue(InputStream inputStream)throws IOException {
        return getQualityValue(inputStream.available());
    }

    /**
     * 获取压缩质量比例
     * @param size
     * @return
     * @throws IOException
     */
    private static float getQualityValue(long size)throws IOException {
        float qualityValue = 0.25f;
        if(size<50*1024 || size>600*1024) {
            qualityValue = 0.15f;
        }
        return qualityValue;
    }

    /**
     * multipartfile转file
     * @param multipartFile
     * @return
     */
    public static File mutipartFile2File(MultipartFile multipartFile) {
        File file = null;
        try {
            file=File.createTempFile("tmp", null);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void main(String[] args) {
        try {
            /*File file = new File("D:\\test\\aa");
            File[] tempList = file.listFiles();
            ImageUtils imageUtils = new ImageUtils();
            for (int i = 0; i < tempList.length; i++) {
                File f = tempList[i];
                imageUtils.compressImage(f, "D:\\test\\bb\\"+f.getName());
            }*/
            /*File file = new File("D:\\photo\\2019\\04");
            File[] tempList = file.listFiles();
            for(File f : tempList) {

            }*/


            File file = new File("D:\\test\\aa\\22.jpg");
            //System.out.println(file.getPath());
            //throw  new IOException("error");
            ImageUtils.compressImage(new FileInputStream(file), file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
