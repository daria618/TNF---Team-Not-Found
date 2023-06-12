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
        routes.add(route_1(users.get(1)));
        routes.add(route_2(users.get(2)));
        routes.add(route_3(users.get(3)));
        routes.add(route_4(users.get(4)));
        routes.add(route_5(users.get(5)));
        routes.add(route_0(users.get(4)));

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

    private Route route_0(User user) {
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
    private Route route_1(User user) {
        HashSet<RouteCategory> categories = new HashSet<>();
        categories.add(RouteCategory.HISTORY);
        categories.add(RouteCategory.ROMANTIC);
        HashSet<String> images = new HashSet<>(){{
            add("r2_a0.webp");
            add("r2_a1.jpg");
            add("r2_a2.jpg");
            add("r2_a3.webp");
        }};
        ArrayList<MapPoint> points = new ArrayList<>(){{
            add(new MapPoint("56.837716", "60.596828", null, "Площадь 1905 года - одно из самых популярных мест в Екатеринбурге с множеством кафе и ресторанов, а также с памятником Малышу, символизирующим детскую страницу истории России."));
            add(new MapPoint("56.812217", "60.646765", null, "Центральный парк культуры и отдыха им. В.В. Маяковского - большой зеленый парк с озером и аттракционами, где можно приятно провести время и насладиться природой."));
            add(new MapPoint("56.838878", "60.616698", null, "Большой Уральский театр - знаменитый театр с длинной историей, в котором проходят множество интересных и качественных спектаклей"));
            add(new MapPoint("56.844402", "60.609081", null, "Церковь на Крови - красивейшая православная церковь, построенная на месте, где был убит царь Николай II и его семья"));
            add(new MapPoint("56.844199", "60.591846", null, "Памятник Ельцину - памятник первому президенту РФ, который был рожден в Екатеринбурге, и ставший символом начала перестройки в стране."));
        }};
        return new Route(
                "История и красота Екатеринбурга",
                "Дорогой турист! Если вы хотите познакомиться с историей и красотами Екатеринбурга, этот маршрут специально для вас.",
                "\"История и красота Екатеринбурга\" - это маршрут, который оставит у вас незабываемые впечатления и поможет узнать об этом городе еще больше. Все достопримечательности на маршруте пронизаны историей и красотой, которые откроют Екатеринбург с новой, удивительной стороны.",
                user,
                "r2_m.webp",
                images,
                points,
                categories
        );
    }
    private Route route_2(User user) {
        HashSet<RouteCategory> categories = new HashSet<>();
        categories.add(RouteCategory.HISTORY);
        categories.add(RouteCategory.ROMANTIC);
        HashSet<String> images = new HashSet<>(){{
            add("r3_a0.jpg");
            add("r3_a1.jpg");
            add("r3_a2.jpg");
            add("r3_a3.jpg");
        }};
        ArrayList<MapPoint> points = new ArrayList<>(){{
            add(new MapPoint("56.827415", "60.615572", null, "Зеленый остров"));
            add(new MapPoint("56.886726","60.625509", null, "Шуваловский парк"));
            add(new MapPoint("56.857900", "60.605973", null, "Английский парк"));
            add(new MapPoint("56.815350", "60.738107", null, "Музей-заповедник \"Ганина яма\""));
        }};
        return new Route(
                "Приключения на Урале",
                "В этом маршруте вы посетите 4 точки в Екатеринбурге и его окрестностях, чтобы насладиться красивыми видами и почувствовать дух приключений на Урале.",
                "1. Зеленый остров – красивый парк в центре города. Здесь вы можете взять напрокат лодку и покататься по озеру, наслаждаясь природой вдали от городской суеты. Место популярно как у местных жителей, так и у туристов, поэтому здесь всегда много людей, особенно в теплое время года.\n" +
                        "\n" +
                        "2. Гора Патриарх – это гора в окрестностях Екатеринбурга с прекрасным видом на город. Вы можете подняться на пик горы и насладиться панорамным видом на город и окрестности. В некоторые дни здесь можно встретить любителей треккинга, которые поднимаются на гору пешком.\n" +
                        "\n" +
                        "3. Английский парк – красивый парк с живописными прудами и аллеей в Екатеринбурге. Это отличное место для отдыха и прогулок в любую погоду. Здесь вы можете отдохнуть на лужайке, посидеть на скамейке и насладиться природой вдали от городской суеты.\n" +
                        "\n" +
                        "4. Музей-заповедник \"Ганина яма\" – это место, которое напоминает о страшных событиях, произошедших в 1918 году, когда была расстреляна царская семья. Здесь вы можете узнать больше об этой истории, увидеть остатки костей и помолиться за покой душ расстрелянных. Место очень тихое и уединенное, идеально подходит для молитв и покаяния.",
                user,
                "r3_m.jpeg",
                images,
                points,
                categories
        );
    }
    private Route route_3(User user) {
        HashSet<RouteCategory> categories = new HashSet<>();
        categories.add(RouteCategory.HISTORY);
        categories.add(RouteCategory.ROMANTIC);
        HashSet<String> images = new HashSet<>(){{
            add("r4_a0.jpg");
            add("r4_a1.webp");
            add("r4_a2.jpg");
        }};
        ArrayList<MapPoint> points = new ArrayList<>(){{
            add(new MapPoint("56.851169","60.604661", null, "Виз-Борисовы подвалы"));
            add(new MapPoint("56.838964","60.605216", null, "Храм на Крови"));
            add(new MapPoint("56.843134","60.626192", null, "Верх-Исетский пруд"));
        }};
        return new Route(
                "Екатеринбургские красоты",
                "Обзорная экскурсия по трем самым популярным и красивым местам города",
                "Обзорная экскурсия по трем самым популярным и красивым местам города - Виз-Борисовым подвалам, Храму на Крови и Верх-Исетскому пруду",
                user,
                "r4_m.jpg",
                images,
                points,
                categories
        );
    }
    private Route route_4(User user) {
        HashSet<RouteCategory> categories = new HashSet<>();
        categories.add(RouteCategory.HISTORY);
        categories.add(RouteCategory.ROMANTIC);
        HashSet<String> images = new HashSet<>(){{
            add("r5_a0.jfif");
            add("r5_a1.jpg");
            add("r5_a2.jpg");
            add("r5_a3.jpg");
        }};
        ArrayList<MapPoint> points = new ArrayList<>(){{
            add(new MapPoint("56.841942", "60.588835", null, "Музей истории плодового садоводства Среднего Урала"));
            add(new MapPoint("56.838605", "60.597691", null, "Парк 1905 года"));
            add(new MapPoint("56.848299", "60.613955", null, "Выставочный зал ВЦИОМ"));
            add(new MapPoint("56.840550", "60.600988", null, "Музей культуры народов Урала"));
        }};
        return new Route(
                "Историко-культурный Екатеринбург",
                "Маршрут по самым значимым и осторожно хранимым местам Екатеринбурга, погрузивший вас в историю города и культуру Урала.",
                "Мавзолей Н. И. Крупской - это одно из наиболее впечатляющих мест в Екатеринбурге. Сюда приезжают не только туристы, но и многие жители города, чтобы почтить память жены Ленина - Надежды Крупской. Мавзолей находится под властью Кремля, и только экскурсовод может проводить здесь экскурсии.",
                user,
                "r5_m.jpg",
                images,
                points,
                categories
        );
    }
    private Route route_5(User user) {
        HashSet<RouteCategory> categories = new HashSet<>();
        categories.add(RouteCategory.HISTORY);
        categories.add(RouteCategory.ROMANTIC);
        HashSet<String> images = new HashSet<>(){{
            add("r6_a0.jpg");
            add("r6_a1.jpg");
        }};
        ArrayList<MapPoint> points = new ArrayList<>(){{
            add(new MapPoint("60.9732", "68.1250", null, "Метеоритный кратер"));
            add(new MapPoint("56.8376", "60.5960", null, "Центральный парк культуры и отдыха"));
            add(new MapPoint("56.8399", "60.6041", null, "Храм на Крови"));
        }};
        return new Route(
                "Знакомство с Екатеринбургом",
                "Обзорная экскурсия по столице урала",
                "Обзорная экскурсия по столице урала.",
                user,
                "r6_m.jpg",
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