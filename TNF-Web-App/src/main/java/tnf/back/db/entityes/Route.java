package tnf.back.db.entityes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "routename", nullable = false)
    private String name;
    private String shortDescription;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    private double rating;
    @Column(name = "image_name")
    private String imageName;

    @ElementCollection(targetClass = RouteCategory.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "route_categories", joinColumns = @JoinColumn(name = "route_id"))
    @Enumerated(EnumType.STRING)
    private Set<RouteCategory> categories;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn
    private List<MapPoint> points;

    public Route() {
    }

    public Route(
            String name,
            String shortDescription,
            String description,
            User author,
            double rating,
            String imageName,
            List<MapPoint> points
    ) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.author = author;
        this.rating = rating;
        this.imageName = imageName;
        this.points = points;
    }
}