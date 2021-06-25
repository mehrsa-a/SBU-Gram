package Common;

import java.util.Random;

/**
 * <h1>Forget Password Questions</h1>
 * <p>this class has some questions for password recovery and it randomly choose one of them</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class ForgetPasswordQuestions {
    static String prefix="whats your favorite ";
    static String[] elements={"book", "movie", "game", "teacher", "music", "country", "color"};
    static String end="?";

    /**
     * @return it returns random question in string way
     */
    public static String choose(){
        Random rand=new Random();
        int upperbound=7;
        int random=rand.nextInt(upperbound);
        return prefix+elements[random]+end;
    }
}
