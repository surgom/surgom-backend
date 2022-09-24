package com.greentea.surgom.repository;

import com.greentea.surgom.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Long save(User user);
    User findById(Long id);
    void delete(Long id);
    void deleteAll();
    List<User> findAllWithAge_range(String range);
    List<User> findAllWithGender(char gender);
    void update(User user);
}
