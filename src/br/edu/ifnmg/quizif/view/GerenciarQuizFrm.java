/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.quizif.view;

import br.edu.ifnmg.quizif.dao.CompetidorDAO;
import br.edu.ifnmg.quizif.dao.QuestaoDAO;
import br.edu.ifnmg.quizif.model.Competicao;
import br.edu.ifnmg.quizif.model.Competidor;
import br.edu.ifnmg.quizif.model.IniciarQuestoes;
import br.edu.ifnmg.quizif.model.Questao;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author danilo
 */
public class GerenciarQuizFrm extends javax.swing.JFrame {

    private PainelQuizFrm painelQuizFrm = new PainelQuizFrm();
    private GraphicsDevice deviceSelected;
    private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice[] gs = ge.getScreenDevices();
    private Competicao competicao = new Competicao();
    private List<Questao> questoes = new ArrayList<>();
    private List<Competidor> competidores = new ArrayList<>();
    private Questao questaoSorteada = null;
    private Integer respostaSelecionada = null;
    private Competidor competidor1;
    private Competidor competidor2;

    /**
     * Creates new form ConfiguracaoQuizFrm
     */
    public GerenciarQuizFrm() {
        initComponents();
        iniciarConfiguracao();
        inicarWindowListener();
        setExtendedState(MAXIMIZED_BOTH);
        verificarAbaPainelQuiz();
        btnEnviarPergunta.setEnabled(false);
        verificarPainelCompetidor1();
        verificarPainelCompetidor2();

    }

