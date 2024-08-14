package com.zjq.dailyrecord.utils.hutool;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.*;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.*;
import cn.hutool.cron.CronUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zjq.dailyrecord.excel.openoffice.OnlinePreviewController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author 共饮一杯无
 * @date 2024/7/29 16:30
 * @description: hutool工具类验证
 */
@Slf4j
public class HutoolTest {


    // 类型转换测试
    @Test
    public  void convert() {
        //转换为字符串
        int a = 1;
        String aStr = Convert.toStr(a);
        //转换为指定类型数组
        String[] b = {"1", "2", "3", "4"};
        Integer[] bArr = Convert.toIntArray(b);
        //转换为日期对象
        String dateStr = "2017-05-06";
        Date date = Convert.toDate(dateStr);
        //转换为列表
        String[] strArr = {"a", "b", "c", "d"};
        List<String> strList = Convert.toList(String.class, strArr);
    }

    // DateUtil测试
    @Test
    public  void dateUtil() {
        //Date、long、Calendar之间的相互转换
        //当前时间
        Date date = DateUtil.date();
        //Calendar转Date
        date = DateUtil.date(Calendar.getInstance());
        System.out.println("date = " + date);
        //时间戳转Date
        date = DateUtil.date(System.currentTimeMillis());
        System.out.println("date = " + date);
        //自动识别格式转换
        String dateStr = "2017-03-01";
        date = DateUtil.parse(dateStr);
        System.out.println("date = " + date);
        //自定义格式化转换
        date = DateUtil.parse(dateStr, "yyyy-MM-dd");
        System.out.println("date = " + date);
        //格式化输出日期
        String format = DateUtil.format(date, "yyyy-MM-dd");
        System.out.println("format = " + format);
        //获得年的部分
        int year = DateUtil.year(date);
        System.out.println("year = " + year);
        //获得月份，从0开始计数
        int month = DateUtil.month(date);
        System.out.println("month = " + month);
        //获取某天的开始、结束时间
        Date beginOfDay = DateUtil.beginOfDay(date);
        System.out.println("beginOfDay = " + beginOfDay);
        Date endOfDay = DateUtil.endOfDay(date);
        System.out.println("endOfDay = " + endOfDay);
        //计算偏移后的日期时间
        Date newDate = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
        System.out.println("newDate = " + newDate);
        //计算日期时间之间的偏移量
        long betweenDay = DateUtil.between(date, newDate, DateUnit.DAY);
        System.out.println("betweenDay = " + betweenDay);
    }

