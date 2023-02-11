package com.octopus.sdk.features.servertasks;

import com.octopus.sdk.http.OctopusClient;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class ServerTaskWaiter {
    private final OctopusClient client;
    private final String spaceName;
    public ServerTaskWaiter(final OctopusClient client, final String spaceName) {
        this.client = client;
        this.spaceName = spaceName;
    }

    public interface PollingCallback {
        void call(ServerTask serverTask);
    }

    public List<ServerTask> waitForServerTasksToComplete(
            List<String> serverTaskIds,
            Integer statusCheckSleepCycle,
            Integer timeout,
            @Nullable PollingCallback pollingCallback
    ) throws ExecutionException, InterruptedException, TimeoutException {
        final SpaceServerTaskRepository spaceServerTaskRepository = new SpaceServerTaskRepository(this.client, this.spaceName);
        return this.waitForTasks(spaceServerTaskRepository, serverTaskIds, statusCheckSleepCycle, timeout, pollingCallback);
    }

    public ServerTask waitForServerTaskToComplete(
            String serverTaskId,
            Integer statusCheckSleepCycle,
            Integer timeout,
            @Nullable PollingCallback pollingCallback
    ) throws ExecutionException, InterruptedException, TimeoutException {
        final SpaceServerTaskRepository spaceServerTaskRepository = new SpaceServerTaskRepository(this.client, this.spaceName);
        final ArrayList<String> serverTaskIdList = new ArrayList<>();
        serverTaskIdList.add(serverTaskId);
        final List<ServerTask> tasks = this.waitForTasks(spaceServerTaskRepository, serverTaskIdList, statusCheckSleepCycle, timeout, pollingCallback);
        return tasks.get(0);
    }
    private List<ServerTask> waitForTasks(
            SpaceServerTaskRepository spaceServerTaskRepository,
            List<String> serverTaskIds,
            int statusCheckSleepCycle,
            int timeout,
            @Nullable PollingCallback pollingCallback
    ) throws ExecutionException, InterruptedException, TimeoutException {
        if (serverTaskIds.size() == 0) {
            return new ArrayList<>();
        }
        final Duration duration = Duration.ofSeconds(timeout);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        final List<ServerTask> completedTasks = new ArrayList<>();

        final Future handler = executor.submit(() -> {
            boolean stop = false;
            final List<String> pendingTaskIds = new ArrayList<>();
            for (String id : serverTaskIds) {
                pendingTaskIds.add(id);
            }
            while (!stop) {
                final List<ServerTask> tasks = spaceServerTaskRepository.getByIds(pendingTaskIds);

                final Stream<String> unknownTaskIds = pendingTaskIds.stream().filter(id -> tasks.stream().filter(t -> t.Id == id).count() == 0);
                if (unknownTaskIds.count() > 0) {
                    throw new RuntimeException(String.format("Unknown task Id(s) %s", unknownTaskIds.reduce("", (a, b) -> a+", "+b)));
                }

                final List<String> nowCompletedTaskIds = new ArrayList<>();

                for (ServerTask task : tasks) {
                    if (pollingCallback != null) {
                        pollingCallback.call(task);
                    }

                    if (task.IsCompleted) {
                        nowCompletedTaskIds.add(task.Id);
                        completedTasks.add(task);
                        int idxToRemove = pendingTaskIds.indexOf(task.Id);
                        if (idxToRemove != -1) {
                            pendingTaskIds.remove(idxToRemove);
                        }
                    }
                }

                if (pendingTaskIds.size() == 0 || tasks.size() == 0) {
                    stop = true;
                }
            }
            return null;
        });

        try {
            handler.get(duration.toMillis(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            handler.cancel(true);
            throw e;
        }
        return completedTasks;
    }



}
