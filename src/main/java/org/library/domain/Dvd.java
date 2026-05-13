package org.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Dvd extends Item {
    private String director;
    private int duration;

    public Dvd(String title, Status status, String director, int duration) {
        super(title, status);
        this.director = director;
        this.duration = duration;
    }
}
