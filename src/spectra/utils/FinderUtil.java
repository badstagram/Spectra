/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spectra.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;

/**
 *
 * @author jagrosh
 */
public class FinderUtil {
    
    public static List<User> findUsers(String query, List<User> users)
    {
        String id;
        String discrim = null;
        if(query.matches("<@!?\\d+>"))
        {
            id = query.replaceAll("<@!?(\\d+)>", "$1");
            for(User u: users)
                if(u.getId().equals(id))
                    return Collections.singletonList(u);
        }
        else if(query.matches("^.*#\\d{4}$"))
        {
            discrim = query.substring(query.length()-4);
            query = query.substring(0,query.length()-5).trim();
        }
        ArrayList<User> exact = new ArrayList<>();
        ArrayList<User> wrongcase = new ArrayList<>();
        ArrayList<User> startswith = new ArrayList<>();
        ArrayList<User> contains = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for(User u: users)
        {
            if(discrim!=null && !u.getDiscriminator().equals(discrim))
                continue;
            if(u.getUsername().equals(query))
                exact.add(u);
            else if (exact.isEmpty() && u.getUsername().equalsIgnoreCase(query))
                wrongcase.add(u);
            else if (wrongcase.isEmpty() && u.getUsername().toLowerCase().startsWith(lowerQuery))
                startswith.add(u);
            else if (startswith.isEmpty() && u.getUsername().toLowerCase().contains(lowerQuery))
                contains.add(u);
        }
        if(!exact.isEmpty())
            return exact;
        if(!wrongcase.isEmpty())
            return wrongcase;
        if(!startswith.isEmpty())
            return startswith;
        return contains;
    }
    
    public static List<User> findUsers(String query, Guild guild)
    {
        String id;
        String discrim = null;
        if(query.matches("<@!?\\d+>"))
        {
            id = query.replaceAll("<@!?(\\d+)>", "$1");
            for(User u: guild.getUsers())
                if(u.getId().equals(id))
                    return Collections.singletonList(u);
        }
        else if(query.matches("^.*#\\d{4}$"))
        {
            discrim = query.substring(query.length()-4);
            query = query.substring(0,query.length()-5).trim();
        }
        ArrayList<User> exact = new ArrayList<>();
        ArrayList<User> wrongcase = new ArrayList<>();
        ArrayList<User> startswith = new ArrayList<>();
        ArrayList<User> contains = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for(User u: guild.getUsers())
        {
            String nickname = guild.getNicknameForUser(u);
            if(discrim!=null && !u.getDiscriminator().equals(discrim))
                continue;
            if(u.getUsername().equals(query) || (nickname!=null && nickname.equals(query)))
                exact.add(u);
            else if (exact.isEmpty() && (u.getUsername().equalsIgnoreCase(query) || (nickname!=null && nickname.equalsIgnoreCase(query))))
                wrongcase.add(u);
            else if (wrongcase.isEmpty() && (u.getUsername().toLowerCase().startsWith(lowerQuery) || (nickname!=null && nickname.toLowerCase().startsWith(lowerQuery))))
                startswith.add(u);
            else if (startswith.isEmpty() && (u.getUsername().toLowerCase().contains(lowerQuery) || (nickname!=null && nickname.toLowerCase().contains(lowerQuery))))
                contains.add(u);
        }
        if(!exact.isEmpty())
            return exact;
        if(!wrongcase.isEmpty())
            return wrongcase;
        if(!startswith.isEmpty())
            return startswith;
        return contains;
    }
    
    public static List<TextChannel> findTextChannel(String query, List<TextChannel> channels)
    {
        String id;
        if(query.matches("<#\\d+>"))
        {
            id = query.replaceAll("<#(\\d+)>", "$1");
            for(TextChannel tc: channels)
                if(tc.getId().equals(id))
                    return Collections.singletonList(tc);
        }
        ArrayList<TextChannel> exact = new ArrayList<>();
        ArrayList<TextChannel> wrongcase = new ArrayList<>();
        ArrayList<TextChannel> startswith = new ArrayList<>();
        ArrayList<TextChannel> contains = new ArrayList<>();
        for(TextChannel tc: channels)
        {
            if(tc.getName().equals(query))
                exact.add(tc);
            else if (tc.getName().equalsIgnoreCase(query) && exact.isEmpty())
                wrongcase.add(tc);
            else if (tc.getName().toLowerCase().startsWith(query) && wrongcase.isEmpty())
                startswith.add(tc);
            else if (tc.getName().toLowerCase().contains(query) && startswith.isEmpty())
                contains.add(tc);
        }
        if(!exact.isEmpty())
            return exact;
        if(!wrongcase.isEmpty())
            return wrongcase;
        if(!startswith.isEmpty())
            return startswith;
        return contains;
    }
    
    public static List<Role> findRole(String query, List<Role> roles)
    {
        String id;
        if(query.matches("<@&\\d+>"))
        {
            id = query.replaceAll("<@&(\\d+)>", "$1");
            for(Role role: roles)
                if(role.getId().equals(id))
                    return Collections.singletonList(role);
        }
        if(query.matches("[Ii][Dd]\\s*:\\s*\\d+"))
        {
            id = query.replaceAll("[Ii][Dd]\\s*:\\s*(\\d+)", "$1");
            for(Role role: roles)
                if(role.getId().equals(id))
                    return Collections.singletonList(role);
        }
        ArrayList<Role> exact = new ArrayList<>();
        ArrayList<Role> wrongcase = new ArrayList<>();
        ArrayList<Role> startswith = new ArrayList<>();
        ArrayList<Role> contains = new ArrayList<>();
        for(Role role: roles)
        {
            if(role.getName().equals(query))
                exact.add(role);
            else if (role.getName().equalsIgnoreCase(query) && exact.isEmpty())
                wrongcase.add(role);
            else if (role.getName().toLowerCase().startsWith(query) && wrongcase.isEmpty())
                startswith.add(role);
            else if (role.getName().toLowerCase().contains(query) && startswith.isEmpty())
                contains.add(role);
        }
        if(!exact.isEmpty())
            return exact;
        if(!wrongcase.isEmpty())
            return wrongcase;
        if(!startswith.isEmpty())
            return startswith;
        return contains;
    }
}
