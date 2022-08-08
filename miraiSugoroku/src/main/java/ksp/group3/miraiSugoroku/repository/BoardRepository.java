package ksp.group3.miraiSugoroku.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ksp.group3.miraiSugoroku.entity.Board;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {

    List<Board> findBySugorokuIdAndSequence(Long sugorokuId, int sequence);

}
