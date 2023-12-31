package com.myproject.registerApi.service;

import com.myproject.registerApi.exception.NotFoundException;
import com.myproject.registerApi.model.ActivityDTO;
import com.myproject.registerApi.model.ChildDTO;
import com.myproject.registerApi.model.db.ActivityDb;
import com.myproject.registerApi.model.db.ChildDb;
import com.myproject.registerApi.model.enums.Gender;
import com.myproject.registerApi.repository.ChildRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChildServiceImpl extends AbstractService implements ChildService {

    private final ChildRepository childRepository;
    private final ActivityService activityService;

    public ChildServiceImpl(ChildRepository childRepository, ActivityService activityService) {
        this.childRepository = childRepository;
        this.activityService = activityService;
    }

    @Override
    public ChildDTO saveChild(ChildDTO childDTO) {

        Gender gender = getGender(childDTO);

        // should be removed in the future due to low performance ?, is this validation necessary ???
        // maybe another layer needed ???
//        activityListValidation(childDTO);

        List<ActivityDb> convertedListDb = convertActivityListDtoToDb(childDTO);

        ChildDb childDb = new ChildDb(
                childDTO.getFirstName(),
                childDTO.getLastName(),
                childDTO.getAge(),
                gender,
                childDTO.getSocialNumber(),
                convertedListDb
        );

        try {
            childDb = childRepository.save(childDb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return childDb.conversionToDto();
    }

    @Override
    public ChildDTO getChild(UUID id) {
        ChildDb child = childRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Child not found id: " + id));

        return child.conversionToDto();
    }

    @Override
    public List<ChildDTO> getAllChildren() {
        return childRepository.findAll().stream()
                .map(ChildDb::conversionToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateChild(String id, ChildDTO childDto) {
        //TODO update yaml, ID is required

//        ChildDTO fetchedChild = getChild(childDto.getId());
//        String id = fetchedChild.getId().toString();

        int result = childRepository.updateChildInfo(
                childDto.getId(),
                childDto.getFirstName(),
                childDto.getLastName(),
                childDto.getAge(),
                Gender.valueOf(childDto.getGender()),
                childDto.getSocialNumber()
        );

        isSuccess(result, id);
    }

    @Override
    public void deleteChild(String id) {
        childRepository.deleteById(UUID.fromString(id));
    }

    private static Gender getGender(ChildDTO child) {
        return Optional.of(Gender.valueOf(child.getGender().toUpperCase()))
                .orElseThrow(IllegalArgumentException::new);
    }

//    private static ChildDTO conversionChildDbToDto(ChildDb childDb) {
//        return new ChildDTO()
//                .id(childDb.getId())
//                .firstName(childDb.getFirstName())
//                .lastName(childDb.getLastName())
//                .age(childDb.getAge())
//                .gender(childDb.getGender().name())
//                .socialNumber(childDb.getSocialNumber())
//                .activities(convertActivityListDbToDto(childDb));
//    }
//
//    private static List<ActivityDTO> convertActivityListDbToDto(ChildDb childDb) {
//        return childDb.getActivities().stream()
//                .map((activityDb ->
//                        new ActivityDTO()
//                                .id(activityDb.getId())
//                                .name(activityDb.getName())
//                ))
//                .toList();
//    }

    private List<ActivityDb> convertActivityListDtoToDb(ChildDTO childDto) {
        return childDto.getActivities().stream()
                .map((dto) ->
                        convertActivityDtoToDb(activityService.getActivityByName(dto.getName())))
                .toList();
    }

    private ActivityDb convertActivityDtoToDb(ActivityDTO activityDto) {
        return new ActivityDb(
                activityDto.getId(),
                activityDto.getName()
        );
    }

//    private void activityListValidation(ChildDTO childDto) {
//        List<ActivityDTO> activitiesDto = childDto.getActivities();
//        if (!activitiesDto.isEmpty()) {
//            List<ActivityDTO> allActivities = activityService.getAllActivities();
//            if (!new HashSet<>(allActivities).containsAll(activitiesDto))
//                throw new NotFoundException("Activity not found");
//        }
//    }
}
