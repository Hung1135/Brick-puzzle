package vn.edu.hcmuaf.fit.ttltw_nhom6.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.ttltw_nhom6.db.JdbiConnector;
import vn.edu.hcmuaf.fit.ttltw_nhom6.model.User;

import java.util.Optional;

public class UserDAO {

    private final Jdbi jdbi;

    public UserDAO() {
        this.jdbi = JdbiConnector.get();
    }

    /**
     * Đăng ký tài khoản
     */
    public boolean register(User user) {

        String sql =
                "INSERT INTO users " +
                        "(username, email, password_hash, created_at, updated_at) " +
                        "VALUES " +
                        "(:username, :email, :passwordHash, NOW(), NOW())";

        return jdbi.withHandle(handle -> {

            int rows = handle.createUpdate(sql)
                    .bind("username", user.getUsername())
                    .bind("email", user.getEmail())
                    .bind("passwordHash", user.getPasswordHash())
                    .execute();

            return rows > 0;
        });
    }

    /**
     * Đăng nhập
     */
    public Optional<User> login(String email,
                                String passwordHash) {

        String sql =
                "SELECT " +
                        "id, " +
                        "username, " +
                        "email, " +
                        "password_hash AS passwordHash, " +
                        "avatar_url AS avatarUrl, " +
                        "created_at AS createdAt, " +
                        "updated_at AS updatedAt " +
                        "FROM users " +
                        "WHERE email = :email " +
                        "AND password_hash = :passwordHash";

        return jdbi.withHandle(handle ->

                handle.createQuery(sql)

                        .bind("email", email)
                        .bind("passwordHash", passwordHash)

                        .mapToBean(User.class)

                        .findFirst()
        );
    }

    /**
     * Kiểm tra email tồn tại
     */
    public boolean emailExists(String email) {

        String sql =
                "SELECT id " +
                        "FROM users " +
                        "WHERE email = :email";

        return jdbi.withHandle(handle ->

                handle.createQuery(sql)

                        .bind("email", email)

                        .mapTo(Long.class)

                        .findFirst()

                        .isPresent()
        );
    }

    /**
     * Kiểm tra username tồn tại
     */
    public boolean usernameExists(String username) {

        String sql =
                "SELECT id " +
                        "FROM users " +
                        "WHERE username = :username";

        return jdbi.withHandle(handle ->

                handle.createQuery(sql)

                        .bind("username", username)

                        .mapTo(Long.class)

                        .findFirst()

                        .isPresent()
        );
    }

    /**
     * Tìm user theo id
     */
    public Optional<User> findById(Long id) {

        String sql =
                "SELECT " +
                        "id, " +
                        "username, " +
                        "email, " +
                        "password_hash AS passwordHash, " +
                        "avatar_url AS avatarUrl, " +
                        "created_at AS createdAt, " +
                        "updated_at AS updatedAt " +
                        "FROM users " +
                        "WHERE id = :id";

        return jdbi.withHandle(handle ->

                handle.createQuery(sql)

                        .bind("id", id)

                        .mapToBean(User.class)

                        .findFirst()
        );
    }
}