package com.mobiroller.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobiroller.activities.preview.LocationPermissionActivity;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.FlagModel;
import com.mobiroller.models.events.MapUserLocationEvent;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class aveMapViewFragment extends BaseModuleFragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    @BindView(2131362654)
    RelativeLayout mapLayout;
    @BindView(2131362653)
    MapView mapView;
    ProgressViewHelper progressViewHelper;

    private void turnGPSOff() {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.map_layout, viewGroup, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        if (this.networkHelper.isConnected()) {
            this.progressViewHelper.show();
            try {
                this.mapView.onCreate(bundle);
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            try {
                MapsInitializer.initialize(getActivity());
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            this.mapView.getMapAsync(this);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection), 1).show();
        }
        return inflate;
    }

    public void onResume() {
        super.onResume();
        this.mapView.onResume();
        if (this.mapLayout != null) {
            this.bannerHelper.addBannerAd(this.mapLayout, this.mapView);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        if (getActivity() == null || getActivity().isFinishing() || this.mMap != null || this.screenModel == null) {
            this.mMap = googleMap;
        } else {
            if (this.screenModel.getShowUserLocation() != null && this.screenModel.getShowUserLocation().equalsIgnoreCase("YES")) {
                if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                    turnGPSOn();
                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                } else {
                    startActivity(new Intent(getActivity(), LocationPermissionActivity.class).putExtra(LocationPermissionActivity.sMapUserLocation, true));
                }
            }
            if (this.screenModel.getMapType().equalsIgnoreCase("std")) {
                googleMap.setMapType(1);
            } else if (this.screenModel.getMapType().equalsIgnoreCase("s")) {
                googleMap.setMapType(2);
            } else if (this.screenModel.getMapType().equalsIgnoreCase("h")) {
                googleMap.setMapType(4);
            }
            ArrayList flags = this.screenModel.getFlags();
            ArrayList<Marker> arrayList = new ArrayList<>();
            LatLng latLng = null;
            int i = 0;
            if (flags.size() > 0) {
                LatLng latLng2 = null;
                for (int i2 = 0; i2 < flags.size(); i2++) {
                    FlagModel flagModel = (FlagModel) flags.get(i2);
                    String lattitude = flagModel.getLattitude();
                    String longitude = flagModel.getLongitude();
                    String localizedTitle = this.localizationHelper.getLocalizedTitle(flagModel.getTitle());
                    String localizedTitle2 = this.localizationHelper.getLocalizedTitle(flagModel.getDetail());
                    LatLng latLng3 = new LatLng(Double.valueOf(lattitude).doubleValue(), Double.valueOf(longitude).doubleValue());
                    if (i2 == 0) {
                        latLng2 = latLng3;
                    }
                    arrayList.add(googleMap.addMarker(new MarkerOptions().position(latLng3).title(localizedTitle).snippet(localizedTitle2)));
                }
                latLng = latLng2;
            }
            Builder builder = new Builder();
            for (Marker position : arrayList) {
                builder.include(position.getPosition());
                i++;
            }
            if (i > 1) {
                try {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 200));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (i == 1 && latLng != null) {
                try {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        this.progressViewHelper.dismiss();
    }

    public void onStop() {
        turnGPSOff();
        super.onStop();
    }

    public void onDestroyView() {
        turnGPSOff();
        super.onDestroyView();
    }

    @Subscribe
    public void onPostMapUserLocationEvent(MapUserLocationEvent mapUserLocationEvent) {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            turnGPSOn();
            this.mapView.getMapAsync(this);
        }
    }

    private void turnGPSOn() {
        if (!Secure.getString(getActivity().getContentResolver(), "location_providers_allowed").contains("gps")) {
            Intent intent = new Intent();
            intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            intent.addCategory("android.intent.category.ALTERNATIVE");
            intent.setData(Uri.parse(ExifInterface.GPS_MEASUREMENT_3D));
            getActivity().sendBroadcast(intent);
        }
    }
}
