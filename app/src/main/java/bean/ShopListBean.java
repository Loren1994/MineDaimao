package bean;

import java.util.List;

/**
 * Created by victor on 17-1-6
 */

public class ShopListBean {

    /**
     * statusCode : 200
     * statusMsg :
     * result : {"store_list":[{"store_id":"11","store_name":"哈哈哈","grade_id":"1","member_id":"105","member_name":"hehehe","seller_name":"hehehe","sc_id":"18","store_company_name":"","province_id":"0","area_info":"","store_zip":"","store_state":"1","store_close_info":"","store_sort":"0","store_time":"1479195735","store_end_time":"","store_label":"http://120.77.36.105/tlg/data/u","store_banner":"","store_avatar":"","store_keywords":"","store_description":"","store_qq":"","store_ww":"","store_phone":"","store_zy":"","store_domain":"","store_domain_times":"0","store_recommend":"0","store_theme":"default","store_credit":"0","store_desccredit":"0","store_servicecredit":"0","store_deliverycredit":"0","store_collect":"0","store_slide":"","store_slide_url":"","store_stamp":"","store_printdesc":"","store_sales":"0","store_presales":"","store_aftersales":"","store_workingtime":"","store_free_price":"0.00","store_bail":"0.00","store_decoration_switch":"0","store_decoration_only":"0","store_decoration_image_count":"0","live_store_name":"","live_store_address":"","live_store_tel":"","live_store_bus":"","is_own_shop":"0","bind_all_gc":"1","store_vrcode_prefix":"","store_baozh":"0","store_baozhopen":"0","store_baozhrmb":"","store_qtian":"0","store_zhping":"0","store_erxiaoshi":"0","store_tuihuo":"0","store_shiyong":"0","store_shiti":"0","store_xiaoxie":"0","store_huodaofk":"0","pos_x":"120.38944500000000000","pos_y":"36.07235800000000000","city_id":"3","area_id":"4","commercial_id":"0","store_address":"乌兰察布啦啦啦","store_star":"1.0","store_type":"0","store_tags":"停车场","store_rebate":"0.00","store_fee":"0.00","store_office":"","is_showing":"1","starting_price":"0.25","sales_volume":"0","eva_volume":"0"},{"store_id":"22","store_name":"dytest","grade_id":"1","member_id":"113","member_name":"dytest","seller_name":"dytest","sc_id":"21","store_company_name":"","province_id":"0","area_info":"","store_zip":"","store_state":"1","store_close_info":"","store_sort":"0","store_time":"1481787821","store_end_time":"","store_label":"http://ohwtlugwp.bkt.clouddn.com/","store_banner":"","store_avatar":"","store_keywords":"","store_description":"","store_qq":"","store_ww":"","store_phone":"","store_zy":"","store_domain":"","store_domain_times":"0","store_recommend":"0","store_theme":"default","store_credit":"0","store_desccredit":"0","store_servicecredit":"0","store_deliverycredit":"0","store_collect":"0","store_slide":"","store_slide_url":"","store_stamp":"","store_printdesc":"","store_sales":"0","store_presales":"","store_aftersales":"","store_workingtime":"","store_free_price":"0.00","store_bail":"0.00","store_decoration_switch":"0","store_decoration_only":"0","store_decoration_image_count":"0","live_store_name":"","live_store_address":"","live_store_tel":"","live_store_bus":"","is_own_shop":"0","bind_all_gc":"1","store_vrcode_prefix":"","store_baozh":"0","store_baozhopen":"0","store_baozhrmb":"","store_qtian":"0","store_zhping":"0","store_erxiaoshi":"0","store_tuihuo":"0","store_shiyong":"0","store_shiti":"0","store_xiaoxie":"0","store_huodaofk":"0","pos_x":"120.33920700000000000","pos_y":"36.08796700000000000","city_id":"3","area_id":"3","commercial_id":"1","store_address":"oasdiogn","store_star":"5.0","store_type":"0","store_tags":"","store_rebate":"0.00","store_fee":"0.00","store_office":"8:00~2:00","is_showing":"1","starting_price":"0.00","sales_volume":"0","eva_volume":"0"}],"hasmore":false,"page_total":1}
     */

    private int statusCode;
    private String statusMsg;
    private ResultBean result;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private boolean hasmore;
        private int page_total;
        private List<StoreListBean> store_list;

        public boolean isHasmore() {
            return hasmore;
        }

        public void setHasmore(boolean hasmore) {
            this.hasmore = hasmore;
        }

        public int getPage_total() {
            return page_total;
        }

        public void setPage_total(int page_total) {
            this.page_total = page_total;
        }

        public List<StoreListBean> getStore_list() {
            return store_list;
        }

