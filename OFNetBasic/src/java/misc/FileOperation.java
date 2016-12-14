/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

/**
 *
 * @author HP
 */
public class FileOperation {
    public static String getNormalizedSize(int size)
    {
        if(size < 1024) return size + " B";
        if(size < (1024 * 1024)) return String.format("%.2f KB", size / 1024.0);
        return String.format("%.2f MB", (size / (1024.0 * 1024.0)));
    }
}
