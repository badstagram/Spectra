/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spectra.utils;

import java.util.Arrays;
import java.util.List;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import spectra.SpConst;

/**
 *
 * @author John Grosh (jagrosh)
 */
public class FormatUtil {
    
    //Cleanly splits on whitespace in 2
    public static String[] cleanSplit(String input){return cleanSplit(input,"\\s+",2);}
    
    //Cleanly splits on whitespace to specified size
    public static String[] cleanSplit(String input, int size){return cleanSplit(input,"\\s+",size);}
    
    //Cleanly splits in 2 on specified regex
    public static String[] cleanSplit(String input, String regex){return cleanSplit(input,regex,2);}
    
    //Cleanly splits on given to specified size, padding with null
    public static String[] cleanSplit(String input, String regex, int size)
    {
        return Arrays.copyOf(input.split(regex, size), size);
    }
    
    public String demention(String input)
    {
        return input.replace("<@", "<@\u180E");
    }
    
    public static String listOfUsers(List<User> list, String query)
    {
        String out = String.format(SpConst.MULTIPLE_FOUND, "users", query);
        for(int i=0; i<6 && i<list.size(); i++)
            out+="\n - "+list.get(i).getUsername()+" #"+list.get(i).getDiscriminator();
        if(list.size()>6)
            out+="\n**And "+(list.size()-6)+" more...**";
        return out;
    }
    
    public static String listOfChannels(List<TextChannel> list, String query)
    {
        String out = String.format(SpConst.MULTIPLE_FOUND, "text channels", query);
        for(int i=0; i<6 && i<list.size(); i++)
            out+="\n - "+list.get(i).getName()+" (<#"+list.get(i).getId()+">)";
        if(list.size()>6)
            out+="\n**And "+(list.size()-6)+" more...**";
        return out;
    }
    
    public static String listOfRoles(List<Role> list, String query)
    {
        String out = String.format(SpConst.MULTIPLE_FOUND, "roles", query);
        for(int i=0; i<6 && i<list.size(); i++)
            out+="\n - "+list.get(i).getName()+" (ID:"+list.get(i).getId()+")";
        if(list.size()>6)
            out+="\n**And "+(list.size()-6)+" more...**";
        return out;
    }
    
}
