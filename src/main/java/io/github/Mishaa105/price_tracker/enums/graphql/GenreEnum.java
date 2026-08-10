package io.github.Mishaa105.price_tracker.enums.graphql;

import lombok.Getter;

@Getter
public enum GenreEnum
{
    ACTION("productGenres:ACTION"),
    ADVENTURE("productGenres:ADVENTURE"),
    SIMULATION("productGenres:SIMULATION"),
    RPG("productGenres:ROLE_PLAYING_GAMES"),
    SHOOTER("productGenres:SHOOTER"),
    CASUAL("productGenres:CASUAL"),
    STRATEGY("productGenres:STRATEGY"),
    ARCADE("productGenres:ARCADE"),
    PUZZLE("productGenres:PUZZLE"),
    SPORT("productGenres:SPORTS"),
    UNIQUE("productGenres:UNIQUE"),
    FAMILY("productGenres:FAMILY"),
    RACING("productGenres:RACING"),
    FIGHTING("productGenres:FIGHTING"),
    HORROR("productGenres:HORROR"),
    SIMULATOR("productGenres:SIMULATOR"),
    PARTY("productGenres:PARTY"),
    MUSIC("productGenres:MUSIC/RHYTHM"),
    ADULT("productGenres:ADULT"),
    BRAIN_TRAINING("productGenres:BRAIN_TRAINING"),
    EDUCATIONAL("productGenres:EDUCATIONAL"),
    QUIZ("productGenres:QUIZ"),
    FITNESS("productGenres:FITNESS"),
    BOARD_GAME("productGenres:BOARD_GAMES");

    private final String genre;

    GenreEnum(String genre)
    {
        this.genre = genre;
    }
}