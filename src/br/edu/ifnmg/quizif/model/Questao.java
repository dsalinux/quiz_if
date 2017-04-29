package br.edu.ifnmg.quizif.model;

import java.util.Objects;

public class Questao {

    private String texto;
    private String resposta1;
    private String resposta2;
    private String resposta3;
    private String resposta4;
    private Integer respostaCorreta;
    private String materia;
    private boolean jaFoi;

    public Questao(String texto, String resposta1, String resposta2, String resposta3, String resposta4, Integer respostaCorreta, boolean jaFoi) {
        this.texto = texto;
        this.resposta1 = resposta1;
        this.resposta2 = resposta2;
        this.resposta3 = resposta3;
        this.resposta4 = resposta4;
        this.respostaCorreta = respostaCorreta;
        this.jaFoi = jaFoi;
    }

    public Questao() {
        
    }
    
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getResposta1() {
        return resposta1;
    }

    public void setResposta1(String resposta1) {
        this.resposta1 = resposta1;
    }

    public String getResposta2() {
        return resposta2;
    }

    public void setResposta2(String resposta2) {
        this.resposta2 = resposta2;
    }

    public String getResposta3() {
        return resposta3;
    }

    public void setResposta3(String resposta3) {
        this.resposta3 = resposta3;
    }

    public String getResposta4() {
        return resposta4;
    }

    public void setResposta4(String resposta4) {
        this.resposta4 = resposta4;
    }

    public Integer getRespostaCorreta() {
        return respostaCorreta;
    }

    public void setRespostaCorreta(Integer respostaCorreta) {
        this.respostaCorreta = respostaCorreta;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public boolean isJaFoi() {
        return jaFoi;
    }

    public void setJaFoi(boolean jaFoi) {
        this.jaFoi = jaFoi;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.texto);
        hash = 59 * hash + Objects.hashCode(this.resposta1);
        hash = 59 * hash + Objects.hashCode(this.resposta2);
        hash = 59 * hash + Objects.hashCode(this.resposta3);
        hash = 59 * hash + Objects.hashCode(this.resposta4);
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
        final Questao other = (Questao) obj;
        if (!Objects.equals(this.texto, other.texto)) {
            return false;
        }
        if (!Objects.equals(this.resposta1, other.resposta1)) {
            return false;
        }
        if (!Objects.equals(this.resposta2, other.resposta2)) {
            return false;
        }
        if (!Objects.equals(this.resposta3, other.resposta3)) {
            return false;
        }
        if (!Objects.equals(this.resposta4, other.resposta4)) {
            return false;
        }
        return true;
    }
    
    
    
}
