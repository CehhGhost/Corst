package spring.crut.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class GrammarMapHolder {
    private Map<String, String> grammarMap;

    @Autowired
    public GrammarMapHolder() {
        this.grammarMap = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        grammarMap.put("Masc", "Gender");
        grammarMap.put("Fem", "Gender");
        grammarMap.put("Neut", "Gender");

        grammarMap.put("Anim", "Animacy");
        grammarMap.put("Inan", "Animacy");

        grammarMap.put("Sing", "Number");
        grammarMap.put("Plur", "Number");

        grammarMap.put("Nom", "Case");
        grammarMap.put("Gen", "Case");
        grammarMap.put("Dat", "Case");
        grammarMap.put("Acc", "Case");
        grammarMap.put("Ins", "Case");
        grammarMap.put("Loc", "Case");
        grammarMap.put("Voc", "Case");
        grammarMap.put("Par", "Case");

        grammarMap.put("Pos", "Degree");
        grammarMap.put("Cmp", "Degree");
        grammarMap.put("Sup", "Degree");

        grammarMap.put("Neg", "Polarity");

        grammarMap.put("Short", "Variant");

        grammarMap.put("Perf", "Aspect");
        grammarMap.put("AspectImp", "Aspect");

        grammarMap.put("Ind", "Mood");
        grammarMap.put("MoodImp", "Mood");
        grammarMap.put("Cnd", "Mood");

        grammarMap.put("Past", "Tense");
        grammarMap.put("Pres", "Tense");
        grammarMap.put("Fut", "Tense");

        grammarMap.put("Act", "Voice");
        grammarMap.put("Pass", "Voice");
        grammarMap.put("Mid", "Voice");

        grammarMap.put("1", "Person");
        grammarMap.put("2", "Person");
        grammarMap.put("3", "Person");

        grammarMap.put("HyphYes", "Hyph");

        grammarMap.put("ForeignYes", "Foreign");
    }

    public Map<String, String> getGrammarMap() {
        return grammarMap;
    }
}
