package entidades;
import java.util.List;

public class Frase {
    private String Texto;
    private List<Traduccion> Traducciones;

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String Texto) {
        this.Texto = Texto;
    }

    public List<Traduccion> getTraducciones() {
        return Traducciones;
    }

    public void setTraducciones(List<Traduccion> Traducciones) {
        this.Traducciones = Traducciones;
    }
}
