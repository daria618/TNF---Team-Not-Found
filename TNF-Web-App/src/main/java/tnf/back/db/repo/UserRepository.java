package tnf.back.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tnf.back.db.entityes.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}