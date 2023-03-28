package sample;

public class FullTranslation {
    private String english;
    private String otherLanguage;
    private String IPA;
    private boolean hasIPA;

    public FullTranslation(String _english, String _otherLanguage, String _IPA){
        english = _english;
        otherLanguage = _otherLanguage;
        IPA = _IPA;
    }

    public String getEnglish(){
        return english;
    }

    public String getOtherLanguage(){
        return otherLanguage;
    }

    public String getIPA(){
        return IPA;
    }


    public String toString(){
        if(!IPA.isEmpty()){
            return "\""+otherLanguage+"\n"+IPA+"\","+english;
        }
        return otherLanguage+","+english;
    }

}

