package unipiloto.edu.co.appiclaje;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mapa extends AppCompatActivity {
    private ListView listView;
    private GoogleMap mMap;
    private GpsTracker gpsTracker;
    private FirebaseAuth firbaseAuth;
    private String nickname;
    private List<String> listaId;
    public int cont;
    public String destino="";
    Uri gmmIntentUri;
    String latlon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_solicitud);
        Intent intent = getIntent();
        cont =0;
        nickname = intent.getStringExtra("nickname");
        listView = (ListView) findViewById(R.id.listId);
        listaId = new ArrayList<>();
        AdapterView.OnItemClickListener itemClickListener = new  AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = listaId.get(i);
                Query query = FirebaseDatabase.getInstance().getReference("solicitudes").child(id);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            destino = snapshot.child("address").getValue().toString();
                            latlon=latLogwithAddress(destino);
                            String monda="google.navigation:q="+latlon;
                            Toast.makeText(Mapa.this, String.valueOf(latlon), Toast.LENGTH_LONG).show();
                            gmmIntentUri=Uri.parse(monda);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }
    public void consultar(View view) {
        if (cont==0){
            cont++;
            Query query = FirebaseDatabase.getInstance().getReference("solicitudes").orderByChild("nickname").equalTo(nickname);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (!dataSnapshot.getKey().isEmpty()) {
                                String estado=dataSnapshot.child("estado").getValue().toString();
                                if (estado.equalsIgnoreCase("Aceptado"));
                                {
                                    listaId.add(dataSnapshot.getKey());
                                }
                            }
                        }
                    }
                    ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                            Mapa.this,
                            android.R.layout.simple_list_item_1,
                            listaId);
                    listView.setAdapter(listAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else{
            Toast.makeText(Mapa.this, "Ya se listaron sus solicitudes", Toast.LENGTH_LONG).show();
        }
    }


    public String latLogwithAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 100);
            if (address != null) {
                try {
                    Address location = address.get(0);
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    String latLnng=location.getLatitude()+","+location.getLongitude();
                    return latLnng;
                } catch (IndexOutOfBoundsException er) {
                    Toast.makeText(this, "Location isn't available", Toast.LENGTH_SHORT).show();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}