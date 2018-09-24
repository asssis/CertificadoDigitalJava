/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
 
import java.math.BigInteger;
import java.util.Date;


/**
 *
 * @author 91282071220
 */
public class Certificado {

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the cpf_cnpj
     */
    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    /**
     * @param cpf_cnpj the cpf_cnpj to set
     */
    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    /**
     * @return the data_inicio
     */
    public Date getData_inicio() {
        return data_inicio;
    }

    /**
     * @param data_inicio the data_inicio to set
     */
    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    /**
     * @return the data_final
     */
    public Date getData_final() {
        return data_final;
    }

    /**
     * @param data_final the data_final to set
     */
    public void setData_final(Date data_final) {
        this.data_final = data_final;
    }

    /**
     * @return the serial
     */
    public BigInteger getSerial() {
        return serial;
    }

    /**
     * @param serial the serial to set
     */
    public void setSerial(BigInteger serial) {
        this.serial = serial;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the versao
     */
    public int getVersao() {
        return versao;
    }

    /**
     * @param versao the versao to set
     */
    public void setVersao(int versao) {
        this.versao = versao;
    }
    private String tipo;
    private String cpf_cnpj;
    private Date data_inicio;
    private Date data_final;
    private BigInteger serial;
    private String nome; 
    private int versao;

}
