package br.edu.ifnmg.quizif;

import br.edu.ifnmg.quizif.view.GerenciarQuizFrm;
import br.edu.ifnmg.quizif.view.PainelQuizFrm;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Metal".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PainelQuizFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PainelQuizFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PainelQuizFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PainelQuizFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        new GerenciarQuizFrm().setVisible(true);
    }

}
