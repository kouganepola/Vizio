package com.example.kyg730.vizio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.kyg730.vizio.UI.ReaderMainActivity;
import com.example.kyg730.vizio.Users.Publisher;
import com.example.kyg730.vizio.Users.Reader;
import com.example.kyg730.vizio.Users.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;



import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "GPlusFragment";
    private int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private Button signOutButton;
    private Spinner role;
    private Button disconnectButton;
    private LinearLayout signOutView;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    private ImageView imgProfilePic;
    private User user;





    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id)).requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        setContentView(R.layout.activity_login);

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signOutButton = (Button) findViewById(R.id.sign_out_button);
        imgProfilePic = (ImageView) findViewById(R.id.img_profile_pic);
        role = (Spinner) findViewById(R.id.role);

        mStatusTextView = (TextView) findViewById(R.id.status);
        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.user_default);
        imgProfilePic.setImageBitmap(icon);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }

        });


        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                updateUI(false);
                            }
                        });
            }

        });


    }


    @Override
    public void onStart() {
        super.onStart();
        File mydir = new File(getApplicationContext().getFilesDir(),"Media");
        mydir.mkdir();
        handleSignInResult();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
           /// handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
           showProgressDialog();

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                   /// handleSignInResult(googleSignInResult);
                }
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
           /// handleSignInResult(result);
        }
    }


    private void updateUI(boolean signedIn) {
        if (signedIn) {
            signInButton.setVisibility(View.GONE);
            role.setVisibility(View.GONE);
            signOutButton.setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.user_default);
            imgProfilePic.setImageBitmap(icon);
            signInButton.setVisibility(View.VISIBLE);
            role.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);
        }
    }




   /// private void handleSignInResult(GoogleSignInResult result) {
        private void handleSignInResult() {
       /// Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        ///   if (result.isSuccess()) {
        if(true){

            // Signed in successfully, show authenticated UI.
           /// GoogleSignInAccount acct = result.getSignInAccount();
          ///  mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
           /// String id_token = acct.getIdToken();

            RequestParams params = new RequestParams();
           /// params.put("IDtoken", id_token);

            JSONObject obj = new JSONObject();

            try {
                obj.put("deviceId", "863703031920635");
                obj.put("deviceKey","a37ac670b474e316518351943cc236e7");

      /*params.put("plant","0");
    /*  } catch (JSONException e) {
        e.printStackTrace();
      }*/
                //  params.put("payload","fjf");


                //passes the results to a string builder/entity
                StringEntity se = new StringEntity(obj.toString());

                //sets the post request as the resulting string

                // params.put("payload",obj.toString());
                Log.d("HTTP","Params READY!!!");

                // try {
                // HttpClient.postJson("upload",se,new TextHttpResponseHandler() {
                HttpClient.post("",params,new JsonHttpResponseHandler() {
                    //HttpClient.post(params,new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.v("Success","nooooo"+statusCode);
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        //  JsonParser parser = new JsonParser();
                        //  JsonElement result = parser.parse(response);
                       /* if (result.isJsonObject()) {
                            JsonObject obj = result.getAsJsonObject();



                            // Log.v("dooo",obj.get("payload").getAsString());
                            // System.out.println(d_obj.get("probability").getAsFloat());



                        }
                        */
                        Log.v("Success","uuuuuu "+statusCode);

                    }
                });
            }catch (Exception e){

            }





            if(String.valueOf(role.getSelectedItem()).equals("Reader")){
                user = new Reader();
                user.start(this);
            }else if (String.valueOf(role.getSelectedItem()).equals("Publisher")){

                Intent readerAct = new Intent(this,ReaderMainActivity.class);
                startActivity(readerAct);


            }else {

            }
            //Similarly you can get the email and photourl using acct.getEmail() and  acct.getPhotoUrl()

          ///  if(acct.getPhotoUrl() != null)
             ///   new LoadProfileImage(imgProfilePic).execute(acct.getPhotoUrl().toString());

            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }

    }


    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... uri) {
            String url = uri[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            if (result != null) {


                Bitmap resized = Bitmap.createScaledBitmap(result,200,200, true);
                bmImage.setImageBitmap(resized);

            }
        }
    }




    /**
     * Created by Koumudi on 13/03/2018.
     */

}
