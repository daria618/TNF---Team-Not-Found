package tnf.back.db.entityes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RatingMark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    private Double value;

    public RatingMark() {
    }

    public RatingMark(User user, Route route, Double value) {
        this.user = user;
        this.route = route;
        this.value = value;
    }
}