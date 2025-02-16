package com.deichor.questapi.paper.configuration;

public class i18n {

    private static i18n INSTANCE;
    private static PaperLocaleManager localeManager;

    private i18n(){
        localeManager = new PaperLocaleManager();
    }

    public static void init(){
        if(INSTANCE == null){
            INSTANCE = new i18n();
        }
    }
    public static PaperLocaleManager get(){
        if(INSTANCE == null){
            throw new IllegalStateException("i18n not initialized");
        }
        return localeManager;
    }


}
