package tnf.back.db.entityes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;
    @OneToOne
    @JoinColumn(name = "way_id")
    private Way way;
    private int rating;

    /**
     * Default constructor
     */
    public Comment() {
    }

    /**
     * Constructor with full initialization
     * @param author - comment creator
     * @param way - commented way
     * @param rating - initial rating value
     */
    public Comment(User author, Way way, int rating) {
        this.author = author;
        this.way = way;
        this.rating = rating;
    }

    /**
     * Constructor with partial initialization. set rating value = 0
     * @param author
     * @param way
     */
    public Comment(User author, Way way) {
        this.author = author;
        this.way = way;
        rating = 0;
    }
}