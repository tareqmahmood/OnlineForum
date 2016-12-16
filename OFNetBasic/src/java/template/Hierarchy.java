/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package template;

import db.DataAccess;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import model.Category;

/**
 *
 * @author HP
 * 
 * To get generated string call from JSP file like below
 * 
 * out.print(Hierarchy.getString(session));  
 *
 */

public class Hierarchy {
    
    private static DataAccess db;
    
    public static String getString(HttpSession session)
    {
        db = (DataAccess) session.getAttribute("db");
        return codeInOrder(Category.motherCategory);
    }
    
    private static String codeInOrder(Category category)
    {
        String code = String.format("<ul><li><a href='recent.jsp?category_id=%d'>%s</a>", category.getCategory_id(), category.getCategory_name());
        ArrayList<Category> childList = db.getChildCategories(category.getCategory_id());
        for(Category c : childList)
        {
            code += codeInOrder(c);
        }
        return code + "</li></ul>";
    }
}
