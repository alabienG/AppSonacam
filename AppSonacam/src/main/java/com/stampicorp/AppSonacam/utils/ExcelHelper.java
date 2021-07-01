package com.stampicorp.AppSonacam.utils;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.ActiviteRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    //    static String[] HEADERs = { "Id", "Title", "Description", "Published" };
    static String SHEET = "Contribuable";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Contribuable> excelToTutorials(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet("");
            Iterator<Sheet> rows = workbook.sheetIterator();

            List<Contribuable> tutorials = new ArrayList<Contribuable>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Sheet sheets = rows.next();
                Iterator<Row> rowIterator = sheets.rowIterator();

                while (rowIterator.hasNext()) {
                    Row currentRow = rowIterator.next();
                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }
                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    Contribuable contribuable = new Contribuable();

                    int cellIdx = 0;
                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();

                        switch (cellIdx) {
                            case 1:
                                String[] noms = (String[]) currentCell.getStringCellValue().split(" ");
                                contribuable.setNom(noms[0]);
                                contribuable.setPrenom(noms[1]);
                                break;

                            case 4:
                                contribuable.setTelephone(currentCell.getNumericCellValue() + "");
                                break;
                            case 3:
                                contribuable.setRaisonSociale((String) currentCell.getStringCellValue());
                                break;
                            case 6:
                                String activiteName = (String) currentCell.getStringCellValue();
                                contribuable.setFakeActivite(activiteName);

//                            case 10:
//                                contribuable.setMontant(currentCell.getNumericCellValue());
//                                break;
                            case 12:
                                String zoneName = (String) currentCell.getStringCellValue();
                                contribuable.setFakeZone(zoneName);
                            case 13:
                                contribuable.setLieuDit((String) currentCell.getStringCellValue());
                                break;

                            default:
                                break;
                        }

                        cellIdx++;
                    }
                    tutorials.add(contribuable);
                }
            }

            workbook.close();

            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
