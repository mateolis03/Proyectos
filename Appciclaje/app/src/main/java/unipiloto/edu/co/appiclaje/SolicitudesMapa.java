package unipiloto.edu.co.appiclaje;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudesMapa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String nickname;
    private String tipo;
    private List<String> listaAddress = new ArrayList<>();
    private List<String> listaId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        nickname = intent.getStringExtra("nickname");
        tipo =intent.getStringExtra("tipo");
    }

    public LatLng latLogwithAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 100);
            if (address != null) {
                try {
                    Address location = address.get(0);
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    return latLng;
                } catch (IndexOutOfBoundsException er) {
                    Toast.makeText(this, "Location isn't available", Toast.LENGTH_SHORT).show();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Query query=null;

        if(tipo.equalsIgnoreCase("Natural")) {
            query = FirebaseDatabase.getInstance().getReference("solicitudes").orderByChild("nickname").equalTo(nickname);
        }else{
            query = FirebaseDatabase.getInstance().getReference("solicitudes");
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (!dataSnapshot.getKey().isEmpty()) {
                            String id = dataSnapshot.getKey();
                            String direccion = dataSnapshot.child("address").getValue().toString();
                            String estado = dataSnapshot.child("estado").getValue().toString();
                            listaAddress.add(direccion);
                            listaId.add(id+"-"+estado);
                        }
                    }
                }
                mMap = googleMap;
                for (int i = 0; i <listaAddress.size(); i++) {
                    LatLng addresses = latLogwithAddress(listaAddress.get(i));
                    String id = listaId.get(i);
                    mMap.addMarker(new MarkerOptions()
                            .position(addresses)
                            .title(id));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(addresses));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}