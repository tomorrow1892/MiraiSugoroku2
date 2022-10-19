package ksp.group3.miraiSugoroku.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ksp.group3.miraiSugoroku.entity.Square;
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.form.SquareForm;
import ksp.group3.miraiSugoroku.repository.CreatorRepository;
import ksp.group3.miraiSugoroku.repository.EventRepository;
import ksp.group3.miraiSugoroku.repository.SquareRepository;

@Service
public class SquareService {
    @Autowired
    CreatorRepository cRepo;
    @Autowired
    EventRepository eRepo;
    @Autowired
    SquareRepository sRepo;

    public Square createSquare(SquareForm form) {
        Square square = form.toEntity();
        return sRepo.save(square);
    }

    public Square getSquare(Long squareId) {
        return sRepo.findById(squareId).get();
    }

    public List<Square> getAllSquare() {
        return sRepo.findAll();
    }

    

    public Square updateSquare(Long squareId, SquareForm form) {
        Square square = getSquare(squareId);
        square.setTitle(form.getTitle());
        square.setSquareEventId(form.getSquareEventId());
        square.setDescription(form.getDescription());
        square.setApproved(form.isApproved());

        return sRepo.save(square);
    }

    public void deleteSquare(Long squareId) {
        sRepo.deleteById(squareId);
    }

    public List<Square> filterSquaresByIsApproved(boolean isApproved) {
        return sRepo.findByIsApproved(isApproved);
    }

    public List<Square> filterSquaresByEventIdAndIsApproved(Long eventId, boolean isApproved) {
        return sRepo.findByEventIdAndIsApproved(eventId, isApproved);
    }

    public List<Square> searchSquaresByKeyword(String keyword) {
        Iterable<Square> result_title = sRepo.findByTitleContaining(keyword);
        ArrayList<Square> list_title = new ArrayList<>();
        result_title.forEach(list_title::add);

        Iterable<Square> result_description = sRepo.findByDescriptionContaining(keyword);
        ArrayList<Square> list_description = new ArrayList<>();
        result_description.forEach(list_description::add);

        for (Square square : list_description) {
            if (!list_title.contains(square)) {
                list_title.add(square);
            }
        }

        ArrayList<Square> result = new ArrayList<>();
        for (Square square : list_title) {
            if (square.isApproved()) {
                result.add(square);
            }
        }

        return result;
    }

    public List<Square> searchSquaresByNickname(String nickname) {
        Iterable<SquareCreator> result = cRepo.findByNicknameContaining(nickname);
        ArrayList<SquareCreator> list_creator = new ArrayList<>();
        result.forEach(list_creator::add);

        ArrayList<Square> list = new ArrayList<>();

        for (SquareCreator creator : list_creator) {
            Long creatorId = creator.getCreatorId();

            List<Square> list_tmp = sRepo.findByCreatorId(creatorId);
            list.addAll(list_tmp);
        }

        ArrayList<Square> result_list = new ArrayList<>();
        for (Square square : list) {
            if (square.isApproved()) {
                result_list.add(square);
            }
        }

        return result_list;
    }

    public List<Square> searchSquaresByMyBroup(Long creatorId) {
        SquareCreator creator = cRepo.findById(creatorId).get();

        return sRepo.findByEventIdAndGroupId(creator.getEventId(),
                creator.getGroup());
    }

    public List<Square> searchMySquares(Long creatorId) {
        return sRepo.findByCreatorId(creatorId);
    }

    public List<Square> filterCreatorIdAndIsApproved(Long creatorId, boolean isApproved){
        return sRepo.findByCreatorIdAndIsApproved(creatorId, isApproved);
    }

    ///////////以下，ページング込みのマス取得クエリ

    public Page<Square> getPageSquare(Pageable pageable){
        return sRepo.findAll(pageable);
    }

    public Page<Square> getPageApprovedSquare(Pageable pageable,boolean isApproved){
        return sRepo.findByIsApproved(pageable,isApproved);
    }

    public Page<Square> searchPageSquaresByKeyword(Pageable pageable,String keyword){
        
        return sRepo.findByTitleContainingAndIsApprovedOrDescriptionContainingAndIsApproved(pageable,keyword,true,keyword,true);
    }

    public Page<Square> searchPageSquaresByNickname(Pageable pageable, String nickname) {
        return sRepo.findByNickNameContainingAndIsApproved(pageable, nickname, true);
    }


    
}