This Mod is based on the game "Nameless cat"
=========================================


Source installation information for modders
-------------------------------------------
This code follows the Minecraft Forge installation methodology. It will apply
some small patches to the vanilla MCP source code, giving you and it access 
to some of the data and functions you need to build a successful mod.

Note also that the patches are built against "un-renamed" MCP source code (aka
SRG Names) - this means that you will not be able to read them directly against
normal code.

Setup Process:
==============================

Step 1: Open your command-line and browse to the folder where you extracted the zip file.

Step 2: You're left with a choice.
If you prefer to use Eclipse:
1. Run the following command: `gradlew genEclipseRuns` (`./gradlew genEclipseRuns` if you are on Mac/Linux)
2. Open Eclipse, Import > Existing Gradle Project > Select Folder 
   or run `gradlew eclipse` to generate the project.

If you prefer to use IntelliJ:
1. Open IDEA, and import project.
2. Select your build.gradle file and have it import.
3. Run the following command: `gradlew genIntellijRuns` (`./gradlew genIntellijRuns` if you are on Mac/Linux)
4. Refresh the Gradle Project in IDEA if required.

If at any point you are missing libraries in your IDE, or you've run into problems you can 
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything 
{this does not affect your code} and then start the process again.

Mapping Names:
=============================
By default, the MDK is configured to use the official mapping names from Mojang for methods and fields 
in the Minecraft codebase. These names are covered by a specific license. All modders should be aware of this
license, if you do not agree with it you can change your mapping names to other crowdsourced names in your 
build.gradle. For the latest license text, refer to the mapping file itself, or the reference copy here:
https://github.com/MinecraftForge/MCPConfig/blob/master/Mojang.md

Additional Resources: 
=========================
Community Documentation: https://mcforge.readthedocs.io/en/latest/gettingstarted/  
LexManos' Install Video: https://www.youtube.com/watch?v=8VEdtQLuLO0  
Forge Forum: https://forums.minecraftforge.net/  
Forge Discord: https://discord.gg/UvedJ9m  



This is the nameless cat from the game Nameless cat.
I saw this skin once on Pony Town and at some point i found where it came
from. i never played the game myself, but i like the idea.


Features
- Entities
	- Nameless cat
		- spawn egg
		- spawn in biomes: jungles, snowy slopes
		- immune to fire, sweet berry bush, wither rose and cactus
		- when a minecraft cat is killed this spawn and it won't turn into adult
		- when natural spawn it can grow into adult
		- can only breed when both are spawn natural
		- tempt ingredients: tropical fish, cooked cod, cooked salmon
		and minecraft default cat tempt ingredients
		- is bright in dark (does not emit natural light)
		- when health gets lower, it loses brightness
		- won't attack other ghost kitties, ghast, players, tamed horses, tamed animals
		- cat struck by lighting turn into ghost kitty

Planned features
- transparency / opacity of 5%
- spawn near witch huts
- spawn in abandoned villages

Known bugs