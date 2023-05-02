package tnf.back.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tnf.back.db.entityes.Comment;
import tnf.back.db.entityes.Route;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByRoute(Route route);
    List<Comment> findAllByRoute(Route route);
}