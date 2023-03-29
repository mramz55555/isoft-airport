package com.isoft.airport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EntityScan("com.isoft.airport.models")
@EnableJpaRepositories(value = "com.isoft.airport.repositories")
public class AirportApplication{
//    private static JdbcTemplate template=new JdbcTemplate();
//    static {
//        DriverManagerDataSource dataSource=new DriverManagerDataSource(
//                "jdbc:mysql://localhost:3306/airportdb","amz","44238313aA#");
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        template.setDataSource(dataSource);
//    }

    public static void main(String[] args) {
        SpringApplication.run(AirportApplication.class, args);
    }

//    public static void main(String[] args) throws SQLException {
//        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
//                .useDigits(true)
//                .useLower(true)
//                .useUpper(true)
//                .build();
//
//        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
//        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/airportdb","amz","44238313aA#");
//        PreparedStatement statement=connection.prepareStatement("update passengerdetails set password=? where passenger_id=?");
//        for (int i=31827;i<=36099;i++) {
//            statement.setString(1, bCryptPasswordEncoder.encode(
//                    passwordGenerator.generate(8+new Random().nextInt(22))));
//            statement.setInt(2,i);
//            statement.executeUpdate();
//            statement.clearParameters();
//        }
//    }
//}
//
//
// final class PasswordGenerator {
//
//    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
//    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//    private static final String DIGITS = "0123456789";
//    private static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";
//    private boolean useLower;
//    private boolean useUpper;
//    private boolean useDigits;
//    private boolean usePunctuation;
//
//    private PasswordGenerator() {
//        throw new UnsupportedOperationException("Empty constructor is not supported.");
//    }
//
//    private PasswordGenerator(PasswordGeneratorBuilder builder) {
//        this.useLower = builder.useLower;
//        this.useUpper = builder.useUpper;
//        this.useDigits = builder.useDigits;
//        this.usePunctuation = builder.usePunctuation;
//    }
//
//     static class PasswordGeneratorBuilder {
//
//        private boolean useLower;
//        private boolean useUpper;
//        private boolean useDigits;
//        private boolean usePunctuation;
//
//        public PasswordGeneratorBuilder() {
//            this.useLower = false;
//            this.useUpper = false;
//            this.useDigits = false;
//            this.usePunctuation = false;
//        }
//
//        public PasswordGeneratorBuilder useLower(boolean useLower) {
//            this.useLower = useLower;
//            return this;
//        }
//
//
//        public PasswordGeneratorBuilder useUpper(boolean useUpper) {
//            this.useUpper = useUpper;
//            return this;
//        }
//
//        public PasswordGeneratorBuilder useDigits(boolean useDigits) {
//            this.useDigits = useDigits;
//            return this;
//        }
//
//
//        public PasswordGeneratorBuilder usePunctuation(boolean usePunctuation) {
//            this.usePunctuation = usePunctuation;
//            return this;
//        }
//
//
//        public PasswordGenerator build() {
//            return new PasswordGenerator(this);
//        }
//    }
//
//
//    public String generate(int length) {
//        // Argument Validation.
//        if (length <= 0) {
//            return "";
//        }
//
//        // Variables.
//        StringBuilder password = new StringBuilder(length);
//        Random random = new Random(System.nanoTime());
//
//        // Collect the categories to use.
//        List<String> charCategories = new ArrayList<>(4);
//        if (useLower) {
//            charCategories.add(LOWER);
//        }
//        if (useUpper) {
//            charCategories.add(UPPER);
//        }
//        if (useDigits) {
//            charCategories.add(DIGITS);
//        }
//        if (usePunctuation) {
//            charCategories.add(PUNCTUATION);
//        }
//
//        // Build the password.
//        for (int i = 0; i < length; i++) {
//            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
//            int position = random.nextInt(charCategory.length());
//            password.append(charCategory.charAt(position));
//        }
//        return new String(password);
//    }
}