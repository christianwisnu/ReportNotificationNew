package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReportPengeluaranHeaderModel implements Serializable {

    private String id;
    private String tanggal;
    private String idVendor;
    private String namaVendor;
    private String tipeBayar;
    private Integer jatuhTempo;
    private BigDecimal subTotal;
    private BigDecimal diskon;
    private BigDecimal total;
    private BigDecimal dpNominal;
    private BigDecimal grandTotal;
    private String keteranganHeader;
    private List<ReportPengeluaranItemModel> itemList;

    public ReportPengeluaranHeaderModel(){
        this.setItemList(new ArrayList<ReportPengeluaranItemModel>());
    }

    public void addItem(ReportPengeluaranItemModel itemModel){
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

    public String getIdVendor() {
        return idVendor;
    }

    public void setIdVendor(String idVendor) {
        this.idVendor = idVendor;
    }

    public String getNamaVendor() {
        return namaVendor;
    }

    public void setNamaVendor(String namaVendor) {
        this.namaVendor = namaVendor;
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

    public String getKeteranganHeader() {
        return keteranganHeader;
    }

    public void setKeteranganHeader(String keteranganHeader) {
        this.keteranganHeader = keteranganHeader;
    }

    public List<ReportPengeluaranItemModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<ReportPengeluaranItemModel> itemList) {
        this.itemList = itemList;
    }
}
