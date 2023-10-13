package com.myproject.registerApi.service;

import com.myproject.registerApi.exception.NotFoundException;
import com.myproject.registerApi.model.ChildDTO;
import com.myproject.registerApi.model.db.ActivityDb;
import com.myproject.registerApi.model.db.ChildDb;
import com.myproject.registerApi.repository.ActivityRepository;
import com.myproject.registerApi.repository.ChildRepository;
import com.myproject.registerApi.utilities.ActivityTestObjects;
import com.myproject.registerApi.utilities.ChildTestObjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.myproject.registerApi.utilities.ChildTestObjects.childDb;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ActiveProfiles("test")
@SpringBootTest
public class ChildServiceTest {

    @Autowired
    private ChildService childService;

    @MockBean
    private ChildRepository childRepository;

    @MockBean
    private ActivityRepository activityRepository;

    @BeforeEach
    public void setUp() {
        reset(childRepository);
        reset(activityRepository);
    }

    @Test
    public void testSaveChild() {

        ChildDTO inputChildDTO = childDb.conversionToDto();

        when(activityRepository.findByName(ArgumentMatchers.eq(ActivityTestObjects.testActivity1.getName())))
                .thenReturn(Optional.of(ActivityTestObjects.testActivity1));
        when(activityRepository.findByName(ArgumentMatchers.eq(ActivityTestObjects.testActivity2.getName())))
                .thenReturn(Optional.of(ActivityTestObjects.testActivity2));
        when(childRepository.save(any(ChildDb.class))).thenReturn(childDb);

        ChildDTO savedChild = childService.saveChild(inputChildDTO);

        assertNotNull(savedChild);
        assertEquals(inputChildDTO, savedChild);
        assertEquals(inputChildDTO.getFirstName(), savedChild.getFirstName());
        verify(childRepository, times(1)).save(any(ChildDb.class));
    }

    @Test
    public void getChildTest() {
        ChildDTO childDTO = childDb.conversionToDto();

        when(childRepository.findById(childDTO.getId())).thenReturn(Optional.of(childDb));

        ChildDTO child = childService.getChild(childDTO.getId());

        assertNotNull(child);
        assertEquals(childDTO, child);
        verify(childRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void testGetAllChild() {
        List<ChildDTO> listDTO =  Arrays.asList(childDb.conversionToDto());
        List<ChildDb> listDb =  Arrays.asList(childDb);

        when(childRepository.findAll()).thenReturn(listDb);

        List<ChildDTO> resultList = childService.getAllChildren();

        assertThat(listDTO.size(), is(equalTo(resultList.size())));
        verify(childRepository, times(1)).findAll();
    }

}
