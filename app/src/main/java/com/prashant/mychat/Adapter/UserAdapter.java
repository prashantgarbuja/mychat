package com.prashant.mychat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prashant.mychat.MessageActivity;
import com.prashant.mychat.Model.Chat;
import com.prashant.mychat.Model.User;
import com.prashant.mychat.R;
import com.prashant.mychat.databinding.UserItemBinding;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> users;
    private boolean ischat;
    String lastMessage;
    private UserItemBinding binding;


    public UserAdapter(Context context, List<User> users, boolean ischat) {
        this.context = context;
        this.users = users;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = UserItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User USER = users.get(position);
        holder.username.setText(USER.getUsername());
        if (USER.getImageURL().equals("default"))
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        else
            Glide.with(context).load(USER.getImageURL()).into(holder.profile_image);

        if (ischat)
            lastMessage(USER.getId(), holder.last_msg);
        else
            holder.last_msg.setVisibility(View.GONE);

        if (ischat){
            if (USER.getStatus().equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            } else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        } else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", USER.getId());
                context.startActivity(intent);
                Log.i("Luffy", "User Adapter was clicked.");
            }
        });
    }

    private void lastMessage(String userid, TextView lastMsg) {
        lastMessage = "default";
        final FirebaseUser FIREBASE_USER = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (FIREBASE_USER != null && chat!= null) {
                        if (chat.getReceiver().equals(FIREBASE_USER.getUid()) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(FIREBASE_USER.getUid())){
                            lastMessage = chat.getMessage();
                        }
                    }
                }
                switch (lastMessage){
                    case "default" :
                        lastMsg.setText("No Message");
                        break;

                    default:
                        lastMsg.setText(lastMessage);
                }
                lastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView profile_image;
        private ImageView img_on;
        public ImageView img_off;
        private TextView last_msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = binding.username;
            profile_image = binding.profileImage;
            img_on = binding.imgOn;
            img_off = binding.imgOff;
            last_msg = binding.lastMsg;
        }
    }
}
