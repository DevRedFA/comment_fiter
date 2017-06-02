import java.util.Arrays;
import java.util.*;
import java.lang.*;
import java.io.*;


public class Main {

    enum Label {
        SPAM, NEGATIVE_TEXT, TOO_LONG, OK
    }

    interface TextAnalyzer {
        Label processText(String text);
    }


    public static Label checkLabels(TextAnalyzer[] analyzers, String text) {
        Label L = Label.OK;
        for (TextAnalyzer t:analyzers) {
            L = t.processText(text);
            if (Label.OK != L) break;
        }
        return L;
    }



    abstract class KeywordAnalyzer implements TextAnalyzer{
        protected abstract Label getLabel();
        protected abstract String[] getKeywords ();

        public Label processText(String text){
            for (String key :getKeywords ()){
                if (text.contains(key))
                    return getLabel();
            }
            return Label.OK;
        }
    };

    class SpamAnalyzer extends KeywordAnalyzer{
    private String[] keywords;
        public SpamAnalyzer(String[] keywords) {
        this.keywords = new String[keywords.length];
        System.arraycopy(keywords,0,this.keywords,0,keywords.length);
    };
        @Override
    protected Label getLabel(){return Label.SPAM;};
        @Override
    protected String[] getKeywords(){return keywords;};
    };

    class NegativeTextAnalyzer extends KeywordAnalyzer {
        private String[] keywords;
        public NegativeTextAnalyzer() {
            this.keywords = new String[]{":(", "=(", ":|"};
        }
        @Override
    protected Label getLabel(){return Label.NEGATIVE_TEXT;};
        @Override
    protected String[] getKeywords(){return this.keywords;};
    };

    class TooLongTextAnalyzer implements TextAnalyzer{
        private int maxLength = 0;
        public TooLongTextAnalyzer(int i){
            this.maxLength = i;
        };
        public Label processText(String text){
        return (text.length()>this.maxLength) ? Label.TOO_LONG : Label.OK;
        }
    };

    public static void main(String[] args) throws java.lang.Exception {

        TextAnalyzer a = new Main().new SpamAnalyzer(new String[]{"first", "second"});
        TextAnalyzer b = new Main().new NegativeTextAnalyzer();
        TextAnalyzer c = new Main().new TooLongTextAnalyzer(140);
        System.out.println(checkLabels(new TextAnalyzer[]{a, b, c}, " :( this is fiasdrst test, and it is spam"));


    }
};

