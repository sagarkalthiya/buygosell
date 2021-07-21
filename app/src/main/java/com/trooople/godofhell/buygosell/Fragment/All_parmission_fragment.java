package com.trooople.godofhell.buygosell.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.trooople.godofhell.buygosell.Activity.Splesh_activity;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.Component;
import com.trooople.godofhell.buygosell.UserSessionManager;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class All_parmission_fragment extends Fragment {

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private Button btnCheckPermissions;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    private View view;


    UserSessionManager session;

    public All_parmission_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_all_parmisssion, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        permissionStatus = getActivity().getSharedPreferences("permissionStatus",getActivity().MODE_PRIVATE);
        session = new UserSessionManager(getActivity());

        if(null != view){


            if(ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[1])
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[2])){
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Need Permissions");
                    builder.setMessage("This app needs Camera and Storage permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(getActivity(),permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                    // Redirect to Settings after showing Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(getContext(), "Go to Permissions to Grant  Camera and Storage", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }  else {
                    //just request the permission
                    ActivityCompat.requestPermissions(getActivity(),permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                }


                SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(permissionsRequired[0],true);
                editor.commit();
            } else {
                //You already have the permission, just go ahead.
                proceedAfterPermission();
            }


            btnCheckPermissions = (Button) view.findViewById(R.id.give_all_parmission_btn);

            btnCheckPermissions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED){
                        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[0])
                                || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[1])
                                || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[2])){
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Need Multiple Permissions");
                            builder.setMessage("This app needs d and Storage permissions.");
                            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(getActivity(),permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                            //Previously Permission Request was cancelled with 'Dont Ask Again',
                            // Redirect to Settings after showing Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Need Multiple Permissions");
                            builder.setMessage("This app needs permissions.");
                            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    sentToSettings = true;
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                    Toast.makeText(getContext(), "Go to Permissions to Grant  Camera and Storage", Toast.LENGTH_LONG).show();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }  else {
                            //just request the permission
                            ActivityCompat.requestPermissions(getActivity(),permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                        }


                        SharedPreferences.Editor editor = permissionStatus.edit();
                        editor.putBoolean(permissionsRequired[0],true);
                        editor.commit();
                    } else {
                        //You already have the permission, just go ahead.
                        proceedAfterPermission();
                    }
                }
            });
        }
    }

    private void proceedAfterPermission() {

        Toast.makeText(getActivity(), "We got All Permissions", Toast.LENGTH_LONG).show();
        ((MainActivity)getActivity()).fregment(new Home_fragment(),false);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_PHONE_STATE)){

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs phone permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getActivity(),"Unable to get Permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }
}

