package br.com.ebac.cadastro.dao;

import br.com.ebac.cadastro.model.Cliente;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ClienteMapDAO implements IClienteDAO {

    private final Map<Long, Cliente> clientes = new LinkedHashMap<>();
    private long proximoId = 1;

    @Override
    public Cliente cadastrar(Cliente cliente) {
        cliente.setId(proximoId++);
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> consultar(Long id) {
        return Optional.ofNullable(clientes.get(id));
    }

    @Override
    public Collection<Cliente> listar() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public boolean alterar(Cliente cliente) {
        if (cliente.getId() == null || !clientes.containsKey(cliente.getId())) {
            return false;
        }
        clientes.put(cliente.getId(), cliente);
        return true;
    }

    @Override
    public boolean excluir(Long id) {
        return clientes.remove(id) != null;
    }
}
