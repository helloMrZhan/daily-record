package com.zjq.dailyrecord.random;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author 共饮一杯无
 * @date 2024/7/23 16:01
 * @description:随机选择器
 */
public class RandomStringSelector {

    /**证书编号*/
    public static final List<String> certNos = Arrays.asList(
            "135792468024680246",
            "147258369052854901",
            "159357246059357246",
            "172839506172839506",
            "180451672839506172",
            "192837456098765432",
            "113579246802468024",
            "125829174365079321",
            "145368752345678901",
            "102394838475873456"
    );
    // 申请人（账号）
    public static final List<String> applicantAccounts = Arrays.asList(
            "13800005678", "15900002345", "18600006789", "13700001234", "15000005678",
            "18000009012", "13900003456", "15500007890", "18500002345", "13000067803"
    );

    // 申请人名称
    public static final List<String> applicantNames = Arrays.asList(
            "张明轩", "王思琪", "李浩宇", "赵欣怡", "刘子涵", "陈雨萱", "郑伟杰", "黄雅芳", "周文博", "吴晓东"
    );

    // 身份标识
    public static final List<String> identityIds = Arrays.asList(
            "1101*************1234", "3203*************5678", "5101*************9012", "4403*************3456", "2202*************7890",
            "6101*************2345", "3502*************6789", "4501*************0123", "5002*************4567", "1301*************890"
    );

    // 企业信用代码
    public static final List<String> creditCodes = Arrays.asList(
            "91330100CU3LN3X72H", "913301008U5MBCEY71", "91330100T6XJLGYT2Q", "91330100T6XJLGYT2Q", "913301007NFBA43X7E",
            "91110000RJHN2DGH5T", "911100007HG45QCP5X", "911100009DMGUGE126", "91110000P47RM6EL1W", "91110000H2WK85LRX9"
    );

    // 申请时间
    public static final List<String> applicationDates = Arrays.asList(
            "2024-07-22 00:00:00", "2024-07-22 01:15:30", "2024-07-22 02:30:45", "2024-07-22 03:45:12", "2024-07-22 05:00:00",
            "2024-07-22 06:15:27", "2024-07-22 07:30:55", "2024-07-22 08:45:08", "2024-07-22 10:00:33", "2024-07-22 11:59:59"
    );

    // 文件名称
    public static final List<String> fileNames = Arrays.asList(
            "data_visualization_trends_2024_analysis_report.pdf", "user_interface_prototype_v2.3_design_overview.jpg", "project_milestone_documentation_Q3_2024.docx",
            "animation_sequence_demo_character_intro_mp4", "code_refactoring_best_practices_guide_v1.0.pdf", "software_architecture_diagram_application_structure.jpg",
            "video_editing_tutorial_beginner_to_advanced_mp4", "database_schema_design_document_v2.2.docx", "UI_UX_case_study_ecommerce_platform_improvements.pdf",
            "project_requirements_specification_document_v3.2_detailed_functional_analysis_for_client_approval.pdf"
    );

    // 文件大小
    public static final List<String> fileSizes = Arrays.asList(
            "500 MB", "12.5 KB", "2.34 MB", "987 KB", "45.67 MB", "1 MB", "678.9 KB", "1.02 MB", "56 KB"
    );

    // 区块链唯一标识
    public static final List<String> blockchainIdentifiers = Arrays.asList(
            "3a98f06f127419f1a4015637521591874e899266613f3e7b7f1249f7762378e4",
            "5f4dcc3b5aa765d61d8327deb882cf994f30283082bd8065132e044f04d4991b",
            "a0e4403f497677241542d3511d88f397e87c1414d1a02906f392bd1d1453dbec",
            "9e872d39e3212777c070f10f4a2379d3ee5e46b0df209c029a7d474e8011465f",
            "b7e020b92709981a98542113a2fc6a2a240d8bb3c1c2c63f356244a6b4b4e8ad",
            "71c480df93f469decbc3c1d2e4f29f148c7a0f46da0cd3293fddbd27d165667f",
            "f05a5b6a56a3e5e4b39f0f85c5c114afe2b371f50c3a37e83457084f277037d8",
            "1f66ab40a6750a74e4a807930bc9eef0c3a202422f3a8d7a617e3ab6832470d7",
            "d296eefe2f7f6f0a9ba1e3f0bc0c520f7445da4099cac652d4e63e73b3d94b3c",
            "248d6a61d20638b8e5c026930c3e600f13c0d85c8f85ac8c00c1808f8efc82a0"
    );


    /**
     * 从给定的字符串列表中随机选择一个元素。
     *
     * @param list 要从中选择元素的列表
     * @return 随机选择的元素
     */
    public static String selectRandomStringElement(List<String> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    /**
     * 从给定的列表中随机选择一个元素(通用)
     *
     * @param list 要从中选择元素的列表
     * @return 随机选择的元素
     */
    public static <T> T selectRandomElement(List<T> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    /**
     * 从给定的列表中随机选择一个元素（Collections实现）
     *
     * @param <T> 列表元素的类型
     * @param list 要从中选择元素的列表
     * @return 随机选择的元素
     */
    public static <T> T collectionsSelectRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty.");
        }

        Collections.shuffle(list);
        // 返回第一个元素，由于列表已经被打乱，所以它是随机的
        return list.get(0);
    }

    /**
     * 从给定的列表中随机选择一个元素。(不推荐)
     *
     * @param <T> 列表元素的类型
     * @param list 要从中选择元素的列表
     * @return 随机选择的元素，如果列表为空则返回null
     */
    public static <T> T streamSelectRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        // 使用Stream API来获取随机索引的元素
        return  list.stream()
                .skip(new java.util.Random().nextInt(list.size()))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        System.out.println(selectRandomStringElement(certNos));
        System.out.println(selectRandomElement(applicantAccounts));
        System.out.println(collectionsSelectRandomElement(applicantNames));
        System.out.println(collectionsSelectRandomElement(identityIds));
        System.out.println(streamSelectRandomElement(creditCodes));
        System.out.println(collectionsSelectRandomElement(applicationDates));
        System.out.println(collectionsSelectRandomElement(fileNames));
        System.out.println(collectionsSelectRandomElement(fileSizes));
        System.out.println(collectionsSelectRandomElement(blockchainIdentifiers));
    }


}
