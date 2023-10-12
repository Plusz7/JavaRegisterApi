package com.myproject.registerApi.service;

import com.myproject.registerApi.exception.AlreadyExistsException;
import com.myproject.registerApi.exception.FailedToExecuteException;
import com.myproject.registerApi.exception.NotFoundException;
import com.myproject.registerApi.model.ActivityDTO;
import com.myproject.registerApi.model.db.ActivityDb;
import com.myproject.registerApi.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ActivityServiceTest {

    private ActivityDTO testActivityDTO = new ActivityDTO().name("testactivity");
    private ActivityDb testActivityDb = new ActivityDb("testactivity");

    @Autowired
    private ActivityServiceImpl activityService;

    @MockBean
    private ActivityRepository activityRepository;

    @BeforeEach
    public void setUp() {
        reset(activityRepository);
    }

    @Test
    public void testSaveActivity() {
        //Given
        ActivityDTO inputActivityDTO = testActivityDTO;
        ActivityDb savedActivityDb = testActivityDb;

        when(activityRepository.save(any(ActivityDb.class))).thenReturn(savedActivityDb);

        //When
        ActivityDTO savedActivityDTO = activityService.saveActivity(inputActivityDTO);

        //Then
        assertNotNull(savedActivityDTO);
        assertEquals(savedActivityDb.getId(), savedActivityDTO.getId());
        assertEquals(savedActivityDb.getName(), savedActivityDTO.getName());
        verify(activityRepository, times(1)).save(any(ActivityDb.class));
    }

    @Test
    public void testSaveActivityAlreadyExists() {
        //Given
        ActivityDTO inputActivityDTO = testActivityDTO;

        when(activityRepository.save(any(ActivityDb.class))).thenThrow(AlreadyExistsException.class);

        //When Then
        assertThrows(AlreadyExistsException.class, () -> activityService.saveActivity(testActivityDTO));
        verify(activityRepository, times(1)).save(any(ActivityDb.class));
    }

    @Test
    public void testGetActivity() {
        //Given
        ActivityDTO fetchedActivityDTO = testActivityDTO;

        when(activityRepository.findByName(any(String.class))).thenReturn(Optional.of(testActivityDb));

        //When
        ActivityDTO activity = activityService.getActivityByName(testActivityDTO.getName());

        //Then
        assertNotNull(activity);
        assertEquals(fetchedActivityDTO, activity);
        verify(activityRepository, times(1)).findByName(any(String.class));
    }

    @Test
    public void testActivityNotFound() {
        //Given
        String activityName = testActivityDTO.getName();

        //Given When
        when(activityRepository.findByName(activityName)).thenThrow(NotFoundException.class);

        //Then
        assertThrows(NotFoundException.class, () -> activityService.getActivityByName(activityName));
        verify(activityRepository, times(1)).findByName(activityName);
    }

    @Test
    public void testGetAllActivities() {
        //Given
        List<ActivityDTO> listDTO = Arrays.asList(testActivityDTO, new ActivityDTO());
        List<ActivityDb> listDb = Arrays.asList(testActivityDb, new ActivityDb());

        when(activityRepository.findAll()).thenReturn(listDb);

        //When
        List<ActivityDTO> resultList = activityService.getAllActivities();

        //Then
        assertThat(listDTO.size(), is(equalTo(resultList.size())));
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateActivity() {
        //Given When
        String oldActivityName = testActivityDb.getName();
        ActivityDTO newActivity = new ActivityDTO().name("newname");
        String newActivityName = newActivity.getName();
        when(activityRepository.findByName(oldActivityName)).thenReturn(Optional.of(testActivityDb));
        when(activityRepository.updateActivityName(newActivityName, testActivityDTO.getId())).thenReturn(1);

        //Then
        assertDoesNotThrow(() -> activityService.updateActivity(oldActivityName, newActivityName));
        verify(activityRepository, times(1)).updateActivityName(newActivityName, testActivityDTO.getId());
    }

    @Test
    public void testThrowAlreadyExistsExceptionOnUpdateActivity() {
        // Given
        String activityName = testActivityDTO.getName();

        // When and Then
        assertThrows(AlreadyExistsException.class, () -> activityService.updateActivity(activityName, activityName));
    }

    @Test
    public void testThrowFailedToExecuteExceptionOnUpdateActivity() {
        //Given
        String oldActivityName = testActivityDb.getName();
        ActivityDTO newActivity = new ActivityDTO().name("newname");
        String newActivityName = newActivity.getName();
        when(activityRepository.findByName(oldActivityName)).thenReturn(Optional.of(testActivityDb));
        when(activityRepository.updateActivityName(newActivityName, testActivityDTO.getId())).thenReturn(0);

        //When Then
        assertThrows(FailedToExecuteException.class, () -> activityService.updateActivity(oldActivityName, newActivityName));
        verify(activityRepository, times(1)).findByName(oldActivityName);
        verify(activityRepository, times(1)).updateActivityName(newActivityName, testActivityDTO.getId());
    }

    @Test
    public void testDeleteActivity() {
        //Given When
        String activityName = testActivityDTO.getName();

        when(activityRepository.deleteByName(activityName)).thenReturn(1);

        //Then
        assertDoesNotThrow(() -> activityService.deleteActivity(activityName));
        verify(activityRepository, times(1)).deleteByName(activityName);
    }
}
