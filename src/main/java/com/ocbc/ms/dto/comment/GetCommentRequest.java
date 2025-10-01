package com.ocbc.ms.dto.comment;

import lombok.Data;

import java.util.List;

@Data
public class GetCommentRequest {

    private String pageName;
    private List<String> commentCode;
}
