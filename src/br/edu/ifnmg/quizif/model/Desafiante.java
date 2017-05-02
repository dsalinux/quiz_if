package br.edu.ifnmg.quizif.model;

import java.util.Objects;

public class Desafiante {

    private Competidor competidor1;
    private Competidor competidor2;
    private Integer desafio;
    private Integer vencedor;
    private Questao questaoRespondida;

    public Competidor getCompetidor1() {
        return competidor1;
    }

    public void setCompetidor1(Competidor competidor1) {
        this.competidor1 = competidor1;
    }

    public Competidor getCompetidor2() {
        return competidor2;
    }

    public void setCompetidor2(Competidor competidor2) {
        this.competidor2 = competidor2;
    }

    public Integer getDesafio() {
        return desafio;
    }

    public void setDesafio(Integer desafio) {
        this.desafio = desafio;
    }

    public Integer getVencedor() {
        return vencedor;
    }

    public void setVencedor(Integer vencedor) {
        this.vencedor = vencedor;
    }

    public Questao getQuestaoRespondida() {
        return questaoRespondida;
    }

    public void setQuestaoRespondida(Questao questaoRespondida) {
        this.questaoRespondida = questaoRespondida;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.competidor1);
        hash = 97 * hash + Objects.hashCode(this.competidor2);
        hash = 97 * hash + Objects.hashCode(this.desafio);
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
        final Desafiante other = (Desafiante) obj;
        if (!Objects.equals(this.competidor1, other.competidor1)) {
            return false;
        }
        if (!Objects.equals(this.competidor2, other.competidor2)) {
            return false;
        }
        if (!Objects.equals(this.desafio, other.desafio)) {
            return false;
        }
        return true;
    }
    
    
    
}
