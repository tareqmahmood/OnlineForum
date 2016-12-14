/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author HP
 */
public class File {
    private int file_id;
    private String filename;
    private int filesize;

    public File(int file_id, String filename, int filesize) {
        this.file_id = file_id;
        this.filename = filename;
        this.filesize = filesize;
    }

    public int getFile_id() {
        return file_id;
    }

    public String getFilename() {
        return filename;
    }

    public int getFilesize() {
        return filesize;
    }
    
    public String getNormalizedFilesize()
    {
        if(filesize < 1024) return filesize + " B";
        if(filesize < (1024 * 1024)) return String.format("%.2f KB", filesize / 1024.0);
        return String.format("%.2f MB", (filesize / (1024.0 * 1024.0)));
    }
}
