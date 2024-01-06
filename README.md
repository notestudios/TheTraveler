# The Traveler
  **The Traveler** Game on GitHub! <br>
  <a href="https://gamejolt.com/games/ttraveler/796130">GameJolt Page</a>
  > Release: **4.5.1** <br>
<center>
<a href="https://gamejolt.com/games/ttraveler/796130">
<img src="https://github.com/notestudios/TheTraveler/blob/master/assets/images/BannerGitHub.png?raw=true" alt="The Traveler Github Banner"></a>
</center>
<br>

  This is **The Traveler** source code on GitHub! You can mod the game, help optimizing, create new game levels and make any changes you want to it!

## Compiling from source
This will guide you how to compile the game and run it with from the source code.

### Cloning Repository
You can clone this repo [Installing Git](https://git-scm.com/downloads) (recommended) or just downloading the source here, on GitHub:

   - Using Git CLI: open a new terminal in the desired folder and paste:
     ```
     git clone https://github.com/retrozinndev/TheTraveler.git
     ```
     It will start cloning the repo in your current working directory.

  - Using GitHub:
    
    1. Select the desired branch in the left corner(stable: master; development: beta):
       
       ![Branch list dropdown](https://github.com/retrozinndev/TheTraveler/assets/65513943/cb173d4f-7828-4517-ba0c-009a578e115b)
    
    3. Click in the green **<>Code** dropdown in the right corner of the project files:

       ![<>Code dropdown](https://github.com/retrozinndev/TheTraveler/assets/65513943/b2e3e50a-1b38-4706-a391-b9dda7d5e898)

    4. In the opened menu: click on the **Download ZIP** button:

       ![Download ZIP button](https://github.com/retrozinndev/TheTraveler/assets/65513943/e5db811b-b6cf-4b91-8af0-5b2875d75349)

    5. Open your system's **Downloads** folder and extract the downloaded ZIP file ([RAR](https://www.win-rar.com/download.html), [7zip](https://www.7-zip.org/download.html))
    
### Compiling
This repository uses [Gradle](https://gradle.org/) for building and running, you can [Install Gradle Tool](https://gradle.org/install/) or use the project one.

  - Windows: Open a new Windows Terminal, Powershell or CMD window on the project path and use the following comand:
    ```
    .\gradlew.bat build
    ```
  - Linux/macOS: Open a new terminal in the project folder and run:
    ```
    ./gradlew build
    ```

### Running
  To run the project build you made previously, in the same terminal window, run the following command:
  - Windows: In the project folder, use the following comand:
    ```
    .\gradlew.bat run
    ```
  - Linux/macOS: In the project folder, run:
    ```
    ./gradlew run
    ```
  After that, the game will start, if it does not start, [open a new issue here](https://github.com/retrozinndev/TheTraveler/issues/new), copy and paste the output of the used command in the issue content and I will help you! Have fun!

## Required Libraries
  See under the [Libs folder]() for more information.
 
## Contributing
  #### License
  This repository is using the [GNU General Public License v3](https://www.gnu.org/licenses/gpl-3.0.en.html#license-text), so you 
  can help getting the game better for everyone! See the [License Permissions](https://choosealicense.com/licenses/gpl-3.0/) for more information. **The code can be modified at any time**!

  This game has some bugs and other things to do, so if you can, please help me getting the game better. 
I'll be happy for your help!
