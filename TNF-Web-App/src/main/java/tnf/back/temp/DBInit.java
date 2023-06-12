package tnf.back.temp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tnf.back.db.entityes.*;
import tnf.back.db.repo.CommentRepository;
import tnf.back.db.repo.RatingRepository;
import tnf.back.db.repo.RouteRepository;
import tnf.back.db.repo.UserRepository;

import java.util.*;

@Component
public class DBInit implements CommandLineRunner {
    private static final Random random = new Random(System.currentTimeMillis());
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final CommentRepository commentRepository;
    private final RatingRepository ratingRepository;

    private String[] comments = new String[]{
            "Лучший маршрут!",
            "Было интересно)",
            "Интересно и красиво.",
            "Выглядит неплохо, не забыть бы."
    };

    public DBInit(
            UserRepository userRepository,
            RouteRepository routeRepository,
            CommentRepository commentRepository,
            RatingRepository ratingRepository
    ) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.commentRepository = commentRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void run(String... args) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(
                "admin",
                "admin",
                "admin@email.com",
                true,
                Collections.singleton(Role.ADMIN),
                null
        ));
        for (int i = 0; i < 5; i++) {
            users.add(new User(
                    "TestUser_" + i,
                    "password_" + i,
                    "EMail_" + i,
                    true,
                    Collections.singleton(Role.USER),
                    "TestUser_" + i + ".jpg"
            ));
        }

        ArrayList<Route> routes = new ArrayList<>();
        routes.add(route_0(users.get(4), users));

        userRepository.saveAll(users);
        routeRepository.saveAll(routes);

        ArrayList<RatingMark> marks = new ArrayList<>();
        for (var route : routes){
            for (var user : users){
                if (!route.getAuthor().equals(user))
                    marks.add(randRating(user, route));
            }
        }
        ratingRepository.saveAll(marks);

        ArrayList<Comment> randComments = new ArrayList<>();
        for (var route : routes){
            for (var user : users){
                if (!route.getAuthor().equals(user))
                    randComments.add(randComment(user, route));
            }
        }
        commentRepository.saveAll(randComments);
    }

    private Route route_0(User user, ArrayList<User> users) {
        HashSet<RouteCategory> categories = new HashSet<>();
        categories.add(RouteCategory.HISTORY);
        categories.add(RouteCategory.ROMANTIC);
        HashSet<String> images = new HashSet<>(){{
            add("r1_a0.jpg");
            add("r1_a1.webp");
            add("r1_a2.jpg");
            add("r1_a3.jpg");
        }};
        ArrayList<MapPoint> points = new ArrayList<>(){{
            add(new MapPoint("56.827532", "60.602487", null, "Выйти на станции метро 'Геологическая'"));
            add(new MapPoint("56.818387", "60.606228", null, "Дойти до входа в парк"));
            add(new MapPoint("56.817654", "60.597064", null, "Пройти по аллее Дворца Спорта"));
            add(new MapPoint("56.818905", "60.593892", null, "Сойти с аллеи и войти в парк"));
            add(new MapPoint("56.823571", "60.595671", null, "Пройти по парку"));
        }};
        return new Route(
                "Зеленая роща",
                "Самый большой и очень старый парк в центре Екатеринбурга.",
                "В позапрошлом веке указом императора Александра I вся эта территория была отдана Ново-Тихвинскому женскому монастырю. В советские времена здесь размещалась станция юных натуралистов.",
                user,
                "r1_m.jpg",
                images,
                points,
                categories
        );
    }

    private Comment randComment(User user, Route route) {
        return new Comment(comments[random.nextInt(comments.length)], 0L, user, route);
    }

    private RatingMark randRating(User user, Route route){
        return new RatingMark(user, route, random.nextInt(5) + 1d);
    }
}