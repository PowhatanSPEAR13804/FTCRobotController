## Creating your own OpModes
The easiest way to create your own OpMode is to copy a Sample OpMode and make it your own.
Sample opmodes exist in the FtcRobotController module.

### Naming of Samples
To gain a better understanding of how the samples are organized, and how to interpret the
naming system, it will help to understand the conventions that were used during their creation.

These conventions are described (in detail) in the sample_conventions.md file in this folder.

To summarize: A range of different samples classes will reside in the java/external/samples.
The class names will follow a naming convention which indicates the purpose of each class.
The prefix of the name will be one of the following:

Basic is a barebones example

Sensor is a Sample OpMode that shows how to use a specific sensor.
  Not intended to drive a functioning robot.

Robot is a Sample OpMode that assumes a simple two-motor (differential) drive base.
  May be used to provide a common baseline driving OpMode.

Concept is a sample OpMode that illustrates performing a specific function or concept.

After the prefix, other conventions will apply:
* Sensor class names:  Sensor - Company - Type
* Robot class names are constructed as:  Robot - Mode - Action - OpModetype
* Concept class names are constructed as:  Concept - Topic - OpModetype

To create a sample as a base file, you must:

 1) Locate the desired sample class in the Project/Android tree.

 2) Right click on the sample class and select "Copy"

 3) Expand the  TeamCode/java folder

 4) Right click on the org.firstinspires.ftc.teamcode folder and select "Paste"

 5) You will be prompted for a class name for the copy.
    Choose something meaningful based on the purpose of this class.
    Start with a capital letter, and remember that there may be more similar classes later.

Once your copy has been created, you should prepare it for use on your robot.
This is done by adjusting the OpMode's name, and enabling it to be displayed on the
Driver Station's OpMode list.

Each OpMode sample class begins with several lines of code like the ones shown below:

```
 @TeleOp(name="Template: Linear OpMode", group="Linear Opmode")
 @Disabled
```

The name that will appear on the driver station's "opmode list" is defined by the code:
 ``name="Template: Linear OpMode"``
You can change what appears between the quotes to better describe your opmode.
The "group=" portion of the code can be used to help organize your list of OpModes.

As shown, the current OpMode will NOT appear on the driver station's OpMode list because of the
  ``@Disabled`` annotation which has been included.
This line can simply be deleted , or commented out, to make the OpMode visible.



## ADVANCED Multi-Team App management:  Cloning the TeamCode Module
In the case that you have multiple teams and want to have seperate code bases for each team,
you may clone the TeamCode module for each team to have. These will appear in the Android Studio
module list alongside the FtcRobotController module. What this allows is other teams to see
eachothers code but not edit it.

Selective Team phones can then be programmed by selecting the desired Module from the pulldown list
prior to clicking to the green Run arrow.

THIS IS NOT FOR INEXPERIENCED DEVELOPERS
These changes are performed OUTSIDE of Android Studio
It is suggested to make a full project backup before you start this :)

To clone TeamCode, do the following:
Note: Some names start with "Team" and others start with "team".  This is intentional.

1)  Using your operating system file management tools, copy the whole "TeamCode"
    folder to a sibling folder with a corresponding new name, eg: "Team0417".

2)  In the new Team0417 folder, delete the TeamCode.iml file.

3)  the new Team0417 folder, rename the "src/main/java/org/firstinspires/ftc/teamcode" folder
    to a matching name with a lowercase 'team' eg:  "team0417".

4)  In the new Team0417/src/main folder, edit the "AndroidManifest.xml" file, change the line that contains
         package="org.firstinspires.ftc.teamcode"
    to be
         package="org.firstinspires.ftc.team0417"

5)  Add:    include ':Team0417' to the "/settings.gradle" file.
    
6)  Open up Android Studios and clean out any old files by using the menu to "Build/Clean Project""

## Code formatting specific to THIS code base

# remove on full format
# Already formatted:
    ButtonClick
    RobotMove

# Naming:
	Folders/Files/Classes use pascal case
	Ex: SimpleName

    Functions/Variables use camel case
    Ex: simpleName


# General:
	Put a space after commas and arithmetic operators as well
	* Ex: str.substring(i, i + 1);
	* EXCEPTION: x++;

    	Seperate code SECTIONS with one line
    	Seperate code BLOCKS with three lines



# Comments:
	Only use comments if the code is not self explanatory. If you dont't know if the code is self explanatory, get another programmer to proof read if they are free.

	Please put comments in the code in the proper spaces
	Ex on what NOT to do: if(<code>) {//comment//
		<code>
	}

	No space after forward slashes and leave lower case
	Ex:   //checks to see if x = 2
	if(x == 2)

    	Also explain the basic input, processing, and output of the
    	function or statement(s) in the same comment. Go into more detail
    	if need-be in the function or statement(s).
    	Ex:   if(x == 2)
		//add one to x
                x++;

    * Multiline comments should look like this
    * /*
      this comment has multiple lines blahblahblahblahblah
      blahblahblahblahblahblahblahblahblahblahblahblahblah
       */

    * Space multiline comments from normal ones like this
    * //blah
      //blah2

      /*
      blahblahblahblahblah
      blahblahblahblahblah
       */

      //blah3

    * Add header comments to explain class purposes
    * Ex: (at top of this file)



# Variables:
    Have a short, mostly self-explanatory name
    Ex: int totalCost = 0;

    * Split up variables into commented groups that share
    similar purposes
    * Ex:   //declare motors
            DcMotor motorFrontLeft = hardwareMap.dcMotor.get("Hub1_Motor3");
            DcMotor motorBackLeft = hardwareMap.dcMotor.get("Hub1_Motor0");
            DcMotor motorFrontRight = hardwareMap.dcMotor.get("Hub2_Motor0");
            DcMotor motorBackRight = hardwareMap.dcMotor.get("Hub2_Motor3");

            //misc
            double sillyVariable = 0.0;
            int seriousVariable = 0;



# If statements:
    Parentheses have no spaces before them
    Ex: if()
    Ex: else if()

    * One line if statements should look like this
    * Ex: if() {<code here>}

    * else statements on same line as closing brackets with spaces
    * Ex: } else {
    * Same for else ifs

    * "One" line if else statements should look like this
    * if() {<code here>}
      else {}

    * Comparison operators need to have spaces between what's
    being compared
    * Ex: if(1 + 1 == 2)
    * Ex: if(1 + 1 == 2 && true)
    * Ex: if(1 + 1 == 2 || true)

    * If an if-else statement seems to get too large, use a
    switch case instead



# Complexity:
    Only nest control flow statements if necessary
    If there's a simpler solution, use it
    Always work as if you're making a finished product
    Don't leave it half done
    Make a class file to reduce repeated code in multiple files



# Github:
    Commit after you're done working on something or someone
        else needs to work with the latest changes
    Experimental changes should be split into separate branches
    Working on a new feature? Make a new branch!!!!!
