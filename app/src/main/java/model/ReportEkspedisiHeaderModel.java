package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReportEkspedisiHeaderModel implements Serializable {

    private String id;
    private String tanggal;
    private String idCust;
    private String namaCust;
    private String tipeBayar;
    private Integer jatuhTempo;
    private String statusbayar;
    private BigDecimal subTotal;
    private BigDecimal diskon;
    private BigDecimal total;
    private BigDecimal dpNominal;
    private BigDecimal grandTotal;
    private String keterangan;
    private List<ReportEkspedisiItemModel> itemList;

    public ReportEkspedisiHeaderModel(){
        this.itemList = new ArrayList<ReportEkspedisiItemModel>();
    }

    public void addItem(ReportEkspedisiItemModel itemModel){
        getItemList().add(itemModel);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getIdCust() {
        return idCust;
    }

    public void setIdCust(String idCust) {
        this.idCust = idCust;
    }

    public String getNamaCust() {
        return namaCust;
    }

    public void setNamaCust(String namaCust) {
        this.namaCust = namaCust;
    }

    public String getTipeBayar() {
        return tipeBayar;
    }

    public void setTipeBayar(String tipeBayar) {
        this.tipeBayar = tipeBayar;
    }

    public Integer getJatuhTempo() {
        return jatuhTempo;
    }

    public void setJatuhTempo(Integer jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }

    public String getStatusbayar() {
        return statusbayar;
    }

    public void setStatusbayar(String statusbayar) {
        this.statusbayar = statusbayar;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getDiskon() {
        return diskon;
    }

    public void setDiskon(BigDecimal diskon) {
        this.diskon = diskon;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDpNominal() {
        return dpNominal;
    }

    public void setDpNominal(BigDecimal dpNominal) {
        this.dpNominal = dpNominal;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public List<ReportEkspedisiItemModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<ReportEkspedisiItemModel> itemList) {
        this.itemList = itemList;
    }
}
