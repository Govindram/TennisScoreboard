package com.govind.tennis.scoreboard.provider;

import com.govind.tennis.scoreboard.utils.Player;

import java.security.SecureRandom;

public class RandomResultProvider implements ResultProvider{

    private SecureRandom secureRandom;
    public static final String PROVIDER_TYPE = "random";

    public RandomResultProvider() {
        secureRandom = new SecureRandom();
    }

    @Override
    public Player getWinner() {
        int random = secureRandom.nextInt(2);
        if(random == 0) {
            return Player.ONE;
        } else {
            return Player.TWO;
        }
    }
}
