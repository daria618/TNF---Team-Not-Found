package tnf.back.temp;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tnf.back.db.entityes.*;
import tnf.back.db.repo.RouteRepository;
import tnf.back.db.repo.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

@Component
public class DBInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;

    public DBInit(UserRepository userRepository, RouteRepository routeRepository) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User testUser = createTestUser();
        User anotherTestUser = createAnotherTestUser();
        User admin = createAdmin();

        Route route1 = createTestRoute_1(admin);
        Route route2 = createTestRoute_2(admin);
        Route route3 = createTestRoute_3(anotherTestUser);
        Route routeMixed = createTestRoute_Mixed(anotherTestUser);

        userRepository.save(testUser);
        userRepository.save(anotherTestUser);
        userRepository.save(admin);

        routeRepository.save(route1);
        routeRepository.save(route2);
        routeRepository.save(route3);
        routeRepository.save(routeMixed);
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

    private User createAnotherTestUser() {
        return new User(
                "anotherTestUser",
                "anotherTestUser",
                "anotherTestUser@email.com",
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

    public Route createTestRoute_1(User author) {
        return new Route(
                "Тестовый маршрут №1, названия",
                "АааааАААААааААааАааааАААААааААааАааааАААААааААаа_ТЕСТОВЫЙ_1",
                "Это типа длинное описание 1",
                author,
                0,
                null,
                new LinkedList<>(){{
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
                new LinkedList<>(){{
                    add(new MapPoint("56.838261", "60.585636", null));
                    add(new MapPoint("56.837380", "60.590364", null));
                    add(new MapPoint("56.838615", "60.597998", null));
                }},
                Collections.singleton(RouteCategory.ART)
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
                new LinkedList<>(){{
                    add(new MapPoint("56.848356", "60.601967", null));
                    add(new MapPoint("56.845208", "60.612073", null));
                    add(new MapPoint("56.847944", "60.637457", null));
                }},
                Collections.singleton(RouteCategory.TRAVEL)
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
                new LinkedList<>(){{
                    add(new MapPoint("56.848356", "60.601967", null));
                    add(new MapPoint(null, null, "улица Луначарского, 31, Екатеринбург"));
                    add(new MapPoint("56.847944", "60.637457", null));
                }},
                Collections.singleton(RouteCategory.ROMANTIC)
        );
    }

}