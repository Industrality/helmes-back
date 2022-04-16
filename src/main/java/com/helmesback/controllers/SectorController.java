package com.helmesback.controllers;

import com.helmesback.domain.SectorDTO;
import com.helmesback.mappers.Mapper;
import com.helmesback.services.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;
    private final Mapper mapper;

    @GetMapping
    public List<SectorDTO> getAllSectors() {
        return mapper.flattenSectorsToSectorDtoList(sectorService.getAllSectors());
    }
}
