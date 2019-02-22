package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportPengeluaranModel implements Serializable {

    public ReportPengeluaranModel(){
        this.headerList = new ArrayList<ReportPengeluaranHeaderModel>();
    }

    private List<ReportPengeluaranHeaderModel> headerList;

    public List<ReportPengeluaranHeaderModel> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<ReportPengeluaranHeaderModel> headerList) {
        this.headerList = headerList;
    }

    public void addItem(ReportPengeluaranHeaderModel itemModel){
        getHeaderList().add(itemModel);
    }
}
