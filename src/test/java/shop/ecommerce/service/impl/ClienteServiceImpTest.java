package shop.ecommerce.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.ecommerce.dto.request.ClienteRequestDTO;
import shop.ecommerce.dto.response.ClienteResponseDTO;
import shop.ecommerce.exception.DuplicatedResourceException;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.ClienteMapper;
import shop.ecommerce.model.ClienteEntity;
import shop.ecommerce.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static shop.ecommerce.constants.TestConstants.CLIENTE_DNI;
import static shop.ecommerce.constants.TestConstants.CLIENTE_ID;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImpTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImp service;

    private ClienteRequestDTO requestDTO;
    private ClienteResponseDTO responseDTO;
    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        requestDTO = new ClienteRequestDTO(
                "Pedrito",
                "Jepeto",
                CLIENTE_DNI,
                "23232322",
                "Calle falsa 11"
        );

        clienteEntity = ClienteEntity.builder()
                .id(CLIENTE_ID)
                .nombre(requestDTO.nombre())
                .apellido(requestDTO.apellido())
                .dni(requestDTO.dni())
                .telefono(requestDTO.telefono())
                .direccion(requestDTO.direccion())
                .build();

        responseDTO = new ClienteResponseDTO(
                clienteEntity.getNombre(),
                clienteEntity.getApellido(),
                clienteEntity.getDni(),
                clienteEntity.getTelefono(),
                clienteEntity.getDireccion()
        );
    }

    @Test
    void crearCliente_exitosamente() {
        when(clienteRepository.existsByDni(requestDTO.dni())).thenReturn(false);
        when(clienteMapper.toEntity(requestDTO)).thenReturn(clienteEntity);
        when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
        when(clienteMapper.toDto(clienteEntity)).thenReturn(responseDTO);

        ClienteResponseDTO resultado = service.crearCliente(requestDTO);

        assertThat(resultado).isEqualTo(responseDTO);

        verify(clienteRepository).existsByDni(requestDTO.dni());
        verify(clienteRepository).save(clienteEntity);
        verify(clienteMapper).toDto(clienteEntity);
        System.out.println(resultado);

    }

    @Test
    void crearCliente_conDniDuplicado_LanzaException() {
        when(clienteRepository.existsByDni(requestDTO.dni())).thenReturn(true);

        assertThatExceptionOfType(DuplicatedResourceException.class)
                .isThrownBy(() -> service.crearCliente(requestDTO));

        verify(clienteRepository).existsByDni(requestDTO.dni());
        verify(clienteRepository, never()).save(any());
        verify(clienteMapper, never()).toDto(any());

    }

    @Test
    void obtenerTodosLosClientes() {
        List<ClienteEntity> productos = List.of(clienteEntity);
        when(clienteRepository.findAll()).thenReturn(productos);
        when(clienteMapper.toDto(clienteEntity)).thenReturn(responseDTO);

        List<ClienteResponseDTO> resultado = service.obtenerClientes();

        Assertions.assertThat(resultado).hasSize(1);
        Assertions.assertThat(resultado.get(0)).isEqualTo(responseDTO);
        verify(clienteRepository).findAll();
        System.out.println(resultado);
    }

    @Test
    void obtenerClientePorId_Correctamente() {
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(clienteEntity));
        when(clienteMapper.toDto(clienteEntity)).thenReturn(responseDTO);

        ClienteResponseDTO resultado = service.obtenerClientePorId(CLIENTE_ID);

        Assertions.assertThat(resultado).isEqualTo(responseDTO);

        verify(clienteRepository).findById(CLIENTE_ID);

        System.out.println(resultado);
    }

    @Test
    void obtenerClientePorId_NoExistente_LanzaException() {
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.obtenerClientePorId(CLIENTE_ID));

        verify(clienteRepository).findById(CLIENTE_ID);

        verify(clienteMapper, never()).toDto(clienteEntity);

    }

    @Test
    void obtenerClientePorDni_Correctamente() {
        when(clienteRepository.findByDni(CLIENTE_DNI)).thenReturn(Optional.of(clienteEntity));
        when(clienteMapper.toDto(clienteEntity)).thenReturn(responseDTO);

        ClienteResponseDTO resultado = service.obtenerClientePorDni(CLIENTE_DNI);

        Assertions.assertThat(resultado).isEqualTo(responseDTO);

        verify(clienteRepository).findByDni(CLIENTE_DNI);

        System.out.println(resultado);
    }

    @Test
    void obtenerClientePorDni_NoExistente_LanzaException() {
        when(clienteRepository.findByDni(CLIENTE_DNI)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.obtenerClientePorDni(CLIENTE_DNI));

        verify(clienteRepository).findByDni(CLIENTE_DNI);

        verify(clienteMapper, never()).toDto(clienteEntity);
    }

    @Test
    void actualizarCliente_Correctamente() {
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(clienteEntity));
        doNothing().when(clienteMapper).updateEntityFromDto(requestDTO, clienteEntity);
        when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
        when(clienteMapper.toDto(clienteEntity)).thenReturn(responseDTO);

        ClienteResponseDTO resultado = service.actualizarCliente(CLIENTE_ID, requestDTO);

        assertThat(resultado).isEqualTo(responseDTO);
    }

    @Test
    void actualizarCliente_NoExisteElId_LanzaException() {
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(()-> service.actualizarCliente(CLIENTE_ID, requestDTO));

        verify(clienteRepository, never()).save(any());
        verify(clienteMapper, never()).updateEntityFromDto(any(), any());

    }

}
