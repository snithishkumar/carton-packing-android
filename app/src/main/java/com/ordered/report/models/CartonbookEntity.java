package com.ordered.report.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.enumeration.PaymentStatus;
import com.ordered.report.json.models.CartonItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/1/2018.
 */

public class CartonbookEntity {
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
    @DatabaseField(persisted = false)
    private List<CartonItemModel> orderDetails = new ArrayList<>();

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

    public List<CartonItemModel> getCartonItemModelList() {
        return orderDetails;
    }

    public void setCartonItemModelList(List<CartonItemModel> cartonItemModelList) {
        this.orderDetails = cartonItemModelList;
    }

    @Override
    public String toString() {
        return "CartonbookEntity{" +
                "Id=" + Id +
                ", orderId='" + orderId + '\'' +
                ", orderGuid='" + orderGuid + '\'' +
                ", clientName='" + clientName + '\'' +
                ", orderType=" + orderStatus +
                ", paymentStatus=" + paymentStatus +
                ", orderedDate=" + orderedDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", serverTime=" + serverTime +
                ", isSync=" + isSync +
                ", orderDetail=" + orderDetails +
                '}';
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public List<CartonItemModel> getOrderDetail() {
        return orderDetails;
    }

    public void setOrderDetail(List<CartonItemModel> orderDetail) {
        this.orderDetails = orderDetail;
    }
}
