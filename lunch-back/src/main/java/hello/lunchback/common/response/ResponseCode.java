package hello.lunchback.common.response;

public interface ResponseCode {

    // http status 200
    String SUCCESS = "SU";

    // http status 400
    String VALIDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_NICKNAME = "DN";
    String DUPLICATE_TEL_NUMBER = "DT";
    String NOT_EXISTED_USER = "NU";
    String NOT_EXISTED_STORE = "NS";
    String NOT_EXISTED_MENU = "NM";
    // http status 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";

    //http status 403
    String NO_PERMISSION = "NP";

    //http status 500
    String DATABASE_ERROR = "DBE";
    String NOT_EXISTED_ORDER_LIST ="NOL";
}
