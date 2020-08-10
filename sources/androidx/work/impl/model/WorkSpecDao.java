package androidx.work.impl.model;

import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.WorkInfo.State;
import androidx.work.impl.model.WorkSpec.IdAndState;
import androidx.work.impl.model.WorkSpec.WorkInfoPojo;
import java.util.List;

public interface WorkSpecDao {
    void delete(String str);

    List<String> getAllUnfinishedWork();

    List<String> getAllWorkSpecIds();

    List<WorkSpec> getEligibleWorkForScheduling(int i);

    List<Data> getInputsFromPrerequisites(String str);

    List<WorkSpec> getRunningWork();

    List<WorkSpec> getScheduledWork();

    State getState(String str);

    List<String> getUnfinishedWorkWithName(String str);

    List<String> getUnfinishedWorkWithTag(String str);

    WorkSpec getWorkSpec(String str);

    List<IdAndState> getWorkSpecIdAndStatesForName(String str);

    WorkSpec[] getWorkSpecs(List<String> list);

    WorkInfoPojo getWorkStatusPojoForId(String str);

    List<WorkInfoPojo> getWorkStatusPojoForIds(List<String> list);

    List<WorkInfoPojo> getWorkStatusPojoForName(String str);

    List<WorkInfoPojo> getWorkStatusPojoForTag(String str);

    LiveData<List<WorkInfoPojo>> getWorkStatusPojoLiveDataForIds(List<String> list);

    LiveData<List<WorkInfoPojo>> getWorkStatusPojoLiveDataForName(String str);

    LiveData<List<WorkInfoPojo>> getWorkStatusPojoLiveDataForTag(String str);

    int incrementWorkSpecRunAttemptCount(String str);

    void insertWorkSpec(WorkSpec workSpec);

    int markWorkSpecScheduled(String str, long j);

    void pruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast();

    int resetScheduledState();

    int resetWorkSpecRunAttemptCount(String str);

    void setOutput(String str, Data data);

    void setPeriodStartTime(String str, long j);

    int setState(State state, String... strArr);
}
