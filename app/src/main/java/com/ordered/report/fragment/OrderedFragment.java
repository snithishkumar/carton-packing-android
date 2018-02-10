package com.ordered.report.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ordered.report.R;
import com.ordered.report.adapter.OrderedListAdapter;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.models.CartonbookEntity;
import com.ordered.report.services.CartonbookService;
import com.ordered.report.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class OrderedFragment extends Fragment {
    private Context context;
    private List<CartonbookEntity> cartonbookEntities = null;
    private CartonbookService cartonbookService = null;
    RecyclerView recyclerView =null;
    public OrderedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordered, container, false);
        cartonbookService = new CartonbookService(getActivity());
        getActivity().setTitle("ORDER");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        OrderedListAdapter mAdapter = new OrderedListAdapter(getActivity(), getOrderedCartonBookList());
        recyclerView.setAdapter(mAdapter);
        copyDataBase();
        return view;
    }

    public void copyDataBase() {
        try {
            String fileName = getActivity().getApplicationInfo().dataDir + "/databases/order.db";
            Log.v("order", fileName);
            String resourcePath = FileUtils.getResourcePath(getActivity(), "orderdb.db");
            File file = new File(fileName);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                FileUtils.downloadStreamData(fileInputStream, resourcePath);
                fileInputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CartonbookEntity> getOrderedCartonBookList() {
        cartonbookEntities = cartonbookService.getCartonBookEntityByType(OrderType.ORDERED);
        return cartonbookEntities;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }
}