    // LocalDateTimeUtil测试
    @Test
    public  void LocalDateTimeUtil() {
        //日期转换
        String dateStr = "2020-01-23 12:23:56";
        DateTime dt = DateUtil.parse(dateStr);

        // Date对象转换为LocalDateTime
        LocalDateTime of = LocalDateTimeUtil.of(dt);

        // 时间戳转换为LocalDateTime
        of = LocalDateTimeUtil.ofUTC(dt.getTime());
        System.out.println("of = " + of);
        // 日期字符串解析
        // 解析ISO时间
        LocalDateTime localDateTime = LocalDateTimeUtil.parse("2020-01-23T12:23:56");

        // 解析自定义格式时间
        localDateTime = LocalDateTimeUtil.parse("2020-01-23", DatePattern.NORM_DATE_PATTERN);

        // 解析同样支持LocalDate：
        LocalDate localDate = LocalDateTimeUtil.parseDate("2020-01-23");
        System.out.println("localDate = " + localDate);

        // 解析日期时间为LocalDate，时间部分舍弃
        localDate = LocalDateTimeUtil.parseDate("2020-01-23T12:23:56", DateTimeFormatter.ISO_DATE_TIME);

       // 日期格式化
        LocalDateTime localDateTime1 = LocalDateTimeUtil.parse("2020-01-23T12:23:56");
        System.out.println("localDateTime1 = " + localDateTime1);

        // "2020-01-23 12:23:56"
        String format = LocalDateTimeUtil.format(localDateTime, DatePattern.NORM_DATETIME_PATTERN);
        System.out.println("format = " + format);

        //日期偏移
        final LocalDateTime localDateTime2 = LocalDateTimeUtil.parse("2020-01-23T12:23:56");

        // 增加一天
        // "2020-01-24T12:23:56"
        LocalDateTime offset = LocalDateTimeUtil.offset(localDateTime2, 1, ChronoUnit.DAYS);
        System.out.println("offset = " + offset);
        //如果是减少时间，offset第二个参数传负数即可：
        // "2020-01-22T12:23:56"
        offset = LocalDateTimeUtil.offset(localDateTime, -1, ChronoUnit.DAYS);
        System.out.println("offset = " + offset);
        //计算时间间隔
        LocalDateTime start = LocalDateTimeUtil.parse("2019-02-02T00:00:00");
        LocalDateTime end = LocalDateTimeUtil.parse("2020-02-02T00:00:00");

        Duration between = LocalDateTimeUtil.between(start, end);

        // 365
        long days = between.toDays();
        System.out.println("days = " + days);

        //一天的开始和结束
        LocalDateTime localDateTime3 = LocalDateTimeUtil.parse("2020-01-23T12:23:56");

        // "2020-01-23T00:00"
        LocalDateTime beginOfDay = LocalDateTimeUtil.beginOfDay(localDateTime3);
        System.out.println("beginOfDay = " + beginOfDay);

        // "2020-01-23T23:59:59.999999999"
        LocalDateTime endOfDay = LocalDateTimeUtil.endOfDay(localDateTime3);
        System.out.println("endOfDay = " + endOfDay);
    }

    @Test
    public  void JSONUtil() {
        PmsBrand brand = new PmsBrand();
        brand.setId(1L);
        brand.setName("小米");
        brand.setShowStatus(1);
        //对象转化为JSON字符串
        String jsonStr = JSONUtil.parse(brand).toString();
        log.info("jsonUtil parse:{}", jsonStr);
        //JSON字符串转化为对象
        PmsBrand brandBean = JSONUtil.toBean(jsonStr, PmsBrand.class);
        log.info("jsonUtil toBean:{}", brandBean);
        List<PmsBrand> brandList = new ArrayList<>();
        brandList.add(brand);
        String jsonListStr = JSONUtil.parse(brandList).toString();
        //JSON字符串转化为列表
        brandList = JSONUtil.toList(new JSONArray(jsonListStr), PmsBrand.class);
        log.info("jsonUtil toList:{}", brandList);
    }


    @Test
    public void StrUtil() {
        //判断是否为空字符串
        String str = "test";
        boolean empty = StrUtil.isEmpty(str);
        boolean notEmpty = StrUtil.isNotEmpty(str);
        log.info("strUtil isEmpty:{}, isNotEmpty:{}", empty, notEmpty);
        str = "   ";
        boolean blank = StrUtil.isBlank(str);
        boolean notBlank = StrUtil.isNotBlank(str);
        log.info("strUtil isBlank:{}, isNotBlank:{}", blank, notBlank);
        //去除字符串的前后缀
        StrUtil.removeSuffix("a.jpg", ".jpg");
        StrUtil.removePrefix("a.jpg", "a.");
        //格式化字符串
        String template = "这只是个占位符:{}";
        String str2 = StrUtil.format(template, "我是占位符");
        log.info("/strUtil format:{}", str2);
    }

