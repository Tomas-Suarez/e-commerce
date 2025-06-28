package shop.ecommerce.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import shop.ecommerce.dto.request.ClienteRequestDTO;
import shop.ecommerce.dto.response.ClienteResponseDTO;
import shop.ecommerce.model.ClienteEntity;

@Component
@RequiredArgsConstructor
public class ClienteMapper {

    private final ModelMapper modelMapper;

    public ClienteEntity toEntity(ClienteRequestDTO dto){
        return modelMapper.map(dto, ClienteEntity.class);

    }

    public ClienteResponseDTO toDto(ClienteEntity entity){
        return modelMapper.map(entity, ClienteResponseDTO.class);
    }

    public void updateEntityFromDto(ClienteRequestDTO dto, ClienteEntity entity){
        modelMapper.map(dto, entity);
    }
}
