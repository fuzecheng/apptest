package utils;

import interfaceUtil.UiWatcher;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import jdk.internal.instrumentation.Tracer;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AndroidDevice extends AndroidDriver {
    Logger logger = LoggerFactory.getLogger(AndroidDevice.class);
    private boolean mInWatcherContext = false;
    private final List<String> mWatchersTriggers = new ArrayList<String>();
    private final HashMap<String, UiWatcher> mWatchers = new HashMap<String, UiWatcher>();

    public void registerWatcher(String name, UiWatcher watcher) {

        if (mInWatcherContext) {
            throw new IllegalStateException("Cannot register new watcher from within another");
        }
        mWatchers.put(name, watcher);
    }
    public void removeWatcher(String name) {
        if (mInWatcherContext) {
            throw new IllegalStateException("Cannot remove a watcher from within another");
        }
        mWatchers.remove(name);
    }
    public void runWatchers() {
        if (mInWatcherContext) {
            return;
        }

        for (String watcherName : mWatchers.keySet()) {
            UiWatcher watcher = mWatchers.get(watcherName);
            if (watcher != null) {
                try {
                    mInWatcherContext = true;
                    if (watcher.checkForCondition()) {
                        setWatcherTriggered(watcherName);
                    }
                } catch (Exception e) {
                    logger.info( "Exceuting watcher: " + watcherName, e);
                } finally {
                    mInWatcherContext = false;
                }
            }
        }
    }

    public boolean hasWatcherTriggered(String watcherName) {

        return mWatchersTriggers.contains(watcherName);
    }

    public void resetWatcherTriggers() {

        mWatchersTriggers.clear();
    }
    public boolean hasAnyWatcherTriggered() {

        return mWatchersTriggers.size() > 0;
    }
    private void setWatcherTriggered(String watcherName) {

        if (!hasWatcherTriggered(watcherName)) {
            mWatchersTriggers.add(watcherName);
        }
    }





    boolean isInWatcherContext() {
        return mInWatcherContext;
    }

    public AndroidDevice(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    public AndroidDevice(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }

    public AndroidDevice(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, desiredCapabilities);
    }

    public AndroidDevice(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, desiredCapabilities);
    }

    public AndroidDevice(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(service, httpClientFactory, desiredCapabilities);
    }

    public AndroidDevice(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, desiredCapabilities);
    }

    public AndroidDevice(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, desiredCapabilities);
    }

    public AndroidDevice(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, desiredCapabilities);
    }

    public AndroidDevice(Capabilities desiredCapabilities) {
        super(desiredCapabilities);
    }


}
