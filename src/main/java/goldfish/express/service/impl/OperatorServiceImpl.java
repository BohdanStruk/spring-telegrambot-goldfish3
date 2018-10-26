package goldfish.express.service.impl;

import goldfish.express.domain.OperatorDTO;
import goldfish.express.entity.OperatorTelegramEntity;
import goldfish.express.repository.OperatorRepository;
import goldfish.express.service.OperatorService;
import goldfish.express.service.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private ObjectMapperUtils modelMapper;

    @Override
    public void saveOperator(OperatorDTO dto) {
        OperatorTelegramEntity operatorEntity = modelMapper.map(dto, OperatorTelegramEntity.class);
        operatorRepository.save(operatorEntity);

    }

    @Override
    public List<Long> findAllOperators() {
        List<OperatorTelegramEntity> operatorEntities = operatorRepository.findAll();
        List<OperatorDTO> operatorDTOS = modelMapper.mapAll(operatorEntities, OperatorDTO.class);
        List<Long> ids = new ArrayList<Long>();
        for(OperatorDTO operatorDTO: operatorDTOS){
            ids.add(operatorDTO.getId());
        }
        return ids;
    }

    @Override
    public void deleteOperator(Long id) {
        operatorRepository.deleteById(id);
    }
}
