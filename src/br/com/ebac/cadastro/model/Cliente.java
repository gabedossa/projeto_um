package br.com.ebac.cadastro.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Objects;

public class Cliente {

    public static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private Long id;
    private String nome;
    private LocalDate dataNasc;
    private Sexo sexo;
    private String telefone;
    private String email;

    public Cliente(String nome, LocalDate dataNasc, Sexo sexo, String telefone, String email) {
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        this.telefone = telefone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente outro = (Cliente) o;
        return id != null && id.equals(outro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | Nasc: %s | Sexo: %s | Tel: %s | Email: %s",
                id, nome, dataNasc.format(FORMATO_DATA), sexo, telefone, email);
    }
}
