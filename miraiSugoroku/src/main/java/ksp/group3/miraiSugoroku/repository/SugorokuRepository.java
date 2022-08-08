package ksp.group3.miraiSugoroku.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ksp.group3.miraiSugoroku.entity.Sugoroku;

@Repository
public interface SugorokuRepository extends CrudRepository<Sugoroku, Long> {

}
