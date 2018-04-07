package com.pelicanus.insight.model;



import android.app.Activity;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pelicanus.insight.ExcursionViewActivity;
import com.pelicanus.insight.R;

import java.util.HashMap;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Olga on 10.02.2018.
 */
@NoArgsConstructor
@Setter
@Getter
@Data
public class Trip {

    @Getter
    @NonNull
    String name;

    @Getter
    @NonNull
    String description;

    @Getter
    @NonNull
    String date;

    @Getter
    @NonNull
    String address;

    @NonNull
    String guide_id;

    @NonNull
    @Setter
    @Getter
    String trip_id;

    @Setter
    @Getter
    String language;
    final private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Trips");
    long max_visitors;

    EditFields editFields;
    Picture avatar = new Picture(Picture.Type.Trip_avatar);
    HashMap<String, String> visitors = new HashMap<String, String>();

    public Trip(String name, String description, String date, String address, String guide_id, String language, long max_visitors) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.guide_id = guide_id;
        this.date = date;
        this.language=language;
        this.max_visitors = max_visitors;
    }
    public Trip(String id) {
        setTrip_id(id);
        readTripData();
    }
    public void setTrip_id(String id) {
        this.trip_id = id;
        avatar.setName(id);
    }
    //Работа с базой
    public void writeTripData(final Activity activity) {
        if(editFields == null || !editFields.readData())
            return;
        getAvatar().setName(getTrip_id());
        getAvatar().Upload();
        final Context context = getEditFields().getContext();
        addVisitor(getGuide_id(), context);
        reference.child(getTrip_id()).setValue(new Soul(this))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Toast.makeText(context, R.string.trip_create_success, Toast.LENGTH_LONG).show();
                            activity.finish();
                        } else
                            Toast.makeText(context, R.string.trip_create_error, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void readTripData() {
        reference.child(getTrip_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                installSoul(new Soul(dataSnapshot));
                //loadToAllField();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Getter
    @Setter
    public class Soul {
        private String name;
        private String description;
        private String address;
        private String guide_id;
        private String date;
        private String language;
        private Long max_visitors;
        public Soul(DataSnapshot dataSnapshot) {
            setName(dataSnapshot.child("name").getValue(String.class));
            setDate(dataSnapshot.child("date").getValue(String.class));
            setAddress(dataSnapshot.child("address").getValue(String.class));
            setGuide_id(dataSnapshot.child("guide_id").getValue(String.class));
            setDescription(dataSnapshot.child("description").getValue(String.class));
            setLanguage(dataSnapshot.child("language").getValue(String.class));
            setMax_visitors(dataSnapshot.child("max_visitors").getValue(Long.class));
        }
        public Soul(Trip trip) {
            setName(trip.getName());
            setDate(trip.getDate());
            setAddress(trip.getAddress());
            setGuide_id(trip.getGuide_id());
            setDescription(trip.getDescription());
            setLanguage(trip.getLanguage());
            setMax_visitors(trip.getMax_visitors());
        }
        public void setMax_visitors(Long count) {
            if (count == null)
                this.max_visitors = (long)2;
            else
                this.max_visitors = Math.max(2, count);
        }
    }
    public void installSoul(Soul soul) {
        setAddress(soul.getAddress());
        setDate(soul.getDate());
        setDescription(soul.getDescription());
        setGuide_id(soul.getGuide_id());
        setName(soul.getName());
        setLanguage(soul.getLanguage());
        setMax_visitors(soul.getMax_visitors());
    }
    //Посетители
    public Long getMax_visitors() {
        return Math.max(2, max_visitors);
    }
    public void downloadVisitors() {
        FirebaseDatabase.getInstance().getReference().child("Visitors").child(this.getTrip_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                visitors = new HashMap<String, String>();
                for ( DataSnapshot v:dataSnapshot.getChildren()) {
                    visitors.put(v.getKey(), v.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public boolean isVisitor(String user_id) {
        return getVisitors().containsKey(user_id);
    }
    public void addVisitor(String user_id, final Context context) {
        FirebaseDatabase.getInstance().getReference().child("Visitors").child(getTrip_id()).child(user_id).setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(context,"Вы записаны на экскурсию",Toast.LENGTH_SHORT);//.show();
                else
                    Toast.makeText(context,"FAIL",Toast.LENGTH_LONG).show();
                //((ExcursionViewActivity)context).setCount_participants();
            }
        });
    }
    public void delVisitor(String user_id, final Context context) {
        FirebaseDatabase.getInstance().getReference().child("Visitors").child(getTrip_id()).child(user_id).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(context,"Вы отписаны на экскурсию",Toast.LENGTH_SHORT);//.show();
                else
                    Toast.makeText(context,"FAIL",Toast.LENGTH_LONG).show();
                //((ExcursionViewActivity)context).setCount_participants();
            }
        });
    }
    //Создание/редактирование
    private class EditFields {
        private EditText nameField;
        private EditText addressField;
        private EditText descriptionField;
        @Setter //TODO нормальный сеттер
        private DatePicker dateField;
        @Setter
        private Spinner languageField;
        private EditText maxVisitorsFiled;

        public EditFields(EditText nameField, EditText addressField, EditText descriptionField, EditText maxVisitorsFiled, DatePicker dateField, Spinner languageField) {
            setAddressField(addressField);
            setDateField(dateField);
            setDescriptionField(descriptionField);
            setLanguageField(languageField);
            setNameField(nameField);
            setMaxVisitorsFiled(maxVisitorsFiled);
        }

        public void setNameField(EditText nameField) {
            this.nameField = nameField;
            nameField.setText(Trip.this.getName());
        }
        public void setAddressField(EditText addressField) {
            this.addressField = addressField;
            addressField.setText(Trip.this.getAddress());
        }
        public void setDescriptionField(EditText descriptionField) {
            this.descriptionField = descriptionField;
            descriptionField.setText(Trip.this.getDescription());
        }
        public void setMaxVisitorsFiled(EditText maxVisitorsFiled) {
            this.maxVisitorsFiled = maxVisitorsFiled;
            maxVisitorsFiled.setText((Trip.this.getMax_visitors().toString()));
        }
        public String getName() {
            return nameField.getText().toString().trim();
        }
        public String getAddress() {
            return addressField.getText().toString().trim();
        }
        public String getDescription() {
            return descriptionField.getText().toString().trim();
        }
        public String getMaxVisitors() {
            return maxVisitorsFiled.getText().toString();
        }
        public String getLanguage() {
            return languageField.getSelectedItem().toString();
        }
        public String getDate() {
            return dateField.getDayOfMonth()+"."+dateField.getMonth()+"."+dateField.getYear();
        }
        public Context getContext() {
            return nameField.getContext();
        }
        public boolean readData() {
            String name = getName();
            String language = getLanguage();
            String date = getDate();
            String address = getAddress();
            String description = getDescription();
            String max_visitors = getMaxVisitors();
            if (name.length() == 0||
                date.length() == 0||
                address.length() == 0||
                description.length() == 0 ||
                max_visitors.length() == 0) {
                Toast.makeText(getContext(), R.string.trip_create_emptydata, Toast.LENGTH_LONG).show();
                return false;
            }
            Trip.this.setAddress(getAddress());
            Trip.this.setDate(date);
            Trip.this.setDescription(description);
            Trip.this.setName(name);
            Trip.this.setLanguage(language);
            Trip.this.setMax_visitors(Long.parseLong(max_visitors));
            return true;
        }
    }
    public void setEditFields(EditText nameField, EditText addressField, EditText descriptionField, EditText maxVisitrosField, DatePicker dateField, Spinner languageField) {
        this.editFields = new EditFields(nameField, addressField, descriptionField, maxVisitrosField, dateField, languageField);
    }
    public String getGuide_id() {
        if (guide_id == null)
            guide_id =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        return guide_id;
    }
    public String getTrip_id() {
        if (trip_id == null)
            trip_id = genTrip_id();
        return trip_id;
    }
    private String genTrip_id() {
        return reference.push().getKey();
    }
}
