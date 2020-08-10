package com.mobiroller.models;

import java.io.Serializable;

public class ApplyzeResponse<T> implements Serializable {
    public T data;
    public boolean isUserFriendlyMessage;
    public String key;
    public String message;
    public boolean success;
}
