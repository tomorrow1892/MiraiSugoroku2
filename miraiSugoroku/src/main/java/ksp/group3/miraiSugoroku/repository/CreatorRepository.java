package ksp.group3.miraiSugoroku.repository;

import ksp.group3.miraiSugoroku.entity.SquareCreator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreatorRepository extends CrudRepository<SquareCreator, Long> {
    List<SquareCreator> findByEventID(long id);

    List<SquareCreator> findByEventIDAndGroup(long eventID, int group);

    List<SquareCreator> findByNickname(String nickname);

    List<SquareCreator> findByNicknameContaining(String keyword);
}
