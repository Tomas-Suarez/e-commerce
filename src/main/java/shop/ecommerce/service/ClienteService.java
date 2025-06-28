package shop.ecommerce.service;

import shop.ecommerce.dto.request.ClienteRequestDTO;
import shop.ecommerce.dto.response.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {

    ClienteResponseDTO crearCliente(ClienteRequestDTO dto);

    List<ClienteResponseDTO> obtenerClientes();

    ClienteResponseDTO obtenerClientePorId(Long idCliente);

    ClienteResponseDTO obtenerClientePorDni(String dni);

    ClienteResponseDTO actualizarCliente(Long idCliente, ClienteRequestDTO dto);

}