    public void inicarComboboxCompetidores() {
        DefaultComboBoxModel<String> modelCompetidores1 = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> modelCompetidores2 = new DefaultComboBoxModel<>();
        modelCompetidores1.addElement("Selecione");
        modelCompetidores2.addElement("Selecione");
        for (Competidor competidor : competidores) {
            modelCompetidores1.addElement(competidor.getNome());
            modelCompetidores2.addElement(competidor.getNome());
        }
        cbxCompetidores1.setModel(modelCompetidores1);
        cbxCompetidores2.setModel(modelCompetidores2);
        cbxCompetidores1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                for (Competidor competidor : competidores) {
                    if(cbxCompetidores1.getSelectedItem().toString().endsWith(competidor.getNome())){
                        competidor1 = competidor;
                    }
                }
                verificarPainelCompetidor1();
                painelQuizFrm.atualzarCompetidores(competidor1, competidor2);
            }
        });
        cbxCompetidores2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                for (Competidor competidor : competidores) {
                    if(cbxCompetidores2.getSelectedItem().toString().endsWith(competidor.getNome())){
                        competidor2 = competidor;
                    }
                }
                verificarPainelCompetidor2();
                painelQuizFrm.atualzarCompetidores(competidor1, competidor2);
            }
        });
    }

    private void verificarAbaPainelQuiz() {
        tbGerenciarQuiz.setEnabledAt(1, false);
        tbGerenciarQuiz.setToolTipTextAt(1, "Seleciona tempo > 2, busque as questões e competidores.");
        if (competicao.getTempo() < 2) {
            return;
        }
        if (questoes == null || questoes.isEmpty()) {
            return;
        }
        if (competidores == null || competidores.isEmpty()) {
            return;
        }
        tbGerenciarQuiz.setToolTipTextAt(1, "");
        tbGerenciarQuiz.setEnabledAt(1, true);
    }

    public void verificarPainelCompetidor1() {
        if (cbxCompetidores1.getSelectedItem() != null && !cbxCompetidores1.getSelectedItem().toString().equals("Selecione")) {
            chbPularCompetidor1.setEnabled(competicao.isOpcaoPular());
            chbPlateiaCompetidor1.setEnabled(competicao.isOpcaoPlateia());
            chbUniversitariosCompetidor1.setEnabled(competicao.isOpcaoUnivesitarios());
            btnConferirCompetidor1.setEnabled(true);
        } else {
            chbPularCompetidor1.setEnabled(false);
            chbPlateiaCompetidor1.setEnabled(false);
            chbUniversitariosCompetidor1.setEnabled(false);
            btnConferirCompetidor1.setEnabled(false);
        }
    }

    public void verificarPainelCompetidor2() {
        if (cbxCompetidores2.getSelectedItem() != null && !cbxCompetidores2.getSelectedItem().toString().equals("Selecione")) {
            chbPularCompetidor2.setEnabled(competicao.isOpcaoPular());
            chbPlateiaCompetidor2.setEnabled(competicao.isOpcaoPlateia());
            chbUniversitariosCompetidor2.setEnabled(competicao.isOpcaoUnivesitarios());
            btnConferirCompetidor2.setEnabled(true);
        } else {
            chbPularCompetidor2.setEnabled(false);
            chbPlateiaCompetidor2.setEnabled(false);
            chbUniversitariosCompetidor2.setEnabled(false);
            btnConferirCompetidor2.setEnabled(false);
        }
    }

    private void iniciarConfiguracao() {
        jsTempo.setValue(competicao.getTempo());
        painelQuizFrm.configurarTempo(competicao.getTempo());
        deviceSelected = gs[gs.length - 1];
        for (int i = 1; i <= gs.length; i++) {
            cboMonitor.addItem("Monitor " + i);
        }
        cboMonitor.setSelectedIndex(gs.length - 1);
        jsTempo.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                competicao.setTempo(jsTempo.getValue());
                if (painelQuizFrm.isPausarTempo()) {
                    painelQuizFrm.configurarTempo(competicao.getTempo());
                }
            }
        });
    }

    public void iniciarPainelQuiz() {
//        painelQuizFrm.setExtendedState(painelQuizFrm.MAXIMIZED_BOTH); 
//        painelQuizFrm.setUndecorated(true);
//        painelQuizFrm.setAlwaysOnTop(true);
        if (rbJanela.isSelected()) {
            painelQuizFrm.setVisible(true);
        } else if (rbTelaCheia.isSelected()) {
            painelQuizFrm.setAlwaysOnTop(true);
            deviceSelected.setFullScreenWindow(painelQuizFrm);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Selecione o modo de exibição do Painel do Quiz!");
        }

    }

    public void inicarWindowListener() {
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                confirmarFecharJanela();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    public void confirmarFecharJanela() {
        int result = 0;//JOptionPane.showConfirmDialog(rootPane, "Tem certeza que deseja fechar?", "Confirme", JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            System.exit(0);
        }
    }

    public void fecharPainelQuiz() {
        deviceSelected.setFullScreenWindow(null);
        painelQuizFrm.setVisible(false);
    }

    public void atualizarQuestaoGerenciadorQuiz() {
        lblQuestao.setText(questaoSorteada.getTexto());
        lblResposta1.setText("A) " + questaoSorteada.getResposta1());
        lblResposta2.setText("B) " + questaoSorteada.getResposta2());
        lblResposta3.setText("C) " + questaoSorteada.getResposta3());
        lblResposta4.setText("D) " + questaoSorteada.getResposta4());
        switch (questaoSorteada.getRespostaCorreta()) {
            case 1:
                lblResposta1.setText(lblResposta1.getText() + "*");
                break;
            case 2:
                lblResposta2.setText(lblResposta2.getText() + "*");
                break;
            case 3:
                lblResposta3.setText(lblResposta3.getText() + "*");
                break;
            case 4:
                lblResposta4.setText(lblResposta4.getText() + "*");
                break;
            default:

        }
    }

    private void resetRespostaSelecionada() {
        lblResposta1.setBackground(Color.ORANGE);
        lblResposta2.setBackground(Color.ORANGE);
        lblResposta3.setBackground(Color.ORANGE);
        lblResposta4.setBackground(Color.ORANGE);
    }

    private void atualizaSelecaoResposta() {
        resetRespostaSelecionada();
        if (painelQuizFrm.isProjetandoQuestao()) {
            painelQuizFrm.selecionarResposta(respostaSelecionada);
        }
        if (respostaSelecionada == null) {
            return;
        }
        switch (respostaSelecionada) {
            case 1:
                lblResposta1.setBackground(Color.GREEN);
                break;
            case 2:
                lblResposta2.setBackground(Color.GREEN);
                break;
            case 3:
                lblResposta3.setBackground(Color.GREEN);
                break;
            case 4:
                lblResposta4.setBackground(Color.GREEN);
                break;
            default:

        }

    }

    private void sortearQuestao() {
        float quantidade = questoes.size();
        int sortada = new Double((Math.random() * quantidade)).intValue();
        questaoSorteada = questoes.get(sortada);
        questaoSorteada.setJaFoi(true);
        atualizarQuestaoGerenciadorQuiz();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel_quiz = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        tbGerenciarQuiz = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        rbJanela = new javax.swing.JRadioButton();
        rbTelaCheia = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cboMonitor = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jsTempo = new javax.swing.JSlider();
        chbPular = new javax.swing.JCheckBox();
        chbUniversitarios = new javax.swing.JCheckBox();
        chbPlateia = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaParticipantes = new javax.swing.JList<>();
        btnAdicionarParticipantes = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listQuestoes = new javax.swing.JList<>();
        btnSelecionarQuestoes = new javax.swing.JButton();
        pnGerenciarQuiz = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnIniciarPainelQuiz = new javax.swing.JButton();
        bntFecharPainel = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        btnConferirCompetidor1 = new javax.swing.JButton();
        chbPularCompetidor1 = new javax.swing.JCheckBox();
        chbPlateiaCompetidor1 = new javax.swing.JCheckBox();
        chbUniversitariosCompetidor1 = new javax.swing.JCheckBox();
        cbxCompetidores1 = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        btnConferirCompetidor2 = new javax.swing.JButton();
        chbPularCompetidor2 = new javax.swing.JCheckBox();
        chbPlateiaCompetidor2 = new javax.swing.JCheckBox();
        chbUniversitariosCompetidor2 = new javax.swing.JCheckBox();
        cbxCompetidores2 = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        btnSortearCompetidores = new javax.swing.JButton();
        btnSortearQuestao = new javax.swing.JButton();
        btnEnviarPergunta = new javax.swing.JButton();
        btnIniciarTempo = new javax.swing.JButton();
        btnPausarTempo = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        lblQuestao = new javax.swing.JLabel();
        lblResposta1 = new javax.swing.JLabel();
        lblResposta2 = new javax.swing.JLabel();
        lblResposta4 = new javax.swing.JLabel();
        lblResposta3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        arvoreCompetidores = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuCompetidor = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jScrollPane1.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Configurações Quiz IF");

        tbGerenciarQuiz.setBackground(javax.swing.UIManager.getDefaults().getColor("menu"));
        tbGerenciarQuiz.setOpaque(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuração Painel Quiz"));
        jPanel3.setOpaque(false);

        painel_quiz.add(rbJanela);
        rbJanela.setText("Janela");

        painel_quiz.add(rbTelaCheia);
        rbTelaCheia.setSelected(true);
        rbTelaCheia.setText("Tela Cheia");

        jLabel1.setText("Painel Quiz");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbJanela)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbTelaCheia)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(rbJanela)
                    .addComponent(rbTelaCheia))
                .addGap(17, 17, 17))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Monitor Painel Quiz"));
        jPanel4.setOpaque(false);

        cboMonitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMonitorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboMonitor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboMonitor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuração Quiz"));
        jPanel6.setOpaque(false);

        jLabel3.setText("Tempo");

        jsTempo.setMaximum(60);
        jsTempo.setMinimum(1);
        jsTempo.setValue(20);

        chbPular.setSelected(true);
        chbPular.setText("Usar opção Pular");
        chbPular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbPularActionPerformed(evt);
            }
        });

        chbUniversitarios.setSelected(true);
        chbUniversitarios.setText("Usar opção Universitários");
        chbUniversitarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbUniversitariosActionPerformed(evt);
            }
        });

        chbPlateia.setSelected(true);
        chbPlateia.setText("Usar opção Plateia");
        chbPlateia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbPlateiaActionPerformed(evt);
            }
        });

        jLabel2.setText("Título Competição");

        txtTitulo.setEnabled(false);
        txtTitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTituloKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsTempo, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(chbPular)
                            .addComponent(chbUniversitarios)
                            .addComponent(chbPlateia)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTitulo))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbPular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chbUniversitarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chbPlateia)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Participantes"));
        jPanel7.setOpaque(false);

        listaParticipantes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(listaParticipantes);

        btnAdicionarParticipantes.setText("Buscar Participantes");
        btnAdicionarParticipantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarParticipantesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(btnAdicionarParticipantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdicionarParticipantes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Questões"));
        jPanel8.setOpaque(false);

        listQuestoes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(listQuestoes);

        btnSelecionarQuestoes.setText("Buscar Questões");
        btnSelecionarQuestoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarQuestoesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(btnSelecionarQuestoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSelecionarQuestoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tbGerenciarQuiz.addTab("Configurações", jPanel2);

        pnGerenciarQuiz.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Painel Quiz", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        btnIniciarPainelQuiz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/window-new.png"))); // NOI18N
        btnIniciarPainelQuiz.setText("Iniciar");
        btnIniciarPainelQuiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarPainelQuizActionPerformed(evt);
            }
        });

        bntFecharPainel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/window-close.png"))); // NOI18N
        bntFecharPainel.setText("Fechar");
        bntFecharPainel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntFecharPainelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnIniciarPainelQuiz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bntFecharPainel, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIniciarPainelQuiz)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntFecharPainel)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Competidores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Competidor 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel12.setOpaque(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(450, 149));

        btnConferirCompetidor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/trophy-gold.png"))); // NOI18N
        btnConferirCompetidor1.setText("Conferir Resposta");
        btnConferirCompetidor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConferirCompetidor1ActionPerformed(evt);
            }
        });

        chbPularCompetidor1.setText("Pular");
        chbPularCompetidor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbPularCompetidor1ActionPerformed(evt);
            }
        });

        chbPlateiaCompetidor1.setText("Plateia");
        chbPlateiaCompetidor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbPlateiaCompetidor1ActionPerformed(evt);
            }
        });

        chbUniversitariosCompetidor1.setText("Universitários");
        chbUniversitariosCompetidor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbUniversitariosCompetidor1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConferirCompetidor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(chbPularCompetidor1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chbPlateiaCompetidor1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chbUniversitariosCompetidor1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbxCompetidores1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbxCompetidores1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConferirCompetidor1)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbPularCompetidor1)
                    .addComponent(chbPlateiaCompetidor1)
                    .addComponent(chbUniversitariosCompetidor1))
                .addContainerGap())
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Competidor 2", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel13.setOpaque(false);
        jPanel13.setPreferredSize(new java.awt.Dimension(450, 149));

        btnConferirCompetidor2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/trophy-gold.png"))); // NOI18N
        btnConferirCompetidor2.setText("Conferir Resposta");
        btnConferirCompetidor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConferirCompetidor2ActionPerformed(evt);
            }
        });

        chbPularCompetidor2.setText("Pular");
        chbPularCompetidor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbPularCompetidor2ActionPerformed(evt);
            }
        });

        chbPlateiaCompetidor2.setText("Plateia");
        chbPlateiaCompetidor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbPlateiaCompetidor2ActionPerformed(evt);
            }
        });

        chbUniversitariosCompetidor2.setText("Universitários");
        chbUniversitariosCompetidor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbUniversitariosCompetidor2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(chbPularCompetidor2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chbPlateiaCompetidor2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chbUniversitariosCompetidor2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnConferirCompetidor2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxCompetidores2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxCompetidores2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConferirCompetidor2)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbPularCompetidor2)
                    .addComponent(chbPlateiaCompetidor2)
                    .addComponent(chbUniversitariosCompetidor2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sortear", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        btnSortearCompetidores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/system-users.png"))); // NOI18N
        btnSortearCompetidores.setText("Sortear Compeditores");
        btnSortearCompetidores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortearCompetidoresActionPerformed(evt);
            }
        });

        btnSortearQuestao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/media-playlist-shuffle.png"))); // NOI18N
        btnSortearQuestao.setText("Sortear Questão");
        btnSortearQuestao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortearQuestaoActionPerformed(evt);
            }
        });

        btnEnviarPergunta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/document-send.png"))); // NOI18N
        btnEnviarPergunta.setText("Enviar para Projeção");
        btnEnviarPergunta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarPerguntaActionPerformed(evt);
            }
        });

        btnIniciarTempo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/appointment-soon.png"))); // NOI18N
        btnIniciarTempo.setText("Iniciar Tempo");
        btnIniciarTempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarTempoActionPerformed(evt);
            }
        });

        btnPausarTempo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/quizif/resources/image/media-playback-pause.png"))); // NOI18N
        btnPausarTempo.setText("Pausar Tempo");
        btnPausarTempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPausarTempoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnIniciarTempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSortearCompetidores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSortearQuestao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPausarTempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEnviarPergunta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSortearCompetidores)
                    .addComponent(btnSortearQuestao)
                    .addComponent(btnEnviarPergunta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciarTempo)
                    .addComponent(btnPausarTempo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Questão Sorteada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        lblQuestao.setBackground(new java.awt.Color(156, 225, 255));
        lblQuestao.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        lblQuestao.setOpaque(true);

        lblResposta1.setBackground(java.awt.Color.orange);
        lblResposta1.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        lblResposta1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblResposta1.setOpaque(true);
        lblResposta1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblResposta1MouseClicked(evt);
            }
        });

        lblResposta2.setBackground(java.awt.Color.orange);
        lblResposta2.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        lblResposta2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblResposta2.setOpaque(true);
        lblResposta2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblResposta2MouseClicked(evt);
            }
        });

        lblResposta4.setBackground(java.awt.Color.orange);
        lblResposta4.setToolTipText("");
        lblResposta4.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        lblResposta4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblResposta4.setOpaque(true);
        lblResposta4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblResposta4MouseClicked(evt);
            }
        });

        lblResposta3.setBackground(java.awt.Color.orange);
        lblResposta3.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        lblResposta3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblResposta3.setOpaque(true);
        lblResposta3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblResposta3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblQuestao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(lblResposta3, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(lblResposta4, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(lblResposta1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(lblResposta2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQuestao, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblResposta1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblResposta2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblResposta3, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(lblResposta4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Competição"));

        arvoreCompetidores.setOpaque(false);
        jScrollPane2.setViewportView(arvoreCompetidores);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnGerenciarQuizLayout = new javax.swing.GroupLayout(pnGerenciarQuiz);
        pnGerenciarQuiz.setLayout(pnGerenciarQuizLayout);
        pnGerenciarQuizLayout.setHorizontalGroup(
            pnGerenciarQuizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGerenciarQuizLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnGerenciarQuizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnGerenciarQuizLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnGerenciarQuizLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnGerenciarQuizLayout.setVerticalGroup(
            pnGerenciarQuizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGerenciarQuizLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnGerenciarQuizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnGerenciarQuizLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbGerenciarQuiz.addTab("Gerenciar Quiz", pnGerenciarQuiz);

        menuCompetidor.setText("Opções");

        jMenuItem2.setText("Competidores");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menuCompetidor.add(jMenuItem2);

        jMenuBar1.add(menuCompetidor);

        jMenu2.setText("Sobre");

        jMenuItem1.setText("QuizIF");
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbGerenciarQuiz)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbGerenciarQuiz)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboMonitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMonitorActionPerformed
        deviceSelected = gs[cboMonitor.getSelectedIndex()];

    }//GEN-LAST:event_cboMonitorActionPerformed

    private void btnIniciarPainelQuizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarPainelQuizActionPerformed
        iniciarPainelQuiz();
        btnEnviarPergunta.setEnabled(true);
    }//GEN-LAST:event_btnIniciarPainelQuizActionPerformed

    private void bntFecharPainelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntFecharPainelActionPerformed
        fecharPainelQuiz();
        btnEnviarPergunta.setEnabled(false);
    }//GEN-LAST:event_bntFecharPainelActionPerformed

    private void btnSelecionarQuestoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarQuestoesActionPerformed
        DefaultListModel<String> listModel = new DefaultListModel<>();
        questoes = new QuestaoDAO().listar(1, null);
        for (Questao questao : questoes) {
            listModel.addElement(questao.getTexto());
        }
        listQuestoes.setModel(listModel);
        listQuestoes.setValueIsAdjusting(true);
        btnSelecionarQuestoes.setText(btnSelecionarQuestoes.getText() + " (" + questoes.size() + " questões carregadas)");
        verificarAbaPainelQuiz();
    }//GEN-LAST:event_btnSelecionarQuestoesActionPerformed

    private void chbPularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbPularActionPerformed
        competicao.setOpcaoPular(chbPular.isSelected());
        verificarAbaPainelQuiz();
        verificarPainelCompetidor1();
        verificarPainelCompetidor2();
    }//GEN-LAST:event_chbPularActionPerformed

    private void chbUniversitariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbUniversitariosActionPerformed
        competicao.setOpcaoUnivesitarios(chbUniversitarios.isSelected());
        verificarAbaPainelQuiz();
        verificarPainelCompetidor1();
        verificarPainelCompetidor2();
    }//GEN-LAST:event_chbUniversitariosActionPerformed

    private void chbPlateiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbPlateiaActionPerformed
        competicao.setOpcaoPlateia(chbPlateia.isSelected());
        verificarAbaPainelQuiz();
        verificarPainelCompetidor1();
        verificarPainelCompetidor2();
    }//GEN-LAST:event_chbPlateiaActionPerformed

    private void txtTituloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTituloKeyReleased
        competicao.setTitulo(txtTitulo.getText());
        verificarAbaPainelQuiz();
    }//GEN-LAST:event_txtTituloKeyReleased

    private void btnSortearQuestaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortearQuestaoActionPerformed
        sortearQuestao();
    }//GEN-LAST:event_btnSortearQuestaoActionPerformed

    private void btnEnviarPerguntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarPerguntaActionPerformed
        painelQuizFrm.setQuestaoSelecionada(questaoSorteada);
    }//GEN-LAST:event_btnEnviarPerguntaActionPerformed

    private void lblResposta1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResposta1MouseClicked
        if (respostaSelecionada != null && respostaSelecionada.equals(1)) {
            respostaSelecionada = null;
        } else {
            respostaSelecionada = 1;
        }
        atualizaSelecaoResposta();
    }//GEN-LAST:event_lblResposta1MouseClicked

    private void lblResposta2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResposta2MouseClicked
        if (respostaSelecionada != null && respostaSelecionada.equals(2)) {
            respostaSelecionada = null;
        } else {
            respostaSelecionada = 2;
        }
        atualizaSelecaoResposta();
    }//GEN-LAST:event_lblResposta2MouseClicked

    private void lblResposta3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResposta3MouseClicked
        if (respostaSelecionada != null && respostaSelecionada.equals(3)) {
            respostaSelecionada = null;
        } else {
            respostaSelecionada = 3;
        }
        atualizaSelecaoResposta();
    }//GEN-LAST:event_lblResposta3MouseClicked

    private void lblResposta4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResposta4MouseClicked
        if (respostaSelecionada != null && respostaSelecionada.equals(4)) {
            respostaSelecionada = null;
        } else {
            respostaSelecionada = 4;
        }
        atualizaSelecaoResposta();
    }//GEN-LAST:event_lblResposta4MouseClicked

    private void btnIniciarTempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarTempoActionPerformed
        painelQuizFrm.iniciarTempo();
    }//GEN-LAST:event_btnIniciarTempoActionPerformed

    private void btnPausarTempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPausarTempoActionPerformed
        painelQuizFrm.setPausarTempo(!painelQuizFrm.isPausarTempo());
    }//GEN-LAST:event_btnPausarTempoActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new AdicionarCompetidores().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnAdicionarParticipantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarParticipantesActionPerformed
        DefaultListModel<String> listModel = new DefaultListModel<>();
        competidores = new CompetidorDAO().listar();
        for (Competidor competidor : competidores) {
            listModel.addElement(competidor.getNome());
        }
        listaParticipantes.setModel(listModel);
        listaParticipantes.setValueIsAdjusting(true);
        btnAdicionarParticipantes.setText(btnAdicionarParticipantes.getText() + " (" + competidores.size() + " competidores)");
        verificarAbaPainelQuiz();
        inicarComboboxCompetidores();
    }//GEN-LAST:event_btnAdicionarParticipantesActionPerformed

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

    private void btnSortearCompetidoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortearCompetidoresActionPerformed
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Rodada " + competicao.getRodada());
        List<Competidor> competidorSelecionar = competidores;
        List<Integer> sorteados = new ArrayList<>();
        for (int i = 0; i < competidores.size(); i++) {
            int nsorteado = new Double(1 + Math.random() * competidores.size()).intValue();
            while (sorteados.contains(nsorteado)) {
                nsorteado = new Double(1 + Math.random() * competidores.size()).intValue();
            }
            sorteados.add(nsorteado);
        }
        int disputa = 1;
        for (int i = 0; i < competidores.size() - 1; i = i + 2) {
            DefaultMutableTreeNode disp = new DefaultMutableTreeNode("Competidores " + disputa);
            disp.add(new DefaultMutableTreeNode(competidores.get(i)));
            disp.add(new DefaultMutableTreeNode(competidores.get(i + 1)));
            root.add(disp);

        }
        TreeModel model = new DefaultTreeModel(root);
        arvoreCompetidores.setModel(model);
        expandAllNodes(arvoreCompetidores, 0, 0);
    }//GEN-LAST:event_btnSortearCompetidoresActionPerformed

    private void btnConferirCompetidor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConferirCompetidor1ActionPerformed
        if (respostaSelecionada != null) {
            painelQuizFrm.mostrarResposta(respostaSelecionada.equals(questaoSorteada.getRespostaCorreta()), questaoSorteada.getRespostaCorreta());
        }
    }//GEN-LAST:event_btnConferirCompetidor1ActionPerformed

    private void btnConferirCompetidor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConferirCompetidor2ActionPerformed
        if (respostaSelecionada != null) {
            painelQuizFrm.mostrarResposta(respostaSelecionada.equals(questaoSorteada.getRespostaCorreta()), questaoSorteada.getRespostaCorreta());
        }
    }//GEN-LAST:event_btnConferirCompetidor2ActionPerformed

    private void chbPularCompetidor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbPularCompetidor1ActionPerformed
        AbstractButton ab = (AbstractButton) evt.getSource();
        competidor1.setPulou(ab.isSelected());
        painelQuizFrm.atualzarCompetidores(competidor1, competidor2);
    }//GEN-LAST:event_chbPularCompetidor1ActionPerformed

    private void chbPlateiaCompetidor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbPlateiaCompetidor1ActionPerformed
        AbstractButton ab = (AbstractButton) evt.getSource();
        competidor1.setAjudadoPlateia(ab.isSelected());
        painelQuizFrm.atualzarCompetidores(competidor1, competidor2);
    }//GEN-LAST:event_chbPlateiaCompetidor1ActionPerformed

    private void chbUniversitariosCompetidor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbUniversitariosCompetidor1ActionPerformed
        AbstractButton ab = (AbstractButton) evt.getSource();
        competidor1.setAjudadoUniversitarios(ab.isSelected());
        painelQuizFrm.atualzarCompetidores(competidor1, competidor2);
    }//GEN-LAST:event_chbUniversitariosCompetidor1ActionPerformed

    private void chbPularCompetidor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbPularCompetidor2ActionPerformed
        AbstractButton ab = (AbstractButton) evt.getSource();
        competidor2.setPulou(ab.isSelected());
        painelQuizFrm.atualzarCompetidores(competidor1, competidor2);
    }//GEN-LAST:event_chbPularCompetidor2ActionPerformed

    private void chbPlateiaCompetidor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbPlateiaCompetidor2ActionPerformed
        AbstractButton ab = (AbstractButton) evt.getSource();
        competidor2.setAjudadoPlateia(ab.isSelected());
        painelQuizFrm.atualzarCompetidores(competidor1, competidor2);
    }//GEN-LAST:event_chbPlateiaCompetidor2ActionPerformed

    private void chbUniversitariosCompetidor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbUniversitariosCompetidor2ActionPerformed
        AbstractButton ab = (AbstractButton) evt.getSource();
        competidor2.setAjudadoUniversitarios(ab.isSelected());
        painelQuizFrm.atualzarCompetidores(competidor1, competidor2);
    }//GEN-LAST:event_chbUniversitariosCompetidor2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GerenciarQuizFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarQuizFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarQuizFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarQuizFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GerenciarQuizFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvoreCompetidores;
    private javax.swing.JButton bntFecharPainel;
    private javax.swing.JButton btnAdicionarParticipantes;
    private javax.swing.JButton btnConferirCompetidor1;
    private javax.swing.JButton btnConferirCompetidor2;
    private javax.swing.JButton btnEnviarPergunta;
    private javax.swing.JButton btnIniciarPainelQuiz;
    private javax.swing.JButton btnIniciarTempo;
    private javax.swing.JButton btnPausarTempo;
    private javax.swing.JButton btnSelecionarQuestoes;
    private javax.swing.JButton btnSortearCompetidores;
    private javax.swing.JButton btnSortearQuestao;
    private javax.swing.JComboBox<String> cboMonitor;
    private javax.swing.JComboBox<String> cbxCompetidores1;
    private javax.swing.JComboBox<String> cbxCompetidores2;
    private javax.swing.JCheckBox chbPlateia;
    private javax.swing.JCheckBox chbPlateiaCompetidor1;
    private javax.swing.JCheckBox chbPlateiaCompetidor2;
    private javax.swing.JCheckBox chbPular;
    private javax.swing.JCheckBox chbPularCompetidor1;
    private javax.swing.JCheckBox chbPularCompetidor2;
    private javax.swing.JCheckBox chbUniversitarios;
    private javax.swing.JCheckBox chbUniversitariosCompetidor1;
    private javax.swing.JCheckBox chbUniversitariosCompetidor2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTree jTree1;
    private javax.swing.JSlider jsTempo;
    private javax.swing.JLabel lblQuestao;
    private javax.swing.JLabel lblResposta1;
    private javax.swing.JLabel lblResposta2;
    private javax.swing.JLabel lblResposta3;
    private javax.swing.JLabel lblResposta4;
    private javax.swing.JList<String> listQuestoes;
    private javax.swing.JList<String> listaParticipantes;
    private javax.swing.JMenu menuCompetidor;
    private javax.swing.ButtonGroup painel_quiz;
    private javax.swing.JPanel pnGerenciarQuiz;
    private javax.swing.JRadioButton rbJanela;
    private javax.swing.JRadioButton rbTelaCheia;
    private javax.swing.JTabbedPane tbGerenciarQuiz;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables

}
