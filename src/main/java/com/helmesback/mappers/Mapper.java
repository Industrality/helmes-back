package com.helmesback.mappers;

import com.helmesback.domain.Sector;
import com.helmesback.domain.SectorDTO;
import com.helmesback.domain.User;
import com.helmesback.domain.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public SectorDTO sectorToSectorDTO (Sector sector) {
        SectorDTO dto = new SectorDTO();
        dto.setDescription(sector.getDescription());
        dto.setValue(sector.getId());
        Long depth = 0L;
        Sector parent = sector.getParent();
        while (parent != null) {
            depth++;
            parent = parent.getParent();
        }
        dto.setDepth(depth);
        return dto;
    }

    public List<SectorDTO> flattenSectorsToSectorDtoList(List<Sector> sectors) {
        List<SectorDTO> dtos = new ArrayList<>();
        sectors.forEach(sector -> dtos.addAll(getChildrenDTOs(sector)));
        return dtos;
    }

    private List<SectorDTO> getChildrenDTOs(Sector sector) {
        List<SectorDTO> dtos = new ArrayList<>();
        dtos.add(sectorToSectorDTO(sector));
        if (!sector.getChildren().isEmpty()) {
            sector.getChildren().forEach(sector1 -> dtos.addAll(getChildrenDTOs(sector1)));
        }
        return dtos;
    }

    public User mapUserDTOToUser(UserDTO userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());

        user.setAgreedToTerms(userDto.getAgreedToTerms());
        user.setSectorIds(userDto.getSectors());
        return user;
    }

    public UserDTO mapUserToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserName(user.getUserName());
        dto.setAgreedToTerms(user.getAgreedToTerms());
        List<Long> sectorIdList = user.getSectors().stream().map(Sector::getId).collect(Collectors.toList());
        dto.setSectors(sectorIdList);
        return dto;
    }
}
