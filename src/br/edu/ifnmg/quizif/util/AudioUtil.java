/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.quizif.util;

import br.edu.ifnmg.quizif.Main;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author danilo
 */
public class AudioUtil {
    
    private static final String DEFAULT_PACKAGE = "/br/edu/ifnmg/quizif/resources/audio/";
    public static final String TICK_TOCK = "ticktock.wav";
    public static final String COMPLETE_TIME = "campainha.wav";
    public static final String FAIL = "fail.wav";
    public static final String SUCCESS = "success.wav";
    public static final String SUSPENSE= "suspense.wav";
    public static final String ABERTURA_PERGUNTA = "abertura-pergunta.wav";
    public static final String SELECIONAR_RESPOSTA = "selecionar-resposta.wav";
    
    public static void reproduzir(String musica){
        try {
            File soundFile = new File(AudioUtil.class.getResource(DEFAULT_PACKAGE+musica).toURI());
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(sound);
            clip.start();
        } catch (URISyntaxException ex) {
            Logger.getLogger(AudioUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AudioUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AudioUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
