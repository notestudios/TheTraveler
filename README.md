# The Traveler 
[Play on Game Jolt](https://gamejolt.com/games/ttraveler/796130)
> Stable: **4.5.1**<br>
> Devel: **unknown**

<a href="https://gamejolt.com/games/ttraveler/796130">
<img src="https://raw.githubusercontent.com/notestudios/TheTraveler/main/src/main/resources/images/BannerGitHub.png" alt="The Traveler Banner">
</a>
<br>

This is **The Traveler** source code on GitHub! You can mod the game, [help optimizing](#Contributing), create [new game levels](#Contributing) and make **any** changes you want to it!
Please, read the [License](#License) section for more information of what you can do.

## ℹ️ About
 The Traveler GitHub Repository has been moved to the **Note Studios** organization. Altough the project is still maintained by me, 
It has been moved to make the Note Studios in-game logo and face make more sense, so I created this organization and moved the repo here.
Even so, you can still use the repo in my account. It has just the `main`(stable) branch, meaning it's stable commits. I am just a single person, 
so feel free to [contribute to the project](#Contributing) if you want!

## 🎮 How can I play?
You can play The Traveler right away on [Game Jolt](https://gamejolt.com/games/ttraveler/796130) or you can [build your own](#Playing-from-source) with the source! 

## 🧰 Playing from source
This section will guide you on how to build the game and run it with from the source code.

### 📦 Installing dependencies
The Traveler needs some dependencies to be installed to make the game run. Such as:
 - [Java](https://java.com) or [OpenJDK](https://jdk.java.net/21/)
 - [Gradle](https://gradle.org/install)
 - [Git](https://git-scm.com/downloads) (optional)
#### Project's Required Libraries
  See under the [Libs folder](https://github.com/retrozinndev/TheTraveler/tree/master/libs#third-party-libraries) for more information.

### 📥 Cloning Repository
You can clone this repo [Installing Git](#Installing-dependencies) (recommended) or just downloading the source here, on GitHub:

   - Using Git CLI: open a new terminal in the desired folder and paste:
     ```
     git clone https://github.com/retrozinndev/TheTraveler.git
     ```
     It will start cloning the repo in your current working directory.

  - Using GitHub:
    
    1. Select the desired branch in the left corner(stable: main; testing: development);
    3. Click in the green **<> Code** dropdown in the right corner of the project files:
    4. In the opened menu: click on the **Download ZIP** button:

       ![Download ZIP button](https://github.com/retrozinndev/TheTraveler/assets/65513943/e5db811b-b6cf-4b91-8af0-5b2875d75349)

    5. Open your system's **Downloads** folder and extract the downloaded ZIP file ([7zip](https://www.7-zip.org/download.html), [RAR](https://www.win-rar.com/))
    
### 🔨 Compiling
This repository uses [Gradle](https://gradle.org/) for building and running, you can [Install Gradle Tool](https://gradle.org/install/) or use the project one.

  - Windows: Open a new Windows Terminal, Powershell or CMD window on the project path and use the following comand:
    ```
    .\gradlew.bat build
    ```
  - Linux/macOS: Open a new terminal in the project folder and run:
    ```
    ./gradlew build
    ```

### 🎉 Running
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
 
## ❤️ Contributing
### I want to Contribute!
You're free to contribute with translations, game experience, interface, design changes and many more! If you just want to suggest a change, or suggest a feature, you can create a [New Issue](https://github.com/retrozinndev/TheTraveler/issues/new) or a [Start a Discussion](https://github.com/notestudios/TheTraveler/discussions/new/choose) and say what you want to be in the game!

### 📜 License
This repository is using the [GNU General Public License v3](https://www.gnu.org/licenses/gpl-3.0.en.html#license-text), so you 
can help getting the game better for everyone! See the [License Permissions](https://choosealicense.com/licenses/gpl-3.0/) for more information. **The code can be modified at any time**!

  This game has some bugs and other things to do, so if you can, please help me getting the game better. 
I'll be happy for your help!
