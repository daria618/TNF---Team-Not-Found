package tnf.back.db.entityes;

import lombok.Getter;

@Getter
public enum RouteCategory {
    TRAVEL("Путешественникам"),
    HISTORY("Любителям истории"),
    ART("Ценителям искусства"),
    ROMANTIC("Романтика");

    private final String textRepresent;

    RouteCategory(String textRepresent) {
        this.textRepresent = textRepresent;
    }
}