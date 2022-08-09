package ksp.group3.miraiSugoroku.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ksp.group3.miraiSugoroku.entity.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    List<Player> findBySugorokuId(Long sugorokuId);

    List<Player> findBySugorokuIdAndOrder(Long sugorokuId, int order);

}