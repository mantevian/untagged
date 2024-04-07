# Installation
Requires **Fabric Loader 0.13+** and any modern version of **Fabric API**. Just put in mods, no configuration needed.

# Commands
This mod adds two commands that are useable by **any player**:

* `/nametag show` - show your name tag
* `/nametag hide` - hide your name tag

These commands can be /executed as any player by operators, console or using command blocks.

# Compatibility
This mod is implemented using vanilla teams, the only thing it really does is wrap `/team modify <team_name> nameTagVisibility never` in fancy no-op useable commands.
This being said, the mod will not be compatible with other datapacks and mods that use teams, and if you have any, you should just use the vanilla command for your team instead.
