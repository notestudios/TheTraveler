# The Traveler
  **The Traveler** Game on GitHub! <br>
  <a href="https://gamejolt.com/games/ttraveler/796130">GameJolt Page</a>
  > Release: **4.5.1** <br>

<a href="https://gamejolt.com/games/ttraveler/796130">
<img src="https://raw.githubusercontent.com/notestudios/TheTraveler/main/src/main/resources/images/BannerGitHub.png" alt="The Traveler Github Banner">
</a>
<br>

  This is **The Traveler** source code on GitHub! You can mod the game, help optimizing, create new game levels and make any changes you want to it!

## Compiling from source
This section will guide you how to build the game and run it with from the source code.

### Installing dependencies
The Traveler needs some dependencies to be installed to make the game run. Such as:
 - [Java](https://java.com) or [OpenJDK](https://jdk.java.net/21/)
 - [Maven](https://maven.apache.org/download.cgi)
 - [Git](https://git-scm.com/downloads) (optional)

### Cloning Repository
You can clone this repo [Installing Git](#Installing-dependencies) (recommended) or just downloading the source here, on GitHub:

   - Using Git CLI: open a new terminal in the desired folder and paste:
     ```
     git clone https://github.com/retrozinndev/TheTraveler.git
     ```
     It will start cloning the repo in your current working directory.

  - Using GitHub:
    
    1. Select the desired branch in the left corner(stable: master; development: beta);
    3. Click in the green **<>Code** dropdown in the right corner of the project files:
    4. In the opened menu: click on the **Download ZIP** button:

       ![Download ZIP button](https://github.com/retrozinndev/TheTraveler/assets/65513943/e5db811b-b6cf-4b91-8af0-5b2875d75349)

    5. Open your system's **Downloads** folder and extract the downloaded ZIP file ([7zip](https://www.7-zip.org/download.html), [RAR](https://www.win-rar.com/))
    
### Compiling
This repository uses [Maven](https://maven.apache.org/) for building and running, you can [Install the Maven Tool](#Installing-dependencies) to compile it.
  - Open a new terminal in the project directory and run:
    ```
    mvn package
    ```

### Running
To run the package build you made previously:
  - In the same terminal, run the following command:
    ```
    java -jar target/TheTraveler-4.1.0.jar
    ```
  After that, the game will start, if it does not start, [open a new issue here](https://github.com/retrozinndev/TheTraveler/issues/new), copy and paste the output of the used command in the issue content and I will help you! Have fun!

## Required Libraries
  In this branch, libraries will be available inside the `pom.xml` file. Maven will automatically download and compile them, so don't worry!
 
## Contributing
  #### License
  This repository is using the [GNU General Public License v3](https://www.gnu.org/licenses/gpl-3.0.en.html#license-text), so you 
  can help getting the game better for everyone! See the [License Permissions](https://choosealicense.com/licenses/gpl-3.0/) for more information. **The code can be modified at any time**!

  This game has some bugs and other things to do, so if you can, please help me getting the game better. 
I'll be happy for your help!
