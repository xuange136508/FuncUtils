package com.my.loopiine.utils.userdemo.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * JSON 解析的demo，运用注解
 * http://wthrcdn.etouch.cn/weather_mini?city=北京
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfo {


    @JsonProperty("desc")
    public String desc;
    @JsonProperty("status")
    public int status;

    @JsonProperty("data")
    public DataEntity data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataEntity {

        @Override
        public String toString() {
            return "DataEntity{" +
                    "wendu='" + wendu + '\'' +
                    ", ganmao='" + ganmao + '\'' +
                    ", yesterday=" + yesterday +
                    ", aqi='" + aqi + '\'' +
                    ", city='" + city + '\'' +
                    ", forecast=" + forecast +
                    '}';
        }

        @JsonProperty("wendu")
        public String wendu;
        @JsonProperty("ganmao")
        public String ganmao;

        @JsonProperty("yesterday")
        public YesterdayEntity yesterday;
        @JsonProperty("aqi")
        public String aqi;
        @JsonProperty("city")
        public String city;

        @JsonProperty("forecast")
        public List<ForecastEntity> forecast;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class YesterdayEntity {

            @Override
            public String toString() {
                return "YesterdayEntity{" +
                        "fl='" + fl + '\'' +
                        ", fx='" + fx + '\'' +
                        ", high='" + high + '\'' +
                        ", type='" + type + '\'' +
                        ", low='" + low + '\'' +
                        ", date='" + date + '\'' +
                        '}';
            }

            @JsonProperty("fl")
            public String fl;
            @JsonProperty("fx")
            public String fx;
            @JsonProperty("high")
            public String high;
            @JsonProperty("type")
            public String type;
            @JsonProperty("low")
            public String low;
            @JsonProperty("date")
            public String date;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ForecastEntity {

            @Override
            public String toString() {
                return "ForecastEntity{" +
                        "fengxiang='" + fengxiang + '\'' +
                        ", fengli='" + fengli + '\'' +
                        ", high='" + high + '\'' +
                        ", type='" + type + '\'' +
                        ", low='" + low + '\'' +
                        ", date='" + date + '\'' +
                        '}';
            }

            @JsonProperty("fengxiang")
            public String fengxiang;
            @JsonProperty("fengli")
            public String fengli;
            @JsonProperty("high")
            public String high;
            @JsonProperty("type")
            public String type;
            @JsonProperty("low")
            public String low;
            @JsonProperty("date")
            public String date;
        }
    }
}
/**
 *
 * {
     "desc": "OK",
     "status": 1000,
     "data": {
     "wendu": "8",
     "ganmao": "灏嗘湁涓€娆″己闄嶆俯杩囩▼锛屽ぉ姘斿瘨鍐凤紝鏋佹槗鍙戠敓鎰熷啋锛岃鐗瑰埆娉ㄦ剰澧炲姞琛ｆ湇淇濇殩闃插瘨銆�",
     "forecast": [
         {
         "fengxiang": "鍖楅",
         "fengli": "3-4绾�",
         "high": "楂樻俯 13鈩�",
         "type": "澶氫簯",
         "low": "浣庢俯 0鈩�",
         "date": "30鏃ユ槦鏈熷ぉ"
         },
         {
         "fengxiang": "鏃犳寔缁鍚�",
         "fengli": "3-4绾�",
         "high": "楂樻俯 6鈩�",
         "type": "鏅�",
         "low": "浣庢俯 -3鈩�",
         "date": "31鏃ユ槦鏈熶竴"
         },
         {
         "fengxiang": "鏃犳寔缁鍚�",
         "fengli": "寰绾�",
         "high": "楂樻俯 8鈩�",
         "type": "澶氫簯",
         "low": "浣庢俯 -1鈩�",
         "date": "1鏃ユ槦鏈熶簩"
         },
         {
         "fengxiang": "鏃犳寔缁鍚�",
         "fengli": "寰绾�",
         "high": "楂樻俯 12鈩�",
         "type": "鏅�",
         "low": "浣庢俯 2鈩�",
         "date": "2鏃ユ槦鏈熶笁"
         },
         {
         "fengxiang": "鏃犳寔缁鍚�",
         "fengli": "寰绾�",
         "high": "楂樻俯 14鈩�",
         "type": "鏅�",
         "low": "浣庢俯 3鈩�",
         "date": "3鏃ユ槦鏈熷洓"
         }
     ],
     "yesterday": {
         "fl": "寰",
         "fx": "鏃犳寔缁鍚�",
         "high": "楂樻俯 12鈩�",
         "type": "鏅�",
         "low": "浣庢俯 2鈩�",
         "date": "29鏃ユ槦鏈熷叚"
         },
         "aqi": "20",
         "city": "鍖椾含"
         }
     }
 *
 * **/