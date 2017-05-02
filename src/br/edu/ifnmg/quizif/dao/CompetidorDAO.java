package br.edu.ifnmg.quizif.dao;

import br.edu.ifnmg.quizif.model.Competidor;
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
public class CompetidorDAO {

    public void salvar(Competidor competidor){
            Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement ps;
            if(competidor.getId() == null){
                ps = connection.prepareStatement("insert into competidor (nome, equipe) values (?,?)");
            } else {
                ps = connection.prepareStatement("update competidor set nome = ?, equipe = ? where id = ?");
                ps.setInt(3, competidor.getId());
            }
            ps.setString(1, competidor.getNome());
            ps.setString(2, competidor.getEquipe());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CompetidorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuestaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        
    }
    
    public List<Competidor> listar(){
        List<Competidor> competidores = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            PreparedStatement ps = connection.prepareStatement("select * from main.competidor");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competidor competidor = new Competidor();
                competidor.setId(rs.getInt("id"));
                competidor.setNome(rs.getString("nome"));
                competidor.setEquipe(rs.getString("equipe"));
                competidores.add(competidor);
            }
            return competidores;
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
