package mank.dtusocialtodo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Swagam on 24/04/2018.
 */

public class AppSingleton {
    private static AppSingleton appInstance;
    private RequestQueue requestQueue;
    private static Context context;


    public AppSingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized AppSingleton getAppInstance(Context context) {
        if (appInstance == null){
            appInstance = new AppSingleton(context);
        }
        return appInstance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);

    }

}
