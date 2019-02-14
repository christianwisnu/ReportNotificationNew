package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportEkspedisiModel implements Serializable{

    public ReportEkspedisiModel(){
        this.headerList = new ArrayList<ReportEkspedisiHeaderModel>();
    }

    private List<ReportEkspedisiHeaderModel> headerList;

    public List<ReportEkspedisiHeaderModel> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<ReportEkspedisiHeaderModel> headerList) {
        this.headerList = headerList;
    }

    public void addItem(ReportEkspedisiHeaderModel itemModel){
        headerList.add(itemModel);
    }

}
