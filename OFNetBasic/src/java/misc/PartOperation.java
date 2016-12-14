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
public class PartOperation {
    
    private static String exts[] = {"pdf", "txt", "mp3", "mp4"};
    
    public static boolean isAllowed(String filename)
    {
        int slash = filename.indexOf("/");
        String ext = filename.substring(slash);
        for(String e : exts)
        {
            if(e.equals("ext")) return true;
        }
        return false;
    }
}
