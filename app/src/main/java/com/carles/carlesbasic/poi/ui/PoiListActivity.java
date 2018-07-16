package com.carles.carlesbasic.poi.ui;

import com.carles.carlesbasic.CarlesApplication;
import com.carles.carlesbasic.R;
import com.carles.carlesbasic.poi.di.DaggerPoiComponent;
import com.carles.carlesbasic.poi.di.PoiModule;
import com.carles.carlesbasic.poi.model.Poi;
import com.carles.common.ui.BaseActivity;
import com.carles.common.ui.BaseContract;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import javax.inject.Inject;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class PoiListActivity extends BaseActivity implements PoiListContract.View,
    PoiListAdapter.PoiListAdapterListener {

    private RecyclerView recyclerView;
    private PoiListAdapter poiListAdapter;
    @Inject
    PoiListContract.Presenter presenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_poi_list;
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.poilist_title);

        recyclerView = findViewById(R.id.poilist_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        poiListAdapter = new PoiListAdapter();
        poiListAdapter.setPoiListAdapterListener(this);
        recyclerView.setAdapter(poiListAdapter);
    }

    @Override
    protected void initComponents() {
        DaggerPoiComponent.builder()
            .applicationComponent(CarlesApplication.getApplicationComponent(this))
            .poiModule(new PoiModule())
            .build().inject(this);
    }

    @Override
    protected BaseContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void displayPoiList(List<Poi> poiList) {
        poiListAdapter.setItems(poiList);
    }

    @Override
    public void navigateToPoiDetail(String id) {
        startActivity(PoiDetailActivity.makeIntent(this, id));
    }

    @Override
    public void onPoiClicked(Poi poi) {
        presenter.onPoiClicked(poi);
    }
}
