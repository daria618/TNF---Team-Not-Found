package tnf.back.db.repo;

import org.springframework.data.repository.CrudRepository;
import tnf.back.db.entityes.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}