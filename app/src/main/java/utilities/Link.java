package utilities;

import service.BaseApiService;

public class Link {

    public static final String BASE_URL_API = "http://softchrist.com/report_notification/php/";
    public static final String BASE_URL_NOTIF = "http://softchrist.com/report_notification/php/notification.php";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
