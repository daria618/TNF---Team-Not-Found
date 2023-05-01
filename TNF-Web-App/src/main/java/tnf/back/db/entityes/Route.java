package tnf.back.db.entityes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    public Route() {
    }

    public Route(String name, String shortDescription, String description, User author, double rating, String imageName) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.author = author;
        this.rating = rating;
        this.imageName = imageName;
    }
}