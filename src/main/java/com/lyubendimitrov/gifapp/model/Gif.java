package com.lyubendimitrov.gifapp.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/**
 * @author <a href="mailto:lyuben.dimitrov@comsysto.com">dimitrov</a>
 * @since 08.09.2016
 */

@Getter
@Setter
@AllArgsConstructor
public class Gif {
    public String name;
    private LocalDate dateUploaded;
    private String username;
    private boolean favourite;
}
