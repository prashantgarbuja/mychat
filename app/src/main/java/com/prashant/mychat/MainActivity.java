package com.prashant.mychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prashant.mychat.Adapter.ViewPager2Adapter;
import com.prashant.mychat.Fragments.ChatsFragment;
import com.prashant.mychat.Fragments.ProfileFragment;
import com.prashant.mychat.Fragments.UsersFragment;
import com.prashant.mychat.Model.Chat;
import com.prashant.mychat.Model.User;
import com.prashant.mychat.databinding.ActivityMainBinding;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        // Initialize the binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // Set the root view using the binding
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");

        profile_image = binding.profileImage;
        username = binding.username;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class); //from Model>User.class
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher_round);
                } else {
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final TabLayout tabLayout = binding.tabLayout;
        final ViewPager2 viewPager2 = binding.viewPager2;

        reference = FirebaseDatabase.getInstance().getReference( "Chats" );
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(getSupportFragmentManager(),getLifecycle());
                int unread = 0;
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals( firebaseUser.getUid() ) && !chat.isIsseen()) {
                        unread++;
                    }
                }

                if (unread == 0) {
                    viewPager2Adapter.addFragment( new ChatsFragment(), "Chats" );
                } else {
                    viewPager2Adapter.addFragment( new ChatsFragment(), "(" + unread + ") Chats" );
                }

                viewPager2Adapter.addFragment( new UsersFragment(), "Users" );
                viewPager2Adapter.addFragment( new ProfileFragment(), "Profile" );

                viewPager2.setAdapter(viewPager2Adapter);

                //Here, the TabLayout title is fixed i.e, Chats, Users, and Profile.
                TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                        tabLayout, viewPager2, (tab, position) -> {
                    if (position == 0)
                        tab.setText("Chats");
                     else if (position == 1)
                        tab.setText("Users");
                     else if (position == 2)
                        tab.setText("Profile");
                    }
                );
                tabLayoutMediator.attach();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
   //Ctrl + o : Override FragmentActivity methods
    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

    @Override
    protected void onStop() {
        super.onStop();
        status("offline");
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    private void status(String status) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap <String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
         reference.updateChildren(hashMap);
    }

    //Override android.app.Activity methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Cannot implement Switch statement because the constants in the resources R is not declared as final.
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
            return true;
        }
        return false;
    }
}