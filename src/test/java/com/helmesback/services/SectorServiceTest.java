package com.helmesback.services;

import com.helmesback.domain.Sector;
import com.helmesback.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SectorServiceTest {

    @Autowired
    SectorService service;

    @Test
    void getSectorsTest() {
        List<Sector> sectors = service.getAllSectors();

        assertNotNull(sectors);
        assertEquals(3, sectors.size());
    }

    @Test
    void resolveUserTest() {
        User user = new User();
        user.setSectorIds(List.of(1L,2L,3L));

        service.resolveUser(user);

        assertEquals(3, user.getSectors().size());
        assertTrue(user.getSectors().stream().anyMatch(it -> it.getDescription().equals("Service")));
    }
}
