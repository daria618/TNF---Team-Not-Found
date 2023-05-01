package tnf.back.db.entityes;

import lombok.Getter;

@Getter
public enum RouteCategory {
    ADVENTURE("Искателям приключений"),
    HISTORY("Любителям истории"),
    SOLO("Одиноким путешественникам"),
    ROMANTIC("Ищущим романтический отдых");

    private final String textRepresent;

    RouteCategory(String textRepresent) {
        this.textRepresent = textRepresent;
    }
}