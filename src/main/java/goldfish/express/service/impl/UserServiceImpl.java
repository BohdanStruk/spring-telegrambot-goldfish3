package goldfish.express.service.impl;

import goldfish.express.config.jwt.JwtTokenProvider;
import goldfish.express.contacts.ErrorMessages;
import goldfish.express.domain.UserDTO;
import goldfish.express.entity.UserEntity;
import goldfish.express.entity.enums.UserRole;
import goldfish.express.exceptions.UserNotFoundException;
import goldfish.express.exceptions.UserServiceException;
import goldfish.express.repository.UserRepository;
import goldfish.express.service.UserService;
import goldfish.express.service.utils.ObjectMapperUtils;
import goldfish.express.service.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapperUtils objectMapperUtils;
    @Autowired
    private StringUtils stringUtils;

    @Override
    public void save(UserDTO dto) {
        System.out.println(dto.toString());
       if(userRepository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS);
        }else {
           dto.setRole(UserRole.ROLE_USER);
           System.out.println("Password: " + dto.getPassword());
           dto.setPassword(passwordEncoder.encode(dto.getPassword()));
           System.out.println("Password: " + dto.getPassword());
           System.out.println("B" + dto.toString());

           UserEntity userEntity = objectMapperUtils.map(dto, UserEntity.class);
           System.out.println("A" + dto.toString());
           if (userEntity.getId() == null){
               userEntity.setId(stringUtils.generate());
           }
           userRepository.save(userEntity);

        }
    }

    @Override
    public UserDTO findById(Long id) {
        return null;
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return objectMapperUtils.mapAll(userEntities, UserDTO.class);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return false;
    }

    @Override
    public UserDTO findByPhoneNumber(String phoneNumber) {
        UserEntity userEntity = userRepository.findByPhoneNumber(phoneNumber);
        if (userEntity ==null){
            throw new UserNotFoundException(ErrorMessages.NO_RECORD_FOUND);
        }
        return objectMapperUtils.map(userEntity, UserDTO.class);
    }

    @Override
    public String signin(String phoneNumber, String password) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phoneNumber, password));
        return jwtTokenProvider.createToken(phoneNumber, userRepository.findByPhoneNumber(phoneNumber).getRole());
    }

    @Override
    public UserDTO findByToken(String token) {

        String phoneNumber = jwtTokenProvider.getUsername(token);

        return findByPhoneNumber(phoneNumber);
    }


    //telegram


    @Override
    public void createUser(UserDTO dto) {
        if(dto.getId() == null){
            dto.setId(Long.valueOf(stringUtils.generate()));
        }
        UserEntity userEntity = objectMapperUtils.map(dto, UserEntity.class);

        userRepository.save(userEntity);
    }

    @Override
    public void saveAddress(String address , Long id) {
        UserEntity entity = userRepository.findById(id).get();
        entity.setAddress(address);
        UserEntity userTelegramEntity = objectMapperUtils.map(entity, UserEntity.class);
        userRepository.save(userTelegramEntity);
    }

    @Override
    public String getAddress(Long id) {
        UserEntity entity = userRepository.findById(id).get();
        return entity.getAddress();
    }

    @Override
    public boolean isUserPresent(Long id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public void savePhoneNumber(String phoneNumber, Long id) {
        UserEntity userTelegramEntity = userRepository.findById(id).get();
        userTelegramEntity.setPhoneNumber(phoneNumber);
        UserEntity entity = objectMapperUtils.map(userTelegramEntity, UserEntity.class);
        userRepository.save(entity);
    }

    @Override
    public String getPhoneNumber(Long id) {
        UserEntity userTelegramEntity = userRepository.findById(id).get();
        return userTelegramEntity.getPhoneNumber();
    }

    @Override
    public List<UserDTO> getAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return objectMapperUtils.mapAll(userEntities, UserDTO.class);
    }

}
