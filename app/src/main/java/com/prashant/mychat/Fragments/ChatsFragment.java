package com.prashant.mychat.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prashant.mychat.Adapter.UserAdapter;
import com.prashant.mychat.Model.Chatlist;
import com.prashant.mychat.Model.User;
import com.prashant.mychat.Notifications.Token;
import com.prashant.mychat.databinding.FragmentChatsBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> users;
    FirebaseUser user;
    private List<Chatlist> usersList;
    private FragmentChatsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatsBinding.inflate(LayoutInflater.from(getContext()));

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        user = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ChatList").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    usersList.clear();
                    Log.i("Chats Fragment", "Inside onDataChange" + dataSnapshot.getChildren());
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.i("Chats Fragment", "Inside DataSnapshot");
                        Chatlist chatlist = snapshot.getValue(Chatlist.class);
                        Log.i("Chats Fragment", "Snapshot value is " + snapshot.getValue().getClass().getSimpleName());
                        Log.i("Chats Fragment", "1st chatList id is " + chatlist.getId());
                        usersList.add(chatlist);
                    }
                    chatList();
                } else
                    Log.i("Chats Fragment", "Snapshot does not exist");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.e("Chats Fragment", "Database Error");

            }
        });
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful())
                    Log.w("Chats Fragment", "Fetching FCM registration token failed", task.getException());
                else
                    updateToken(task.getResult());
            }
        });
        return binding.getRoot();
    }

    private void updateToken(String token) {
        DatabaseReference token_reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        Log.i("Token", "Value of token is "+token);
        token_reference.child(user.getUid()).setValue(token1);
    }

    private void chatList() {
        users = new ArrayList<>();
        DatabaseReference users_reference = FirebaseDatabase.getInstance().getReference("Users");
        users_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                Log.i("Chats Fragment","dataSnapshot getChildren "+dataSnapshot.getChildren());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user1 = snapshot.getValue(User.class);
                    Log.i("Chats Fragment", "User1 id is " + user1.getId());
                    for (Chatlist chatlist : usersList) {
                        Log.i("Chats Fragment", "2nd Chatlist id is " + chatlist.getId());
                        if (user1.getId().equals(chatlist.getId()))
                            users.add(user1);
                    }
                }
                userAdapter = new UserAdapter(getContext(), users, true);
                Log.i("Chats Fragment", "Item count is " + userAdapter.getItemCount());
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
