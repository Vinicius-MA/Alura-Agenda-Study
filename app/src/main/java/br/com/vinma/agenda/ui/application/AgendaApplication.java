package br.com.vinma.agenda.ui.application;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgendaApplication extends Application {
    public static final ExecutorService bgExecutor = Executors.newSingleThreadExecutor();

    public static void dummySleep(){
        /*
            methods that simply make current thread sleep so some behaviors with long
            background tasks can be tested.
        */
        try {
            Thread.sleep(2_500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
