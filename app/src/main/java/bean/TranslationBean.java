package bean;

import java.util.List;

/**
 * Created by loren on 2017/3/9.
 */

public class TranslationBean {

    /**
     * translation : ["罗兰"]
     * basic : {"explains":["n. 洛伦（男子名）"]}
     * query : Loren
     * errorCode : 0
     * web : [{"value":["洛伦","劳伦","爱子罗伦"],"key":"Loren"},{"value":["洛伦冰原岛峰"],"key":"Loren Nunataks"},{"value":["徐志达"],"key":"Loren Shuster"}]
     */

    private BasicBean basic;
    private String query;
    private int errorCode;
    private List<String> translation;
    private List<WebBean> web;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public static class BasicBean {
        private List<String> explains;

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean {
        /**
         * value : ["洛伦","劳伦","爱子罗伦"]
         * key : Loren
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
