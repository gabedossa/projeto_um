package br.com.ebac.cadastro.model;

public enum Sexo {
    MASCULINO("M"),
    FEMININO("F"),
    OUTRO("O");

    private final String sigla;

    Sexo(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }

    public static Sexo fromSigla(String valor) {
        for (Sexo sexo : values()) {
            if (sexo.sigla.equalsIgnoreCase(valor.trim())) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Sexo inválido. Use M, F ou O.");
    }
}
