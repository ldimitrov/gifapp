package com.lyubendimitrov.gifapp.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Gif {
    public String name;
    private int categoryId;
    private LocalDate dateUploaded;
    private String username;
    private boolean favourite;
}
