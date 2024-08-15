package com.zjq.dailyrecord.utils.qrcode;

import cn.hutool.core.img.Img;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 在Java中生成二维码图片通常会使用到ZXing库，这是一个非常流行的用于生成和解析一维码及二维码的库。
 * 下面是一个简单的示例，展示如何使用ZXing来根据给定的链接生成二维码图片，并将其保存到文件中。
 * @author 共饮一杯无
 */
@Slf4j
public class QRCodeGenerator {

    /**
     * 生成二维码图片(项目根目录)
     * @param text
     * @param width
     * @param height
     * @param filePath
     * @throws WriterException
     * @throws IOException
     */
    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        Path path = FileSystems.getDefault().getPath(filePath);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        ImageIO.write(image, "PNG", new File(path.toUri()));
    }

    /**
     * 生成二维码图片(指定目录)
     * @param text
     * @param width
     * @param height
     * @param file
     * @throws WriterException
     * @throws IOException
     */
    public static void generateQRCodeImageFile(String text, int width, int height, File file) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        ImageIO.write(image, "PNG", file);
    }


    /**
     * 生成二维码图片(Base64)
     * @param text
     * @param width
     * @param height
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static String generateQRCodeBase64(String text, int width, int height) throws WriterException, IOException {
        // 设置二维码的参数
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        // 生成二维码的 BitMatrix
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        // 将 BitMatrix 转换为 BufferedImage
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // 将 BufferedImage 转换为 Base64 编码
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        return base64Image;
    }

    @Test
    public void generateQrCodeBase64() {
        try {
            String base64QRCode = generateQRCodeBase64("https://zhanjq.blog.csdn.net/", 200, 200);
            log.info("data:image/png;base64," + base64QRCode);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成透明背景的二维码图片（白色二维码）
     *
     * @param text      二维码内容
     * @param width     二维码宽度
     * @param height    二维码高度
     * @param file      二维码图片文件
     * @throws WriterException
     * @throws IOException
     */
    public static void generateTransparentQRCodeImage(String text, int width, int height, File file) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        // 创建一个具有透明背景的图像
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = image.createGraphics();
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, width, height);
        graphics.setComposite(AlphaComposite.SrcOver);

        // 绘制二维码
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitMatrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }

        graphics.dispose();

        // 将图像保存为PNG文件
        ImageIO.write(image, "PNG", file);
    }

    /**
     * 生成带透明背景的黑色二维码
     * @param qrCodeData
     * @param width
     * @param height
     * @param file
     * @return
     */
    private static BufferedImage generateTransparentQrCodeBlack(String qrCodeData, int width, int height,File file) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            // 设置二维码提示
            HashMap<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置二维码的边距为0
            hints.put(EncodeHintType.MARGIN, 0);

            // 生成二维码
            bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height, hints);

            // 创建带有透明背景的BufferedImage
            BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // 遍历BitMatrix，绘制二维码
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x, y)) {
                        // 设置黑色像素
                        qrCodeImage.setRGB(x, y, Color.BLACK.getRGB());
                    } else {
                        // 设置透明像素
                        qrCodeImage.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
                    }
                }
            }

            // 将图像保存为PNG文件
            ImageIO.write(qrCodeImage, "PNG", file);
            return qrCodeImage;
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 生成五颜六色的二维码
     * @param qrCodeData
     * @param width
     * @param height
     * @param file
     * @return
     */
    private static BufferedImage generateQrCodeAllKindsOfColors(String qrCodeData, int width, int height,File file) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            // 设置二维码提示
            HashMap<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置二维码的边距为0
            hints.put(EncodeHintType.MARGIN, 0);

            // 生成二维码
            bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // 遍历BitMatrix，绘制二维码
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x, y)) {
                        // 设置黄色像素
                        qrCodeImage.setRGB(x, y, Color.YELLOW.getRGB());
                    } else {
                        // 设置黑色像素
                        qrCodeImage.setRGB(x, y, Color.BLACK.getRGB());
                    }
                }
            }

            // 将图像保存为PNG文件
            ImageIO.write(qrCodeImage, "PNG", file);
            return qrCodeImage;
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成五颜六色的二维码
     * @param qrCodeData
     * @param width
     * @param height
     * @param file
     * @return
     */
    private static BufferedImage generateQrCodeAllKindsOfColors(String qrCodeData, int width, int height,File file, int foreColor,int bgColor) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            // 设置二维码提示
            HashMap<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置二维码的边距为0
            hints.put(EncodeHintType.MARGIN, 0);

            // 生成二维码
            bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // 遍历BitMatrix，绘制二维码
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x, y)) {
                        // 设置黄色像素码
                        qrCodeImage.setRGB(x, y, foreColor);
                    } else {
                        // 设置黑色像素背景
                        qrCodeImage.setRGB(x, y, bgColor);
                    }
                }
            }

            // 将图像保存为PNG文件
            ImageIO.write(qrCodeImage, "PNG", file);
            return qrCodeImage;
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成充满型的二维码图片
     *
     * @param qrCodeData      二维码内容
     * @param width     二维码宽度
     * @param height    二维码高度
     * @param file      二维码图片文件
     * @throws WriterException
     * @throws IOException
     */
    public static void generateNoMarginQRCodeImage(String qrCodeData, int width, int height, File file, int color,int bgColor)  {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            // 设置编码提示
            HashMap<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置二维码的边距为0
            hints.put(EncodeHintType.MARGIN, 0);

            // 生成二维码
            bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // 遍历BitMatrix，绘制二维码
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x, y)) {
                        // 设置黑色像素
                        qrCodeImage.setRGB(x, y, color);
                    } else {
                        // 设置透明像素
                        qrCodeImage.setRGB(x, y, bgColor);
                    }
                }
            }

            // 将图像保存为PNG文件
            ImageIO.write(qrCodeImage, "PNG", file);
        } catch (WriterException | IOException e) {
            log.error("生成透明背景的二维码图片失败:{}", e);
        }
    }

    /**
     * 生成带logo的二维码图片
     *
     * @param qrCodeData      二维码内容
     * @param width     二维码宽度
     * @param height    二维码高度
     * @param file      二维码图片文件
     * @param logoFile  logo文件
     * @throws WriterException
     * @throws IOException
     */
    public static void generateInLogoQRCodeImage(String qrCodeData, int width, int height, File file, int color, int bgColor, File logoFile)  {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            // 设置编码提示
            HashMap<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置二维码的边距为0
            hints.put(EncodeHintType.MARGIN, 0);

            // 生成二维码
            bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // 遍历BitMatrix，绘制二维码
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x, y)) {
                        // 设置黑色像素
                        qrCodeImage.setRGB(x, y, color);
                    } else {
                        // 设置透明像素
                        qrCodeImage.setRGB(x, y, bgColor);
                    }
                }
            }
            final int qrWidth = qrCodeImage.getWidth();
            final int qrHeight = qrCodeImage.getHeight();
            int logoWidth;
            int logoHeight;
            BufferedImage logoImg =  read(logoFile);
            // 按照最短的边做比例缩放
            if (qrWidth < qrHeight) {
                logoWidth = qrWidth / 6;
                logoHeight = logoImg.getHeight(null) * logoWidth / logoImg.getWidth(null);
            } else {
                logoHeight = qrHeight / 6;
                logoWidth = logoImg.getWidth(null) * logoHeight / logoImg.getHeight(null);
            }

            Img.from(qrCodeImage).pressImage(//
                    Img.from(logoImg).round(0.3).getImg(), // 圆角
                    new Rectangle(logoWidth, logoHeight), //
                    1//
            );

            // 将图像保存为PNG文件
            ImageIO.write(qrCodeImage, "PNG", file);
        } catch (WriterException | IOException e) {
            log.error("生成透明背景的二维码图片失败:{}", e);
        }
    }


    @Test
    public void generateNoMarginQRCodeImage() {
        String url = "https://zhanjq.blog.csdn.net/";
        File qrFile = createTempFile("qrcode", UUID.randomUUID().toString() + "_", ".png");
        generateNoMarginQRCodeImage(url, 200, 200, qrFile,Color.BLACK.getRGB(),Color.WHITE.getRGB());
        System.out.println("QR code generated successfully at: " + qrFile.getAbsolutePath());
    }

    @Test
    public void generateQrCodeAllKindsOfColors() {
        String url = "https://zhanjq.blog.csdn.net/";
        File qrFile = createTempFile("qrcode", UUID.randomUUID().toString() + "_", ".png");
        generateQrCodeAllKindsOfColors(url, 200, 200, qrFile);
        System.out.println("QR code generated successfully at: " + qrFile.getAbsolutePath());
    }

    @Test
    public void generateQrCodeAllKindsOfColorsDesign() {
        String url = "https://zhanjq.blog.csdn.net/";
        File qrFile = createTempFile("qrcode", UUID.randomUUID().toString() + "_", ".png");
        generateQrCodeAllKindsOfColors(url, 200, 200, qrFile,Color.YELLOW.getRGB(),Color.BLACK.getRGB());
        System.out.println("QR code generated successfully at: " + qrFile.getAbsolutePath());
    }


    @Test
    public void generateQRCodeImage() {
        try {
            String url = "https://zhanjq.blog.csdn.net/";
            String filePath = "qr-code.png";
            generateQRCodeImage(url, 200, 200, filePath);
            System.out.println("QR code generated successfully at: " + filePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void generateQRCodeImageFile() throws IOException, WriterException {
        String url = "https://zhanjq.blog.csdn.net/";

        File qrFile = createTempFile("qrcode", UUID.randomUUID().toString() + "_", ".png");
        generateQRCodeImageFile(url, 200, 200, qrFile);
        System.out.println("QR code generated successfully at: " + qrFile.getAbsolutePath());
    }

    @Test
    public void generateTransparentQRCodeImage() throws IOException, WriterException {
        String url = "https://zhanjq.blog.csdn.net/";

        File qrFile = createTempFile("qrcode", UUID.randomUUID().toString() + "_", ".png");
        generateTransparentQRCodeImage(url, 200, 200, qrFile);
        System.out.println("QR code generated successfully at: " + qrFile.getAbsolutePath());
    }

    @Test
    public void generateTransparentQrCodeBlack() throws IOException, WriterException {
        String url = "https://zhanjq.blog.csdn.net/";
        File qrFile = createTempFile("qrcode", UUID.randomUUID().toString() + "_", ".png");
        generateTransparentQrCodeBlack(url, 200, 200, qrFile);
        System.out.println("QR code generated successfully at: " + qrFile.getAbsolutePath());
    }


    @Test
    public void generateInLogoQRCodeImage() {
        String url = "https://zhanjq.blog.csdn.net/";
        File qrFile = createTempFile("qrcode", UUID.randomUUID().toString() + "_", ".png");
        File logoFile = FileUtil.file("d:/logo_small1.png");
        generateInLogoQRCodeImage(url, 200, 200, qrFile,Color.BLUE.getRGB(),Color.GRAY.getRGB(),logoFile);
        System.out.println("QR code generated successfully at: " + qrFile.getAbsolutePath());
    }

    /**
     * 从文件中读取图片
     *
     * @param imageFile 图片文件
     * @return 图片
     */
    public static BufferedImage read(File imageFile) {
        BufferedImage result;
        try {
            result = ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }

        if (null == result) {
            throw new IllegalArgumentException("Image type of file [" + imageFile.getName() + "] is not supported!");
        }

        return result;
    }

    /**
     * 生成临时文件
     *
     * @param subPath 文件目录
     * @param prefix  文件名（不少于3个字符）
     * @param suffix  文件后缀
     * @return 临时文件
     */
    public static File createTempFile(String subPath, String prefix, String suffix) {

        String pattern = "yyyyMMddHHmm";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        String dateStr = dtf.format(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()));
        String directory = String.format("%s/%s", tempFilePath(subPath), dateStr);
        File f = new File(directory);
        if (!f.exists()) {
            f.mkdirs();
        }
        try {
            return File.createTempFile(prefix, suffix, f);
        } catch (Exception e) {
            log.error("error 3:{}",e);
        }
        return null;
    }

    /**
     * 生成临时文件路径
     *
     * @param subPath 子目录
     * @return 用户home目录下的临时文件路径
     */
    public static String tempFilePath(String subPath) {
        File tmpdir = new File(System.getProperty("user.home"));
        return tmpdir.getAbsolutePath() + "/tmp/" + subPath;
    }
}
