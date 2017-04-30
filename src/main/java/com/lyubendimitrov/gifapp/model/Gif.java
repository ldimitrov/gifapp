package com.lyubendimitrov.gifapp.model;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class Gif {
    private Long id;
    private byte[] bytes;
    private String description;
    private Category category;
    private LocalDateTime dateUploaded = LocalDateTime.now();
    private String username = "You";
    private boolean favorite;
    private String hash;

    public String getTimeSinceUploaded() {
        String unit = "";
        LocalDateTime now = LocalDateTime.now();
        long diff;
        if((diff = ChronoUnit.SECONDS.between(dateUploaded,now)) < 60){
            unit = "secs";
        } else if ((diff = ChronoUnit.MINUTES.between(dateUploaded,now)) < 60) {
            unit = "mins";
        } else if ((diff = ChronoUnit.HOURS.between(dateUploaded,now)) < 24) {
            unit = "hours";
        } else if ((diff = ChronoUnit.DAYS.between(dateUploaded,now)) < 30) {
            unit = "days";
        } else if ((diff = ChronoUnit.MONTHS.between(dateUploaded,now)) < 12) {
            unit = "months";
        } else{
            diff = ChronoUnit.YEARS.between(dateUploaded,now);
        }
        return String.format("%d %s",diff,unit);
    }
}
