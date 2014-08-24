# Additional Buildcraft Objects

   ![Additional Buildcraft Objects](http://ma.ra-doersch.de/abo-logo.png "Additional Buildcraft Objects")
   
   **The majority of this readme is outdated.**

   Minecraft Forum: http://www.minecraftforum.net/index.php?app=forums&module=forums&section=findpost&pid=8920844

   Download Links:   http://www.siedler25.org/uploads/minecraft/AdditionalBuildcraftObjects/

   Please report any bugs to GitHUB: https://github.com/Flow86/Additional-Buildcraft-Objects-Mod



## License

   **AdditionalBuildcraftObjects is open-source.**

   It is distributed under the terms of my Open Source License.

   It grants rights to read, modify, compile or run the code.

   It does **NOT** grant the right to redistribute this software or its
   modifications in any form, binary or source, except if expressively
   granted by the copyright holder.

   * **Mod Packs:**
      You can include ( **precompiled** ) ABO-packages in your modpacks as long you're not making money out of it ( **so no adfly or something like that!** ).

      **AND: you have to send me a pm in which you post a link to your modpack.**
      <br />(If you want to use it in a private modpack, only pm me that you'll use it)

   **If you want to distribute a custom/modified abo build, you have to ask for permission first!**


## Items

#### Gate Settings Duplicator
   With the Gate Settings Duplicator you can simply copy and paste Gate Settings.
   <br />
   Right-Click on gate will copy the settings.
   <br />
   Sneak+Right-Click will paste it.
   
   * Recipe:
   
      ![Wrench AutarchicGate](recipes/GateSettingsDuplicator.png)


## Pipes



#### Round Robin Transport Pipe
   will change output per item(stack)

   * Recipe:

      ![StoneTransportPipe Gravel](recipes/RoundRobinTransportPipe.png)


#### Insertion Transport Pipe
   Insertion Pipes given the choice between putting a item in the pipe in to an
   inventory (A chest or a furnace) and passing it along to another pipe, the
   Insertion Pipe will always try to add the item to the inventory.

   This pipe is very useful for feedback loops as it forces the item to go in
   to the last machine if it can.

   * Recipe:

      ![IronTransportPipe CactusGreen](recipes/InsertionTransportPipe.png)



#### Extraction Transport Pipe
   Extraction Pipe is the opposite of the Insertion Pipe. If the pipe can choose
   between an inventory and another pipe the pipe will always go with the next
   pipe. Also this pipe behaves the same as a wood pipe where it will pull items
   out of an inventory if the pipe has an active redstone engine applied to it.

   This pipe is useful for tight spaces where you do not want a pipe to
   accidentally fill a chest or machine.

   * Recipe:

      ![WoodTransportPipe Planks](recipes/ExtractionTransportPipe.png)



#### Bounce Transport Pipe
   This pipe will cause whatever enters the pipe to come back out the way it came
   unless the pipe is powered by redstone, then it behaves as a normal pipe.

   * Recipe:

      ![StoneTransportPipe Cobblestone](recipes/BounceTransportPipe.png)



#### Crossover Transport Pipe
   This pipe will direct material entering it to the pipe
   (or something accepting material, like chests) directly across from it.
   If no such pipe exists, a random direction is chosen.

   * Recipe:

      ![StoneTransportPipe IronTransportPipe](recipes/CrossoverTransportPipe.png)


#### Compactor Transport Pipe
   Combines items(tacks) to larger itemstacks (currently to itemstacks >= 16 items)
   <br>
   can be toggled/switched by ABO-Actions and Redstone

   * Recipe:

      ![StoneTransportPipe Piston](recipes/CompactorTransportPipe.png)



#### Valve Pipe
   acts like a (golden) wooden pipe (without need of wooden engine)
   <br>
   can be toggled/switched by ABO-Actions and Redstone
   
   **can empty a full tank with enough (=full) pressure for two golden pipes in seconds!**

   * Recipe:

      ![WoodenWaterproofPipe AutarchicGate](recipes/ValvePipe.png)



#### Golden Iron Waterproof Pipe
   acts like a iron pipe with speed of a golden pipe

   * Recipe:

      ![GoldenWaterproofPipe IronWaterproofPipe](recipes/GoldenIronWaterproofPipe.png)



#### Balancing Waterproof Pipe
   balances liquid of connected tanks

   Thanks to Oxygene13 for his idea :-)

   * Recipe:

      ![WoodenWaterproofPipe IronORGate WoodenWaterproofPipe](recipes/BalancingWaterproofPipe.png)



#### Diamond Waterproof Pipe
   acts like a diamond pipe but for liquids.
   every filter item not being a bucket or liquid gets ignored.

   * Recipe:

      ![DiamondTransportPipe PipeWaterproof](recipes/DiamondWaterproofPipe.png)

   * GUI:

      - Left Click: Set next liquid on clicked slot
      - Right Click: Set previous liquid on clicked slot
      - Middle Click: Delete filter on clicked slot


#### Power Switch Pipe
   acts as lever for power pipes
   <br>
   can be toggled/switched by ABO-Actions and Redstone

   * Recipe:

      ![GoldenKinesisPipe Lever](recipes/PowerSwitchPipe.png)

#### Directed Kinesis Pipe
   acts as a diode/iron pipe for kinesis pipes

   * Recipe:

      ![GoldenKinesisPipe IronANDGate](recipes/DirectedKinesisPipe.png)

#### Distribution Kinesis Pipe
   acts as a power distributor - every filter line which has an item in it is 
   "open" to receive power

   * Recipe:

      ![DiamondTransportPipe DirectedKinesisPipe](recipes/DistributionKinesisPipe.png)

   * GUI:

      You can toggle each side on or off via GUI.
	  
	  ![Diamond Kinesis Pipe GUI](recipes/DistributionKinesisPipeGUI.png)

#### Water Pump Pipe
   soaks water from a source block below it
   
   * (Shaped) Recipe:

      ![X GoldenWaterproofPipe X<br>
      RedstoneDust IronGear RedstoneDust<br>
      X WoodenWaterproofPipe X<br>](recipes/WaterPumpPipe.png)
      
   Original by [AdditionalPipes](https://github.com/DaStormBringer/AdditionalPipesBC2)
   
   It will only show up if AdditionalPipes is not installed and tries to use the same Id.
   
   Since both pipe do the same (I even have copied the Recipe), you can load old maps 
   where you only used the Water Pump Pipe.

## Triggers
   

#### Safe-Engine Trigger
   acts like the blue & green state trigger, so it will be active as long the
   engines are "blue" or "green"

   i.e power them up via an AND gate and an external wire signal, but have an
   "emergency stop"
   

## Actions

#### Switch On Pipe
   activates the pipe as long as the action is active
   (On/Off via Signal)
   <br>
   a pipe toggled "on" will be set to off if switch-on is deactivated 
   
#### Toggle On Pipe
   activates the pipe and keeps it activated until a ToggleOff/SwitchOn occurs
   (On via Signal)
   
#### Toggle Off Pipe
   deactivates the pipe
   (Off via Signal)

---------------------------------------------------------------------------------

## Changelog:

* 1.0.7@559
  - fixed recipe conflict (Iron Kinesis Pipe)
    renamed it to Directed Kinesis Pipe
  - renamed all Conductive Pipes to Kinesis Pipes in readme

* 1.0.6
  - fixed recipe conflict (Insertion Pipe)
    new recipe for Insertion Pipe: Cactus Green + Iron Transport Pipe

* 1.0.5
  - renamed Diamond Conductive Pipe to Distribution Conductive Pipe due to the fact
    that BC added "Diamond Conductive Pipe"
  - buildcraft compatibility

* 1.0.4
  - readded compactor pipe and made it special item aware 
    (simply saving/comparing the complete ItemStack)

* 1.0.3
   - added custom gui for diamond waterproof pipe
   - added custom gui for diamond conductive pipe
   - added water pump pipe like the one from AdditionalPipes
   - renamed "Power" Pipes to "Conductive", unless Power Switch Pipe

* 1.0.2
   - ported to 1.5.1
   - added additional actions
   - added correct textures for actions
   - valve pipe and powerswitch pipe react on those actions.

* 1.0.1@99
   - added gate settings duplicator

* 1.0.1
   - added diamond power pipe
   - fixed iron power pipe logic,
     failed to switch "output" to other power distributors like RedStoneCell

* 1.0.0
   - added iron power pipe

* 0.9.9@83
   - fixed diamond liqudis pipe loosing its filter on load/unload
   

* 0.9.9@81
   - fixed valve to stop output immediately

* 0.9.9
   - updated/readded diamond liquids pipe

* 0.9.8:
   - bc 3.4.2 compatible


* 0.9.7:
   - bc 3.4.0 / mc 1.4.7 compatible


* 0.9.6:
   - fixed various bugs (balance pipe and redstone engine ...)
   - updated/readded valve pipe


* 0.9.5:
   - bc 3.3.0 / mc 1.4.6 compatible
   

* 0.9.4:
   - updated/readded balance pipe


* 0.9.4@56:
   - updated/readded power switch pipe
   - updated/readded switch on pipe action
   - fixed bounce pipe


* 0.9.3:
   - mc 1.4.2 compatible


* 0.9.2:
   - bc 3.2.0pre compatible


* 0.9.1:
   - mc 1.3.2 compatible
   - bc 3.1.8 compatible
   - not everything is implemented again for 1.3.2


* 0.9:
   - removed engine control pipe (unnecessary now, buildcraft has a trigger api now)
   - mc 1.1 compatible
   - bc 3.1.2/2.2.12 compatible


* 0.8.2:
   - added diamond liquid pipe


* 0.8.1:
   - added engine control pipe


* 0.8:
   - added pipes from old "ExtraBuildcraftPipes"-Mod by leftler/blakmajik
      <br />\- Insertion Transport Pipe
      <br />\- Extraction Transport Pipe
      <br />\- Bounce Transport Pipe
      <br />\- Crossover Transport Pipe

      see: http://www.minecraftforum.net/topic/474348-173-2012-extrabuildcraftpipes/

      oh and YES - they gave me the permission (before someones shouting about copyright...)


* 0.7:
   - bc 3.0.4 compat
   - removed obsolete Redstone Power Converter
   - removed "Invalid Pipe" (buildcraft now reacts correctly on unknown pipe ids)


* 0.6f:
   - fix missing class file (damn packaging script ...)


* 0.6e:
   - fix valve crash 2 (sorry again!!)


* 0.6d:
   - fix valve crash (sorry!!)


* 0.6c:
   - bc 3.0.3 compat
   - mc 1.0.0 compat


* 0.6b:
   - bc 3.0.2 compat
   - fixed power switch pipe


* 0.6:
   - bc 3.0.1 compat
   - removed flowmeter (obsolete)


* 0.5b:
   - fixed bc > 2.2.2 compat


* 0.5:
   - added Redstone Power Converter
   - added Compactor Pipe


* 0.4:
   - added Balance Pipe


* 0.3:
   - added Flow Meter Liquids Pipe
   - changed recipes


* 0.2:
   - fixed Valve Liquids Pipe
   - added Power Switch Pipe
   - added Golden Iron   Liquids Pipe


* 0.1:
   - added Round Robin Transport Pipe
   - added Valve Liquids Pipe

---------------------------------------------------------------------------------

Recipe Images generated by [Advanced Recipe Generator](https://github.com/Flow86/Advanced-Recipe-Generator-Mod)
