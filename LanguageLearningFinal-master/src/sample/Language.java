package sample;

public class Language {
    private String language;
    private String languageCode;

    public void setLanguage(String _language){
        language = _language;
        languageCode = firstTwo(language);
    }

    public String getLanguage(){
        return language;
    }

    public String getLanguageCode(){
        return languageCode;
    }

    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }
}

