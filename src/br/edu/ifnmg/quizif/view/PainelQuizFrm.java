/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.quizif.view;

import br.edu.ifnmg.quizif.model.Competidor;
import br.edu.ifnmg.quizif.model.Questao;
import br.edu.ifnmg.quizif.util.AudioUtil;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author danilo
 */
public class PainelQuizFrm extends javax.swing.JFrame {

    private Questao questaoSelecionada = null;
    private Integer tempo = null;
    private boolean pausarTempo = true;
    private Color corResposta = new Color(83, 168, 254);

    public PainelQuizFrm() {
        initComponents();
        limpaTela();
        pbTempo.setVisible(false);
    }

    public void configurarTempo(int tempo) {
        pbTempo.setString("Tempo Restante (" + tempo + "s)");
        this.tempo = tempo * 1000;
        pbTempo.setMaximum(this.tempo);
        pbTempo.setValue(this.tempo);
        pbTempo.setVisible(true);
    }

    public void mostrarResposta(boolean correta, int respostaCorreta) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    AudioUtil.reproduzir(AudioUtil.SUSPENSE);
                try {
                    Thread.sleep(5700);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PainelQuizFrm.class.getName()).log(Level.SEVERE, null, ex);
                }
                Color cor = Color.RED;
                if (correta) {
                    cor = Color.GREEN;
                }
                Color corOriginal = null;
                if(correta){
                    AudioUtil.reproduzir(AudioUtil.SUCCESS);
                } else {
                    AudioUtil.reproduzir(AudioUtil.FAIL);
                }
                for (int i = 0; i < 30; i++) {
                    switch (respostaCorreta) {
                        case 1:
                            corOriginal = corOriginal == null ? lblResposta1.getBackground() : corOriginal;
                            lblResposta1.setBackground(i % 2 == 0 ? cor : corOriginal);
                            lblResposta1.repaint();
                            break;
                        case 2:
                            corOriginal = corOriginal == null ? lblResposta2.getBackground() : corOriginal;
                            lblResposta2.setBackground(i % 2 == 0 ? cor : corOriginal);
                            lblResposta2.repaint();
                            break;
                        case 3:
                            corOriginal = corOriginal == null ? lblResposta3.getBackground() : corOriginal;
                            lblResposta3.setBackground(i % 2 == 0 ? cor : corOriginal);
                            lblResposta3.repaint();
                            break;
                        case 4:
                            corOriginal = corOriginal == null ? lblResposta4.getBackground() : corOriginal;
                            lblResposta4.setBackground(i % 2 == 0 ? cor : corOriginal);
                            lblResposta4.repaint();
                            break;
                        default:
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PainelQuizFrm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    public void iniciarTempo() {
        this.pausarTempo = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = tempo; i >= 0; i--) {
                    if (!pausarTempo) {
                        pbTempo.setValue(i);
                        if ((new Float(i) / 1000) == (i / 1000) && i > 0) {
                            pbTempo.setString("Tempo Restante (" + i / 1000 + "s)");
                            AudioUtil.reproduzir(AudioUtil.TICK_TOCK);
                        } else if(i == 0){
                            AudioUtil.reproduzir(AudioUtil.COMPLETE_TIME);
                        }
                        pbTempo.repaint();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GerenciarQuizFrm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        tempo++;
                    }
                }
                pausarTempo = true;
            }
        }).start();

    }

    public void atualzarCompetidores(Competidor competidor1, Competidor competidor2){
        String pacote = "/br/edu/ifnmg/quizif/resources/image/";
        if(competidor1 != null){
            lblParticipante1.setText(competidor1.getNome());
            lblEquipe1.setText(competidor1.getEquipe());
            if(competidor1.isPulou()){
                lblPularCompetidor1.setIcon(new ImageIcon(getClass().getResource(pacote+"pular-off.png")));
            } else {
                lblPularCompetidor1.setIcon(new ImageIcon(getClass().getResource(pacote+"pular-on.png")));
            }
            if(competidor1.isAjudadoPlateia()){
                lblPlateiaCompetidor1.setIcon(new ImageIcon(getClass().getResource(pacote+"plateia-off.png")));
            } else {
                lblPlateiaCompetidor1.setIcon(new ImageIcon(getClass().getResource(pacote+"plateia-on.png")));
            }
            if(competidor1.isAjudadoUniversitarios()){
                lblUniversitariosCompetidor1.setIcon(new ImageIcon(getClass().getResource(pacote+"universitarios-off.png")));
            } else {
                lblUniversitariosCompetidor1.setIcon(new ImageIcon(getClass().getResource(pacote+"universitarios-on.png")));
            }
        }
        if(competidor2 != null){
            lblParticipante2.setText(competidor2.getNome());
            lblEquipe2.setText(competidor2.getEquipe());
            if(competidor2.isPulou()){
                lblPularCompetidor2.setIcon(new ImageIcon(getClass().getResource(pacote+"pular-off.png")));
            } else {
                lblPularCompetidor2.setIcon(new ImageIcon(getClass().getResource(pacote+"pular-on.png")));
            }
            if(competidor2.isAjudadoPlateia()){
                lblPlateiaCompetidor2.setIcon(new ImageIcon(getClass().getResource(pacote+"plateia-off.png")));
            } else {
                lblPlateiaCompetidor2.setIcon(new ImageIcon(getClass().getResource(pacote+"plateia-on.png")));
            }
            if(competidor2.isAjudadoUniversitarios()){
                lblUniversitariosCompetidor2.setIcon(new ImageIcon(getClass().getResource(pacote+"universitarios-off.png")));
            } else {
                lblUniversitariosCompetidor2.setIcon(new ImageIcon(getClass().getResource(pacote+"universitarios-on.png")));
            }
        }
    }
    
    public void setQuestaoSelecionada(Questao questaoSelecionada) {
        this.questaoSelecionada = questaoSelecionada;
        AudioUtil.reproduzir(AudioUtil.ABERTURA_PERGUNTA);
        atualizaTela();
    }

    public boolean isProjetandoQuestao() {
        return questaoSelecionada != null;
    }

    public void resetRespostaSelecionada() {
        lblResposta1.setBackground(new Color(255, 255, 255));
        lblResposta2.setBackground(new Color(255, 255, 255));
        lblResposta3.setBackground(new Color(255, 255, 255));
        lblResposta4.setBackground(new Color(255, 255, 255));
    }

    public void selecionarResposta(Integer resposta) {
        AudioUtil.reproduzir(AudioUtil.SELECIONAR_RESPOSTA);
        resetRespostaSelecionada();
        if (resposta == null) {
            return;
        }
        switch (resposta) {
            case 1:
                lblResposta1.setBackground(Color.ORANGE);
                break;
            case 2:
                lblResposta2.setBackground(Color.ORANGE);
                break;
            case 3:
                lblResposta3.setBackground(Color.ORANGE);
                break;
            case 4:
                lblResposta4.setBackground(Color.ORANGE);
                break;
            default:
                throw new AssertionError();
        }
    }

    private void limpaTela() {
        lblQuestao.setText("");
        lblResposta1.setText("");
        lblResposta2.setText("");
        lblResposta3.setText("");
        lblResposta4.setText("");
    }

    private void atualizaTela() {
        StringBuilder textoQuestao = new StringBuilder();
        textoQuestao.append("<html><p \\\"line-height:1\\\" align=\\\"JUSTIFY\\\"><b>");
        textoQuestao.append(questaoSelecionada.getTexto());
        textoQuestao.append("</b></p></html>");
        lblQuestao.setText(textoQuestao.toString());
        lblResposta1.setText("<html><p \\\"line-height:1\\\" align=\\\"JUSTIFY\\\"><b>A) " + questaoSelecionada.getResposta1() + "</b></p></html>");
        lblResposta2.setText("<html><p \\\"line-height:1\\\" align=\\\"JUSTIFY\\\"><b>B) " + questaoSelecionada.getResposta2() + "</b></p></html>");
        lblResposta3.setText("<html><p \\\"line-height:1\\\" align=\\\"JUSTIFY\\\"><b>C) " + questaoSelecionada.getResposta3() + "</b></p></html>");
        lblResposta4.setText("<html><p \\\"line-height:1\\\" align=\\\"JUSTIFY\\\"><b>D) " + questaoSelecionada.getResposta4() + "</b></p></html>");
    }

    public boolean isPausarTempo() {
        return pausarTempo;
    }

    public void setPausarTempo(boolean pausarTempo) {
        this.pausarTempo = pausarTempo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        pbTempo = new javax.swing.JProgressBar();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblParticipante1 = new javax.swing.JLabel();
        lblPularCompetidor1 = new javax.swing.JLabel();
        lblPlateiaCompetidor1 = new javax.swing.JLabel();
        lblUniversitariosCompetidor1 = new javax.swing.JLabel();
        lblEquipe1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblParticipante2 = new javax.swing.JLabel();
        lblPularCompetidor2 = new javax.swing.JLabel();
        lblPlateiaCompetidor2 = new javax.swing.JLabel();
        lblUniversitariosCompetidor2 = new javax.swing.JLabel();
        lblEquipe2 = new javax.swing.JLabel();
        lblResposta4 = new javax.swing.JLabel();
        lblQuestao = new javax.swing.JLabel();
        lblResposta1 = new javax.swing.JLabel();
        lblResposta3 = new javax.swing.JLabel();
        lblResposta2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Quiz IF");
        setBackground(new java.awt.Color(75, 194, 234));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        pbTempo.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        pbTempo.setValue(50);
        pbTempo.setString("Tempo Restante (5s)");
        pbTempo.setStringPainted(true);

        jPanel5.setBackground(java.awt.Color.lightGray);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/dia-matematica.jpg"))); // NOI18N

        jPanel1.setBackground(java.awt.Color.lightGray);

        lblParticipante1.setFont(new java.awt.Font("Ubuntu", 0, 28)); // NOI18N
        lblParticipante1.setText("Participante 1");

        lblPularCompetidor1.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblPularCompetidor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPularCompetidor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/pular-on.png"))); // NOI18N
        lblPularCompetidor1.setText("Pular");
        lblPularCompetidor1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPularCompetidor1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblPlateiaCompetidor1.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblPlateiaCompetidor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPlateiaCompetidor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/plateia-on.png"))); // NOI18N
        lblPlateiaCompetidor1.setText("Platéia");
        lblPlateiaCompetidor1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPlateiaCompetidor1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblUniversitariosCompetidor1.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblUniversitariosCompetidor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUniversitariosCompetidor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/universitarios-on.png"))); // NOI18N
        lblUniversitariosCompetidor1.setText("Universitários");
        lblUniversitariosCompetidor1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUniversitariosCompetidor1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblEquipe1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblEquipe1.setText("Equipe");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblParticipante1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPularCompetidor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPlateiaCompetidor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUniversitariosCompetidor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEquipe1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblParticipante1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEquipe1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPularCompetidor1)
                    .addComponent(lblPlateiaCompetidor1)
                    .addComponent(lblUniversitariosCompetidor1))
                .addContainerGap())
        );

        jPanel7.setBackground(java.awt.Color.lightGray);

        lblParticipante2.setFont(new java.awt.Font("Ubuntu", 0, 28)); // NOI18N
        lblParticipante2.setText("Participante 1");

        lblPularCompetidor2.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblPularCompetidor2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPularCompetidor2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/pular-on.png"))); // NOI18N
        lblPularCompetidor2.setText("Pular");
        lblPularCompetidor2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPularCompetidor2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblPlateiaCompetidor2.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblPlateiaCompetidor2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPlateiaCompetidor2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/plateia-on.png"))); // NOI18N
        lblPlateiaCompetidor2.setText("Platéia");
        lblPlateiaCompetidor2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPlateiaCompetidor2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblUniversitariosCompetidor2.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblUniversitariosCompetidor2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUniversitariosCompetidor2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/universitarios-on.png"))); // NOI18N
        lblUniversitariosCompetidor2.setText("Universitários");
        lblUniversitariosCompetidor2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUniversitariosCompetidor2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblEquipe2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblEquipe2.setText("Equipe");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblParticipante2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lblPularCompetidor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPlateiaCompetidor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUniversitariosCompetidor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblEquipe2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblParticipante2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEquipe2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPularCompetidor2)
                    .addComponent(lblPlateiaCompetidor2)
                    .addComponent(lblUniversitariosCompetidor2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblResposta4.setBackground(new java.awt.Color(254, 254, 254));
        lblResposta4.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        lblResposta4.setText("jLabel2");
        lblResposta4.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true), javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1)));
        lblResposta4.setOpaque(true);

        lblQuestao.setBackground(new java.awt.Color(172, 255, 202));
        lblQuestao.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        lblQuestao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuestao.setText("<html><p \\\"line-height:1\\\" align=\\\"JUSTIFY\\\">Qual a cor do cavalo brando de napoleão?<b><font size=\\\"70px\\\">João</font></b></p></html>");
        lblQuestao.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblQuestao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblResposta1.setBackground(new java.awt.Color(254, 254, 254));
        lblResposta1.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        lblResposta1.setText("jLabel2");
        lblResposta1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true), javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1)));
        lblResposta1.setOpaque(true);

        lblResposta3.setBackground(new java.awt.Color(254, 254, 254));
        lblResposta3.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        lblResposta3.setText("jLabel2");
        lblResposta3.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true), javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1)));
        lblResposta3.setOpaque(true);

        lblResposta2.setBackground(new java.awt.Color(254, 254, 254));
        lblResposta2.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        lblResposta2.setText("jLabel2");
        lblResposta2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true), javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1)));
        lblResposta2.setOpaque(true);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/logo_ifnmg.png"))); // NOI18N

        jPanel4.setLayout(null);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblQuestao, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(lblResposta3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblResposta2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblResposta1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblResposta4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pbTempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblQuestao, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblResposta1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblResposta2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblResposta3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblResposta4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(pbTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblResposta1, lblResposta2, lblResposta3, lblResposta4});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblEquipe1;
    private javax.swing.JLabel lblEquipe2;
    private javax.swing.JLabel lblParticipante1;
    private javax.swing.JLabel lblParticipante2;
    private javax.swing.JLabel lblPlateiaCompetidor1;
    private javax.swing.JLabel lblPlateiaCompetidor2;
    private javax.swing.JLabel lblPularCompetidor1;
    private javax.swing.JLabel lblPularCompetidor2;
    private javax.swing.JLabel lblQuestao;
    private javax.swing.JLabel lblResposta1;
    private javax.swing.JLabel lblResposta2;
    private javax.swing.JLabel lblResposta3;
    private javax.swing.JLabel lblResposta4;
    private javax.swing.JLabel lblUniversitariosCompetidor1;
    private javax.swing.JLabel lblUniversitariosCompetidor2;
    private javax.swing.JProgressBar pbTempo;
    // End of variables declaration//GEN-END:variables
}
