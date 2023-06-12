package tnf.back.mvc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tnf.back.db.entityes.*;
import tnf.back.db.repo.CommentRepository;
import tnf.back.db.repo.FavoritesRepository;
import tnf.back.db.repo.RouteRepository;
import tnf.back.logic.Checker;
import tnf.back.logic.Transform;

import java.util.ArrayList;

@Controller
public class RouteCardController {

    private final RouteRepository routeRepository;
    private final CommentRepository commentRepository;
    private final FavoritesRepository favoritesRepository;

    public RouteCardController(
            RouteRepository routeRepository,
            CommentRepository commentRepository,
            FavoritesRepository favoritesRepository
    ) {
        this.routeRepository = routeRepository;
        this.commentRepository = commentRepository;
        this.favoritesRepository = favoritesRepository;
    }

    @GetMapping("/routes/{route_id}")
    public String open(
            @AuthenticationPrincipal User user,
            @PathVariable("route_id") Long id,
            Model model
    ) {
        model.addAttribute("logUser", user);

        var route = routeRepository.findById(id).get();
        model.addAttribute("route", route);

        var comments = commentRepository.findAllByRoute(route);
        model.addAttribute("comments", comments);

        var texts = new ArrayList<String>();
        for (var point : route.getPoints()) texts.add(Transform.MapPointToYMAPString(point));
        model.addAttribute("texts", texts);

        model.addAttribute("favorite", isFavorite(user, route));

        return "route_card";
    }

    private Favorites isFavorite(User user, Route route){
        if (user != null && route != null){
            var favorites = favoritesRepository.findByUser(user);
            for (var fav : favorites)
                if (fav.getRoute().getId() == route.getId())
                    return fav;
        }
        return null;
    }

    @PostMapping("/routes/{route_id}")
    public String acceptAddCommentForm(
            @AuthenticationPrincipal User user,
            @PathVariable("route_id") Long id,
            @RequestParam("comment_text") String comment_text,
            Model model
    ) {
        var route = routeRepository.findById(id).get();
        Comment comment = new Comment(comment_text, 0L, user, route);
        commentRepository.saveAndFlush(comment);
        return "redirect:/routes/" + id;
    }


}
