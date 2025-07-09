package shop.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ecommerce.dto.request.ClienteRequestDTO;
import shop.ecommerce.dto.response.ClienteResponseDTO;
import shop.ecommerce.exception.DuplicatedResourceException;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.ClienteMapper;
import shop.ecommerce.model.ClienteEntity;
import shop.ecommerce.repository.ClienteRepository;
import shop.ecommerce.service.ClienteService;

import java.util.List;

import static shop.ecommerce.constants.ClienteConstants.*;

@Service
@RequiredArgsConstructor
public class ClienteServiceImp implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;


    @Override
    public ClienteResponseDTO crearCliente(ClienteRequestDTO dto) {

        if (clienteRepository.existsByDni(dto.dni())) {
            throw new DuplicatedResourceException(DNI_EXISTENTE + dto.dni());
        }

        ClienteEntity cliente = clienteMapper.toEntity(dto);

        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    public List<ClienteResponseDTO> obtenerClientes() {
        List<ClienteEntity> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(clienteMapper::toDto)
                .toList();
    }

    @Override
    public ClienteResponseDTO obtenerClientePorId(Long idCliente) {
        ClienteEntity clienteEntity = clienteRepository.findById(idCliente)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO_POR_ID + idCliente));
        return clienteMapper.toDto(clienteEntity);
    }

    @Override
    public ClienteResponseDTO obtenerClientePorDni(String dni) {
        ClienteEntity clienteEntity = clienteRepository.findByDni(dni)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO_POR_DNI + dni));
        return clienteMapper.toDto(clienteEntity);
    }

    @Override
    public ClienteResponseDTO actualizarCliente(Long idCliente, ClienteRequestDTO dto) {

        ClienteEntity clienteEntity = clienteRepository.findById(idCliente)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO_POR_ID + idCliente));

        clienteMapper.updateEntityFromDto(dto, clienteEntity);

        return clienteMapper.toDto(clienteRepository.save(clienteEntity));
    }

}