import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {
    private static Locale DefaultLocale = new Locale("ru");

    private DataGenerator() {
    }

    public static String generateUserName() {
        Faker faker = new Faker(DefaultLocale);
        return faker.name().username();
    }

    public static String generatePassword() {
        Faker faker = new Faker(DefaultLocale);
        return faker.internet().password();
    }
}

