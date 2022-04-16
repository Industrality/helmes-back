package com.helmesback.services;

import com.helmesback.domain.User;
import com.helmesback.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SectorService sectorService;

    public User getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void saveUser(User user) {
        User preExisting = userRepository.findByUserName(user.getUserName());
        if (preExisting != null) {
            preExisting.setSectorIds(user.getSectorIds());
            preExisting.setAgreedToTerms(user.getAgreedToTerms());
            sectorService.resolveUser(preExisting);
            userRepository.save(preExisting);
        } else {
            sectorService.resolveUser(user);
            userRepository.save(user);
        }
    }
}
