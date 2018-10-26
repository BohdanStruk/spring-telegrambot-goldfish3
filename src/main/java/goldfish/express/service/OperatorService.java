package goldfish.express.service;

import goldfish.express.domain.OperatorDTO;

import java.util.List;

public interface OperatorService {
    void saveOperator(OperatorDTO dto);
    List<Long> findAllOperators();
    void deleteOperator(Long id);
}
