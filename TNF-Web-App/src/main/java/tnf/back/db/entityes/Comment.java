package tnf.back.db.entityes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String text;
    private Long rating;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    public Comment() {
    }

    public Comment(String text, Long rating, User author, Route route) {
        this.text = text;
        this.rating = rating;
        this.author = author;
        this.route = route;
    }
}