package com.example.filesinlist;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.example.filesinlist.interfaces.AdapterClickListener;
import com.example.filesinlist.interfaces.OnUserInteraction;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FileExplorer extends Fragment implements AdapterClickListener {
    private Button btnListShow;
    private File file;
    private ArrayList<String> listFiles;
    RecyclerView mRecyclerView;
    FilesAdapter filesAdapter;
    File currentDirectory;
    OnUserInteraction onUserInteraction;
    File list[];

    public FileExplorer() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserInteraction) {
            onUserInteraction = (OnUserInteraction) context;
        } else {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_explorer, container, false);
        listFiles = new ArrayList<>();
        filesAdapter = new FilesAdapter(new ArrayList<String>(), this);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = currentDirectory.listFiles();
        listFiles.clear();
        for (int i = 0; i < list.length; i++) {
            listFiles.add(list[i].getName());
            Log.d("name" + i, "" + listFiles.get(i));
        }
        mRecyclerView.setAdapter(new FilesAdapter(listFiles, FileExplorer.this));
        return view;
    }

    public void setRoot(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    @Override
    public void onFileSelected(int position) {

        File openFile = new File(currentDirectory, listFiles.get(position));
        if (!openFile.isFile()) {
            FileExplorer fileExplorer = new FileExplorer();
            fileExplorer.setRoot(openFile);
            //currentDirectory = openFile;
            onUserInteraction.addFragment(fileExplorer,true);


        } else {
            file = new File(currentDirectory, listFiles.get(position));
            file.getParent();
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = FileProvider.getUriForFile(getContext(), "com.example.filesinlist", file);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, getMimeType(file.getAbsolutePath()));
            startActivity(intent);
        }
    }

    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

    public String getExt(String filePath) {
        int strLength = filePath.lastIndexOf(".");
        if (strLength > 0)
            return filePath.substring(strLength + 1).toLowerCase();
        return null;
    }

    private String getMimeType(String url) {
        String parts[] = url.split("\\.");
        String extension = parts[parts.length - 1];
        String type = null;
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

}
