package br.com.dio.persistence.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CardEntity {

    private Long id;
    private String title;
    private String description;
    private String priority; // low, medium, high
    private List<String> tags = new ArrayList<>();
    private BoardColumnEntity boardColumn = new BoardColumnEntity();

}