package ksp.group3.miraiSugoroku.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ksp.group3.miraiSugoroku.entity.SquareEvent;

@Repository
public interface SquareEventRepository extends CrudRepository<SquareEvent, Long>{
}