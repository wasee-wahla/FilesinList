package com.example.filesinlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.example.filesinlist.interfaces.AdapterClickListener;
import com.example.filesinlist.interfaces.OnUserInteraction;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnUserInteraction {
    private Button btnListShow;
    private File file;
    private ArrayList<String> listFiles;
    RecyclerView mRecyclerView;
    FilesAdapter filesAdapter;
    File currentDirectory;
    File list[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listFiles = new ArrayList<>();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            currentDirectory = new File(Environment.getExternalStorageDirectory().toString());

            FileExplorer fileExplorer = new FileExplorer();
            fileExplorer.setRoot(currentDirectory);
            addFragment(fileExplorer,false);
        }

    }

    private void getAllFilesDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file != null) {
                    if (file.isDirectory()) {
                        getAllFilesDirectory(file);
                    } else {
                        file.getAbsolutePath();
                        listFiles.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    @Override
    public void addFragment(Fragment fragment,boolean addBack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainFrame, fragment);
        if (addBack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

}
