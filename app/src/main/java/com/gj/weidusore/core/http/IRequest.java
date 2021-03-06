package com.gj.weidusore.core.http;

import com.gj.weidusore.bean.Circle;
import com.gj.weidusore.bean.Comment;
import com.gj.weidusore.bean.DetailsBean;
import com.gj.weidusore.bean.Foot;
import com.gj.weidusore.bean.Goods;
import com.gj.weidusore.bean.GoodsBanner;
import com.gj.weidusore.bean.MyCircle;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.Search;
import com.gj.weidusore.bean.UserInfoLogin;
import com.gj.weidusore.bean.address.Address;
import com.gj.weidusore.bean.car.Car;
import com.gj.weidusore.bean.car.ShopCar;
import com.gj.weidusore.bean.homebean.GoodsList;
import com.gj.weidusore.bean.order.Null_Bean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IRequest {

    @POST("user/v1/login")//登录
    @FormUrlEncoded
    Observable<ResultLogin<UserInfoLogin>> login(@Field("phone") Object phone,
                                                 @Field("pwd") Object pwd);


    @POST("user/v1/register")//注册
    @FormUrlEncoded
    Observable<ResultLogin> reg(@Field("phone") String phone,
                                @Field("pwd") String pwd);

    @GET("commodity/v1/bannerShow")//首页轮播图
    Observable<ResultLogin<List<GoodsBanner>>> lunboBanner();

    @GET("commodity/v1/commodityList")//首页列表
    Observable<ResultLogin<GoodsList>> getList();

    @GET("commodity/v1/findCommodityByKeyword")//搜索
    Observable<ResultLogin<List<Search>>> getSearch(@Query("keyword")String keyword,
                                                    @Query("page")int page,
                                                    @Query("count")int count);

    @GET("circle/v1/findCircleList")//圈子列表
    Observable<ResultLogin<List<Circle>>> getCircle(@Header("userId")long userId,
                                                    @Header("sessionId")String sessionId,
                                                    @Query("page")int page,
                                                    @Query("count")int count);

    @GET("circle/verify/v1/findMyCircleById")//我的圈子列表
    Observable<ResultLogin<List<MyCircle>>> getMyCircle(@Header("userId")long userId,
                                                        @Header("sessionId")String sessionId,
                                                        @Query("page")int page,
                                                        @Query("count")int count);


    //首页商品信息列表详情
    @GET("commodity/v1/findCommodityDetailsById")
    Observable<ResultLogin<Goods>> homegoods (@Header("userId") long userId,
                                         @Header("sessionId") String sessionId,
                                         @Query("commodityId") int commodityId);

    /**
     * 商品详情
     * @param userId
     * @param sessionId
     * @param commodityId
     * @return
     */
    @GET("commodity/v1/findCommodityDetailsById")
    Observable<ResultLogin<DetailsBean>> details(@Header("userId")long userId,
                                            @Header("sessionId")String sessionId,
                                            @Query("commodityId")int commodityId);

    /**
     * 购物车列表
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("order/verify/v1/findShoppingCart")
    Observable<ResultLogin<List<ShopCar>>> shopcar(@Header("userId")long userId,
                                                   @Header("sessionId")String sessionId);

    /**
     * 同步购物车数据
     * @param userId
     * @param sessionId
     * @param data
     * @return
     */
    @PUT("order/verify/v1/syncShoppingCart")
    @FormUrlEncoded
    Observable<ResultLogin> addTo(@Header("userId") long userId,
                             @Header("sessionId") String sessionId,
                             @Field("data") String data);
    //评论
    @GET("commodity/v1/CommodityCommentList")
    Observable<ResultLogin<List<Comment>>> comment(@Query("commodityId")int commodityId,
                                              @Query("page")int page,
                                              @Query("count")int count);

    @GET("commodity/verify/v1/browseList")//我的足迹
    Observable<ResultLogin<List<Foot>>> getFoot(@Header("userId")long userId,
                                                @Header("sessionId")String sessionId,
                                                @Query("page")int page,
                                                @Query("count")int count);

    //展示购物车
    @GET("order/verify/v1/findShoppingCart")
    Observable<ResultLogin<List<Car>>> getCar(@Header("userId")long userId,
                                              @Header("sessionId")String sessionId);

    //我的收货地址列表
    @GET("user/verify/v1/receiveAddressList")
    Observable<ResultLogin<List<Address>>> addressList(@Header("userId")long userId,
                                                  @Header("sessionId")String sessionId);

    //默认收货地址完成
    @POST("user/verify/v1/setDefaultReceiveAddress")
    @FormUrlEncoded
    Observable<ResultLogin> moren(@Header("userId")int userId,
                                  @Header("sessionId")String sessionId,
                                  @Field("id") int id);

    //新增收货地址
    @POST("user/verify/v1/addReceiveAddress")
    @FormUrlEncoded
    Observable<ResultLogin> addAddrss(@Header("userId")int userId,
                                      @Header("sessionId")String sessionId,
                                      @Field("realName") String realName,
                                      @Field("phone")String phone,
                                      @Field("address")String address,
                                      @Field("zipCode")String zipCode);

    /*//查询订单
    @GET("order/verify/v1/findOrderListByStatus")
    Observable<ResultLogin<List<OrderBean>>> order(@Header("userId")long userId,
                                                       @Header("sessionId")String sessionId,
                                                       @Query("status") int status,
                                                       @Query("page")int page,
                                                       @Query("count")int count);*/

    /*
   订单
    */
    @GET("order/verify/v1/findOrderListByStatus")
    Observable<ResultLogin<List<Null_Bean>>> order(@Header("userId") long userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Query("page") int page,
                                                   @Query("count") int count,
                                                   @Query("status") int status);

    /**
     * 发布圈子
     */
    @POST("circle/verify/v1/releaseCircle")
    Observable<ResultLogin> releaseCircle(@Header("userId") long userId,
                                     @Header("sessionId")String sessionId,
                                     @Body MultipartBody body);
}
