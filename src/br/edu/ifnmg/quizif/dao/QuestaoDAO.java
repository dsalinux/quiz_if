/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.quizif.dao;

import br.edu.ifnmg.quizif.model.Questao;
import br.edu.ifnmg.quizif.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danilo
 */
public class QuestaoDAO {
    
    public void salvar(Questao questao){
        
    }
    
    public List<Questao> listar(Integer dificuldade, String materia){
        Connection connection = ConnectionUtil.getConnection();
        List<Questao> questoes = new ArrayList<Questao>();
        try {
            StringBuilder buildWhere = new StringBuilder();
            if(dificuldade != null){
                buildWhere.append(" tipo = ").append(dificuldade);
            }
            if((materia != null && !"".equals(materia))){
                buildWhere.append(" materia = ").append(materia);
            }
            if(buildWhere.length() > 0){
                buildWhere.insert(0, " where ");
            }
            PreparedStatement ps = connection.prepareStatement("select * from main.questao "+buildWhere.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Questao questao = new Questao();
                questao.setTexto(rs.getString("texto"));
                questao.setResposta1(rs.getString("resposta1"));
                questao.setResposta2(rs.getString("resposta2"));
                questao.setResposta3(rs.getString("resposta3"));
                questao.setResposta4(rs.getString("resposta4"));
                questao.setRespostaCorreta(rs.getInt("resposta_correta"));
                questao.setJaFoi(rs.getBoolean("jafoi"));
                questao.setMateria(rs.getString("materia"));
                questoes.add(questao);
            }
            return questoes;
        } catch (SQLException ex) {
            Logger.getLogger(QuestaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        } finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuestaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
