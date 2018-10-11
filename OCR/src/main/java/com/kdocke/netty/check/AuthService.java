package com.kdocke.netty.check;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 获取token类: 获取 access_token
 * @author Kdocke[kdocked@gmail.com]
 * @create 2018/10/11 - 9:10
 */
public class AuthService {

    /**
     * 获取权限token
     * @return 返回示例：
     * result: {
     * 	"access_token": "24.5c47b5b6bf139acecc1deb923c06cc08.2592000.1541814764.282335-14393453",
     * 	"session_key": "9mzdDtGSBPtaeiE5cvcT8mESRpVPc5IAxGs9TX9pSbxgc+oG1Jv09hfnppQoDk0NlymcW1tFqE4IYEnOM15LehilNrh\/lw==",
     * 	"scope": "public vis-classify_dishes vis-classify_car brain_ocr_general_basic brain_all_scope vis-classify_animal vis-classify_plant brain_object_detect brain_realtime_logo brain_dish_detect brain_car_detect brain_animal_classify brain_plant_classify brain_advanced_general_classify wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test\u6743\u9650 vis-classify_flower lpq_\u5f00\u653e cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey",
     * 	"refresh_token": "25.8c915de57268fd59061224f25b53b917.315360000.1854582764.282335-14393453",
     * 	"session_secret": "d1bb7900963cedfd0aa6606faea46204",
     * 	"expires_in": 2592000
     * }
     */
    public static String getAuth(){
        // 官网获取的 API Key 更新为你注册的
        String clientId = "***";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret  = "***";
        
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak 百度云官网获取的 API Key
     * @param sk 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取 token 地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            final URL realurl = new URL(getAccessTokenUrl);

            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realurl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }

            // 定义 BufferedReader 输入流来读取 URL 的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            // 返回结果示例
            System.err.println("result: " + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;

        } catch (Exception e) {
            System.out.println("获取 token 失败!");
            e.printStackTrace(System.err);
        }
        return null;
    }

}