    @Test
    public void ClassPathResource() throws IOException {
        //获取定义在src/main/resources文件夹中的配置文件
        ClassPathResource resource = new ClassPathResource("generator.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());
        log.info("/classPath:{}", properties);
    }


    @Test
    public void ReflectUtil() {
        //获取某个类的所有方法
        Method[] methods = ReflectUtil.getMethods(PmsBrand.class);
        log.info("/reflectUtil getMethods:{}", methods);
        //获取某个类的指定方法
        Method method = ReflectUtil.getMethod(PmsBrand.class, "getId");
        log.info("/reflectUtil getMethod:{}", method);
        //使用反射来创建对象
        PmsBrand pmsBrand = ReflectUtil.newInstance(PmsBrand.class);
        //反射执行对象的方法
        ReflectUtil.invoke(pmsBrand, "setId", 1);
    }

    @Test
    public void NumberUtil() {
        double n1 = 1.234;
        double n2 = 1.234;
        double result;
        //对float、double、BigDecimal做加减乘除操作
        result = NumberUtil.add(n1, n2);
        result = NumberUtil.sub(n1, n2);
        result = NumberUtil.mul(n1, n2);
        result = NumberUtil.div(n1, n2);
        //保留两位小数
        BigDecimal roundNum = NumberUtil.round(n1, 2);
        log.info("/numberUtil round:{}", roundNum);
        String n3 = "1.234";
        //判断是否为数字、整数、浮点数
        NumberUtil.isNumber(n3);
        NumberUtil.isInteger(n3);
        NumberUtil.isDouble(n3);
    }


    @Test
    public void BeanUtil() {
        PmsBrand brand = new PmsBrand();
        brand.setId(1L);
        brand.setName("小米");
        brand.setShowStatus(0);
        //Bean转Map
        Map<String, Object> map = BeanUtil.beanToMap(brand);
        log.info("beanUtil bean to map:{}", map);
        //Map转Bean
        PmsBrand mapBrand = BeanUtil.mapToBean(map, PmsBrand.class, false);
        log.info("beanUtil map to bean:{}", mapBrand);
        //Bean属性拷贝
        PmsBrand copyBrand = new PmsBrand();
        BeanUtil.copyProperties(brand, copyBrand);
        log.info("beanUtil copy properties:{}", copyBrand);
    }

    @Test
    public void IdUtil() {
        //生成的UUID是带-的字符串，类似于：a5c8a5e8-df2b-4706-bea4-08d0939410e3
        String uuid = IdUtil.randomUUID();
        //生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String appKey = IdUtil.fastSimpleUUID();
        String appSecret = IdUtil.fastSimpleUUID();
        System.out.println("appKey = " + appKey);
        System.out.println("appKey = " + appSecret);
    }

    @Test
    public void CollUtil() {
        //数组转换为列表
        String[] array = new String[]{"a", "b", "c", "d", "e"};
        List<String> list = CollUtil.newArrayList(array);
        //join：数组转字符串时添加连接符号
        String joinStr = CollUtil.join(list, ",");
        log.info("collUtil join:{}", joinStr);
        //将以连接符号分隔的字符串再转换为列表
        List<String> splitList = StrUtil.split(joinStr, ',');
        log.info("collUtil split:{}", splitList);
        //创建新的Set、List
        HashSet<Object> newHashSet = CollUtil.newHashSet();
        ArrayList<Object> newList = CollUtil.newArrayList();
        //判断列表是否为空
        CollUtil.isEmpty(list);
    }


    @Test
    public void MapUtil() {
        //将多个键值对加入到Map中
        Map<Object, Object> map = MapUtil.of(new String[][]{
                {"43255key1", "value1"},
                {"key2", "value2"},
                {"key3", "value3"}
        });
        //判断Map是否为空
        MapUtil.isEmpty(map);
        MapUtil.isNotEmpty(map);
    }

    /**
     * 脱敏工具类（以手机号、银行卡号、身份证号、密码信息的脱敏为例）
     */
    @Test
    public void DesensitizedUtil() {
        String phone="13723231234";
        //输出：137****1234
        System.out.println(DesensitizedUtil.mobilePhone(phone));

        String bankCard="6217000130008255666";
        //输出：6217 **** **** *** 5666
        System.out.println(DesensitizedUtil.bankCard(bankCard));

        String idCardNum="411021199901102321";
        //只显示前4位和后2位
        //输出：4110************21
        System.out.println(DesensitizedUtil.idCardNum(idCardNum,4,2));

        String password="www.jd.com_35711";
        //输出：****************
        System.out.println(DesensitizedUtil.password(password));
    }

    @Test
    public void AnnotationUtil() {
        //获取指定类、方法、字段、构造器上的注解列表
        Annotation[] annotationList = AnnotationUtil.getAnnotations(OnlinePreviewController.class, false);
        log.info("annotationUtil annotations:{}", annotationList);
        //获取指定类型注解
        Api api = AnnotationUtil.getAnnotation(OnlinePreviewController.class, Api.class);
        log.info("annotationUtil api value:{}", api.tags());
        //获取指定类型注解的值
        Object annotationValue = AnnotationUtil.getAnnotationValue(OnlinePreviewController.class, RequestMapping.class);
        System.out.println("annotationValue = " + annotationValue);
    }


    @Test
    public void SecureUtil() {
        //MD5加密
        String str = "123456";
        String md5Str = SecureUtil.md5(str);
        log.info("secureUtil md5:{}", md5Str);

        // 生成密钥对（用于非对称加密）
        KeyPair rsa = SecureUtil.generateKeyPair("RSA");
    }

    @Test
    public void Validator() {
        //判断是否为邮箱地址
        boolean result = Validator.isEmail("zjq666@qq.com");
        log.info("Validator isEmail:{}", result);
        //判断是否为手机号码
        result = Validator.isMobile("18666668888");
        log.info("Validator isMobile:{}", result);
        //判断是否为IPV4地址
        result = Validator.isIpv4("192.168.3.101");
        log.info("Validator isIpv4:{}", result);
        //判断是否为汉字
        result = Validator.isChinese("你好");
        log.info("Validator isChinese:{}", result);
        //判断是否为身份证号码（18位中国）
        result = Validator.isCitizenId("123456");
        log.info("Validator isCitizenId:{}", result);
        //判断是否为URL
        result = Validator.isUrl("http://www.baidu.com");
        log.info("Validator isUrl:{}", result);
        //判断是否为生日
        result = Validator.isBirthday("2020-02-01");
        log.info("Validator isBirthday:{}", result);
    }

    @Test
    public void FileUtil() {
        //创建临时文件
        FileUtil.createTempFile("123456", ".txt", new File("D:\\temp"),false);
        //获取文件后缀名
        String fileName = "test.txt";
        String extension = FileUtil.extName(fileName);
        log.info("fileUtil extension:{}", extension);
        //获取文件大小
        File file = new File("D:\\elevoc_dnn_kernel.log");
        long size = FileUtil.size(file);
        log.info("fileUtil size:{}", size);
        //获取文件内容
        String content = FileUtil.readString(file, Charset.forName("UTF-8"));
        log.info("fileUtil content:{}", content);
    }

    @Test
    public void DigestUtil() {
        String password = "123456";
        //计算MD5摘要值，并转为16进制字符串
        String result = DigestUtil.md5Hex(password);
        log.info("DigestUtil md5Hex:{}", result);
        //计算SHA-256摘要值，并转为16进制字符串
        result = DigestUtil.sha256Hex(password);
        log.info("DigestUtil sha256Hex:{}", result);
        //生成Bcrypt加密后的密文，并校验
        String hashPwd = DigestUtil.bcrypt(password);
        boolean check = DigestUtil.bcryptCheck(password,hashPwd);
        log.info("DigestUtil bcryptCheck:{}", check);
    }

    @Test
    public void HttpUtil() {
        //发送GET请求
        String result = HttpUtil.get("https://www.baidu.com");
        log.info("HttpUtil get:{}", result);
        //发送POST请求
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.putOnce("customerTel","17688886666");
        jsonObject.putOnce("remarks","备注1：我是HttpRequest测试请求！");

        HttpResponse response = HttpRequest.post("http://127.0.0.1:9001/api/order/saveRecord")
                //设置请求头(可任意加)
                .header("Content-Type", "application/json")
                // 添加token
                .header("Authorization","eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ7E3NjJ9.9vgMKLpftjcXxmvViSyJDnBcXrO6c3bLlatwD83frAs")
                //请求参数
                .body(jsonObject.toString())
                .execute();
        log.info("请求响应结果：{}",response);
        log.info("响应数据：{}",response.body());
    }

}
