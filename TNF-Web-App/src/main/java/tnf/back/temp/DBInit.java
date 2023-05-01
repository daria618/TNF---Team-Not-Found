package tnf.back.temp;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tnf.back.db.entityes.Role;
import tnf.back.db.entityes.User;
import tnf.back.db.repo.UserRepository;

import java.util.Collection;
import java.util.Collections;

@Component
public class DBInit implements CommandLineRunner {
    private final UserRepository userRepository;

    public DBInit(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(createTestUser());
        userRepository.save(createAdmin());
    }

    private User createTestUser() {
        return new User(
                "testUser",
                "testUser",
                "testUser@email.com",
                true,
                Collections.singleton(Role.USER)
        );
    }

    private User createAdmin() {
        return new User(
                "admin",
                "admin",
                "admin@email.com",
                true,
                Collections.singleton(Role.ADMIN)
        );
    }
}
