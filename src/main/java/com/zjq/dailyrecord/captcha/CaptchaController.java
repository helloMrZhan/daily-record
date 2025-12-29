package com.zjq.dailyrecord.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 共饮一杯无
 * @date 2024/8/14 19:06
 * @description:
 */
@Api(tags = "验证码管理")
@RequestMapping("/api/captcha")
@RestController
@Slf4j
public class CaptchaController {

    @ApiOperation(value = "获得图形验证码", notes = "生成并返回一个图形验证码图片")
    @GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletResponse response, HttpServletRequest request){
        //生成验证码图片(定义图形的宽和高,验证码的位数，干扰线的条数)
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(100, 50, 4, 25);
        //告诉浏览器输出内容为jpeg类型的图片
        response.setContentType("image/jpeg");
        //禁止浏览器缓存
        response.setHeader("Pragma","No-cache");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            //图形验证码写出到流，也可以写出到文件如：circleCaptcha.write(“d:/circle25.jpeg”);
            circleCaptcha.write(outputStream);
            //从图形验证码图片中获取它的字符串验证码(获取字符串验证码要在图形验证码wirte写出后面才行，不然得到的值为null)
            String captcha = circleCaptcha.getCode();
            request.getSession().setAttribute("captcha",captcha);
            log.info("生成的验证码:{}",captcha);

            // todo 实际使用应该把验证码写入到redis中并设置有效期，这里只是测试，所以直接保存在session中
            log.info("session id:{}",request.getSession().getId());
            //关闭流
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}