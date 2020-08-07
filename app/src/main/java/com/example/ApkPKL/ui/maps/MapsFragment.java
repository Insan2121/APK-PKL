package com.example.ApkPKL.ui.maps;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.ApkPKL.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {

    private MapsViewModel mapsViewModel;
    private LocationManager locationManager;
    private TextView namaP;
    private TextView kontakP;
    private TextView alamatP;
    private Button track;
    private Switch tes;
    FusedLocationProviderClient client;
    String alamat;
    SupportMapFragment supportMapFragment;
    Location loc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapsViewModel =
                ViewModelProviders.of(this).get(MapsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        tes = root.findViewById(R.id.stat);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        namaP = root.findViewById(R.id.namaP);
        kontakP = root.findViewById(R.id.kontakP);
        alamatP = root.findViewById(R.id.alamatP);
        track = root.findViewById(R.id.trac);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            System.out.println("Latitude" + loc.getLatitude());
            System.out.println("Longitude" + loc.getLongitude());
            tes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        System.out.println("Checked");
                    }else {
                        System.out.println("Unchecked");
                    }
                }
            });
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                if(addresses.size() > 0){
                    Address address = addresses.get(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    alamat = address.getAddressLine(0);
                    System.out.println(alamat);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String source = alamat;
                String desti = alamatP.toString();
                if(source.equals("") && desti.equals("")){
                    Toast.makeText(getActivity(),"Location Undefined",Toast.LENGTH_SHORT).show();
                }else {
                    startLoationService();
                    DisplayTrack(source,desti);
                }
            }
        });
        return root;
    }

    private void DisplayTrack(String source, String desti) {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + source +"/" + desti);
            Intent intent =new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/storte/apps/details?id=com.google.android.apps.maps/");
            Intent intent =new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(loc.getLatitude(),loc.getLongitude());
                        markerOptions.position(latLng);
                        int height = 110;
                        int width = 90;
                        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.cek2);
                        Bitmap b = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                        markerOptions.rotation(loc.getBearing());
                        markerOptions.anchor((float)0.5,(float)0.5);
                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
                startLoationService();
            }
        }
    }

    private boolean isLocatioanServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager !=null){
            for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)){
                if(LocationService.class.getName().equals(service.service.getClassName())){
                    if(service.foreground){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void startLoationService(){
        if(!isLocatioanServiceRunning()){
            Intent intent = new Intent(getContext().getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            getActivity().startService(intent);
            Toast.makeText(getActivity(),"Location Service Started",Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLoationService(){
        if(!isLocatioanServiceRunning()){
            Intent intent = new Intent(getContext().getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            getActivity().startService(intent);
            Toast.makeText(getActivity(),"Location Service Stopped",Toast.LENGTH_SHORT).show();
        }
    }
}