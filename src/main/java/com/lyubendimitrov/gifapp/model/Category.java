package com.lyubendimitrov.gifapp.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Category {
    private Long id;
    private String name;
    private String colorCode;
    private List<Gif> gifs = new ArrayList<>();
}
