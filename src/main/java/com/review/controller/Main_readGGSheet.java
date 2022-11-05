package com.review.controller;

import com.review.constant.CommentConst;
import com.review.dto.TopicDto;
import com.review.service.GenerateCommentAndPenaltyFilesService;
import com.review.service.ReadSheetsService;
import com.review.util.LinkUtil;
import org.apache.http.HttpStatus;

import java.util.*;
import java.util.stream.Collectors;

public class Main_readGGSheet {
    private static List<TopicDto> topicDtoList;

    public static TopicDto topicDto;
    public static boolean newReview;

    static {
        topicDtoList = new ArrayList<>();

        topicDtoList.add(TopicDto.builder()
                .topicNo(1)
                .topic("1. Introduction to Java")
                .exercises(Arrays.asList("Hiển thị lời chào",
                        "Ứng dụng đọc số thành chữ",
                        "Ứng dụng chuyển đổi tiền tệ"))
                .startRow(4)
                .endRow(6)
                .build());

        topicDtoList.add(TopicDto.builder()
                .topicNo(2)
                .topic("2. Loop & Array")
                .exercises(Arrays.asList("Hiển thị các loại hình",
                        "Hiển thị 20 số nguyên tố đầu tiên",
                        "Hiển thị các số nguyên tố nhỏ hơn 100",
                        "Xoá phần tử khỏi mảng",
                        "Thêm phần tử vào mảng",
                        "Gộp mảng"))
                .startRow(7)
                .endRow(12)
                .build());

        topicDtoList.add(TopicDto.builder()
                .topicNo(3)
                .topic("3. Method")
                .exercises(Arrays.asList("Tìm phần tử lớn nhất trong mảng hai chiều",
                        "Tìm giá trị nhỏ nhất trong mảng",
                        "Tính tổng các số ở một cột xác định",
                        "Mảng hai chiều - tính tổng các số ở đường chéo chính",
                        "Đếm số lần xuất hiện của ký tự trong chuỗi"))
                .startRow(13)
                .endRow(17)
                .build());

        topicDtoList.add(TopicDto.builder()
                .topicNo(4)
                .topic("4. Lớp và đối tượng trong Java")
                .exercises(Arrays.asList("Xây dựng lớp QuadraticEquation",
                        "Xây dựng lớp Fan"))
                .startRow(18)
                .endRow(19)
                .build());

        topicDtoList.add(TopicDto.builder()
                .topicNo(5)
                .topic("5. Access modifier")
                .exercises(Arrays.asList("Access modifier",
                        "Xây dựng lớp chỉ ghi trong Java"))
                .startRow(20)
                .endRow(21)
                .build());

        topicDtoList.add(TopicDto.builder()
                .topicNo(7)
                .topic("7. Abstract Class & Interface")
                .exercises(Arrays.asList("Triển khai interface 'Resizeable'",
                        "Triển khai interface 'Colorable'"))
                .startRow(24)
                .endRow(25)
                .build());

        topicDtoList.add(TopicDto.builder()
                .topicNo(6)
                .topic("6. Kế thừa")
                .exercises(Arrays.asList("Lớp Circle và lớp Cylinder",
                        "Lớp Point2D và lớp Point3D"))
                .startRow(22)
                .endRow(23)
                .build());

        topicDtoList.add(TopicDto.builder()
                .topicNo(7)
                .topic("7. Abstract Class & Interface")
                .exercises(Arrays.asList("Triển khai interface 'Resizeable'",
                        "triển khai interface 'Colorable' "))
                .startRow(24)
                .endRow(25)
                .build());





        topicDtoList.add(TopicDto.builder()
                .topicNo(9)
                .topic("9. Tổng quan về java web")
                .exercises(Arrays.asList("Ứng dụng Product Discount Calculator"))
                .startRow(17)
                .endRow(17)
                .build());

        topicDtoList.add(TopicDto.builder()
                .topicNo(10)
                .topic("10.Jsp và JSTL")
//                .exercises(Arrays.asList("Hiển thị danh sách khách hàng"))
                .exercises(Arrays.asList("Ứng dụng Calculator"))
                .startRow(19)
                .endRow(19)
                .build());
    }

    public static void main(String[] args) throws Exception {
        //============== Các thông số bắt buộc phải config ==============
        String sheetName = "C0722G1";
        String endColumn = "AG";
        int topicNo = 10;
        newReview = true;
        final String spreadsheetId = "1ebzVMscU6MjmUIWphKeJ4_6fM5qtQVkxdW1WBAkxiFc";
        //============== Các thông số bắt buộc phải config ==============

        StringBuilder totalMessage = new StringBuilder();

        List<List<String>> commentAndNameList = new ArrayList<>();
        // Add name
        String range = String.format("%s!F3:%s3", sheetName, endColumn);
        List<List<Object>> linkGitFromGSheetLists = ReadSheetsService.getLinkGitFromGSheet(spreadsheetId, range);
        commentAndNameList.add(linkGitFromGSheetLists.get(0).stream().map(Objects::toString).collect(Collectors.toList()));

        List<Object> names = linkGitFromGSheetLists.get(0);

        //Add links
        topicDto = topicDtoList.stream().filter(it -> it.getTopicNo() == topicNo).findFirst().orElseThrow(NoSuchElementException::new);
        range = String.format("%s!F%s:%s%s", sheetName, topicDto.getStartRow(), endColumn, topicDto.getEndRow());
        linkGitFromGSheetLists = ReadSheetsService.getLinkGitFromGSheet(spreadsheetId, range);

        List<String> commentList;
        StringBuilder commentOneLink;
        for (int i = 0; i < linkGitFromGSheetLists.size(); i++) {
            List<Object> links = linkGitFromGSheetLists.get(i);
            commentList = new ArrayList<>();
            for (int j = 0; j < names.size(); j++) {
                commentOneLink = new StringBuilder();

                totalMessage.append(String.format("\n\n\n=========== %s ===========\n", names.get(j)));
                System.out.printf("\n\n\n=========== %d. %s ===========\n", j + 1, names.get(j));
                if (j >= links.size() || "".equals(links.get(j))) {
                    commentOneLink.append(CommentConst.CHUA_LAM_BAI_TAP);
                } else if (LinkUtil.checkLinkStatus(links.get(j).toString()) == HttpStatus.SC_NOT_FOUND) {
                    commentOneLink.append(CommentConst.LINK_404);
                } else {
                    if (links.get(j).toString().contains("/blob/")) { //Là file
                        GitHubAPI_GetTree_Example.commentFileType(links.get(j).toString(), commentOneLink);
                    } else {
                        commentOneLink.append(GitHubAPI_GetTree_Example.commentAllFile(links.get(j).toString()));
                    }
                }
                commentList.add(commentOneLink.toString());
            }
            commentAndNameList.add(commentList);
        }


        GenerateCommentAndPenaltyFilesService.writeNameAndKComments(commentAndNameList);
        GenerateCommentAndPenaltyFilesService.writeMoney(commentAndNameList);
    }
}
