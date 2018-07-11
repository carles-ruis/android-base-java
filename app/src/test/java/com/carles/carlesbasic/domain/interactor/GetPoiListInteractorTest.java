package com.carles.carlesbasic.domain.interactor;

import com.carles.carlesbasic.data.repository.PoiRepository;
import com.carles.carlesbasic.domain.model.Poi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static com.carles.carlesbasic.TestHelper.createPoiList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetPoiListInteractorTest {

    private GetPoiListInteractor interactor;
    @Mock
    private PoiRepository poiRepository;

    @Before
    public void setup() {
        interactor = new GetPoiListInteractor(poiRepository);
    }

    @Test
    public void createObservableUsecase_observableSuccess() {
        List<Poi> poiList = createPoiList("1", "2");
        when(poiRepository.getPoiList()).thenReturn(Observable.just(poiList));

        TestObserver<List<Poi>> testObserver = interactor.createObservableUsecase(null).test();
        testObserver.assertNoErrors();
        testObserver.assertValue(poiList);
        testObserver.assertComplete();
    }

    @Test
    public void createObservableUsecase_observableError() {
        Throwable error = new Throwable();
        when(poiRepository.getPoiList()).thenReturn(Observable.error(error));

        TestObserver<List<Poi>> testObserver = interactor.createObservableUsecase(null).test();
        testObserver.assertError(error);
        testObserver.assertNoValues();
        testObserver.assertNotComplete();
    }

}
