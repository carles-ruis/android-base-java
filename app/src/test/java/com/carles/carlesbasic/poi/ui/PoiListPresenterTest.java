package com.carles.carlesbasic.poi.ui;

import com.carles.carlesbasic.poi.data.repository.PoiRepository;
import com.carles.carlesbasic.poi.model.Poi;
import com.carles.common.ui.BaseContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static com.carles.carlesbasic.poi.TestHelper.createPoi;
import static com.carles.carlesbasic.poi.TestHelper.createPoiList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class PoiListPresenterTest {

    private TestScheduler testScheduler = new TestScheduler();
    private PoiListPresenter presenter;
    @Mock
    private PoiListContract.View view;
    @Mock
    private PoiRepository poiRepository;

    @Before
    public void setup() {
        presenter = new PoiListPresenter(poiRepository, testScheduler, testScheduler);
        Whitebox.setInternalState(presenter, "view", view);
    }

    @Test
    public void onViewCreated_shouldGetPoiList() {
        doReturn(Single.just(createPoiList("1", "2"))).when(poiRepository).getPoiList();
        presenter.onViewCreated(view);
        verify(view).showProgress();
        verify(poiRepository).getPoiList();
    }

    @Test
    public void onGetPoiListSuccess_shouldDisplayPoiList() {
        final List<Poi> poiList = createPoiList("1", "2");
        presenter.onGetPoiListSuccess(poiList);
        verify(view).hideProgress();
        verify(view).displayPoiList(eq(poiList));
    }

    @Test
    public void onGetPoiListError_shouldShowErrorAndRetry() {
        presenter.onGetPoiListError(new Throwable());
        verify(view).showError(anyInt(), any(BaseContract.OnRetryListener.class));
    }

    @Test
    public void onPoiClicked_shouldStartPoiDetail() {
        presenter.onPoiClicked(createPoi("1"));
        verify(view).navigateToPoiDetail(eq("1"));
    }
}
