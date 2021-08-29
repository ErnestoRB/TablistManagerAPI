# TablistManagerAPI
TablistManagerAPI for SpigotMC servers

This API is useful to display custom Tablist header and footer, and also, display FakePlayers. You can achieve it by sending packets, and since packets are wrapped by ProtocolLib,
it should be multiversion (only tested on spigot API 1.8.8). It has native support for displaying fakeplayers to fill the tablist, and tablistperworld support.

## How can it be used ?
First, you need to download the source, build jar, and then install it on your repository.
The API needs an existing plugin to work, so you need to code one.
Next, add this to your POM.

```
<dependencies>
  <dependency>
    <groupId>me.ernestorb</groupId>
    <artifactId>tablistmanager</artifactId>
    <version>1.0-SNAPSHOT</version>
  <dependency>
</dependencies>
```

Build your plugin jar, and then get an instance of TablistManager.

```
  TablistManager manager = new TablistManager(this);
```

## Displaying header and footer

Get the tablist handler from TablistManager#getTablistHandler(), and to display a Tablist to a player used TablistHandler#setPlayerTablist(Player,TablistTemplate)

```
  TablistManager manager = new TablistManager(this);
  Player p = ...
  TablistTemplate = new TablistTemplate((tablistTemplate,player) => {
    // this callback is used to update info every tick!
    tablistTemplate.setHeader("Hello!\nThis is great %player_name%\nThis is my %new_placeholder%")
    tablistTemplate.setFooter("§cYou are on %player_world%") // built in player placeholders
    tablistTemplate.setHeader(tablistTemplate.getHeader().replaceAll("%new_placeholder%", player.getGameMode().toString());
  })
  
```
