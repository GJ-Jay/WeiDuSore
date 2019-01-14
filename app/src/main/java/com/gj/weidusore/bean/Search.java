package com.gj.weidusore.bean;

import java.util.List;

public class Search {

    /**
     * commodityId : 141
     * commodityName : AUXTUN港仔原宿男鞋秋季鞋子男潮鞋百搭韩版潮流男士休闲鞋板鞋
     * masterPic : http://172.17.8.100/images/small/commodity/nx/nbx/7/1.jpg
     * price : 99
     * saleNum : 0
     */

    private int commodityId;
    private String commodityName;
    private String masterPic;
    private double  price;
    private int saleNum;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getMasterPic() {
        return masterPic;
    }

    public void setMasterPic(String masterPic) {
        this.masterPic = masterPic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

}
