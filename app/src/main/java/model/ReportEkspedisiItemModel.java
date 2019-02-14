package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportEkspedisiItemModel implements Serializable {

    private String idHeader;
    private Integer idDetail;
    private Integer index;
    private Integer idKapal;
    private String namaKapal;
    private Integer idJenisKendaraan;
    private String namaJenisKendaraan;
    private String platNo;
    private String namaPemilik;
    private String namaSupir;
    private BigDecimal harga;
    private String ket;

    public String getIdHeader() {
        return idHeader;
    }

    public void setIdHeader(String idHeader) {
        this.idHeader = idHeader;
    }

    public Integer getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIdKapal() {
        return idKapal;
    }

    public void setIdKapal(Integer idKapal) {
        this.idKapal = idKapal;
    }

    public String getNamaKapal() {
        return namaKapal;
    }

    public void setNamaKapal(String namaKapal) {
        this.namaKapal = namaKapal;
    }

    public Integer getIdJenisKendaraan() {
        return idJenisKendaraan;
    }

    public void setIdJenisKendaraan(Integer idJenisKendaraan) {
        this.idJenisKendaraan = idJenisKendaraan;
    }

    public String getNamaJenisKendaraan() {
        return namaJenisKendaraan;
    }

    public void setNamaJenisKendaraan(String namaJenisKendaraan) {
        this.namaJenisKendaraan = namaJenisKendaraan;
    }

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
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

    public String getNamaSupir() {
        return namaSupir;
    }

    public void setNamaSupir(String namaSupir) {
        this.namaSupir = namaSupir;
    }
}
