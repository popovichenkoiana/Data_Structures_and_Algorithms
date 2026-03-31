package other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPractice {

    public static void main(String[] args) {
        System.out.println("cat".matches("cat"));  // prints "true"
        System.out.println("bat".matches("[cCbB]at"));  // prints "true"
        System.out.println("Dat".matches("[a-fA-F]at"));  // prints "true"
        System.out.println("Cat".matches("[^c]at"));  // prints "true" - any char except c
        System.out.println("Lat".matches("[^a-z]at"));  // prints "true" - any char except a-z
        System.out.println("Lat".matches("\\wat"));  // prints "true"
        System.out.println("d_t".matches("\\w\\w\\w"));  // prints "true" - any string that have 3 word char
        System.out.println("1".matches("\\d"));  // prints "true" - any string that have 1 digit char
        System.out.println("321-333-7652".matches("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d"));  // prints "true"
        System.out.println("321-333-7652".matches("\\d{3}-\\d{3}-\\d{4}"));  // prints "true"
        System.out.println("321.333.7652".matches("\\d{3}[-.,]\\d{3}[-.,]\\d{4}"));  // prints "true"
        System.out.println("321 333 7652".matches("\\d{3}[-.,\\s]\\d{3}[-.,\\s]\\d{4}"));  // prints "true" 1 space
        System.out.println("321..333     7652".matches("\\d{3}[-.,\\s]+\\d{3}[-.,\\s]+\\d{4}"));  // prints "true" 1 or many spaces
        System.out.println("321333     7652".matches("\\d{3}[-.,\\s]*\\d{3}[-.,\\s]*\\d{4}"));  // prints "true" 0 or many spaces
        System.out.println("321333 7652".matches("\\d{3}[-.,\\s]?\\d{3}[-.,\\s]?\\d{4}"));  // prints "true" 0 or 1 spaces
        System.out.println("321-333-765".matches("\\d{3}[-.,\\s]?\\d{3}[-.,\\s]?\\d{3,4}"));  // prints "true" 3 or 4 digits at the end
        System.out.println("321-333-765".matches("\\d{3}[-.,\\s]?\\d{3}[-.,\\s]?\\d{3,}"));  // prints "true" at least 3,but it can be more
        System.out.println("321-333-765".matches("(\\d{3}[-.,\\s]?){2}\\d{3,}"));  // prints "true"
        System.out.println("321-7652".matches("(\\d{3}[-.,\\s]?){1,2}\\d{3,}"));  // prints "true"
        System.out.println("1.232.321-7652".matches("(\\d{1}[-.,\\s]?){1}(\\d{3}[-.,\\s]?){1,2}\\d{3,}"));  // prints "true"
        System.out.println("1.232.321-7652".matches("(\\d{1}[-.,\\s]?)?(\\d{3}[-.,\\s]?){1,2}\\d{3,}"));// prints "true" 0 or 1 at the beginning

        String regex = """
                (?x)
                (?:(?<countryCode>\\d{1,2})[-.,\\s]?)? 
                (?:\\(?(?<areaCode>\\d{3})\\)?[-.,\\s]?) 
                (?:(?<exchange>\\d{3})[-.,\\s]?) 
                (?<lineNumber>\\d{4})""";

        String phoneNumber = "12.(232)-333-2365";

        System.out.println(phoneNumber.matches(regex));

        Pattern pat = Pattern.compile(regex, Pattern.COMMENTS);
        Matcher mat = pat.matcher(phoneNumber);

        if (mat.matches()) {
            System.out.format("Country code: %s\n", mat.group("countryCode")); // prints "12"
            System.out.format("Area codeß: %s\n", mat.group("areaCode")); // prints "232"
            System.out.format("Exchange: %s\n", mat.group("exchange")); // prints "333"
            System.out.format("Line number: %s\n", mat.group("lineNumber")); // prints "2365"
        }

        System.out.println("doggy".matches("....."));// prints "true"
        System.out.println("doggy".matches(".*"));// prints "true"
        System.out.println("doggy".matches(".+"));// prints "true" 1 or more
        System.out.println("a".matches(".?"));// prints "true" 0 or 1
        System.out.println("doggy".matches("^.....$"));// prints "true" match any 5 characters
        System.out.println("cat doggy".matches("...\\s\\b....."));// prints "true"
        System.out.println("---".matches("\\W\\W\\W"));// prints "true"  not word char
        System.out.println("abc".matches("\\D\\D\\D"));// prints "true"  not digit
        System.out.println(".".matches("\\S"));// prints "true"  any that is not space

    }
}
