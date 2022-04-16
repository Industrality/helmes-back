package com.helmesback.services;

import com.helmesback.domain.Sector;
import com.helmesback.domain.User;
import com.helmesback.repositories.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;

    public List<Sector> getAllSectors() {
        return sectorRepository.findAllByParentId(null);
    }

    public void resolveUser(User user) {
        List<Sector> sectors = sectorRepository.findAllById(user.getSectorIds());
        user.setSectors(sectors);
    }
}
