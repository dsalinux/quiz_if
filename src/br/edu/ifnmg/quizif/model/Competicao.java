package br.edu.ifnmg.quizif.model;

import java.util.List;

/**
 *
 * @author danilo
 */
public class Competicao {

    private String titulo;
    private int rodada;
    private List<Competidor> competidores;
    private boolean opcaoPular = true;
    private boolean opcaoUnivesitarios = true;
    private boolean opcaoPlateia = true;
    private int tempo = 5;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getRodada() {
        return rodada;
    }

    public void setRodada(int rodada) {
        this.rodada = rodada;
    }

    public List<Competidor> getCompetidores() {
        return competidores;
    }

    public void setCompetidores(List<Competidor> competidores) {
        this.competidores = competidores;
    }

    public boolean isOpcaoPular() {
        return opcaoPular;
    }

    public void setOpcaoPular(boolean opcaoPular) {
        this.opcaoPular = opcaoPular;
    }

    public boolean isOpcaoUnivesitarios() {
        return opcaoUnivesitarios;
    }

    public void setOpcaoUnivesitarios(boolean opcaoUnivesitarios) {
        this.opcaoUnivesitarios = opcaoUnivesitarios;
    }

    public boolean isOpcaoPlateia() {
        return opcaoPlateia;
    }

    public void setOpcaoPlateia(boolean opcaoPlateia) {
        this.opcaoPlateia = opcaoPlateia;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    
    
    
}
