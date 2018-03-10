import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        String testParagraph = "Contrary to popular belief, Lorem Ipsum is not simply random text e.g. Mr. Richard Simmons. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Mrs. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";
        ArrayList<String> result = parseParagraphChars(testParagraph);
        String breakPoint = "";
        //todo: fill with real tests or at least asserts
    }


    //cases I've thought about: '. ', '? ', '! '
    // don't do e.g., or i.e.//somewhat covered by ". <uppercase>". better would be
    // don't do mr. or mrs.
    //B.C. or etc. could be end of sentence or not?
    //Names are weird. George R. R. Martin. could end a sentence, (middle names).
    ////or sentences ending in acronyms. I', gonna just pass over that for now.



    /*Potentially might be better written as a regex. They're terrible to debug and stressful for interviews. So here
    we are with old-school string character parsing. Perhaps set behind an interface or something and replace later.
    assumptions:
    * english
    * don't pull in jars to hackerrank. A quick google search for libraries that parse sentences reveals
    ** a Stanford NLP Natural Language Proessing library. Looks like they have python and java support, but if this is going
    ** to be a larger effort python would be a better choice probably.
    * tbh there's a lot of cases to think about and someone's already probably thought through them
     */
    public static ArrayList<String> parseParagraphChars(String longStr){
        ArrayList<String> sentences = new ArrayList<String>();

        StringBuilder currentSent = new StringBuilder();
        int i = 0;
        while(i < longStr.length()){
            char currentChar = longStr.charAt(i);
            currentSent.append(currentChar);
            i++;
            if(isEndingSign(currentChar)){
                if(i < longStr.length()){
                    char nextChar = longStr.charAt(i);
                    currentSent.append(nextChar);
                    i++;
                    if(isBlankSpace(nextChar)){
                        if(isEgIeMrMrsMS(longStr, i)){
                            //continue;//not strictly necessary in this block but if it's e.g. Mr. Richard Simmons the sentence is not complete yet.
                            continue;
                        } else if(i < longStr.length()){
                            char nextNextChar = longStr.charAt(i);
                            //check if capital
                            if((int)nextNextChar >= 65 || (int)nextNextChar < 91){
                                sentences.add(currentSent.toString());
                                currentSent.setLength(0);//reset the stringbuilder
                            }else{
                                currentSent.append(nextNextChar);
                                i++;
                                //perhaps return error?
                                System.out.println("something got to '. <lowercase>'. figure it out: " + currentChar + currentSent + ".");
                                //continue parsing
                            }
                        }else{
                            sentences.add(currentSent.toString());
                            currentSent.setLength(0);//reset the stringbuilder
                        }
                    }else{
                        //debug log
                        System.out.println("something got to '.CHAR'. figure it out: " + currentChar + currentSent + ".");
                        //continue parsing
                    }
                }else{
                    sentences.add(currentSent.toString());
                    currentSent.setLength(0);//reset the stringbuilder
                }
            }


        }
        return  sentences;
    }

    //i'm sure there are more of these cases;
    private static boolean isEgIeMrMrsMS(String longStr, int i) {
        if(i - 5 > 0){//okay, this should rarely happen but just to be sure we don't go below the string
            //todo: better java
            if(longStr.charAt(i - 5) == 'e' && longStr.charAt(i - 4) == '.' && longStr.charAt(i - 3) == 'g'){
                return true;
            }else if(longStr.charAt(i - 5) == 'i' && longStr.charAt(i - 4) == '.' && longStr.charAt(i - 3) == 'e') {
                return true;
            }else if(longStr.charAt(i - 5) == 'M' && longStr.charAt(i - 4) == 'r' && longStr.charAt(i - 3) == 's') {
                return true;
            }
        }
        if(i - 4 > 0){
            if(longStr.charAt(i - 4) == 'M' && longStr.charAt(i - 3) == 'r'){
                return true;
            }
            if(longStr.charAt(i - 4) == 'M' && longStr.charAt(i - 3) == 's') {
                return true;
            }
        }
        return false;
    }

    private static boolean isBlankSpace(char a) {
        return a == ' ' || a == '\n' || a == '\r';
    }

    private static boolean isEndingSign(char a){
        return a == '?' || a == '.' || a == '!';
    }
}
