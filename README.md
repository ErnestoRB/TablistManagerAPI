# TablistManagerAPI
TablistManagerAPI is a API for displaying content on tablists for SpigotMC servers. Depends on ProtocolLib.
Actually, this repo contains two packages: the API, and a plugin which depends on the API.

## Features of the plugin

* Display slots (fake players)
* Fill the tablist to a custom size (with fake players, outta box)
![Tablist filled with fakePlayers](fakePlayers.png)
* Per world tablist (Only players within the World can see each others)
* Multiversion (or I think it should be since is built on top ProtocolLib)

## Features of the API
All of the API and:
* Custom header and footer
  ![Tablist with placeholders](placeholders.png)

## Developing a plugin using the API
You can use the tablistmanager-api package by itself (suitable for customization), or use the tablistmanager-plugin.

### Installing using Maven
The API and the Plugin are published to GitHub Packages.

1. First, you should [create a personal access token](https://docs.github.com/es/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) 
2. Then, authenticate with that token on your settings.xml, as described [here](https://docs.github.com/es/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#authenticating-to-github-packages)
3. Finally, add the dependency you want to your project POM.

```xml
<dependencies>
   ...
   <dependency>
      <groupId>com.ernestorb.tablistmanager</groupId>
      <artifactId>tablistmanager-api</artifactId>
      <version>1.0.0</version>
   </dependency>
   ...
</dependencies>
```

```xml
<dependencies>
   ...
   <dependency>
      <groupId>com.ernestorb.tablistmanager</groupId>
      <artifactId>tablistmanager-plugin</artifactId>
      <version>1.0.0</version>
   </dependency>
   ...
</dependencies>
```

### Developing using the API
1. Declare the tablistmanager-api dependency to your plugin POM
2. Create an instance of TablistManager. There may be just one instance of this class, having more can lead to issues.
```java
  TablistManager manager = new TablistManager(this);
```
3. As the API depends on ProtocolLib, you need to make sure it gets loaded before. Add the following to your plugin.yml
```yaml
loadbefore: [ ProtocolLib ]
depend: [ ProtocolLib ]
```


### Developing using the plugin
Download the latest plugin available. (Releases tab)

1. Install the plugin downloaded before in you server plugin's folder.
2. Declare the tablistmanager-plugin dependency to your plugin POM.
3. In your plugin.yml add the following lines
```yaml
loadbefore: [ TablistManager ]
depend: [ TablistManager ]
```
3. Get the attached TablistManager instance of the TablistManagerPlugin.
```java
Plugin pl = this.getServer().getPluginManager().getPlugin("TablistManager");
if(pl != null && pl instanceof  TablistManagerPlugin) {
    this.manager = ((TablistManagerPlugin)pl).getManager();
} else {
    this.getLogger().severe("Plugin cannot be loaded due lack of TablistManager plugin");
    this.getPluginLoader().disablePlugin(this);
    return;
}
```

## Code examples

### Displaying header and footer

Get the tablist handler attached to any instance of TablistManager, define a template and send it to the player you want.

```java
  TablistManager manager = ...
  Player p = ...
  TablistTemplate myTemplate = new TablistTemplate((tablistTemplate,player) => {
    // this callback is used to update info every 20 ticks!
    tablistTemplate.setHeader("Hello!\nThis is great %player_name%\nThis is my %new_placeholder%")
    tablistTemplate.setFooter("§cYou are on %player_world%") // built in player placeholders
    tablistTemplate.setHeader(tablistTemplate.getHeader().replaceAll("%new_placeholder%", player.getGameMode().toString());
  });
  manager.getTablistHandler().setPlayerTablist(player, myTemplate);
  ```

### Adding slots
If you want to display slots to a tablist, use the FakePlayers class.

#### If you want a "empty" slot
1. Use FakePlayer#randomFakePlayer() to create an instance of a FakePlayer
2. Add fake player to player's tablist with `FakePlayer#getTablistAddPacket().sendPacketOnce(Player)`
3. And to remove it use `FakePlayer#getTablistRemovePacket().sendPacketOnce(Player)`
#### If you want a slot with custom display name
1. Use FakePlayer(name, displayname) to create an instance of a FakePlayer
2. Add fake player to player's tablist with `FakePlayer#getTablistAddPacket().sendPacketOnce(Player)`
3. And to remove it use `FakePlayer#getTablistRemovePacket().sendPacketOnce(Player)`
