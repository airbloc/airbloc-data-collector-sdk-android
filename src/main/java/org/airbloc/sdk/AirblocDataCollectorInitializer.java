package org.airbloc.sdk;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.airbloc.sdk.datacollector.AirbridgeDataCollector;
import org.airbloc.sdk.datacollector.R;
import org.airbloc.sdk.internal.logger.AirblocLogger;

import java8.util.Optional;

import io.airbridge.AirBridge;

public class AirblocDataCollectorInitializer extends ContentProvider {
    @Override
    public boolean onCreate() {
        Context context = Optional.ofNullable(getContext()).orElseThrow();
        Resources resources = context.getResources();

        String appName = resources.getString(R.string.org_airbloc_datacollector_app_name);
        if (appName.isEmpty()) {
            AirblocLogger.e("You need to provide org_airbloc_datacollector_app_name to your" +
                    "airbloc.xml to use data collection. Disabling the feature...");
            return false;
        }
        String appToken = resources.getString(R.string.org_airbloc_datacollector_app_token);
        if (appToken.isEmpty()) {
            AirblocLogger.e("You need to provide org_airbloc_datacollector_app_token to your" +
                    "airbloc.xml to use data collection. Disabling the feature...");
            return false;
        }

        AirBridge.init(getContext(), appName, appToken);
        Extensions.getInstance().useDataCollector(new AirbridgeDataCollector());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
