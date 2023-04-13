package ksp.group3.miraiSugoroku.security;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ユーザリポジトリ
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
    public Optional<User> findByName(String name);

    public boolean existsByName(String name);

    public Optional<User> findByUid(Long uid);
}