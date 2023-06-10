package tnf.back.db.entityes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tnf.back.logic.Checker;

@Getter
@Setter
@Entity
@Table(name = "map_point")
public class MapPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String latitude;
    private String longitude;
    @Column(name = "text_represent")
    private String textRepresent;
    private String description;

    public MapPoint() {
    }

    public MapPoint(String latitude, String longitude, String textRepresent) {
        this(latitude, longitude, textRepresent, null);
    }

    public MapPoint(String latitude, String longitude, String textRepresent, String description) {
        if (Checker.isEmptyStrings(latitude, longitude, textRepresent))
            throw new IllegalArgumentException("Coordinates and name is empty");
        this.latitude = latitude;
        this.longitude = longitude;
        this.textRepresent = textRepresent;
        this.description = description;
    }

    public String getStr() {
        if (textRepresent != null) return textRepresent + " | " + description;
        return "[" + latitude + ", " + longitude + "] | " + description;
    }
}