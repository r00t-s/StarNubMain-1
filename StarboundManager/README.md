#Starbound Manager
This library is use to manage the Starbound directory, files and process.

Authors: Underbalanced

Contributors:

NOTE: This code has been refined and is ready for public consumption

Requirements
============
- Java 8 (32 or 64 bit version)

Current Classes
========
- StarboundConfiguration - This classes methods will generate a new configuration, create a back up as well as modify the starbound configuration
- StarboundManagement - Manages the Starbound Process and States
- StarboundProcess - Contains the Starbound process that the JAVA JVM started as well as extends RUNNABLE to manage the starbound stream. It is printed or sent through an event system if packet_decoding is off
- StarboundQuery - Static method to query the starnubserver
- StarboundServerExe - This class extends OperatingSystem from the Utilities Library, it contains the OS type, OS Bit Version, Starbound_Server.exe location
- StarboundStatus - Abstract class for managing states also contains methods for querying the starnubdata.network
    - Running - Extends StarboundStatus
    - Starting - Extends StarboundStatus
    - Stopped - Extends StarboundStatus
    - Stopping - Extends StarboundStatus
    - Unresponsive - Extends StarboundStatus


Installation
============
- Coming soon maven "pom.xml"...
- Coming soon adding a library wiki...

References
============
-
