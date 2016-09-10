package com.lyubendimitrov.gifapp.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * @author <a href="mailto:lyuben.dimitrov@comsysto.com">dimitrov</a>
 * @since 08.09.2016
 */
@Getter
@Setter
@EqualsAndHashCode
public class Gif {
    public String name;
    private Date dateUploaded;
    private String username;
    private boolean favourite;

    public Gif(String name, Date dateUploaded, String username, boolean favourite) {
        this.name = name;
        this.dateUploaded = dateUploaded;
        this.username = username;
        this.favourite = favourite;
    }
}
