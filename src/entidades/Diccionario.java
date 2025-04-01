package entidades;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Diccionario {
    @JsonProperty
    private List<Frase> Frases;

    public List<Frase> getFrases() {
        return Frases;
    }

    public void setFrases(List<Frase> Frases) {
        this.Frases = Frases;
    }
}
