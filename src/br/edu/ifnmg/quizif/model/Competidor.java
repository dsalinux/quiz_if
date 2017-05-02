package br.edu.ifnmg.quizif.model;

import java.util.Objects;

/**
 *
 * @author danilo
 */
public class Competidor {

    private Integer id;
    private String nome;
    private String equipe;
    private boolean pulou;
    private boolean ajudadoUniversitarios;
    private boolean ajudadoPlateia;

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

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public boolean isPulou() {
        return pulou;
    }

    public void setPulou(Boolean pulou) {
        this.pulou = pulou;
    }

    public boolean isAjudadoUniversitarios() {
        return ajudadoUniversitarios;
    }

    public void setAjudadoUniversitarios(Boolean ajudadoUniversitarios) {
        this.ajudadoUniversitarios = ajudadoUniversitarios;
    }

    public boolean isAjudadoPlateia() {
        return ajudadoPlateia;
    }

    public void setAjudadoPlateia(Boolean ajudadoPlateia) {
        this.ajudadoPlateia = ajudadoPlateia;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Competidor other = (Competidor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }

}
