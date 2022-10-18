package com.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TopicDto {
    private int topicNo;
    private String topic;
    private List<String> exercises;
    private int startRow;
    private int endRow;
}
