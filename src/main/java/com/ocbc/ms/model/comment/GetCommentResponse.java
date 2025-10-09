package com.ocbc.ms.dto.comment;

import com.ocbc.ms.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class GetCommentResponse {

    private List<Comment> commentList;
}
