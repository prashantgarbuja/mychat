package com.prashant.mychat.Fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.prashant.mychat.Model.User;
import com.prashant.mychat.R;
import com.prashant.mychat.databinding.FragmentProfileBinding;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    CircleImageView image_profile;
    TextView username;
    DatabaseReference reference;
    FirebaseUser user;
    StorageReference storageReference;
    private Uri imageUri;
    private StorageTask uploadTask;
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(LayoutInflater.from(getContext()));

        image_profile = binding.profileImage;
        username = binding.username;

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                Log.i("Luffy", "Username : "+user.getUsername());
                if (user.getImageURL().equals("default"))
                    image_profile.setImageResource(R.mipmap.ic_launcher);
                else {
                    if (getActivity() == null)
                        return;
                    Glide.with(getContext()).load(user.getImageURL()).into(image_profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        image_profile.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        }));
        return binding.getRoot();
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Caller
        getResult.launch(intent);
    }
    // Receiver
    ActivityResultLauncher<Intent> getResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        imageUri = data.getData();
                        if (uploadTask != null && uploadTask.isInProgress()) {
                            Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                        } else {
                            uploadImage();
                        }
                    }
                }
            }
    );

    private void uploadImage() {
        final ProgressBar PROGRESS_BAR = binding.progressBar;

        if (imageUri != null) {
            final StorageReference FILE_REFERENCE = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = FILE_REFERENCE.putFile(imageUri);
            uploadTask.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                if (!task.isSuccessful()){
                    throw  task.getException();
                }
                return  FILE_REFERENCE.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    String uri = downloadUri.toString();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("imageURL", ""+uri);
                    reference.updateChildren(map);

                    PROGRESS_BAR.setVisibility(View.GONE);
                    //pd.dismiss();
                } else {
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                    PROGRESS_BAR.setVisibility(View.GONE);
                    //pd.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    PROGRESS_BAR.setVisibility(View.GONE);
                    //pd.dismiss();
                }
            });
            PROGRESS_BAR.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}
