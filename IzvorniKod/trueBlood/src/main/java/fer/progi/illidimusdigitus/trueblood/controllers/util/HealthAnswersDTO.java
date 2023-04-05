package fer.progi.illidimusdigitus.trueblood.controllers.util;

import java.util.Map;

public class HealthAnswersDTO {

    private String id_donora;
    private String mjesto_darivanja;

    private Map<Long,Boolean> upitnik;

    public Map<Long, Boolean> getUpitnik() {
        return upitnik;
    }

    public void setId_zdravstvenihOdgovor_donora(Map<Long, Boolean> upitnik) {
        this.upitnik = upitnik;
    }

    public String getId_donora() {
        return id_donora;
    }

    public void setId_donora(String id_donora) {
        this.id_donora = id_donora;
    }

    public String getMjesto_darivanja() {
        return mjesto_darivanja;
    }

    public void setMjesto_darivanja(String mjesto_darivanja) {
        this.mjesto_darivanja = mjesto_darivanja;
    }

    public HealthAnswersDTO(String id_donora, String mjesto_darivanja, Map<Long, Boolean> upitnik) {
        this.id_donora = id_donora;
        this.mjesto_darivanja = mjesto_darivanja;
        this.upitnik = upitnik;
    }

    public HealthAnswersDTO() {
    }
}
