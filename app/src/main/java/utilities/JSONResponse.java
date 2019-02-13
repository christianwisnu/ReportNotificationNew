package utilities;

import model.ListCustomerModel;
import model.ListJenisKendaraanModel;
import model.ListKapalModel;
import model.ListVendorModel;

public class JSONResponse {

    private ListVendorModel[] listvendor;
    private ListCustomerModel[] listcust;
    private ListKapalModel[] listkapal;
    private ListJenisKendaraanModel[] listjeniskendaraan;

    public ListVendorModel[] getListvendor() {
        return listvendor;
    }

    public void setListvendor(ListVendorModel[] listvendor) {
        this.listvendor = listvendor;
    }

    public ListCustomerModel[] getListcust() {
        return listcust;
    }

    public void setListcust(ListCustomerModel[] listcust) {
        this.listcust = listcust;
    }

    public ListKapalModel[] getListkapal() {
        return listkapal;
    }

    public void setListkapal(ListKapalModel[] listkapal) {
        this.listkapal = listkapal;
    }

    public ListJenisKendaraanModel[] getListjeniskendaraan() {
        return listjeniskendaraan;
    }

    public void setListjeniskendaraan(ListJenisKendaraanModel[] listjeniskendaraan) {
        this.listjeniskendaraan = listjeniskendaraan;
    }
}
