package br.com.ebac.cadastro.dao;

import br.com.ebac.cadastro.model.Cliente;

import java.util.Collection;
import java.util.Optional;

public interface IClienteDAO {

    Cliente cadastrar(Cliente cliente);

    Optional<Cliente> consultar(Long id);

    Collection<Cliente> listar();

    boolean alterar(Cliente cliente);

    boolean excluir(Long id);
}
