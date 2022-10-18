package model;

public enum GymTypes {

    MSC("Musculacao"), NAT("Natacao"), JIU("Jiu Jitsu"), CRS("Cross Fit"), MTH("Muay Thai");

    private String tipo;

    GymTypes(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
