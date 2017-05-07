package com.lyubendimitrov.gifapp.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.time.temporal.ChronoUnit.YEARS;

@Data
@Entity
public class Gif {
    @Transient
    MultipartFile file;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] bytes;
    private String description;
    @ManyToOne
    private Category category;
    private LocalDateTime dateUploaded = LocalDateTime.now();
    private String username = "You";
    private boolean favorite;
    private String hash;

    public String getTimeSinceUploaded() {
        String unit = "";
        LocalDateTime now = LocalDateTime.now();
        long diff;
        if ((diff = SECONDS.between(dateUploaded, now)) < 60) {
            unit = "secs";
        } else if ((diff = MINUTES.between(dateUploaded, now)) < 60) {
            unit = "mins";
        } else if ((diff = HOURS.between(dateUploaded, now)) < 24) {
            unit = "hours";
        } else if ((diff = DAYS.between(dateUploaded, now)) < 30) {
            unit = "days";
        } else if ((diff = MONTHS.between(dateUploaded, now)) < 12) {
            unit = "months";
        } else {
            diff = YEARS.between(dateUploaded, now);
        }
        return String.format("%d %s", diff, unit);
    }
}
