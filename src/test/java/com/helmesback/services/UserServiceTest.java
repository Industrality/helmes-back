package com.helmesback.services;

import com.helmesback.domain.Sector;
import com.helmesback.domain.User;
import com.helmesback.repositories.SectorRepository;
import com.helmesback.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class UserServiceTest {

    @SpyBean
    UserRepository repository;

    @MockBean
    SectorService sectorService;

    @Autowired
    UserService service;

    @Test
    void getUserTest() {
        User testUser = new User();

        String testName = "Test User 1";
        testUser.setAgreedToTerms(true);
        testUser.setUserName(testName);
        User savedUser = repository.save(testUser);

        assertNotNull(savedUser);
        assertEquals(testName, testUser.getUserName());

        User serviceGetUser = service.getUser(testName);
        assertEquals(testName, serviceGetUser.getUserName());
    }

    @Test
    void saveUserTest() {
        User testUser = new User();

        String testName = "Test User 2";
        testUser.setAgreedToTerms(true);
        testUser.setUserName(testName);

        doNothing().when(sectorService).resolveUser(any());

        service.saveUser(testUser);

        User serviceGetUser = service.getUser(testName);
        assertEquals(testName, serviceGetUser.getUserName());

    }

    @Test
    void saveUserDoesNotCauseDuplicate() {
        User testUser = new User();

        String testName = "Test User 3";
        testUser.setAgreedToTerms(true);
        testUser.setUserName(testName);

        doNothing().when(sectorService).resolveUser(any());

        service.saveUser(testUser);
        testUser.setAgreedToTerms(false);

        service.saveUser(testUser);

        List<User> users = repository.findAll();
        assertEquals(1, users.size());

    }

}
