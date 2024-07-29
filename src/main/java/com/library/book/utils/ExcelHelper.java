package com.library.book.utils;

import com.library.book.entity.BookEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static boolean hasExcelFormat(MultipartFile file) {
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<BookEntity> excelToBookList(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<BookEntity> list = new ArrayList<>();

            int rowNumber = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                BookEntity bookEntity = new BookEntity();

                int cellIdx = 0;

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            bookEntity.setTitle(currentCell.getStringCellValue());
                            break;
                        case 1:
                            bookEntity.setAuthor(currentCell.getStringCellValue());
                            break;
                        case 2:
                            bookEntity.setISBN(currentCell.getStringCellValue());
                            break;
                        case 3:
                            bookEntity.setPrice(currentCell.getNumericCellValue());
                            break;
                        case 4:
                            bookEntity.setStock((int)currentCell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                list.add(bookEntity);
            }

            workbook.close();

            return list;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse excel file:" + e.getMessage());
        }
    }
}
