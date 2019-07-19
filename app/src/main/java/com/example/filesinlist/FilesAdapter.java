package com.example.filesinlist;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.filesinlist.interfaces.AdapterClickListener;

import java.io.File;
import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {
    ArrayList<String> arrayList;
    AdapterClickListener adapterClickListener;
    String ext = null;

    public FilesAdapter(ArrayList<String> arrayList, AdapterClickListener adapterClickListener) {
        this.arrayList = arrayList;
        this.adapterClickListener = adapterClickListener;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filesitem, viewGroup, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder fileViewHolder, final int position) {
        File path = new File(arrayList.get(position));
        ext=String.valueOf(path);
        if (path.isDirectory()) {
            fileViewHolder.iconView.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
        } else {
            Uri selectedUri = Uri.fromFile(path);
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
            if (fileExtension.equals("mp3")) {
                fileViewHolder.iconView.setImageResource(R.drawable.ic_music_note_black_24dp);
            } else if (fileExtension.equals("jpg")) {
                fileViewHolder.iconView.setImageResource(R.drawable.ic_image_black_24dp);
            } else if (fileExtension.equals("JPG")) {
                fileViewHolder.iconView.setImageResource(R.drawable.ic_image_black_24dp);
            } else if (fileExtension.equals("jpeg")) {
                fileViewHolder.iconView.setImageResource(R.drawable.ic_image_black_24dp);
            } else if (fileExtension.equals("pdf")) {
                fileViewHolder.iconView.setImageResource(R.drawable.ic_picture_as_pdf_black_24dp);
            } else if (fileExtension.equals("apk")) {
                fileViewHolder.iconView.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
            } else if (fileExtension.equals("mp4")) {
                fileViewHolder.iconView.setImageResource(R.drawable.ic_video_library_black_24dp);
            } else {
                fileViewHolder.iconView.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
            }
        }
        /*if (!path.isFile()) {
            fileViewHolder.iconView.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
        } else {
            if (path.toString().endsWith(".jpg")) {
                fileViewHolder.iconView.setImageResource(R.drawable.ic_image_black_24dp);
            }else if (path.toString().endsWith(".mp3")){
                fileViewHolder.iconView.setImageResource(R.drawable.ic_music_note_black_24dp);
            }else {
                fileViewHolder.iconView.setImageResource(R.drawable.ic_launcher_background);

            }

        }*/
        fileViewHolder.textView.setText(arrayList.get(position));
        fileViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapterClickListener.onFileSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView iconView;
        View rootView;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            textView = itemView.findViewById(R.id.textviewName);
            iconView = itemView.findViewById(R.id.iconview);
        }
    }

    public String getExt(String filePath) {
        int strLength = filePath.lastIndexOf(".");
        if (strLength > 0)
            return filePath.substring(strLength + 1).toLowerCase();
        return null;
    }
}
