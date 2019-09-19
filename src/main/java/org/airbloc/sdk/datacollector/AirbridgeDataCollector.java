package org.airbloc.sdk.datacollector;

import org.airbloc.sdk.event.Event;
import org.airbloc.sdk.user.User;

import io.airbridge.AirBridge;
import io.airbridge.ecommerce.PurchaseEvent;
import io.airbridge.statistics.Tracker;
import io.airbridge.statistics.events.GoalEvent;
import java8.util.concurrent.CompletableFuture;

public class AirbridgeDataCollector implements DataCollector<AirbridgeEventResult> {

    private static final String AIRBLOC_EVENT_CATEGORY = "airbloc";
    private static final String KEY_PAYLOAD = "payload";

    private void updateAirbridgeUserFrom(User user) {
        Tracker tracker = AirBridge.getTracker();
        tracker.setUserEmail(user.getExternalId());
        tracker.setUserId(user.getAccountId());
    }

    @Override
    public CompletableFuture<AirbridgeEventResult> updateUserAttribute(User user, String key, Object value) {
        updateAirbridgeUserFrom(user);
        return CompletableFuture.supplyAsync(AirbridgeEventResult::new);
    }

    @Override
    public CompletableFuture<AirbridgeEventResult> sendEvent(User user, Event event) {
        updateAirbridgeUserFrom(user);

        AirBridge.getTracker().sendEvent(
                new GoalEvent(AIRBLOC_EVENT_CATEGORY, event.dataType())
                        .setCustomAttribute(KEY_PAYLOAD, event.toJson().toJsonObject()));

        return CompletableFuture.supplyAsync(AirbridgeEventResult::new);
    }
}
