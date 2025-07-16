package shop.ecommerce.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import shop.ecommerce.dto.request.DireccionRequestDTO;
import shop.ecommerce.dto.response.DireccionResponseDTO;
import shop.ecommerce.model.DireccionEntity;

@Component
@RequiredArgsConstructor
public class DireccionMapper {

    private final ModelMapper modelMapper;

    public DireccionEntity toEntity(DireccionRequestDTO dto){
        return modelMapper.map(dto, DireccionEntity.class);
    }

    public DireccionResponseDTO toDto(DireccionEntity entity){
        return modelMapper.map(entity, DireccionResponseDTO.class);
    }
}
