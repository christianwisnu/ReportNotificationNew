package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportPengeluaranItemModel implements Serializable {

    private String idHeader;//PENGELUARAN_ID
    private Integer index;
    private String tglShipping;
    private String buktiBayar;
    private BigDecimal harga;
    private String ket;

    public String getIdHeader() {
        return idHeader;
    }

    public void setIdHeader(String idHeader) {
        this.idHeader = idHeader;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTglShipping() {
        return tglShipping;
    }

    public void setTglShipping(String tglShipping) {
        this.tglShipping = tglShipping;
    }

    public String getBuktiBayar() {
        return buktiBayar;
    }

    public void setBuktiBayar(String buktiBayar) {
        this.buktiBayar = buktiBayar;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }
}
