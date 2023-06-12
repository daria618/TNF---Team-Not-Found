package tnf.back.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tnf.back.db.entityes.Route;
import tnf.back.db.entityes.User;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findByName(String name);
    List<Route> findByAuthor(User author);
}