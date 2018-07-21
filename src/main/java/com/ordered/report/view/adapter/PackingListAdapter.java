package com.ordered.report.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ordered.report.R;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.activity.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nithish on 25/02/18.
 */

public class PackingListAdapter extends RecyclerView.Adapter<PackingListAdapter.PackingListViewHolder>{

    private List<OrderEntity> orderEntities;
    private HomeActivity homeActivity;
    private PackingListAdapterCallBack packingListAdapterCallBack;

    public PackingListAdapter(Context context, List<OrderEntity> orderEntities) {
        homeActivity = (HomeActivity) context;
        this.orderEntities = orderEntities;
        packingListAdapterCallBack = homeActivity;
    }

    @Override
    public PackingListAdapter.PackingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(homeActivity).inflate(R.layout.adapter_order_list, parent, false);

        return new PackingListAdapter.PackingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PackingListAdapter.PackingListViewHolder holder, int position) {
        final OrderEntity orderEntity = orderEntities.get(position);

        holder.orderTitle.setText(orderEntity.getOrderId());
        holder.clientName.setText(orderEntity.getClientName());
        holder.createdBy.setText(orderEntity.getCreatedBy());
        int orderCount = getOrderItemsCount(orderEntity);
        holder.orderItemsCount.setText(String.valueOf(orderCount));
        holder.orderImage.setImageResource(R.drawable.order_packing);
        holder.createdDate.setText(formatDate(orderEntity.getOrderedDate()));
        if(orderEntity.getOrderStatus().toString().equals(OrderStatus.PACKING.toString()) ||orderEntity.getOrderStatus().toString().equals(OrderStatus.PARTIAL_DELIVERED.toString())  ){
            holder.deliveryView.setVisibility(View.VISIBLE);
        }

    }


    private int getOrderItemsCount(OrderEntity orderEntity) {
        String orderedItems = orderEntity.getOrderedItems();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(orderedItems);
        return jsonArray.size();
    }


    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }

    @Override
    public int getItemCount() {
        return orderEntities.size();
    }



    public class PackingListViewHolder extends RecyclerView.ViewHolder {

        public TextView orderTitle;
        public TextView clientName;
        public TextView createdDate;
        public TextView createdBy;
        public TextView orderItemsCount;
        public ImageView orderImage;
        public RelativeLayout relativeLayout;
        public ImageView deliveryView;



        public PackingListViewHolder(View itemView) {
            super(itemView);
            orderTitle = (TextView) itemView.findViewById(R.id.ordered_list_order_id);
            clientName = (TextView) itemView.findViewById(R.id.ordered_list_client_name);
            createdDate = (TextView) itemView.findViewById(R.id.ordered_list_order_date);
            createdBy = (TextView) itemView.findViewById(R.id.ordered_list_created_by);
            orderItemsCount = (TextView) itemView.findViewById(R.id.ordered_list_ordered_items);
            orderImage = (ImageView) itemView.findViewById(R.id.order_image);
            relativeLayout = itemView.findViewById(R.id.order_list_details_layout);
            deliveryView = itemView.findViewById(R.id.ordered_list_packing_delivery);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderEntity orderEntity =  orderEntities.get(getAdapterPosition());
                    packingListAdapterCallBack.showPackingDetails(orderEntity.getOrderGuid(),null);
                    return;
                }
            });

            deliveryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderEntity orderEntity =  orderEntities.get(getAdapterPosition());
                    packingListAdapterCallBack.showPackingDetails(orderEntity.getOrderGuid(), Constants.VIEW_DELIVERY);
                    return;
                }
            });
        }
    }


    public interface PackingListAdapterCallBack{
        void showPackingDetails(String orderGuid,String nextView);
    }
}
