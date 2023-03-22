package ksp.group3.miraiSugoroku.service;

import ksp.group3.miraiSugoroku.exception.MiraiSugorokuException;
import ksp.group3.miraiSugoroku.entity.SquareCreator;
import ksp.group3.miraiSugoroku.form.SquareCreatorForm;
import ksp.group3.miraiSugoroku.form.UpdateSquareCreatorForm;
import ksp.group3.miraiSugoroku.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreatorService {
    @Autowired
    CreatorRepository cRepo;

    public SquareCreator createSquareCreator(SquareCreatorForm form) {
        return cRepo.save(form.toEntity());
    }

    public SquareCreator getSquareCreator(Long creatorId) {
        Optional<SquareCreator> sc = cRepo.findById(creatorId);
        if (sc.isEmpty()) {
            return null;
        }

        return cRepo.findById(creatorId).get();
    }

    public SquareCreator updateSquareCreator(Long creatorId, SquareCreatorForm form) {
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

    public SquareCreator updateSquareCreator(Long creatorId, UpdateSquareCreatorForm form) {
        SquareCreator sc = cRepo.findById(creatorId).get();

        sc.setNickname(form.getNickname());

        sc.setGroup(form.getSelectedGroup());

        return cRepo.save(sc);
    }

    public void deleteSquareCreator(Long creatorId) {
        cRepo.deleteById(creatorId);
    }

    public List<SquareCreator> getSquareCreatorsByEventId(Long eventId) {
        return cRepo.findByEventId(eventId);
    }

    public SquareCreator getSquareCreatorByEventIdAndLoginId(Long eventId, String loginId) {
        if (cRepo.findByEventIdAndLoginId(eventId, loginId) != null) {
            throw new MiraiSugorokuException(MiraiSugorokuException.USED_LOGINID, "");
        }

        return cRepo.findByEventIdAndLoginId(eventId, loginId);
    }

    public SquareCreator login(Long eventId, String loginId) {
        if (cRepo.findByEventIdAndLoginId(eventId, loginId) == null) {
            throw new MiraiSugorokuException(MiraiSugorokuException.WRONG_PASSWORD, "");
        }

        return cRepo.findByEventIdAndLoginId(eventId, loginId);
    }

    public void createSquareCreatorsOneTime(int nCreators, Long eventId ) {
        for (int i = 0; i < nCreators; i++) {
            String id = generateCreatorId(eventId, i);
            // この部分はSquareCreatorエンティティによって変更の必要あり（name属性など）
            SquareCreator creator = new SquareCreator(null, id, eventId, 0, true, "", "");
            cRepo.save(creator);
        }
    }

    public String generateCreatorId(long eventId, int index) {
        int number = (int)eventId + index;
        int sum = getDigitsSum(number);
        int check_digit = sum % 26;
        char alphabet = 'A';
        for (int i = 0; i < check_digit; i++) {
            alphabet ++;
        }
        String creatorId = String.format("%04d", eventId) + alphabet + String.format("%03d", index);
        return creatorId; 
    }

    private int getDigitsSum(int number) {
        int sum = 0;
        while ( number < 1 ) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

}
