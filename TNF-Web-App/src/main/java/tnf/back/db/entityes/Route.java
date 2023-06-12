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
    @Column(length = 1024)
    private String shortDescription;
    @Column(length = 8192)
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id: ").append(id).append("\n");
        builder.append("name: ").append(name).append("\n");
        builder.append("shortDescription: ").append(shortDescription).append("\n");
        builder.append("description: ").append(description).append("\n");

        builder.append("author: ").append(author.toString()).append("\n");
        builder.append("imageName: ").append(imageName).append("\n");
        if (categories != null) builder.append("categories: ").append(categories).append("\n");
        builder.append("addImages: ").append(addImages).append("\n");

        builder.append("POINTS:\n");
        for (var point : points){
            builder.append("\t");
            builder.append(point.getLatitude()).append(" | ");
            builder.append(point.getLongitude()).append(" | ");
            builder.append(point.getTextRepresent()).append(" | ");
            builder.append(point.getDescription()).append("\n");
        }

        return builder.toString();
    }
}