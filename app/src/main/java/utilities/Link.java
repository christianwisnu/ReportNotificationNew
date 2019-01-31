package utilities;

import service.BaseApiService;

public class Link {

    public static final String BASE_URL_API = "http://softchrist.com/sidang_ta/php/";
    public static final String BASE_URL_NOTIF = "http://softchrist.com/sidang_ta/php/notification.php";
    public static final String BASE_URL_IMAGE_BAYAR = "http://softchrist.com/sidang_ta/image/pembayaran/";
    public static final String BASE_URL_IMAGE_BIMBINGAN = "http://softchrist.com/sidang_ta/image/bimbingan/";
    public static final String BASE_URL_DOC = "http://softchrist.com/sidang_ta/dokumen/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

    public static BaseApiService getImageServiceBayar(){
        return RetrofitClient.getClient(BASE_URL_IMAGE_BAYAR).create(BaseApiService.class);
    }

    public static BaseApiService getImageServiceBimbingan(){
        return RetrofitClient.getClient(BASE_URL_IMAGE_BIMBINGAN).create(BaseApiService.class);
    }

    public static BaseApiService getDocService(){
        return RetrofitClient.getClient(BASE_URL_DOC).create(BaseApiService.class);
    }
}
