/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.quizif.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author danilo
 */
public class IniciarQuestoes {
    

    public static List<Questao> IniciarQuestoes() {
        List<Questao> lista = new ArrayList<Questao>();
        lista.add(new Questao("QUAL É O MÍNIMO MÚLTIPLO COMUM ENTRE 2, 3 E 5?", "30", "10", "15", "2", 1, false));
        lista.add(new Questao("CERTO PRODUTO TEVE SEU PREÇO AUMENTADO DE R$ 300,00 PARA R$ 390,00. QUAL FOI A PORCENTAGEM DE AUMENTO?", "0.1", "0.2", "0.3", "0.4", 3, false));
        lista.add(new Questao("QUAL DESTES NÚMEROS É DIVISÍVEL POR 5:","50", "51", "52", "53", 1, false));
        return lista;
    }
    
}
