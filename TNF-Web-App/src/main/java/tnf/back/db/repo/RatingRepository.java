package tnf.back.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tnf.back.db.entityes.RatingMark;
import tnf.back.db.entityes.Route;
import tnf.back.db.entityes.User;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingMark, Long> {
    List<RatingMark> findByUser(User user);

    List<RatingMark> findByRoute(Route route);

    RatingMark findByUserAndRoute(User user, Route route);
}
