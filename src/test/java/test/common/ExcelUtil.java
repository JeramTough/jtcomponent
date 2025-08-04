package test.common;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * <pre>
 * Created on 2025/8/4 下午4:11
 * by @author WeiBoWen
 * </pre>
 */
public class ExcelUtil {

    /**
     * 将 List<Map<String, Object>> 导出为 Excel 文件
     *
     * @param dataList 数据列表，每个 Map 表示一行
     * @param headers  列名映射：key=Map中的键，value=Excel列标题
     * @param filePath 输出文件路径
     * @throws IOException IO异常
     */
    public static void exportToExcel(List<Map<String, Object>> dataList, Map<String, String> headers, String filePath) throws IOException {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("数据导出");

        // 第一行：表头
        Row headerRow = sheet.createRow(0);
        List<String> fieldKeys = new ArrayList<>(headers.keySet());
        List<String> fieldTitles = new ArrayList<>(headers.values());

        for (int i = 0; i < fieldTitles.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(fieldTitles.get(i));
        }

        // 填充数据行
        int rowNum = 1;
        for (Map<String, Object> data : dataList) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < fieldKeys.size(); i++) {
                String key = fieldKeys.get(i);
                Object value = data.get(key);
                Cell cell = row.createCell(i);
                if (value != null) {
                    cell.setCellValue(value.toString());
                } else {
                    cell.setCellValue("");
                }
            }
        }

        // 自动调整列宽
        for (int i = 0; i < fieldKeys.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // 写出文件
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            workbook.write(out);
        }

        // 关闭工作簿
        workbook.close();

        System.out.println("Excel 文件已导出至: " + filePath);
    }

    // =================== 使用示例 ===================

    /*public static void main(String[] args) throws IOException {
        // 模拟数据
        List<Map<String, Object>> dataList = Arrays.asList(
                new HashMap<String, Object>() {{
                    put("id", 1);
                    put("name", "张三");
                    put("age", 25);
                    put("email", "zhangsan@example.com");
                }},
                new HashMap<String, Object>() {{
                    put("id", 2);
                    put("name", "李四");
                    put("age", 30);
                    put("email", "lisi@example.com");
                }},
                new HashMap<String, Object>() {{
                    put("id", 3);
                    put("name", "王五");
                    put("age", 28);
                    put("email", "wangwu@example.com");
                }}
        );

        // 定义表头：Map键 -> Excel列名
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("id", "编号");
        headers.put("name", "姓名");
        headers.put("age", "年龄");
        headers.put("email", "邮箱");

        // 导出文件路径
        String filePath = "D:/data_export.xlsx"; // Windows 示例
        // String filePath = "/tmp/data_export.xlsx"; // Linux/Mac 示例

        // 执行导出
        exportToExcel(dataList, headers, filePath);
    }*/

}
