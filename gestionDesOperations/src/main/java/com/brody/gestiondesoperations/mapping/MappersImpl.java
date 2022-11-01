package com.brody.gestiondesoperations.mapping;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.brody.gestiondesoperations.dto.OperationDTO;
import com.brody.gestiondesoperations.entities.Operation;

@Service
public class MappersImpl implements  Mappers {

	@Override
	public OperationDTO fromOperation(Operation operation) {
		try {
			OperationDTO operationDTO = new OperationDTO();
			BeanUtils.copyProperties(operation, operationDTO);
			return operationDTO;
		}catch(Exception e) {
			return null;
		}	
	}

	@Override
	public Operation fromOperationDTO(OperationDTO operationDTO) {
		try {
			Operation operation = new Operation();
			BeanUtils.copyProperties(operationDTO, operation);
			return operation;
		}catch(Exception e) {
			return null;
		}
	}

}
