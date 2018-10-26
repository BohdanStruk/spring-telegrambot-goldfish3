package goldfish.express.service;

import goldfish.express.domain.UserDTO;



import java.util.List;

public interface UserService {
    boolean existsByPhoneNumber(String phoneNumber);
    void save(UserDTO dto);
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    UserDTO findByPhoneNumber(String phoneNumber);
    String signin(String phoneNumber, String password);
    UserDTO findByToken(String token);

   //telegram

    void createUser(UserDTO dto);
    void saveAddress(String address, Long id);
    String getAddress(Long id);
    boolean isUserPresent(Long id);
    void savePhoneNumber(String phoneNumber, Long id);
    String getPhoneNumber(Long id);
    List<UserDTO> getAll();
}
