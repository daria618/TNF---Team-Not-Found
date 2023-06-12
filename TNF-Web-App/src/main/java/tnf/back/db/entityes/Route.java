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
    @Column(name = "image_name")
    private String imageName;

    @ElementCollection(targetClass = RouteCategory.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "route_categories", joinColumns = @JoinColumn(name = "route_id"))
    @Enumerated(EnumType.STRING)
    private Set<RouteCategory> categories;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn
    private List<MapPoint> points;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "addImages_imgs", joinColumns = @JoinColumn(name = "route_id"))
    private Set<String> addImages;

    public Route() {
    }

    public Route(
            String name,
            String shortDescription,
            String description,
            User author,
            String imageName,
            Set<String> addImages,
            List<MapPoint> points,
            Set<RouteCategory> categories
    ) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.author = author;
        this.imageName = imageName;
        this.addImages = addImages;
        this.points = points;
        this.categories = categories;
    }
}