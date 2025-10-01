package com.ocbc.ms.dto.comment;

import lombok.Data;

@Data
public class GetCommentRequest {

    private String pageName;
    private String commentCode;
}
