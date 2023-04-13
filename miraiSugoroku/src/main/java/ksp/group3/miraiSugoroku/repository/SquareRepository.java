package ksp.group3.miraiSugoroku.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ksp.group3.miraiSugoroku.entity.Square;

@Repository
public interface SquareRepository extends CrudRepository<Square, Long> {
    public List<Square> findAll();

    


    public List<Square> findByIsApproved(boolean isApproved);

    public List<Square> findByTitleContaining(String keyword);

    public List<Square> findByDescriptionContaining(String keyword);

    public List<Square> findByCreatorId(Long creatorId);

    public List<Square> findByCreatorIdAndIsApproved(Long creatorId, boolean isApproved);

    public List<Square> findByEventIdAndGroupId(Long eventId, int groupId);

    public List<Square> findByEventIdAndIsApproved(Long eventId, boolean isApproved);

    public List<Square> findByEventIdAndGroupIdAndIsApproved(Long eventId, int groupId, boolean isApproved);

    public List<Square> findByEventIdAndGroupIdAndSquareEffectAndIsApproved(Long eventId, int groupId, int squareEffect,
            boolean isApproved);

    public List<Square> findBySquareEffectAndIsApproved(int squareEffect, boolean isApproved);

    public Page<Square> findAll(Pageable pageable);

    public Page<Square> findByIsApproved(Pageable pageable,boolean isApproved);

    public Page<Square> findByTitleContainingAndIsApprovedOrDescriptionContainingAndIsApproved(Pageable pageable,String title,boolean isApproved1, String description,boolean isApproved2);

    public Page<Square> findByNickNameAndIsApproved(Pageable pageable,String nickName,boolean isApproved);

    public Page<Square> findByEventIdAndIsApproved(Pageable pageable, Long eventId, boolean isApproved);

    public Page<Square> findByCreatorIdAndIsApproved(Pageable pageable, Long creatorId, boolean isApproved);
   
}
