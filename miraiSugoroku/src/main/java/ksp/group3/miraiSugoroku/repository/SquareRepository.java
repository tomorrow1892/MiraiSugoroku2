package ksp.group3.miraiSugoroku.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ksp.group3.miraiSugoroku.entity.Square;

@Repository
public interface SquareRepository extends CrudRepository<Square, Long> {
    public List<Square> findByIsApproved(boolean isApproved);

    public List<Square> findByTitleContaining(String keyword);

    public List<Square> findByDescriptionContaining(String keyword);

    public List<Square> findByCreatorId(Long creatorId);

    public List<Square> findByEventIdAndGroupId(Long eventId, int groupId);

    public List<Square> findByEventIdAndIsApproved(Long eventId, boolean isApproved);
}
