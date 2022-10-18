package basiclearning.version1;

import basiclearning.constant.CommentConst;
import basiclearning.dto.GoCellDto;
import basiclearning.util.DataTimeUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ReadAndWriteFile {
    public static String writeNameAndKComments(List<List<String>> commentAndNameList) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        XSSFWorkbook workbookTemplateReview = null;
        XSSFSheet xssfSheetIssues = null;
        String fileNameWeeklyReport = "";

        try {
            fileInputStream = new FileInputStream("src/main/java/basiclearning/template/Comment_James_Exercises_Module_2.xlsx");
            workbookTemplateReview = new XSSFWorkbook(fileInputStream);

            Iterator<Sheet> sheets = workbookTemplateReview.iterator();

            xssfSheetIssues = (XSSFSheet) sheets.next();
            int rowStartOfSheetIssues = 3;
            int rowNames = 3;
            int noStudent = 1;
            GoCellDto goCellDto = null;

            List<String> names = commentAndNameList.get(0);
            int sizeNameAndLink = commentAndNameList.size();
            String comment;
            for (int i = 0; i < names.size(); i++) {
                goCellDto = new GoCellDto(xssfSheetIssues, "B" + rowNames);
                goCellDto.setCellValue(names.get(i));

                for (int j = 1; j < sizeNameAndLink; j++) {
                    comment = commentAndNameList.get(j).get(i);
                    goCellDto = new GoCellDto(xssfSheetIssues, "C" + (rowNames + j - 1));
                    goCellDto.setCellValue("[Bài tập] " + Main_readGGSheet.topicDto.getExercises().get(j - 1));

                    if ("".equals(comment)) {
                        goCellDto = new GoCellDto(xssfSheetIssues, "D" + (rowNames + j - 1));
                        goCellDto.setCellValue("OK");
                    } else {
                        goCellDto = new GoCellDto(xssfSheetIssues, "E" + (rowNames + j - 1));
                        goCellDto.setCellValue(comment);

                        goCellDto = new GoCellDto(xssfSheetIssues, "D" + (rowNames + j - 1));
                        goCellDto.setCellValue("NG");
                    }
                }

                rowNames += 8;
            }
//            for(int i = 1; i < commentAndNameList.size(); i++) {
//
//            }

//            for (GoWeeklyReportDto goWeeklyReportDto : goWeeklyReportDtoList) {
//            goCellDto = new GoCellDto(xssfSheetIssues, "E" + rowStartOfSheetIssues);
//            goCellDto.setCellValue("noStudent++");
//
//            goCellDto = new GoCellDto(xssfSheetIssues, "B" + 3);
//            goCellDto.setCellValue("noStudent++");
//
//            goCellDto = new GoCellDto(xssfSheetIssues, "D" + 4);
//            goCellDto.setCellValue("OK");
//
//            goCellDto = new GoCellDto(xssfSheetIssues, "D" + 5);
//            goCellDto.setCellValue("NG");
//
//            goCellDto = new GoCellDto(xssfSheetIssues, "D" + 6);
//            goCellDto.setCellValue("NG -> OK");
//
//            goCellDto = new GoCellDto(xssfSheetIssues, "D" + 7);
//            goCellDto.setCellValue("NG -> NG");

//            goCellDto.setCellValue("" + noStudent++);

//                goCellDto = new GoCellDto(xssfSheetIssues, "C" + rowStartOfSheetIssues);
//                goCellDto.setCellValue(goWeeklyReportDto.getName());
//                goCellDto = new GoCellDto(xssfSheetIssues, "D" + rowStartOfSheetIssues);
//                goCellDto.setCellValue(goWeeklyReportDto.getClassName());
//                goCellDto = new GoCellDto(xssfSheetIssues, "E" + rowStartOfSheetIssues);
//                goCellDto.setCellValue(goWeeklyReportDto.getReportDate());
//                goCellDto = new GoCellDto(xssfSheetIssues, "F" + rowStartOfSheetIssues);
//                goCellDto.setCellValue(goWeeklyReportDto.getStartDate());
//                goCellDto = new GoCellDto(xssfSheetIssues, "G" + rowStartOfSheetIssues);
//                goCellDto.setCellValue(goWeeklyReportDto.getEndDate());
//                goCellDto = new GoCellDto(xssfSheetIssues, "H" + rowStartOfSheetIssues);
//                goCellDto.setCellValue(getAllIssues(goWeeklyReportDto.getGoIssueWeeklyDtoList()));
//                goCellDto = new GoCellDto(xssfSheetIssues, "I" + rowStartOfSheetIssues);
//                goCellDto.setCellValue(goWeeklyReportDto.getKeywords());
//
//                rowStartOfSheetIssues++;
//            }

            fileOutputStream = new FileOutputStream(new File("src/main/java/basiclearning/data/Comment_James_Exercises_Module_2.xlsx"));

            // Write from template review to result review file
            workbookTemplateReview.write(fileOutputStream);

            fileInputStream.close();
            workbookTemplateReview.close();
            fileOutputStream.close();

        } catch (IOException e) {
            try {
                fileInputStream.close();
                workbookTemplateReview.close();
                fileOutputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return fileNameWeeklyReport;
    }

    public static String writeMoney(List<List<String>> commentAndNameList) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        XSSFWorkbook workbookTemplateReview = null;
        XSSFSheet xssfSheetIssues = null;
        String fileNameWeeklyReport = "";

        try {
            fileInputStream = new FileInputStream("src/main/java/basiclearning/template/Thuong_Phat.xlsx");
            workbookTemplateReview = new XSSFWorkbook(fileInputStream);

            Iterator<Sheet> sheets = workbookTemplateReview.iterator();

            xssfSheetIssues = (XSSFSheet) sheets.next();
            int numberRow = 2;
            GoCellDto goCellDto = null;

            List<String> names = commentAndNameList.get(0);
            int sizeNameAndLink = commentAndNameList.size();
            String comment;
            for (int i = 0; i < names.size(); i++) {
//                goCellDto = new GoCellDto(xssfSheetIssues, "B" + numberRow);
//                goCellDto.setCellValue(names.get(i));

                for (int j = 1; j < sizeNameAndLink; j++) {
                    comment = commentAndNameList.get(j).get(i);
//                    goCellDto = new GoCellDto(xssfSheetIssues, "C" + (rowNames + j - 1));
//                    goCellDto.setCellValue("[Bài tập] " + Main_readGGSheet.topicDto.getExercises().get(j - 1));

                    if (CommentConst.CHUA_LAM_BAI_TAP.equals(comment)) {
                        goCellDto = new GoCellDto(xssfSheetIssues, "B" + numberRow);
                        goCellDto.setCellValue(names.get(i));

                        goCellDto = new GoCellDto(xssfSheetIssues, "C" + numberRow);
                        goCellDto.setCellValue("Phạt");

                        goCellDto = new GoCellDto(xssfSheetIssues, "D" + numberRow);
                        goCellDto.setCellValue(String.format("- %s - %s - %s", Main_readGGSheet.topicDto.getTopic()
                                , Main_readGGSheet.topicDto.getExercises().get(j - 1)
                                , CommentConst.CHUA_LAM_BAI_TAP
                        ));

                        goCellDto = new GoCellDto(xssfSheetIssues, "E" + numberRow);
                        goCellDto.setCellValue("5000");

                        goCellDto = new GoCellDto(xssfSheetIssues, "F" + numberRow);
                        goCellDto.setCellValue("Chưa nộp");

                        goCellDto = new GoCellDto(xssfSheetIssues, "G" + numberRow);
                        goCellDto.setCellValue(DataTimeUtil.getDateCurrentDDMMYYYY());


                        numberRow++;
                    } else if (!Main_readGGSheet.newReview && !"".equals(comment)) {

                        numberRow++;
                    }


                }
            }

            fileOutputStream = new FileOutputStream(new File("src/main/java/basiclearning/data/Thuong_Phat.xlsx"));

            // Write from template review to result review file
            workbookTemplateReview.write(fileOutputStream);

            fileInputStream.close();
            workbookTemplateReview.close();
            fileOutputStream.close();

        } catch (IOException e) {
            try {
                fileInputStream.close();
                workbookTemplateReview.close();
                fileOutputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return fileNameWeeklyReport;
    }

    public static void main(String[] args) {
//        writeNameAndKComments();
    }
}
