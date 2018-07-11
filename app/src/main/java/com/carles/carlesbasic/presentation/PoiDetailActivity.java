package com.carles.carlesbasic.presentation;

import com.carles.carlesbasic.R;
import com.carles.carlesbasic.data.datasource.PoiCloudDatasource;
import com.carles.carlesbasic.data.datasource.PoiLocalDatasource;
import com.carles.carlesbasic.data.entity.PoiMapper;
import com.carles.carlesbasic.data.repository.PoiRepository;
import com.carles.carlesbasic.domain.interactor.GetPoiDetailInteractor;
import com.carles.carlesbasic.domain.model.Poi;
import com.carles.common.presentation.BaseActivity;
import com.carles.common.presentation.BaseContract;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import static android.view.View.VISIBLE;

public class PoiDetailActivity extends BaseActivity implements PoiDetailContract.View {

    private static final String EXTRA_ID = "poi_detail.extra_id";
    private View contentView;
    private TextView descriptionTextView;
    private TextView addressTextView;
    private TextView transportTextView;
    private TextView phoneTextView;
    private TextView mailTextView;
    private Toolbar toolbar;

    private PoiDetailContract.Presenter presenter;
    private GetPoiDetailInteractor getPoiDetailInteractor;
    private PoiRepository poiRepository;
    private PoiCloudDatasource poiCloudDatasource;
    private PoiLocalDatasource poiLocalDatasource;
    private PoiMapper poiMapper;

    public static Intent makeIntent(Context context, String id) {
        Intent intent = new Intent(context, PoiDetailActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_poi_detail;
    }

    @Override
    protected void initViews() {
        contentView = findViewById(R.id.poidetail_contentview);
        descriptionTextView = findViewById(R.id.poidetail_description_textview);
        addressTextView = findViewById(R.id.poidetail_address_textview);
        transportTextView = findViewById(R.id.poidetail_transport_textview);
        phoneTextView = findViewById(R.id.poidetail_phone_textview);
        mailTextView = findViewById(R.id.poidetail_mail_textview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void initComponents() {
        poiMapper = new PoiMapper();
        poiLocalDatasource = new PoiLocalDatasource(poiMapper);
        poiCloudDatasource = new PoiCloudDatasource(poiMapper, poiLocalDatasource);
        poiRepository = new PoiRepository(poiLocalDatasource, poiCloudDatasource);
        getPoiDetailInteractor = new GetPoiDetailInteractor(poiRepository);
        presenter = new PoiDetailPresenter(getPoiDetailInteractor);
        presenter.initialize(getIntent().getStringExtra(EXTRA_ID));
        presenter.onViewCreated(this);
    }

    @Override
    protected BaseContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void displayPoiDetail(Poi poi) {
        toolbar.setTitle(poi.title);
        contentView.setVisibility(VISIBLE);
        addressTextView.setText(poi.address);
        descriptionTextView.setText(poi.description);

        if (poi.transport != null) {
            transportTextView.setText(poi.transport);
        } else {
            transportTextView.setVisibility(View.GONE);
        }

        if (poi.email != null) {
            mailTextView.setText(poi.email);
        } else {
            mailTextView.setVisibility(View.GONE);
        }

        if (poi.phone != null) {
            phoneTextView.setText(poi.phone);
        } else {
            phoneTextView.setVisibility(View.GONE);
        }
    }
}
