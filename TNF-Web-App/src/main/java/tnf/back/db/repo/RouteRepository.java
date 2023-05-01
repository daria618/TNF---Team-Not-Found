package tnf.back.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tnf.back.db.entityes.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
}