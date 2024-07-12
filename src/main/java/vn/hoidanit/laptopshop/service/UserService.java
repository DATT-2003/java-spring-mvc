package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UseRepository;

@Service
public class UserService {
    private final UseRepository useRepository;

    public UserService(UseRepository useRepository) {
        this.useRepository = useRepository;
    }

    public User handalSaveUser(User user) {
        User inforuser = this.useRepository.save(user);
        System.out.println(inforuser);
        return inforuser;
    }
}
