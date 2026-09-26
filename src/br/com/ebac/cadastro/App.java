package br.com.ebac.cadastro;

import br.com.ebac.cadastro.dao.ClienteMapDAO;
import br.com.ebac.cadastro.dao.IClienteDAO;
import br.com.ebac.cadastro.model.Cliente;
import br.com.ebac.cadastro.model.Sexo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern TELEFONE = Pattern.compile("^[0-9()\\s+-]{8,20}$");

    private static final IClienteDAO dao = new ClienteMapDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Cadastro de Clientes ===");
        while (true) {
            System.out.println();
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Consultar");
            System.out.println("3 - Listar");
            System.out.println("4 - Alterar");
            System.out.println("5 - Excluir");
            System.out.println("0 - Sair");
            String opcao = ler("Opção: ");
            if (opcao == null) {
                return;
            }
            switch (opcao) {
                case "1": cadastrar(); break;
                case "2": consultar(); break;
                case "3": listar(); break;
                case "4": alterar(); break;
                case "5": excluir(); break;
                case "0":
                    System.out.println("Até logo!");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrar() {
        String nome = lerNome(null);
        LocalDate dataNasc = lerData(null);
        Sexo sexo = lerSexo(null);
        String telefone = lerTelefone(null);
        String email = lerEmail(null);
        Cliente cliente = dao.cadastrar(new Cliente(nome, dataNasc, sexo, telefone, email));
        System.out.println("Cliente cadastrado: " + cliente);
    }

    private static void consultar() {
        buscarPorId().ifPresent(System.out::println);
    }

    private static void listar() {
        Collection<Cliente> clientes = dao.listar();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        clientes.forEach(System.out::println);
    }

    private static void alterar() {
        Optional<Cliente> encontrado = buscarPorId();
        if (!encontrado.isPresent()) {
            return;
        }
        Cliente cliente = encontrado.get();
        System.out.println("Deixe em branco para manter o valor atual.");
        cliente.setNome(lerNome(cliente.getNome()));
        cliente.setDataNasc(lerData(cliente.getDataNasc()));
        cliente.setSexo(lerSexo(cliente.getSexo()));
        cliente.setTelefone(lerTelefone(cliente.getTelefone()));
        cliente.setEmail(lerEmail(cliente.getEmail()));
        dao.alterar(cliente);
        System.out.println("Cliente alterado: " + cliente);
    }

    private static void excluir() {
        Long id = lerId();
        if (id == null) {
            return;
        }
        if (dao.excluir(id)) {
            System.out.println("Cliente excluído.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static Optional<Cliente> buscarPorId() {
        Long id = lerId();
        if (id == null) {
            return Optional.empty();
        }
        Optional<Cliente> cliente = dao.consultar(id);
        if (!cliente.isPresent()) {
            System.out.println("Cliente não encontrado.");
        }
        return cliente;
    }

    private static Long lerId() {
        String valor = ler("Id do cliente: ");
        try {
            return Long.parseLong(valor);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Id inválido.");
            return null;
        }
    }

    private static String lerNome(String atual) {
        while (true) {
            String valor = lerCampo("Nome", atual);
            if (valor == null) return atual;
            if (!valor.isEmpty()) return valor;
            System.out.println("Nome é obrigatório.");
        }
    }

    private static LocalDate lerData(LocalDate atual) {
        String exibicao = atual == null ? null : atual.format(Cliente.FORMATO_DATA);
        while (true) {
            String valor = lerCampo("Data de nascimento (dd/MM/aaaa)", exibicao);
            if (valor == null) return atual;
            try {
                LocalDate data = LocalDate.parse(valor, Cliente.FORMATO_DATA);
                if (data.isAfter(LocalDate.now())) {
                    System.out.println("A data não pode estar no futuro.");
                    continue;
                }
                return data;
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida.");
            }
        }
    }

    private static Sexo lerSexo(Sexo atual) {
        while (true) {
            String valor = lerCampo("Sexo (M/F/O)", atual == null ? null : atual.getSigla());
            if (valor == null) return atual;
            try {
                return Sexo.fromSigla(valor);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String lerTelefone(String atual) {
        while (true) {
            String valor = lerCampo("Telefone", atual);
            if (valor == null) return atual;
            if (TELEFONE.matcher(valor).matches()) return valor;
            System.out.println("Telefone inválido.");
        }
    }

    private static String lerEmail(String atual) {
        while (true) {
            String valor = lerCampo("Email", atual);
            if (valor == null) return atual;
            if (EMAIL.matcher(valor).matches()) return valor;
            System.out.println("Email inválido.");
        }
    }

    private static String lerCampo(String rotulo, String atual) {
        String prompt = atual == null ? rotulo + ": " : rotulo + " [" + atual + "]: ";
        String valor = ler(prompt);
        if (valor == null) {
            throw new IllegalStateException("Entrada encerrada.");
        }
        if (valor.isEmpty() && atual != null) {
            return null;
        }
        return valor;
    }

    private static String ler(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }
}