        public void setStore_list(List<StoreListBean> store_list) {
            this.store_list = store_list;
        }

        public static class StoreListBean {

            private String store_id;
            private String store_name;
            private String grade_id;
            private String member_id;
            private String member_name;
            private String seller_name;
            private String sc_id;
            private String store_company_name;
            private String province_id;
            private String area_info;
            private String store_zip;
            private String store_state;
            private String store_close_info;
            private String store_sort;
            private String store_time;
            private String store_end_time;
            private String store_label;
            private String store_banner;
            private String store_avatar;
            private String store_keywords;
            private String store_description;
            private String store_qq;
            private String store_ww;
            private String store_phone;
            private String store_zy;
            private String store_domain;
            private String store_domain_times;
            private String store_recommend;
            private String store_theme;
            private String store_credit;
            private String store_desccredit;
            private String store_servicecredit;
            private String store_deliverycredit;
            private String store_collect;
            private String store_slide;
            private String store_slide_url;
            private String store_stamp;
            private String store_printdesc;
            private String store_sales;
            private String store_presales;
            private String store_aftersales;
            private String store_workingtime;
            private String store_free_price;
            private String store_bail;
            private String store_decoration_switch;
            private String store_decoration_only;
            private String store_decoration_image_count;
            private String live_store_name;
            private String live_store_address;
            private String live_store_tel;
            private String live_store_bus;
            private String is_own_shop;
            private String bind_all_gc;
            private String store_vrcode_prefix;
            private String store_baozh;
            private String store_baozhopen;
            private String store_baozhrmb;
            private String store_qtian;
            private String store_zhping;
            private String store_erxiaoshi;
            private String store_tuihuo;
            private String store_shiyong;
            private String store_shiti;
            private String store_xiaoxie;
            private String store_huodaofk;
            private String pos_x;
            private String pos_y;
            private String city_id;
            private String area_id;
            private String commercial_id;
            private String store_address;
            private String store_star;
            private String store_type;
            private String store_tags;
            private String store_rebate;
            private String store_fee;
            private String store_office;
            private String is_showing;
            private String starting_price;
            private String sales_volume;
            private String eva_volume;
            private int distance;

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getGrade_id() {
                return grade_id;
            }

