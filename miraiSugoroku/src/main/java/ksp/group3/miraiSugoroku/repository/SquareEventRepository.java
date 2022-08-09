package ksp.group3.miraiSugoroku.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ksp.group3.miraiSugoroku.entity.SquareEvent;

@Repository
public interface SquareEventRepository extends CrudRepository<SquareEvent, Long> {
    public List<SquareEvent> findAll();
}