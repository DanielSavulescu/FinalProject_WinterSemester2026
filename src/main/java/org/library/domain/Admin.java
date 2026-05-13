package org.library.domain;

import lombok.*;

@ToString(callSuper = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
    public Admin(String name) {
        super(name);
    }
}
