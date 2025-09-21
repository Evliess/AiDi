package evl.io.constant;

public class ServiceConstants {
    public static final String OK = "ok";
    public static final String NG = "ng";
    public static final String DOUBLE_COLON = "::";
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String ILLEGAL_ARGS_MSG = "参数错误！";
    public static final String DATE_PATTERN_FULL = "^(?:(?:19|20)\\d{2})/(?:(?:0[1-9]|1[0-2]))/(?:(?:0[1-9]|[12]\\d|3[01]))$";
    public static final String PHONE_PATTERN = "^1[3-9]\\d{9}$";
    public static final String PUBLIC_PATH_PREFIX = "/public/";
    public static final String X_TOKEN = "X-token";
    public static final String X_OPENID = "X-Openid";
    public static final String NA = "无";
    public static final String TYPE_DAY = "day";
    public static final String TOKEN = "token";
    public static final String APP_SECRET = "APP_SECRET";
    public static final String APP_ID = "APP_ID";
    public static final String UID_ENDPOINT = "https://api.weixin.qq.com/sns/jscode2session?appid=${appid}&secret=${secret}&js_code=${code}&grant_type=authorization_code";

}
