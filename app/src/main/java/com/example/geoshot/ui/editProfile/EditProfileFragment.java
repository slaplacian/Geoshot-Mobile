package com.example.geoshot.ui.editProfile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoshot.R;
import com.example.geoshot.generalUtilities.get.GetEditPerfil;
import com.example.geoshot.generalUtilities.imageUtils.ImageUtilsPika;
import com.example.geoshot.generalUtilities.put.PutEditPerfil;
import com.example.geoshot.generalUtilities.sqlite.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditProfileFragment extends Fragment {

    ActivityResultLauncher<Intent> resultLauncher;
    private Uri imageUri = null;
    private EditProfileViewModel mViewModel;
    TextView username_editpage, change_image_editpage;
    EditText edit_password_editpage, confirm_password_editpage, current_password_editpage;
    ImageView profile_image_editpage;
    Button btn_change_editpage;

    String encodedString;

    double accuracy;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        registerResult();
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        username_editpage = v.findViewById(R.id.username_editpage);
        profile_image_editpage = v.findViewById(R.id.profile_image_editpage);
        change_image_editpage = v.findViewById(R.id.change_image_editpage);
        edit_password_editpage = v.findViewById(R.id.edit_password_editpage);
        confirm_password_editpage = v.findViewById(R.id.confirm_password_editpage);
        btn_change_editpage = v.findViewById(R.id.btn_change_editpage);
        current_password_editpage = v.findViewById(R.id.current_password_editpage);


        btn_change_editpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementação para a troca de senha
                String encodedStr = "";

                if(imageUri != null){
                    encodedStr = convertUriToBase64(getContext(), imageUri);
                    Log.d("Depurando", "Estou aquiiiii");
                    ImageUtilsPika.setImageToViewProfile(getContext(), profile_image_editpage, encodedStr);
                }

                String confirmPassword = confirm_password_editpage.getText().toString();

                String oldPassword =  current_password_editpage.getText().toString();

                String password = edit_password_editpage.getText().toString();

                String username = SessionManager.getSession(getContext());

                String response = PutEditPerfil.put(username,encodedStr,oldPassword,password,confirmPassword);

                JSONObject json;

                try {
                    json = new JSONObject(response);
                    String status = json.getString("status");
                    if(status.equals("error")) {
                        if(json.getString("non-equal-password").equals("true")) {
                            Toast.makeText(getActivity(), "As senhas não são iguais!", Toast.LENGTH_LONG).show();
                        } else if(json.getString("no-old-password-on-update").equals("true")) {
                            Toast.makeText(getActivity(), "Precisa da senha antiga!", Toast.LENGTH_LONG).show();
                        } else if(json.getString("no-photo").equals("true")) {
                            Toast.makeText(getActivity(), "Nada a alterar!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Alterado com Sucesso!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e ) {
                    Log.e("Depurando", "Erro Edit Perfil Json" + e.getMessage());
                    throw new RuntimeException(e);
                }



            }
        });

        change_image_editpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
                Toast.makeText(getActivity(), "Sua Imagem será trocada quando apertar TROCAR", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pickImage() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(pickImageIntent);
    }

    @Override
    public void onStart() {
        super.onStart();

        String username = SessionManager.getSession(getContext());

        String response = GetEditPerfil.get(username);

        parseJson(response);

        username_editpage.setText(username);
        ImageUtilsPika.setImageToViewProfile(getContext(),profile_image_editpage,encodedString);


    }

    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                            imageUri = result.getData().getData();
                            if (imageUri != null) {
                                // Atualize a imagem no ImageView aqui
                                ImageUtilsPika.setImageToViewProfile(getContext(), profile_image_editpage, imageUri.toString());
                            }
                        } else {
                            Log.d("Depurando", "Erro ao adicionar a imagem: resultado inválido");
                        }
                    }
                }
        );
    }

    private String convertUriToBase64(Context context, Uri uri) {
        String base64String = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            byte[] bytes = getBytes(inputStream);
            base64String = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void parseJson(String jsonText) {
//        Log.d("Depurando", "HomeFragment -> parseJson -> Entrei no parseJson");
        try {
            JSONObject json = new JSONObject(jsonText);
            JSONObject user = json.getJSONObject("user");

            accuracy = user.getDouble("accuracy");

            encodedString = user.getString("photo");

        } catch (JSONException e ) {
            Log.e("Depurando", "HomeFragment -> parseJson -> JSONException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
