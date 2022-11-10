# TablistManagerAPI
TablistManagerAPI is a API for displaying content on tablists for SpigotMC servers 

## Features
* Custom header and footer
![Tablist with placeholders](placeholders.png)
* Display fake players
* Fill the tablist to a custom size (with fake players, outta box)
![Tablist filled with fakePlayers](fakePlayers.png)
* Per world tablist (Only players within the World can see each others)
* Multiversion (or I think it should be since is built on top ProtocolLib)

## How can it be used ?
First, you need to download the source, build jar, and then install it on your repository.
The API needs an existing plugin to work, so you need to code one.
Next, add this to your POM.
NOTE: You must use maven shade plugin to add all the code of ths API on your plugin uber jar.

```xml
<dependencies>
  <dependency>
    <groupId>me.ernestorb</groupId>
    <artifactId>tablistmanager</artifactId>
    <version>1.0-SNAPSHOT</version>
  <dependency>
</dependencies>
```

Build your plugin jar, and then get an instance of TablistManager.

```java
  TablistManager manager = new TablistManager(this);
```

## Displaying header and footer

Get the tablist handler from TablistManager#getTablistHandler(), and to display a Tablist to a player used TablistHandler#setPlayerTablist(Player,TablistTemplate)

```java
  TablistManager manager = new TablistManager(this);
  Player p = ...
  TablistTemplate = new TablistTemplate((tablistTemplate,player) => {
    // this callback is used to update info every tick!
    tablistTemplate.setHeader("Hello!\nThis is great %player_name%\nThis is my %new_placeholder%")
    tablistTemplate.setFooter("§cYou are on %player_world%") // built in player placeholders
    tablistTemplate.setHeader(tablistTemplate.getHeader().replaceAll("%new_placeholder%", player.getGameMode().toString());
  })
  
```

## Going Further
If your main goal is send FakePlayers to tablist you have 2 choices
1. Code it yourself by the help of this API
   1. Just use FakePlayer#randomFakePlayer() to create an instance of a FakePlayer
    2. Add fake player to player's tablist with `FakePlayer#getTablistAddPacket().sendPacketOnce(Player)`
    3. And to remove it use `FakePlayer#getTablistRemovePacket().sendPacketOnce(Player)`
    4. Your done, now just use it as you want.
2. Use the built-in tablistperworld and fill tablist with fake players until.
    - It can be configured in your plugins on tablistConfig.yml