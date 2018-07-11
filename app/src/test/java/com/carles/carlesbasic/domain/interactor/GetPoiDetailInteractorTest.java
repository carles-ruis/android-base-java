package com.carles.carlesbasic.domain.interactor;

import com.carles.carlesbasic.data.repository.PoiRepository;
import com.carles.carlesbasic.domain.model.Poi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static com.carles.carlesbasic.TestHelper.createPoi;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetPoiDetailInteractorTest {

    private GetPoiDetailInteractor interactor;
    @Mock
    PoiRepository poiRepository;

    @Before
    public void setup() {
        interactor = new GetPoiDetailInteractor(poiRepository);
    }

    @Test
    public void createObservableUsecase_observableSuccess() {
        GetPoiDetailInteractor.Params params = GetPoiDetailInteractor.Params.create("1");
        Poi poi = createPoi("1");
        when(poiRepository.getPoiDetail("1")).thenReturn(Observable.just(poi));

        TestObserver<Poi> testObserver = interactor.createObservableUsecase(params).test();
        testObserver.assertNoErrors();
        testObserver.assertValue(poi);
        testObserver.assertComplete();
    }

    @Test
    public void createObservableUsecase_observableError() {
        GetPoiDetailInteractor.Params params = GetPoiDetailInteractor.Params.create("1");
        Throwable error = new Throwable();
        when(poiRepository.getPoiDetail("1")).thenReturn(Observable.error(error));

        TestObserver<Poi> testObserver = interactor.createObservableUsecase(params).test();
        testObserver.assertError(error);
        testObserver.assertNoValues();
        testObserver.assertNotComplete();
    }

}
