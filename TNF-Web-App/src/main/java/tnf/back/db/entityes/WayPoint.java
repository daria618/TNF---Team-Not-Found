package tnf.back.db.entityes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class WayPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "way_id")
    private Way way;
    private double longitude;
    private double latitude;

    public WayPoint() {
    }

    public WayPoint(Way way, double longitude, double latitude) {
        this.way = way;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
