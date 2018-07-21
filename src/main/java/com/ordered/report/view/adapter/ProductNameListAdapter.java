package com.ordered.report.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.view.activity.OrderDetailsActivity;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nithish on 24/02/18.
 */

public class ProductNameListAdapter extends RecyclerView.Adapter<ProductNameListAdapter.ProductNameViewHolder> {

    private List<OrderDetailsListViewModel> orderDetailsListViewModels;
    private OrderDetailsActivity orderDetailsActivity;
    private ProductNameListAdapterCallBack productNameListAdapterCallBack;

    public ProductNameListAdapter(Context context, List<OrderDetailsListViewModel> orderDetailsListViewModels) {
        orderDetailsActivity = (OrderDetailsActivity) context;
        this.orderDetailsListViewModels = orderDetailsListViewModels;
        productNameListAdapterCallBack = orderDetailsActivity;
    }

    @Override
    public ProductNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(orderDetailsActivity).inflate(R.layout.adapter_product_list, parent, false);

        return new ProductNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductNameViewHolder holder, int position) {
        final OrderDetailsListViewModel orderDetailsListViewModel = orderDetailsListViewModels.get(position);

        holder.productName.setText(orderDetailsListViewModel.getOrderItemName());
        holder.productGroup.setText(orderDetailsListViewModel.getOrderItemGroup());

        holder.productColor.setText(orderDetailsListViewModel.getOrderItemColor());

        formDateTime(orderDetailsListViewModel.getOrderItemCreatedDateTime(),holder.createDateTime);
        formDateTime(orderDetailsListViewModel.getOrderItemCreatedDateTime(),holder.lastModifiedDateTime);

        setProductVal(holder.orderedOneSize,"OneSize",orderDetailsListViewModel.getOrderItemOneSize());
        setProductVal(holder.orderedXS,"XS",orderDetailsListViewModel.getOrderItemXS());
        setProductVal(holder.orderedSmall,"S",orderDetailsListViewModel.getOrderItemS());
        setProductVal(holder.orderedMedium,"M",orderDetailsListViewModel.getOrderItemM());
        setProductVal(holder.orderedLarge,"L",orderDetailsListViewModel.getOrderItemL());
        setProductVal(holder.orderedXl,"XL",orderDetailsListViewModel.getOrderItemXl());
        setProductVal(holder.orderedXxl,"XXL",orderDetailsListViewModel.getOrderItemXxl());
        setProductVal(holder.orderedXxxl,"XXXL",orderDetailsListViewModel.getOrderItemXxxl());

    }


    private void setProductVal(TextView textView,String key,String val){
        if(val != null && !val.isEmpty()){
            textView.setText(key+" --> "+val);
        }else{
            textView.setText(key+" --> 0");
        }
    }


    private void formDateTime(long milliSeconds,TextView createDateTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date(milliSeconds);
        String res =  simpleDateFormat.format(date);
        createDateTime.setText(res);
    }




    @Override
    public int getItemCount() {
        return orderDetailsListViewModels.size();
    }


    public class ProductNameViewHolder extends RecyclerView.ViewHolder {

        public TextView productName;
        public TextView productGroup;
        public TextView productColor;


        private TextView orderedOneSize;
        private TextView orderedXS;
        private TextView orderedSmall;
        private TextView orderedMedium;
        private TextView orderedLarge;
        private TextView orderedXl;
        private TextView orderedXxl;
        private TextView orderedXxxl;

        private TextView createDateTime;
        private TextView lastModifiedDateTime;


        public ProductNameViewHolder(View itemView) {
            super(itemView);
            productName =  itemView.findViewById(R.id.product_name_sel);
            productGroup = itemView.findViewById(R.id.product_group_sel);
            productColor =  itemView.findViewById(R.id.product_color_sel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderDetailsListViewModel orderDetailsListViewModel =  orderDetailsListViewModels.get(getAdapterPosition());
                    orderDetailsActivity.setOrderDetailsListViewModel(orderDetailsListViewModel);
                    productNameListAdapterCallBack.callBack();
                    return;
                }
            });


            orderedOneSize = itemView.findViewById(R.id.ordered_one_size_count);
            orderedXS = itemView.findViewById(R.id.ordered_l_count);
            orderedSmall = itemView.findViewById(R.id.ordered_xs_count);
            orderedMedium = itemView.findViewById(R.id.ordered_xl_count);
            orderedLarge = itemView.findViewById(R.id.ordered_s_count);
            orderedXl = itemView.findViewById(R.id.ordered_xxxl_count);
            orderedXxl = itemView.findViewById(R.id.ordered_m_count);
            orderedXxxl = itemView.findViewById(R.id.ordered_xxl_count);

            createDateTime = itemView.findViewById(R.id.order_details_created_date);
            lastModifiedDateTime = itemView.findViewById(R.id.order_details_lastmodified_val);


        }
    }

    public interface ProductNameListAdapterCallBack{
        void callBack();
    }

}
