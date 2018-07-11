package com.carles.carlesbasic.presentation;

import com.carles.carlesbasic.R;
import com.carles.carlesbasic.data.datasource.PoiCloudDatasource;
import com.carles.carlesbasic.data.datasource.PoiLocalDatasource;
import com.carles.carlesbasic.data.entity.PoiMapper;
import com.carles.carlesbasic.data.repository.PoiRepository;
import com.carles.carlesbasic.domain.interactor.GetPoiListInteractor;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.presentation.BaseActivity;
import com.carles.common.presentation.BaseContract;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class PoiListActivity extends BaseActivity implements PoiListContract.View,
    PoiListAdapter.PoiListAdapterListener {

    private RecyclerView recyclerView;

    private PoiListAdapter poiListAdapter;
    private PoiListContract.Presenter presenter;
    private GetPoiListInteractor getPoiListInteractor;
    private PoiRepository poiRepository;
    private PoiCloudDatasource poiCloudDatasource;
    private PoiLocalDatasource poiLocalDatasource;
    private PoiMapper poiMapper;

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
        poiMapper = new PoiMapper();
        poiLocalDatasource = new PoiLocalDatasource(poiMapper);
        poiCloudDatasource = new PoiCloudDatasource(poiMapper, poiLocalDatasource);
        poiRepository = new PoiRepository(poiLocalDatasource, poiCloudDatasource);
        getPoiListInteractor = new GetPoiListInteractor(poiRepository);
        presenter = new PoiListPresenter(getPoiListInteractor);
        presenter.onViewCreated(this);
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
