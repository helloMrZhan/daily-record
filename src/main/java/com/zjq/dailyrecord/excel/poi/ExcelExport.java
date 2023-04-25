package com.zjq.dailyrecord.excel.poi;

import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExport {

    public static void main(String[] args) throws Exception {

        // 准备数据
        String[][] products = {
            {"电子产品", "手机", "iPhone 12", "$999"},
            {"电子产品", "手机", "Galaxy S21", "$899"},
            {"电子产品", "电视", "OLED TV", "$2999"},
            {"服装", "衬衫", "白色衬衫", "$29"},
            {"服装", "裙子", "花裙子", "$49"}
        };

        // 创建新的工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建数据来源工作表
        Sheet sourceSheet = workbook.createSheet("数据来源");

        // 将大类别和小类别数据添加到数据来源工作表中
        String[] categories = { "电子产品", "服装" };
        String[][] subcategories = {
            { "手机", "电视" },
            { "衬衫", "裙子" }
        };

        for (int i = 0; i < categories.length; i++) {
            Row row = sourceSheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(categories[i]);
            for (int j = 0; j < subcategories[i].length; j++) {
                cell = row.createCell(j+1);
                cell.setCellValue(subcategories[i][j]);
            }
            // 将每一列设置为列表
            String reference = String.format("A%d:B%d", i+1, i+1);
            Name name = workbook.createName();
            name.setNameName(categories[i]);
            name.setRefersToFormula(reference);
            DataValidationHelper helper = sourceSheet.getDataValidationHelper();
            DataValidationConstraint constraint = helper.createFormulaListConstraint(categories[i]);
            CellRangeAddressList range = new CellRangeAddressList(0, 100, i, i);
            DataValidation validation = helper.createValidation(constraint, range);
            sourceSheet.addValidationData(validation);
        }

        // 创建商品清单工作表
        Sheet productSheet = workbook.createSheet("商品清单");

        // 设置大类别下拉菜单
        CellRangeAddressList categoryList = new CellRangeAddressList(1, 100, 0, 0);
        DataValidationHelper categoryHelper = productSheet.getDataValidationHelper();
        DataValidationConstraint categoryconstraint = categoryHelper.createFormulaListConstraint("电子产品, 服装");
        DataValidation categoryValidation = categoryHelper.createValidation(categoryconstraint, categoryList);
        productSheet.addValidationData(categoryValidation);

        // 设置小类别下拉菜单
        for (int i = 1; i <= products.length; i++) {
            CellRangeAddressList subcategoryList = new CellRangeAddressList(i, i, 1, 1);
            DataValidationHelper subcategoryHelper = productSheet.getDataValidationHelper();
            String categoryRef = String.format("$A$%d", i+1);
            String subcategoryFormula = String.format("INDIRECT(%s)", categoryRef);
            DataValidationConstraint subcategoryConstraint = subcategoryHelper.createFormulaListConstraint(subcategoryFormula);
            DataValidation subcategoryValidation = subcategoryHelper.createValidation(subcategoryConstraint, subcategoryList);
            productSheet.addValidationData(subcategoryValidation);
        }

        // 将商品数据添加到商品清单工作表中
        for (int i = 0; i < products.length; i++) {
            Row row = productSheet.createRow(i+1);
            for (int j = 0; j < products[i].length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(products[i][j]);
            }
        }

        // 将工作簿写入文件
        String filename = "商品清单.xlsx";
        FileOutputStream outputStream = new FileOutputStream(filename);
        workbook.write(outputStream);
        workbook.close();
        System.out.println("Excel文件 " + filename + " 导出成功！");
    }
}
