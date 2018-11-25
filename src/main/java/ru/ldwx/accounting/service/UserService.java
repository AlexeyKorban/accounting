package ru.ldwx.accounting.service;

import ru.ldwx.accounting.model.User;
import ru.ldwx.accounting.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    List<User> getAll();

    User getWithProjects(int id);
}
