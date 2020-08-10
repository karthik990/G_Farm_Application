package com.google.firebase.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class OnDisconnect {
    /* access modifiers changed from: private */
    public Path path;
    /* access modifiers changed from: private */
    public Repo repo;

    OnDisconnect(Repo repo2, Path path2) {
        this.repo = repo2;
        this.path = path2;
    }

    public Task<Void> setValue(Object obj) {
        return onDisconnectSetInternal(obj, PriorityUtilities.NullPriority(), null);
    }

    public Task<Void> setValue(Object obj, String str) {
        return onDisconnectSetInternal(obj, PriorityUtilities.parsePriority(this.path, str), null);
    }

    public Task<Void> setValue(Object obj, double d) {
        return onDisconnectSetInternal(obj, PriorityUtilities.parsePriority(this.path, Double.valueOf(d)), null);
    }

    public void setValue(Object obj, CompletionListener completionListener) {
        onDisconnectSetInternal(obj, PriorityUtilities.NullPriority(), completionListener);
    }

    public void setValue(Object obj, String str, CompletionListener completionListener) {
        onDisconnectSetInternal(obj, PriorityUtilities.parsePriority(this.path, str), completionListener);
    }

    public void setValue(Object obj, double d, CompletionListener completionListener) {
        onDisconnectSetInternal(obj, PriorityUtilities.parsePriority(this.path, Double.valueOf(d)), completionListener);
    }

    public void setValue(Object obj, Map map, CompletionListener completionListener) {
        onDisconnectSetInternal(obj, PriorityUtilities.parsePriority(this.path, map), completionListener);
    }

    private Task<Void> onDisconnectSetInternal(Object obj, Node node, CompletionListener completionListener) {
        Validation.validateWritablePath(this.path);
        ValidationPath.validateWithObject(this.path, obj);
        Object convertToPlainJavaTypes = CustomClassMapper.convertToPlainJavaTypes(obj);
        Validation.validateWritableObject(convertToPlainJavaTypes);
        final Node NodeFromJSON = NodeUtilities.NodeFromJSON(convertToPlainJavaTypes, node);
        final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                OnDisconnect.this.repo.onDisconnectSetValue(OnDisconnect.this.path, NodeFromJSON, (CompletionListener) wrapOnComplete.getSecond());
            }
        });
        return (Task) wrapOnComplete.getFirst();
    }

    public Task<Void> updateChildren(Map<String, Object> map) {
        return updateChildrenInternal(map, null);
    }

    public void updateChildren(Map<String, Object> map, CompletionListener completionListener) {
        updateChildrenInternal(map, completionListener);
    }

    private Task<Void> updateChildrenInternal(final Map<String, Object> map, CompletionListener completionListener) {
        final Map parseAndValidateUpdate = Validation.parseAndValidateUpdate(this.path, map);
        final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                OnDisconnect.this.repo.onDisconnectUpdate(OnDisconnect.this.path, parseAndValidateUpdate, (CompletionListener) wrapOnComplete.getSecond(), map);
            }
        });
        return (Task) wrapOnComplete.getFirst();
    }

    public Task<Void> removeValue() {
        return setValue(null);
    }

    public void removeValue(CompletionListener completionListener) {
        setValue((Object) null, completionListener);
    }

    public Task<Void> cancel() {
        return cancelInternal(null);
    }

    public void cancel(CompletionListener completionListener) {
        cancelInternal(completionListener);
    }

    private Task<Void> cancelInternal(CompletionListener completionListener) {
        final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                OnDisconnect.this.repo.onDisconnectCancel(OnDisconnect.this.path, (CompletionListener) wrapOnComplete.getSecond());
            }
        });
        return (Task) wrapOnComplete.getFirst();
    }
}
