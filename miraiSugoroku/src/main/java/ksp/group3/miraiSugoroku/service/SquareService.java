package ksp.group3.miraiSugoroku.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Square updateSquare(Long squareId, SquareForm form) {
        Square square = getSquare(squareId);
        square.setTitle(form.getTitle());
        square.setSquareEventId(form.getSquareEventId());
        square.setDescription(form.getDescription());
        square.setCreatorId(form.getCreatorId());
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
        Iterable<Square> result_title = sRepo.findByTitleContaining("%" + keyword +
                "%");
        ArrayList<Square> list_title = new ArrayList<>();
        result_title.forEach(list_title::add);

        Iterable<Square> result_description = sRepo.findByTitleContaining("%" +
                keyword + "%");
        ArrayList<Square> list_description = new ArrayList<>();
        result_description.forEach(list_description::add);

        for (Square square : list_description) {
            if (!list_title.contains(square)) {
                list_title.add(square);
            }
        }

        return list_title;
    }

    public List<Square> searchSquaresByNickname(String nickname) {
        Iterable<SquareCreator> result = cRepo.findByNicknameContaining("%" +
                nickname + "%");
        ArrayList<SquareCreator> list_creator = new ArrayList<>();
        result.forEach(list_creator::add);

        ArrayList<Square> list = new ArrayList<>();

        for (SquareCreator creator : list_creator) {
            Long creatorId = creator.getCreatorID();

            List<Square> list_tmp = sRepo.findByCreatorId(creatorId);
            list.addAll(list_tmp);
        }

        return list;
    }

    public List<Square> searchSquaresByMyBroup(Long creatorId) {
        SquareCreator creator = cRepo.findById(creatorId).get();

        return sRepo.findByEventIdAndGroupId(creator.getEventID(),
                creator.getGroup());
    }

    public List<Square> searchMySquares(Long creatorId) {
        return sRepo.findByCreatorId(creatorId);
    }
}
