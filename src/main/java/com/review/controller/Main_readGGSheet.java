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
    }

    public static void main(String[] args) throws Exception {
        //============== Các thông số bắt buộc phải config ==============
        String sheetName = "A0722I1";
        String endColumn = "AD";
        int topicNo = 3;
        newReview = true;
        final String spreadsheetId = "1efV2twW7CuPjwEy8JGjY899-khFwfqkNha-GMx_5U5k";
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