            public void setGrade_id(String grade_id) {
                this.grade_id = grade_id;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getSeller_name() {
                return seller_name;
            }

            public void setSeller_name(String seller_name) {
                this.seller_name = seller_name;
            }

            public String getSc_id() {
                return sc_id;
            }

            public void setSc_id(String sc_id) {
                this.sc_id = sc_id;
            }

            public String getStore_company_name() {
                return store_company_name;
            }

            public void setStore_company_name(String store_company_name) {
                this.store_company_name = store_company_name;
            }

            public String getProvince_id() {
                return province_id;
            }

            public void setProvince_id(String province_id) {
                this.province_id = province_id;
            }

            public String getArea_info() {
                return area_info;
            }

            public void setArea_info(String area_info) {
                this.area_info = area_info;
            }

            public String getStore_zip() {
                return store_zip;
            }

            public void setStore_zip(String store_zip) {
                this.store_zip = store_zip;
            }

            public String getStore_state() {
                return store_state;
            }

            public void setStore_state(String store_state) {
                this.store_state = store_state;
            }

            public String getStore_close_info() {
                return store_close_info;
            }

            public void setStore_close_info(String store_close_info) {
                this.store_close_info = store_close_info;
            }

            public String getStore_sort() {
                return store_sort;
            }

            public void setStore_sort(String store_sort) {
                this.store_sort = store_sort;
            }

            public String getStore_time() {
                return store_time;
            }

            public void setStore_time(String store_time) {
                this.store_time = store_time;
            }

            public String getStore_end_time() {
                return store_end_time;
            }

            public void setStore_end_time(String store_end_time) {
                this.store_end_time = store_end_time;
            }

            public String getStore_label() {
                return store_label;
            }

            public void setStore_label(String store_label) {
                this.store_label = store_label;
            }

            public String getStore_banner() {
                return store_banner;
            }

            public void setStore_banner(String store_banner) {
                this.store_banner = store_banner;
            }

            public String getStore_avatar() {
                return store_avatar;
            }

            public void setStore_avatar(String store_avatar) {
                this.store_avatar = store_avatar;
            }

            public String getStore_keywords() {
                return store_keywords;
            }

            public void setStore_keywords(String store_keywords) {
                this.store_keywords = store_keywords;
            }

            public String getStore_description() {
                return store_description;
            }

            public void setStore_description(String store_description) {
                this.store_description = store_description;
            }

            public String getStore_qq() {
                return store_qq;
            }

            public void setStore_qq(String store_qq) {
                this.store_qq = store_qq;
            }

            public String getStore_ww() {
                return store_ww;
            }

            public void setStore_ww(String store_ww) {
                this.store_ww = store_ww;
            }

            public String getStore_phone() {
                return store_phone;
            }

            public void setStore_phone(String store_phone) {
                this.store_phone = store_phone;
            }

            public String getStore_zy() {
                return store_zy;
            }

            public void setStore_zy(String store_zy) {
                this.store_zy = store_zy;
            }

            public String getStore_domain() {
                return store_domain;
            }

            public void setStore_domain(String store_domain) {
                this.store_domain = store_domain;
            }

            public String getStore_domain_times() {
                return store_domain_times;
            }

            public void setStore_domain_times(String store_domain_times) {
                this.store_domain_times = store_domain_times;
            }

            public String getStore_recommend() {
                return store_recommend;
            }

            public void setStore_recommend(String store_recommend) {
                this.store_recommend = store_recommend;
            }

            public String getStore_theme() {
                return store_theme;
            }

            public void setStore_theme(String store_theme) {
                this.store_theme = store_theme;
            }

            public String getStore_credit() {
                return store_credit;
            }

            public void setStore_credit(String store_credit) {
                this.store_credit = store_credit;
            }

            public String getStore_desccredit() {
                return store_desccredit;
            }

            public void setStore_desccredit(String store_desccredit) {
                this.store_desccredit = store_desccredit;
            }

            public String getStore_servicecredit() {
                return store_servicecredit;
            }

            public void setStore_servicecredit(String store_servicecredit) {
                this.store_servicecredit = store_servicecredit;
            }

            public String getStore_deliverycredit() {
                return store_deliverycredit;
            }

            public void setStore_deliverycredit(String store_deliverycredit) {
                this.store_deliverycredit = store_deliverycredit;
            }

            public String getStore_collect() {
                return store_collect;
            }

            public void setStore_collect(String store_collect) {
                this.store_collect = store_collect;
            }

            public String getStore_slide() {
                return store_slide;
            }

            public void setStore_slide(String store_slide) {
                this.store_slide = store_slide;
            }

            public String getStore_slide_url() {
                return store_slide_url;
            }

            public void setStore_slide_url(String store_slide_url) {
                this.store_slide_url = store_slide_url;
            }

            public String getStore_stamp() {
                return store_stamp;
            }

            public void setStore_stamp(String store_stamp) {
                this.store_stamp = store_stamp;
            }

            public String getStore_printdesc() {
                return store_printdesc;
            }

            public void setStore_printdesc(String store_printdesc) {
                this.store_printdesc = store_printdesc;
            }

            public String getStore_sales() {
                return store_sales;
            }

            public void setStore_sales(String store_sales) {
                this.store_sales = store_sales;
            }

            public String getStore_presales() {
                return store_presales;
            }

            public void setStore_presales(String store_presales) {
                this.store_presales = store_presales;
            }

            public String getStore_aftersales() {
                return store_aftersales;
            }

            public void setStore_aftersales(String store_aftersales) {
                this.store_aftersales = store_aftersales;
            }

            public String getStore_workingtime() {
                return store_workingtime;
            }

            public void setStore_workingtime(String store_workingtime) {
                this.store_workingtime = store_workingtime;
            }

            public String getStore_free_price() {
                return store_free_price;
            }

            public void setStore_free_price(String store_free_price) {
                this.store_free_price = store_free_price;
            }

            public String getStore_bail() {
                return store_bail;
            }

            public void setStore_bail(String store_bail) {
                this.store_bail = store_bail;
            }

            public String getStore_decoration_switch() {
                return store_decoration_switch;
            }

            public void setStore_decoration_switch(String store_decoration_switch) {
                this.store_decoration_switch = store_decoration_switch;
            }

            public String getStore_decoration_only() {
                return store_decoration_only;
            }

            public void setStore_decoration_only(String store_decoration_only) {
                this.store_decoration_only = store_decoration_only;
            }

            public String getStore_decoration_image_count() {
                return store_decoration_image_count;
            }

            public void setStore_decoration_image_count(String store_decoration_image_count) {
                this.store_decoration_image_count = store_decoration_image_count;
            }

            public String getLive_store_name() {
                return live_store_name;
            }

            public void setLive_store_name(String live_store_name) {
                this.live_store_name = live_store_name;
            }

            public String getLive_store_address() {
                return live_store_address;
            }

            public void setLive_store_address(String live_store_address) {
                this.live_store_address = live_store_address;
            }

            public String getLive_store_tel() {
                return live_store_tel;
            }

            public void setLive_store_tel(String live_store_tel) {
                this.live_store_tel = live_store_tel;
            }

            public String getLive_store_bus() {
                return live_store_bus;
            }

            public void setLive_store_bus(String live_store_bus) {
                this.live_store_bus = live_store_bus;
            }

            public String getIs_own_shop() {
                return is_own_shop;
            }

            public void setIs_own_shop(String is_own_shop) {
                this.is_own_shop = is_own_shop;
            }

            public String getBind_all_gc() {
                return bind_all_gc;
            }

            public void setBind_all_gc(String bind_all_gc) {
                this.bind_all_gc = bind_all_gc;
            }

            public String getStore_vrcode_prefix() {
                return store_vrcode_prefix;
            }

            public void setStore_vrcode_prefix(String store_vrcode_prefix) {
                this.store_vrcode_prefix = store_vrcode_prefix;
            }

            public String getStore_baozh() {
                return store_baozh;
            }

            public void setStore_baozh(String store_baozh) {
                this.store_baozh = store_baozh;
            }

            public String getStore_baozhopen() {
                return store_baozhopen;
            }

            public void setStore_baozhopen(String store_baozhopen) {
                this.store_baozhopen = store_baozhopen;
            }

            public String getStore_baozhrmb() {
                return store_baozhrmb;
            }

            public void setStore_baozhrmb(String store_baozhrmb) {
                this.store_baozhrmb = store_baozhrmb;
            }

            public String getStore_qtian() {
                return store_qtian;
            }

            public void setStore_qtian(String store_qtian) {
                this.store_qtian = store_qtian;
            }

            public String getStore_zhping() {
                return store_zhping;
            }

            public void setStore_zhping(String store_zhping) {
                this.store_zhping = store_zhping;
            }

            public String getStore_erxiaoshi() {
                return store_erxiaoshi;
            }

            public void setStore_erxiaoshi(String store_erxiaoshi) {
                this.store_erxiaoshi = store_erxiaoshi;
            }

            public String getStore_tuihuo() {
                return store_tuihuo;
            }

            public void setStore_tuihuo(String store_tuihuo) {
                this.store_tuihuo = store_tuihuo;
            }

            public String getStore_shiyong() {
                return store_shiyong;
            }

            public void setStore_shiyong(String store_shiyong) {
                this.store_shiyong = store_shiyong;
            }

            public String getStore_shiti() {
                return store_shiti;
            }

            public void setStore_shiti(String store_shiti) {
                this.store_shiti = store_shiti;
            }

            public String getStore_xiaoxie() {
                return store_xiaoxie;
            }

            public void setStore_xiaoxie(String store_xiaoxie) {
                this.store_xiaoxie = store_xiaoxie;
            }

            public String getStore_huodaofk() {
                return store_huodaofk;
            }

            public void setStore_huodaofk(String store_huodaofk) {
                this.store_huodaofk = store_huodaofk;
            }

            public String getPos_x() {
                return pos_x;
            }

            public void setPos_x(String pos_x) {
                this.pos_x = pos_x;
            }

            public String getPos_y() {
                return pos_y;
            }

            public void setPos_y(String pos_y) {
                this.pos_y = pos_y;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getCommercial_id() {
                return commercial_id;
            }

            public void setCommercial_id(String commercial_id) {
                this.commercial_id = commercial_id;
            }

            public String getStore_address() {
                return store_address;
            }

            public void setStore_address(String store_address) {
                this.store_address = store_address;
            }

            public String getStore_star() {
                return store_star;
            }

            public void setStore_star(String store_star) {
                this.store_star = store_star;
            }

            public String getStore_type() {
                return store_type;
            }

            public void setStore_type(String store_type) {
                this.store_type = store_type;
            }

            public String getStore_tags() {
                return store_tags;
            }

            public void setStore_tags(String store_tags) {
                this.store_tags = store_tags;
            }

            public String getStore_rebate() {
                return store_rebate;
            }

            public void setStore_rebate(String store_rebate) {
                this.store_rebate = store_rebate;
            }

            public String getStore_fee() {
                return store_fee;
            }

            public void setStore_fee(String store_fee) {
                this.store_fee = store_fee;
            }

            public String getStore_office() {
                return store_office;
            }

            public void setStore_office(String store_office) {
                this.store_office = store_office;
            }

            public String getIs_showing() {
                return is_showing;
            }

            public void setIs_showing(String is_showing) {
                this.is_showing = is_showing;
            }

            public String getStarting_price() {
                return starting_price;
            }

            public void setStarting_price(String starting_price) {
                this.starting_price = starting_price;
            }

            public String getSales_volume() {
                return sales_volume;
            }

            public void setSales_volume(String sales_volume) {
                this.sales_volume = sales_volume;
            }

            public String getEva_volume() {
                return eva_volume;
            }

            public void setEva_volume(String eva_volume) {
                this.eva_volume = eva_volume;
            }
        }
    }
}
