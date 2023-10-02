package com.prashant.mychat.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prashant.mychat.Adapter.UserAdapter;
import com.prashant.mychat.Model.User;
import com.prashant.mychat.Model.UserObject;
import com.prashant.mychat.databinding.FragmentChatsBinding;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> users;
    private FragmentChatsBinding binding;
    Cursor cursor;
    String phone;
    ArrayList<UserObject> userList, contactList;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatsBinding.inflate(LayoutInflater.from(getContext()));
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        users = new ArrayList<>();
        userList = new ArrayList<>();
        contactList = new ArrayList<>();

        readUsers();

        return binding.getRoot();
    }

    @SuppressLint("Range")
    private void readUsers() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            cursor = getActivity().getApplication().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            }
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if (phone.length() <=10)
                continue;

            if (!String.valueOf(phone.charAt(0)).equals("+"))
                phone = "+61" +phone;
            phone = phone.replace(" ", "");
            phone = phone.replace("-", "");
            phone = phone.replace("(", "");
            phone = phone.replace(")", "");

            final UserObject CONTACT = new UserObject("",name, phone);
            contactList.add(CONTACT);


            final FirebaseUser FIREBASE_USER = FirebaseAuth.getInstance().getCurrentUser();
            final DatabaseReference USER_DB = FirebaseDatabase.getInstance().getReference().child("Users");
            Query query = USER_DB.orderByChild("phone").equalTo(CONTACT.getPhone());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            User user = childSnapshot.getValue(User.class);
                            if (!user.getId().equals(FIREBASE_USER.getUid()))
                                users.add(user);
                        }
                        userAdapter = new UserAdapter(getContext(), users,false);
                        recyclerView.setAdapter(userAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        cursor.close();
    }
}
