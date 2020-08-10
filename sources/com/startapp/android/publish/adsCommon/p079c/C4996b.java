package com.startapp.android.publish.adsCommon.p079c;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.startapp.common.C5183d;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONObject;

/* renamed from: com.startapp.android.publish.adsCommon.c.b */
/* compiled from: StartAppSDK */
public class C4996b {

    /* renamed from: a */
    protected Context f3190a;

    /* renamed from: b */
    protected C5183d f3191b;

    /* renamed from: c */
    protected C4995a f3192c = new C4995a();

    /* renamed from: d */
    protected BluetoothAdapter f3193d = m3149d();

    /* renamed from: e */
    protected BroadcastReceiver f3194e;

    public C4996b(Context context, C5183d dVar) {
        this.f3190a = context;
        this.f3191b = dVar;
    }

    /* renamed from: a */
    public void mo62212a(boolean z) {
        BluetoothAdapter bluetoothAdapter = this.f3193d;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            this.f3191b.mo62338a(null);
            return;
        }
        this.f3192c.mo62210a(m3148c());
        if (!z || !C5146c.m3760a(this.f3190a, "android.permission.BLUETOOTH_ADMIN")) {
            this.f3191b.mo62338a(mo62213b());
        } else {
            IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
            this.f3194e = m3150e();
            try {
                this.f3190a.registerReceiver(this.f3194e, intentFilter);
                this.f3193d.startDiscovery();
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("BluetoothManager - start() ");
                sb.append(e.getMessage());
                C5155g.m3805a(3, sb.toString());
                this.f3193d.cancelDiscovery();
                this.f3191b.mo62338a(mo62213b());
            }
        }
    }

    /* renamed from: a */
    public void mo62211a() {
        if (C5146c.m3760a(this.f3190a, "android.permission.BLUETOOTH_ADMIN") && this.f3194e != null) {
            BluetoothAdapter bluetoothAdapter = this.f3193d;
            if (bluetoothAdapter != null) {
                bluetoothAdapter.cancelDiscovery();
                try {
                    this.f3190a.unregisterReceiver(this.f3194e);
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("BluetoothManager - stop() ");
                    sb.append(e.getMessage());
                    C5155g.m3805a(3, sb.toString());
                }
                this.f3194e = null;
            }
        }
    }

    /* renamed from: c */
    private Set<BluetoothDevice> m3148c() {
        HashSet hashSet = new HashSet();
        try {
            if (C5146c.m3760a(this.f3190a, "android.permission.BLUETOOTH") && this.f3193d.isEnabled()) {
                return this.f3193d.getBondedDevices();
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to get devices ");
            sb.append(e.getMessage());
            C5155g.m3805a(6, sb.toString());
        }
        return hashSet;
    }

    /* renamed from: d */
    private BluetoothAdapter m3149d() {
        if (C5146c.m3760a(this.f3190a, "android.permission.BLUETOOTH")) {
            return BluetoothAdapter.getDefaultAdapter();
        }
        return null;
    }

    /* renamed from: e */
    private BroadcastReceiver m3150e() {
        return new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("android.bluetooth.device.action.FOUND".equals(action)) {
                    C4996b.this.f3192c.mo62209a((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                    C4996b.this.mo62211a();
                    C4996b.this.f3191b.mo62338a(C4996b.this.mo62213b());
                }
            }
        };
    }

    /* renamed from: b */
    public JSONObject mo62213b() {
        try {
            return this.f3192c.mo62208a();
        } catch (Exception unused) {
            return null;
        }
    }
}
