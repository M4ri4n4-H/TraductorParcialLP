import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import entidades.Frase;
import entidades.Diccionario;
import entidades.Traduccion;

import javax.swing.*;

public class FrmTraductor extends JFrame {

    JComboBox cmbfrase;
    JComboBox cmbidioma;
    JList mostrarFrase;

    public FrmTraductor() {

        String nombreArchivo = "/icono/parlante.jpg";
        ImageIcon imgReproducir = new ImageIcon(getClass().getResource(nombreArchivo));

        setSize(500, 300);
        setTitle("Traductor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(173, 216, 230));

        // jlabel 2 (frase e idioma)
        JLabel lblfrase = new JLabel("Frase");
        lblfrase.setBounds(25, 10, 100, 25);
        getContentPane().add(lblfrase);

        JLabel lblidioma = new JLabel("Idioma");
        lblidioma.setBounds(25, 50, 100, 25);
        getContentPane().add(lblidioma);

        mostrarFrase = new JList();
        mostrarFrase.setBounds(25, 160, 320, 80);
        getContentPane().add(mostrarFrase);

        cmbfrase = new JComboBox(new DefaultComboBoxModel());
        cmbfrase.setBounds(110, 10, 300, 25);
        getContentPane().add(cmbfrase);

        cmbidioma = new JComboBox(new DefaultComboBoxModel());
        cmbidioma.setBounds(110, 50, 100, 25);
        getContentPane().add(cmbidioma);

        JButton btnreproducir = new JButton();
        btnreproducir.setIcon(imgReproducir);
        btnreproducir.setBounds(380, 160, imgReproducir.getIconWidth(), imgReproducir.getIconHeight());
        getContentPane().add(btnreproducir);

        JButton btntraducir = new JButton("traducir");
        btntraducir.setBounds(25, 100, 100, 25);
        getContentPane().add(btntraducir);
        btnreproducir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reproducir();
            }
        });

        btntraducir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Traducir();
            }
        });
        cargarDatos();
    }
    private List<Frase> frases;
    private List<String> idiomas;

    private void cargarDatos() {
        ObjectMapper objectMapper = new ObjectMapper();
        String nombreArchivo = System.getProperty("user.dir") + "/src/datos/FrasesTraducidas.json";
    
        try {
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            Diccionario diccionario = objectMapper.readValue(new File(nombreArchivo), Diccionario.class);
            frases = diccionario.getFrases();
    
            for (Frase frase : frases) {
                cmbfrase.addItem(frase.getTexto());
            }
    
            List<String> idiomas = new ArrayList<>();
            for (Frase frase : frases) {
                for (Traduccion traduccion : frase.getTraducciones()) {
                    String idioma = traduccion.getIdioma();
                    boolean existe = false;
                    
                    for (String item : idiomas) {
                        if (item.equals(idioma)) {
                            existe = true;
                            break;
                        }
                    }
                    
                    if (!existe) {
                        idiomas.add(idioma);
                    }
                }
            }
    
            for (String idioma : idiomas) {
                cmbidioma.addItem(idioma);
            }
    
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar: " + ex.getMessage());
        }
    }

     private void Reproducir() {
        String fraseSeleccionada = (String) cmbfrase.getSelectedItem();
        String idiomaSeleccionado = (String) cmbidioma.getSelectedItem();
        
        String nombreArchivo = CambiarNombre(fraseSeleccionada) + "-" + CambiarNombre(idiomaSeleccionado) + ".mp3";
        String rutaCompleta = "src/sonidos/" + nombreArchivo;
    
        ReproductorAudio.reproducir(rutaCompleta);
    }
    
    private String CambiarNombre(String texto) {
        return texto.toLowerCase()
                .replace("á", "a").replace("é", "e").replace("í", "i")
                .replace("ó", "o").replace("ú", "u").replace("ñ", "n")
                .replace(" ", "")
                .replace("?", "");
    }
    private void Traducir() {
        String fraseSeleccionada = (String) cmbfrase.getSelectedItem();
        String idiomaSeleccionado = (String) cmbidioma.getSelectedItem();
        
        for (Frase frase : frases) {
            if (frase.getTexto().equals(fraseSeleccionada)) {
                for (Traduccion traduccion : frase.getTraducciones()) {
                    if (traduccion.getIdioma().equals(idiomaSeleccionado)) {
                        mostrarFrase.setListData(new String[]{traduccion.getTextoTraducido()});
                        return;
                    }
                }
            }
        }
        mostrarFrase.setListData(new String[]{"Traducción no encontrada"});
    }
}