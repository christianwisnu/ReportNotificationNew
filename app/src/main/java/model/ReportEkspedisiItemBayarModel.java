package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportEkspedisiItemBayarModel implements Serializable {

    private String idBayar;
    private String tglBayar;
    private String idEkspedisi;
    private String keterangan;
    private BigDecimal grandtotal;
    private String voidKet;

    public String getIdBayar() {
        return idBayar;
    }

    public void setIdBayar(String idBayar) {
        this.idBayar = idBayar;
    }

    public String getTglBayar() {
        return tglBayar;
    }

    public void setTglBayar(String tglBayar) {
        this.tglBayar = tglBayar;
    }

    public String getIdEkspedisi() {
        return idEkspedisi;
    }

    public void setIdEkspedisi(String idEkspedisi) {
        this.idEkspedisi = idEkspedisi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public BigDecimal getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(BigDecimal grandtotal) {
        this.grandtotal = grandtotal;
    }

    public String getVoidKet() {
        return voidKet;
    }

    public void setVoidKet(String voidKet) {
        this.voidKet = voidKet;
    }
}
