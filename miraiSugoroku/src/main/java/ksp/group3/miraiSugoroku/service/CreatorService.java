package ksp.group3.miraiSugoroku.service;

import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.form.SquareCreatorForm;
import ksp.group3.miraiSugoroku.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreatorService {
    @Autowired
    CreatorRepository cRepo;

    public SquareCreator createSquareCreator(SquareCreatorForm form) {
        return cRepo.save(form.toEntity());
    }

    public SquareCreator getSquareCreator(long creatorId) {
        return cRepo.findById(creatorId).get();
    }

    public SquareCreator updateSquareCreator(long creatorId, SquareCreatorForm form) {
        SquareCreator sc = cRepo.findById(creatorId).get();

        if (Objects.nonNull(form.getName())) {
            sc.setName(form.getName());
        }
        if (Objects.nonNull(form.getNickname())) {
            sc.setNickname(form.getNickname());
        }
        if (form.getGroup() != 0) {
            sc.setGroup(form.getGroup());
        }

        return cRepo.save(sc);
    }

    void deleteSquareCreator(long creatorId) {
        cRepo.deleteById(creatorId);
    }

    List<SquareCreator> getSquareCreatorsByEventId(long eventId) {
        return cRepo.findByEventID(eventId);
    }
}
