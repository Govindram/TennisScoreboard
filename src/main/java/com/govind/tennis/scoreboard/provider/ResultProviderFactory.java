package com.govind.tennis.scoreboard.provider;

import java.awt.*;

public  class ResultProviderFactory {

    public static final ResultProvider getResultProvider(String providerType) {
        switch (providerType) {
            case RandomResultProvider.PROVIDER_TYPE:
            default:
                return new RandomResultProvider();
        }
    }

}
