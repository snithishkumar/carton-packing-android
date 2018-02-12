package com.ordered.report.models;

import com.google.gson.Gson;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.enumeration.PaymentStatus;
import com.ordered.report.json.models.CartonItemModel;
import com.ordered.report.json.models.OrderDetailsJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/1/2018.
 */

public class OrderEntity {
    public static final String ID = "ID";
    public static final String ORDER_ID = "OrderId";
    public static final String ORDER_GUID = "OrderGuid";
    public static final String CLIENT_NAME = "ClientName";
    public static final String ORDER_STATUS = "OrderStatus";
    public static final String PAYMENT_STATUS = "PaymentStatus";
    public static final String ORDER_DATE = "OrderedDate";
    public static final String PHONE_NUMBER = "PhoneNumber";
    public static final String LAST_MODIFIED_DATE = "LastModifiedDate";
    public static final String SERVER_TIME = "ServerTime";
    public static final String IS_SYNC = "IsSync";

    @DatabaseField(columnName = "Id", generatedId = true)
    private int Id;
    @DatabaseField(columnName = "OrderId")
    private String orderId;
    @DatabaseField(columnName = "OrderGuid")
    private String orderGuid;
    @DatabaseField(columnName = "clientName")
    private String clientName;
    @DatabaseField(columnName = "OrderStatus", dataType = DataType.ENUM_INTEGER)
    private OrderType orderStatus;
    @DatabaseField(columnName = "PaymentStatus", dataType = DataType.ENUM_INTEGER)
    private PaymentStatus paymentStatus;
    @DatabaseField(columnName = "OrderedDate")
    private long orderedDate;
    @DatabaseField(columnName = "LastModifiedDate")
    private long lastModifiedDate;
    @DatabaseField(columnName = "ServerTime")
    private long serverTime;
    @DatabaseField(columnName = "IsSync")
    private boolean isSync;

    @DatabaseField(columnName = "OrderedItems",dataType = DataType.LONG_STRING)
    private String orderedItems;


    @DatabaseField(columnName = "OrderedDetails",dataType = DataType.LONG_STRING)
    private String orderedDetails;




    public OrderEntity(){

    }


    public OrderEntity(OrderDetailsJson orderDetailsJson){
        this.orderId = orderDetailsJson.getOrderId();
        this.orderGuid = orderDetailsJson.getOrderGuid();
        this.clientName = null;
       // this.orderStatus = orderDetailsJson.getOrderStatus();
        this.paymentStatus = orderDetailsJson.getPaymentStatus();
        this.orderedDate = orderDetailsJson.getOrderedDate();
        this.serverTime = orderDetailsJson.getServerTime();
        this.isSync = true;
        Gson gson = new Gson();
        this.orderedItems = gson.toJson(orderDetailsJson.getOrderedItems());;

    }

    public String getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(String orderedItems) {
        this.orderedItems = orderedItems;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderGuid() {
        return orderGuid;
    }

    public void setOrderGuid(String orderGuid) {
        this.orderGuid = orderGuid;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public OrderType getOrderType() {
        return orderStatus;
    }

    public void setOrderType(OrderType orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public long getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(long orderedDate) {
        this.orderedDate = orderedDate;
    }

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }



    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public OrderType getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderType orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderedDetails() {
        return orderedDetails;
    }

    public void setOrderedDetails(String orderedDetails) {
        this.orderedDetails = orderedDetails;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "Id=" + Id +
                ", orderId='" + orderId + '\'' +
                ", orderGuid='" + orderGuid + '\'' +
                ", clientName='" + clientName + '\'' +
                ", orderStatus=" + orderStatus +
                ", paymentStatus=" + paymentStatus +
                ", orderedDate=" + orderedDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", serverTime=" + serverTime +
                ", isSync=" + isSync +
                ", orderedItems='" + orderedItems + '\'' +
                ", orderedDetails='" + orderedDetails + '\'' +
                '}';
    }
}
