package com.zjq.dailyrecord.utils.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 共饮一杯无
 * @date 2024/9/10 17:15
 * @description:识别图片二维码 （只支持识别高质量的二维码，com.zjq.dailyrecord.utils.qrcode.QRCodeGenerator#generateHighQRCodeImage(java.lang.String, int, int, java.lang.String)生成方式）
 */
public class QRCodeReader {

    public static void main(String[] args)  {
        try {
            // 使用绝对路径，或者将图片放在项目resources目录下
            String imagePath = "C:\\Users\\11876\\tmp\\qrcode\\202512011637\\qr-code.png";
            File imageFile = new File(imagePath);

            // 如果上面路径找不到，尝试使用类路径资源
            if (!imageFile.exists()) {
                // 尝试从resources目录加载
                File resourceFile = new File(QRCodeReader.class.getClassLoader()
                        .getResource("qr-code.png").getFile());
                imageFile = resourceFile;
            }

            if (!imageFile.exists()) {
                System.err.println("文件不存在: " + imageFile.getAbsolutePath());
                return;
            }

            // 读取图片
            BufferedImage image = ImageIO.read(imageFile);

            // 转换成二进制图片
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            // 解码二维码 - 添加更多提示提高识别率
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.QR_CODE));

            try {
                Result result = new MultiFormatReader().decode(binaryBitmap, hints);
                System.out.println("二维码内容: " + result.getText());
            } catch (NotFoundException e) {
                System.err.println("在图片中未找到二维码: " + e.getMessage());
                System.err.println("请确认图片中包含有效的二维码");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
