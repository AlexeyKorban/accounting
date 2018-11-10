package local.ldwx.accounting.repository;

import local.ldwx.accounting.model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

    default User getWithProjects(int id) {
        throw new UnsupportedOperationException();
    }
}
