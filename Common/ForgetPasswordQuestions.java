package Common;

import java.util.Random;

public class ForgetPasswordQuestions {
    static String prefix="whats your favorite ";
    static String[] elements={"book", "movie", "game", "teacher", "music", "country", "color"};
    static String end="?";

    public static String choose(){
        Random rand=new Random();
        int upperbound=7;
        int random=rand.nextInt(upperbound);
        return prefix+elements[random]+end;
    }
}
