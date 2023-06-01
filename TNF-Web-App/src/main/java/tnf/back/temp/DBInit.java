package tnf.back.temp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tnf.back.db.entityes.*;
import tnf.back.db.repo.CommentRepository;
import tnf.back.db.repo.RouteRepository;
import tnf.back.db.repo.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

@Component
public class DBInit implements CommandLineRunner {
    private static final Random random = new Random(System.currentTimeMillis());
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final CommentRepository commentRepository;

    public DBInit(UserRepository userRepository, RouteRepository routeRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) {
        ArrayList<User> users = new ArrayList<>() {{
            add(createTestUser());
            add(createAnotherTestUser());
            add(createAdmin());
            add(createFastLogInUser());
        }};

        ArrayList<Route> routes = new ArrayList<>() {{
            add(createTestRoute_1(users.get(1)));
            add(createTestRoute_2(users.get(2)));
            add(createTestRoute_3(users.get(1)));
            add(createTestRoute_Mixed(users.get(2)));
        }};

        userRepository.saveAll(users);
        routeRepository.saveAll(routes);

        for (var u : users)
            for (var r : routes)
                commentRepository.save(new Comment(randString(random.nextInt(120) + 10), 0L, u, r));
    }

    private User createTestUser() {
        return new User(
                "testUser",
                "testUser",
                "testUser@email.com",
                true,
                Collections.singleton(Role.USER),
                null
        );
    }

    private User createAnotherTestUser() {
        return new User(
                "anotherTestUser",
                "anotherTestUser",
                "anotherTestUser@email.com",
                true,
                Collections.singleton(Role.USER),
                null
        );
    }

    private User createAdmin() {
        return new User(
                "admin",
                "admin",
                "admin@email.com",
                true,
                Collections.singleton(Role.ADMIN),
                null
        );
    }

    private User createFastLogInUser() {
        return new User(
                "n",
                "p",
                "emailAdress",
                true,
                Collections.singleton(Role.USER),
                null
        );
    }

    public Route createTestRoute_1(User author) {
        return new Route(
                "Тестовый маршрут №1, названия",
                "АааааАААААааААааАааааАААААааААааАааааАААААааААаа_ТЕСТОВЫЙ_1",
                "Это типа длинное описание 1",
                author,
                0,
                null,
                new LinkedList<>() {{
                    add(new MapPoint(null, null, "улица Челюскинцев, 33А, Екатеринбург"));
                    add(new MapPoint(null, null, "улица Луначарского, 31, Екатеринбург"));
                    add(new MapPoint(null, null, "ул. Железнодорожников, 3, Екатеринбург"));
                }},
                Collections.singleton(RouteCategory.HISTORY)
        );
    }

    public Route createTestRoute_2(User author) {
        return new Route(
                "Тестовый маршрут №2, координаты",
                "бла-бла-бла-бла-бла-бла-бла-бла-бла-бла-бла-бла-ТЕСТОВЫЙ_2",
                "Это типа длинное описание 2",
                author,
                15,
                null,
                new LinkedList<>() {{
                    add(new MapPoint("56.838261", "60.585636", null));
                    add(new MapPoint("56.837380", "60.590364", null));
                    add(new MapPoint("56.838615", "60.597998", null));
                }},
                Collections.singleton(RouteCategory.SOLO)
        );
    }

    public Route createTestRoute_3(User author) {
        return new Route(
                "Тестовый маршрут №3, координаты",
                "бла-бла-бла-бла-бла-бла-бла-бла-бла-бла-бла-бла-ТЕСТОВЫЙ_3",
                "Это типа длинное описание 3",
                author,
                30,
                null,
                new LinkedList<>() {{
                    add(new MapPoint("56.848356", "60.601967", null));
                    add(new MapPoint("56.845208", "60.612073", null));
                    add(new MapPoint("56.847944", "60.637457", null));
                }},
                Collections.singleton(RouteCategory.ADVENTURE)
        );
    }

    public Route createTestRoute_Mixed(User author) {
        return new Route(
                "Тестовый маршрут №4 микс",
                "Это описание тестового маршрута с точками заданными разными методами",
                "Это типа длинное описание 4",
                author,
                30,
                null,
                new LinkedList<>() {{
                    add(new MapPoint("56.848356", "60.601967", null));
                    add(new MapPoint(null, null, "улица Луначарского, 31, Екатеринбург"));
                    add(new MapPoint("56.847944", "60.637457", null));
                }},
                Collections.singleton(RouteCategory.ROMANTIC)
        );
    }

    private String randString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            var a = random.nextDouble();
            if (a < 0.1) builder.append(" ");
            else if (a < 0.6) builder.append((char) ('a' + random.nextInt(26)));
            else builder.append((char) ('A' + random.nextInt(26)));
        }
        return builder.toString();
    }
}