package com.ocbc.ms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_comment")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_code", length = 100)
    private String commentCode;

    @Column(name = "page_name", length = 100)
    private String pageName;

    /**
     * text column to store long description
     */
    @Column(name = "description")
    private String description;


}
