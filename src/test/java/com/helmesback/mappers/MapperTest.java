package com.helmesback.mappers;

import com.helmesback.domain.Sector;
import com.helmesback.domain.SectorDTO;
import com.helmesback.domain.User;
import com.helmesback.domain.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class MapperTest {

    Mapper mapper = new Mapper();

    @Test
    void userToUserDTOTest() {
        User testUser = new User();
        String testName = "Test User";
        testUser.setAgreedToTerms(true);
        testUser.setUserName(testName);

        Sector childSector = new Sector();
        childSector.setDescription("Child");

        Sector parentSector = new Sector();
        parentSector.setId(1L);
        parentSector.setDescription("Parent");
        parentSector.setChildren(List.of(childSector));
        childSector.setParent(parentSector);

        testUser.setSectors(List.of(parentSector));

        UserDTO dto = mapper.mapUserToUserDTO(testUser);
        assertNotNull(dto);
        assertEquals(testName, dto.getUserName());
        assertEquals(List.of(1L), dto.getSectors());
        assertEquals(true, dto.getAgreedToTerms());
    }

    @Test
    void userDTOToUserTest() {
        UserDTO testUser = new UserDTO();
        String testName = "Test User";
        testUser.setAgreedToTerms(true);
        testUser.setUserName(testName);

        testUser.setSectors(List.of(1L));

        User user = mapper.mapUserDTOToUser(testUser);
        assertNotNull(user);
        assertEquals(testName, user.getUserName());
        assertEquals(List.of(1L), user.getSectorIds());
        assertEquals(true, user.getAgreedToTerms());
    }

    @Test
    void SectorToSectorDTOTest() {
        Sector sector = new Sector();
        sector.setId(1L);
        sector.setDescription("Test");
        SectorDTO dto = mapper.sectorToSectorDTO(sector);
        assertEquals(0, dto.getDepth());
        assertEquals("Test", dto.getDescription());
        assertEquals(1L, dto.getValue());
    }

    @Test
    void flattenSectorsToSectorDtoListTest() {
        Sector sector1 = new Sector();
        sector1.setId(1L);
        sector1.setDescription("Test1");

        Sector sector2 = new Sector();
        sector2.setId(2L);
        sector2.setDescription("Test2");

        Sector sector3 = new Sector();
        sector3.setId(3L);
        sector3.setDescription("Test3");

        sector1.setChildren(List.of(sector2));
        sector2.setChildren(List.of(sector3));

        sector2.setParent(sector1);
        sector3.setParent(sector2);

        List<SectorDTO> dtos = mapper.flattenSectorsToSectorDtoList(List.of(sector1));
        assertNotNull(dtos);
        assertEquals(0, dtos.get(0).getDepth());
        assertEquals(1, dtos.get(1).getDepth());
        assertEquals(2, dtos.get(2).getDepth());
    }

}
