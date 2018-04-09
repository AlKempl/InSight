package com.pelicanus.insight.model;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pelicanus.insight.CreateTrip;
import com.pelicanus.insight.ExcursionViewActivity;
import com.pelicanus.insight.R;
import com.pelicanus.insight.TripList;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Olga on 10.02.2018.
 */
@NoArgsConstructor
public class Trip {

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String description;

    @Getter
    @Setter
    String date;

    @Getter
    @Setter
    String address;

    @Setter
    String guide_id;

    @NonNull
    String trip_id;

    ArrayList<String> hashtags=new ArrayList<String>();

    @Setter
    @Getter
    String language;
    final private static DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Trips");
    Integer max_visitors;
    TripButton tripButton;
    EditFields editFields;
    ViewFields viewFields;
    Picture avatar = new Picture(Picture.Type.Trip_avatar);
    Visitors visitors = new Visitors();
    public Trip(String id) {
        setTrip_id(id);
    }
    public Integer getMax_visitors() {
        if (max_visitors == null)
            return 2;
        return Math.max(2, max_visitors);
    }
    @Exclude
    public Picture getAvatar() {
        return avatar;
    }
    public void setTrip_id(String id) {
        this.trip_id = id;
        avatar.setName(id);
    }
    private void setMax_visitors(Integer max_visitors) {
        if (max_visitors == null)
            this.max_visitors = 2;
        else
        this.max_visitors = Math.max(2, Math.max(max_visitors, visitors.getCount()));
    }
    //Работа с базой
    public void writeTripData(final Activity activity) {
        if (editFields == null || !editFields.readData())
            return;
        getAvatar().setName(getTrip_id());
        getAvatar().Upload();
        final Context context = getEditFields().getContext();
        reference.child(getTrip_id()).setValue(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if (task.isComplete()) {
                                                   activity.finish();
                                                   if (tripButton == null) {
                                                       activity.startActivity(new Intent(context, ExcursionViewActivity.class));
                                                       Toast.makeText(context, R.string.trip_create_success, Toast.LENGTH_LONG).show();
                                                       //TODO запись в БД список созданных
                                                       DatabaseReference triplist = FirebaseDatabase.getInstance().getReference().child("TripLists");
                                                       visitors.addUser(getGuide_id(), null);
                                                       triplist.child(getLanguage()).child(getDate().replace('.', '_')).child(getTrip_id()).setValue(false);
                                                       triplist.child(getLanguage()).child(getAddress()).child(getTrip_id()).setValue(false);

                                                       FirebaseDatabase.getInstance().getReference().child("Test").child(getTrip_id()).setValue(false);
                                                   } else
                                                       Toast.makeText(context, R.string.trip_edit_successfully, Toast.LENGTH_LONG).show();
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
                installSoul(dataSnapshot);
                if (viewFields != null)
                    viewFields.loadToAllField();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Exclude
    public EditFields getEditFields() {
        return editFields;
    }
    @Exclude
    public Set<String> getVisitors() {
        return visitors.get();
    }

    public void installSoul(DataSnapshot dataSnapshot) {
        setName(dataSnapshot.child("name").getValue(String.class));
        setDate(dataSnapshot.child("date").getValue(String.class));
        setAddress(dataSnapshot.child("address").getValue(String.class));
        setGuide_id(dataSnapshot.child("guide_id").getValue(String.class));
        setDescription(dataSnapshot.child("description").getValue(String.class));
        setLanguage(dataSnapshot.child("language").getValue(String.class));
        setMax_visitors(dataSnapshot.child("max_visitors").getValue(Integer.class));
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
        private EditText maxVisitorsField;

        public EditFields(EditText nameField, EditText addressField, EditText descriptionField, EditText maxVisitorsField, DatePicker dateField, Spinner languageField) {
            setAddressField(addressField);
            setDateField(dateField);
            setDescriptionField(descriptionField);
            setLanguageField(languageField);
            setNameField(nameField);
            setMaxVisitorsField(maxVisitorsField);

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
        public void setMaxVisitorsField(EditText maxVisitorsField) {
            this.maxVisitorsField = maxVisitorsField;
            maxVisitorsField.setText(Trip.this.getMax_visitors().toString());
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
            return maxVisitorsField.getText().toString();
        }
        public String getLanguage() {
            return languageField.getSelectedItem().toString();
        }
        public String getDate() {
            return ConvertDate(dateField.getDayOfMonth())+"."+ConvertDate(dateField.getMonth()+1)+"."+ConvertDate(dateField.getYear());
        }
        private String ConvertDate(int date){
            if(date<10)
                return "0"+date;
            return date+"";
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
                max_visitors.length() == 0 ||
                    CheckDate(date)) {
                Toast.makeText(getContext(), R.string.trip_create_emptydata, Toast.LENGTH_LONG).show();
                return false;
            }
            getHashtags();
            HashTagHelper hashTagHelper = HashTagHelper.Creator.create(R.color.colorPrimaryDark, new HashTagHelper.OnHashTagClickListener() {
                @Override
                public void onHashTagClicked(String hashTag) {

                }
            });
            hashTagHelper.handle(descriptionField);
            List<String> edithashtags = ConvertHashtags(hashTagHelper.getAllHashTags());
            updateHashtags(edithashtags);

            hashtags=(ArrayList)hashTagHelper.getAllHashTags();

            Trip.this.setAddress(getAddress());
            Trip.this.setDate(date);
            Trip.this.setDescription(description);
            Trip.this.setName(name);
            Trip.this.setLanguage(language);
            Trip.this.setMax_visitors(Integer.parseInt(max_visitors));
            return true;
        }
        @Exclude
        private void getHashtags(){
            if(description!=null) {
                Pattern p = Pattern.compile("#(\\w)+");


                hashtags.add(Trip.this.getDate().replace('.', '_'));
                Matcher m = p.matcher(description);
                while (m.find()) {
                    hashtags.add(m.group(1));
                }
            }
        }
        private List<String> ConvertHashtags(List<String> hashtags){
            for (int i=0;i<hashtags.size();i++) {
                hashtags.get(i).replace('.',' ').trim();
                hashtags.get(i).replace(',',' ').trim();
                hashtags.get(i).replace('/',' ').trim();
                hashtags.get(i).replace('\\',' ').trim();
                hashtags.get(i).replace('[',' ').trim();
                hashtags.get(i).replace(']',' ').trim();
            }
            return hashtags;
        }
        public void updateHashtags(List<String> edithashtags){
            HashSet<String> past = new HashSet<>(hashtags);
            HashSet<String> now = new HashSet<>(edithashtags);

            past.removeAll(now);
            for (String h:past) {
                FirebaseDatabase.getInstance().getReference().child("TripList").child(getLanguage()).child(h).child(Trip.this.genTrip_id()).setValue(null);
            }
           now.removeAll(hashtags);
            for (String h:now) {
                FirebaseDatabase.getInstance().getReference().child("TripList").child(getLanguage()).child(h).child(Trip.this.genTrip_id()).setValue(false);
            }
            hashtags=(ArrayList)edithashtags;
        }
    }
    //Отображение
    private class ViewFields {
        private TextView nameField;
        private TextView addressField;
        private TextView descriptionField;
        private TextView dateField;
        private TextView languageField;
        private TextView visitorsField;

        public ViewFields(TextView nameField, TextView addressField, TextView descriptionField, TextView visitorsField, TextView dateField, TextView languageField, ImageView imageView) {
            setAddressField(addressField);
            setDateField(dateField);
            setDescriptionField(descriptionField);
            setLanguageField(languageField);
            setNameField(nameField);
            setVisitorsField(visitorsField);
            Trip.this.getAvatar().setImageView(imageView);
        }

        public void setNameField(TextView nameField) {
            this.nameField = nameField;
            loadToNameField();
        }
        public void loadToNameField() {
            if(nameField!=null && getName() != null)
                nameField.setText(getName());
        }
        public void setAddressField(TextView addressField) {
            this.addressField = addressField;
            loadToAddressField();
        }
        public void loadToAddressField() {
            if(addressField!=null && getAddress() != null)
                addressField.setText(Trip.this.getAddress());
        }
        public void setDescriptionField(final TextView descriptionField) {
            this.descriptionField = descriptionField;
            HashTagHelper hashTagHelper = HashTagHelper.Creator.create(R.color.colorPrimaryDark, new HashTagHelper.OnHashTagClickListener() {
                @Override
                public void onHashTagClicked(String hashTag) {
                    Intent intent = new Intent(descriptionField.getContext(), TripList.class);
                    intent.putExtra("hashtag",hashTag);
                    intent.putExtra("language",Trip.this.getLanguage());
                    descriptionField.getContext().startActivity(intent);
                }
            });
            hashTagHelper.handle(descriptionField);
            loadToDescriptionField();
        }
        public void loadToDescriptionField() {
            if(descriptionField!=null && getDescription() != null)
                descriptionField.setText(Trip.this.getDescription());
        }
        public void setVisitorsField(TextView visitorsField) {
            this.visitorsField = visitorsField;
            loadToVisitorsField();
        }
        public void loadToVisitorsField() {
            if(visitorsField !=null && getMax_visitors() != null)
                visitorsField.setText(getCountVisitors()+"/"+getMax_visitors()+" participants");
        }
        public void setDateField(TextView dateField) {
            this.dateField = dateField;
            loadToDateField();
        }
        public void loadToDateField() {
            if(dateField!=null && getDate() != null)
                dateField.setText(Trip.this.getDate());
        }
        public void setLanguageField(TextView languageField) {
            this.languageField = languageField;
            loadToLanguageField();
        }
        public void loadToLanguageField() {
            if(languageField!=null && getLanguage() != null)
                languageField.setText(getLanguage());
        }
        public void loadToAllField() {
            loadToLanguageField();
            loadToDateField();
            loadToVisitorsField();
            loadToDescriptionField();
            loadToAddressField();
            loadToNameField();
        }
    }
    @Exclude
    private Integer getCountVisitors() {
        return visitors.getCount();
    }

    public void setEditFields(EditText nameField, EditText addressField, EditText descriptionField, EditText maxVisitorsField, DatePicker dateField, Spinner languageField) {
        this.editFields = new EditFields(nameField, addressField, descriptionField, maxVisitorsField, dateField, languageField);
    }
    public void setViewFields(TextView nameField, TextView addressField, TextView descriptionField, TextView visitorsField, TextView dateField, TextView languageField, ImageView imageView) {
        this.viewFields = new ViewFields(nameField, addressField, descriptionField, visitorsField, dateField, languageField, imageView);
        readTripData();
    }
    public String getGuide_id() {
        if (guide_id == null)
            guide_id =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        return guide_id;
    }
    @Exclude
    public String getTrip_id() {
        if (trip_id == null)
            trip_id = genTrip_id();
        return trip_id;
    }
    private String genTrip_id() {
        return reference.push().getKey();
    }
    //Запись/отпись
    public class Visitors {
        private HashSet<String> visitors = new HashSet<String>();
        private HashSet<String> getVisitors() {
            return this.visitors;
        }
        public Set<String> get() {
            if (visitors.size() == 0)
                download();
            return getVisitors();
        }
        public void download() {
            FirebaseDatabase.getInstance().getReference().child("Visitors").child(getTrip_id()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap _visitors = new HashMap<String, String>();
                    for ( DataSnapshot v:dataSnapshot.getChildren()) {
                        _visitors.put(v.getKey(), v.getValue().toString());
                    }
                    Set<String> past = (Set<String>) getVisitors().clone();
                    Set<String> now = _visitors.keySet();
                    past.removeAll(now);
                    for (String k:past) {
                        visitors.remove(k);
                    }
                    visitors.addAll(_visitors.keySet());
                    if (viewFields != null)
                        viewFields.loadToVisitorsField();
                    if (tripButton != null)
                        tripButton.modeUpdate();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        public boolean isVisitor(String user_id) {
            return getVisitors().contains(user_id);
        }
        public void addUser(final String user_id, final Context context) {
            FirebaseDatabase.getInstance().getReference().child("Visitors").child(getTrip_id()).child(user_id).setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                    if (getGuide_id().contentEquals(user_id))
                        FirebaseDatabase.getInstance().getReference().child("TripLists").child("Guide").child(user_id).child(getTrip_id()).setValue(false);
                    else if (context != null)
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference().child("TripLists").child("Participant").child(user_id).child(getTrip_id()).setValue(false);
                            Toast.makeText(context, "Вы записаны на экскурсию", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(context, "FAIL", Toast.LENGTH_LONG).show();
                }
            });
        }
        public void deleteUser(final String user_id, final Context context) {
            FirebaseDatabase.getInstance().getReference().child("Visitors").child(getTrip_id()).child(user_id).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                    if (context != null)
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference().child("TripLists").child("Participant").child(user_id).child(getTrip_id()).setValue(null);
                            Toast.makeText(context, "Вы отписаны на экскурсию", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(context, "FAIL", Toast.LENGTH_LONG).show();
                }
            });
        }
        public int getCount() {
            if (visitors.size() == 0) {
                download();
            }
            return visitors.size();
        }
    }
    private class TripButton {
        private Button button;
        private ButtonMode mode;
        @Setter
        @Getter
        private Activity activity;
        @Setter
        @Getter
        private String user;
        public TripButton(Activity activity, Button button, String user) {
            setUser(user);
            setActivity(activity);
            setButton(button);
        }
        private void modeUpdate() {
            if (CheckDate(Trip.this.getDate())) //TODO проверка, "Время экскурсии в прошлом?"
                setMode(ButtonMode.CloseTrip);
            else if (getUser().contentEquals(getGuide_id()))
                setMode(ButtonMode.EditTrip);
            else if (visitors.isVisitor(getUser()))
                setMode(ButtonMode.DelUser);
            else if (visitors.getCount() >= getMax_visitors())
                setMode(ButtonMode.NoPlaces);
            else
                setMode(ButtonMode.AddUser);
        }
        private void setMode(ButtonMode mode) {
            this.mode = mode;
            String text;
            switch (mode) {
                case AddUser:
                    text = "I'm in";
                    break;
                case DelUser:
                    text = "I'm out";
                    break;
                case EditTrip:
                    text = "Edit";
                    break;
                case CloseTrip:
                    text = "Close";
                    break;
                case NoPlaces:
                    text = "No places";
                    break;
                default:
                    text = "Error";
                    break;
            }
            button.setText(text);
        }
        private void setButton(Button button) {
            this.button = button;
            final Activity c = getActivity();
            modeUpdate();
            this.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (mode) {
                        case AddUser:
                            visitors.addUser(getUser(), c);
                            break;
                        case DelUser:
                            visitors.deleteUser(getUser(), c);
                            break;
                        case EditTrip:
                            Intent intent = new Intent(c, CreateTrip.class);
                            c.startActivity(intent);
                            break;
                        case CloseTrip:
                            //text = "Close";
                            break;
                        case NoPlaces:
                            //text = "No places";
                            break;
                        default:
                            //text = "Error";
                            break;
                    }
                }});
        }
    }
    public void setTripButton(Activity activity, Button button, String user_id) {
        this.tripButton = new TripButton(activity, button, user_id);
    }
    public boolean CheckDate(String curr_date){
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        Date ex_date;
        try {
            date= dateFormat.parse(dateFormat.format(new Date()));
            ex_date = dateFormat.parse(curr_date);

        }
        catch (ParseException e){
            return false;
        }
        if(date.compareTo(ex_date)>0)
            return true;
        return false;
    }
    private enum ButtonMode {AddUser, DelUser, EditTrip, CloseTrip, NoPlaces}
}
