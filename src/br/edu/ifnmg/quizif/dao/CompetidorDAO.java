package br.edu.ifnmg.quizif.dao;

import br.edu.ifnmg.quizif.model.Competidor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author danilo
 */
public class CompetidorDAO {

    private List<Competidor> competidores = new ArrayList<>();
    
    public void salvar(Competidor competidor){
        competidores.add(competidor);
    }
    
    public List<Competidor> listar(){
        return competidores;
    }
    
}
