/*
 * Copyright 2016 John Grosh (jagrosh).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spectra.commands;

import java.util.List;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import spectra.Argument;
import spectra.Command;
import spectra.Sender;
import spectra.SpConst;
import spectra.web.YoutubeSearcher;

/**
 *
 * @author John Grosh (jagrosh)
 */
public class YoutubeSearch extends Command {
    private final YoutubeSearcher youtube;
    public YoutubeSearch(YoutubeSearcher youtube)
    {
        this.youtube = youtube;
        this.command = "youtube";
        this.aliases = new String[]{"yt"};
        this.help = "find the best Youtube result for the given query";
        this.longhelp = "This command finds the top Youtube result for the given query.";
        this.arguments = new Argument[]{
            new Argument("query",Argument.Type.LONGSTRING,true,1,500)
        };
        this.cooldown = 10;
        this.cooldownKey = event -> event.getAuthor().getId()+"|youtube";
    }
    @Override
    protected boolean execute(Object[] args, MessageReceivedEvent event) {
        String query = (String)args[0];
        event.getChannel().sendTyping();
        List<String> urls = youtube.getResults(query, 1);
        if(urls==null)
        {
            Sender.sendResponse(SpConst.ERROR+"An error occurred while searching", event);
            return false;
        }
        if(urls.isEmpty())
        {
            Sender.sendResponse(SpConst.WARNING+"No results found for \""+query+"\"", event);
            return false;
        }
        Sender.sendResponse("<@"+event.getAuthor().getId()+"> \uD83D\uDCF9 https://www.youtube.com/watch?v="+urls.get(0), event);
        return true;
    }
}
