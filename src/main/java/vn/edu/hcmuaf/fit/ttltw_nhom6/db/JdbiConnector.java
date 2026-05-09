package vn.edu.hcmuaf.fit.ttltw_nhom6.db;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class
JdbiConnector {
    private static Jdbi jdbi;

    public static Jdbi get() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (jdbi == null) {
            jdbi = Jdbi.create(
                    "jdbc:mysql://" + DBProperties.host() + ":" + DBProperties.port() + "/" + DBProperties.database(),
                    DBProperties.username(),
                    DBProperties.password()
            );


            jdbi.installPlugin(new SqlObjectPlugin());
        }
        return jdbi;
    }

    static class DBProperties {
        private static final Properties prop = new Properties();

        static {
            try (InputStream is = DBProperties.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (is == null) {
                    throw new RuntimeException("File 'vn.edu.hcmuaf.fit.ltw_nhom5.resources.db.properties' not found in classpath!");
                }
                prop.load(is);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load vn.edu.hcmuaf.fit.ltw_nhom5.db.properties", e);
            }
        }

        public static String host() {
            return prop.getProperty("db.host");
        }

        public static int port() {
            return Integer.parseInt(prop.getProperty("db.port"));
        }

        public static String username() {
            return prop.getProperty("db.username");
        }

        public static String password() {
            return prop.getProperty("db.password");
        }

        public static String database() {
            return prop.getProperty("db.databaseName");
        }
    }

    public static void main(String[] args) {
        System.out.println("Host: " + DBProperties.host());
        System.out.println("Port: " + DBProperties.port());
        System.out.println("Database: " + DBProperties.database());
        System.out.println("Username: " + DBProperties.username());
        System.out.println("Password: " + DBProperties.password());
        Jdbi jdbi = get();
        System.out.println("Kết nối DB OK: " + jdbi);
    }
}
