public class Kriteria {
    private String namaKriteria;
    private boolean jenis;
    private float bobot;
    
    Kriteria(String namaKriteria, float bobot, boolean jenis) {
        this.namaKriteria = namaKriteria;
        this.bobot = bobot;
        this.jenis = jenis;
    }

    public String getNamaKriteria() {
        return namaKriteria;
    }
    public void setNamaKriteria(String namaKriteria) {
        this.namaKriteria = namaKriteria;
    }
    public float getBobot() {
        return bobot;
    }
    public void setBobot(float bobot) {
        this.bobot = bobot;
    }

    public boolean getJenis() {
        return jenis;
    }

    public void setJenis(boolean jenis) {
        this.jenis = jenis;
    }
    
}
