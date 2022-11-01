package com.brody.gestiondesoperations.mapping;

import com.brody.gestiondesoperations.dto.OperationDTO;
import com.brody.gestiondesoperations.entities.Operation;


public interface Mappers {
	
	OperationDTO fromOperation(Operation operation);
	Operation fromOperationDTO(OperationDTO operation);

}
