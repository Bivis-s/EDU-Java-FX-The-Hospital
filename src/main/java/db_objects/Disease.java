package db_objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Disease extends Entity {
    private String name;
    private int degree;
}
