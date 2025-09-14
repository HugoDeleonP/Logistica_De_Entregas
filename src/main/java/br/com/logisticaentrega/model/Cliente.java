package br.com.logisticaentrega.model;

public class Cliente {

    private Integer id;
    private String nome;
    private String cpf_cnpj;
    private String endereco;
    private String cidade;
    private String estado;

    public Cliente(Integer id, String nome, String cpf_cnpj, String endereco, String cidade, String estado) {
        this.id = id;
        this.nome = nome;
        this.cpf_cnpj = cpf_cnpj;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Cliente(String nome, String cpf_cnpj, String endereco, String cidade, String estado) {
        this.nome = nome;
        this.cpf_cnpj = cpf_cnpj;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Cliente(Integer id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String toString() {
        return "=========================| Cliente |=========================\n" +
                "ID: " + this.id +
                "\nNome: " + this.nome +
                "\nCPF/CNPJ: " + this.cpf_cnpj +
                "\nEndereço: " + this.endereco +
                "\nCidade: " + this.cidade +
                "\nEstado: " + this.estado + "\n";
    }
}
