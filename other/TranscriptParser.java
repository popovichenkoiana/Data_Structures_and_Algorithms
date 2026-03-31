package other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranscriptParser {
    static void main(String[] args) {
        String transcript = """
                Student Number:	1234598872			Grade:		11
                Birthdate:		01/02/2000			Gender:	M
                State ID:		8923827123
                
                Cumulative GPA (Weighted)		3.82
                Cumulative GPA (Unweighted)	3.46
                """;
        String regex = """
              Student\\sNumber:\\s(?<studentNum>\\d{10}).*                                   #studentNum
              Grade:\\s+(?<grade>\\d{1,2}).*                                                 #grade
              Birthdate:\\s+(?<birthMonth>\\d{2})/(?<birthDay>\\d{2})/(?<birthYear>\\d{4}).* #date
              Gender:\\s+(?<gender>\\w+)\\b.*                                                #gender
              State\\sID:\\s+(?<stateID>\\d+)\\b.*                                           #stateID
              Weighted\\)\\s+(?<weightedGPA>[\\d\\.]+)\\b.*                                  #weightedGPA
              Unweighted\\)\\s+(?<unWeightedGPA>[\\d\\.]+)\\b.*                              #unWeightedGPA
              """;
        Pattern pat = Pattern.compile(regex,Pattern.DOTALL | Pattern.COMMENTS);
        Matcher mat = pat.matcher(transcript);
        if (mat.matches()){
            System.out.println((mat.group("studentNum"))); // 1234598872
            System.out.println((mat.group("grade"))); // 11
            System.out.println((mat.group("birthMonth"))); //01
            System.out.println((mat.group("birthDay"))); //02
            System.out.println((mat.group("birthYear"))); //2000
            System.out.println((mat.group("gender"))); //M
            System.out.println((mat.group("stateID"))); //8923827123
            System.out.println((mat.group("weightedGPA"))); //3.82
            System.out.println((mat.group("unWeightedGPA"))); //3.46
        }
    }
}
