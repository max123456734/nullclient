# Null Client

A Fabric client-side mod for **Minecraft Java 1.21.11**. The distributable Null Client JAR bundles the official Meteor Client build for Minecraft 1.21.11 (archive build 86) as a Fabric nested mod.

## Install — one JAR only

1. Use Minecraft Java 1.21.11 with Fabric Loader and Fabric API installed.
2. Download the **Null Client** JAR from the GitHub Actions artifact.
3. Put only that JAR in your `.minecraft/mods` folder.
4. Launch the Fabric 1.21.11 profile.

You do **not** need to download or install a separate Meteor Client JAR. Fabric loads the bundled Meteor mod from inside Null Client.

## Included code and credits

- Null Client branding, project integration, and custom features: Null Client contributors.
- Bundled upstream client: **Meteor Client**, copyright Meteor Development and contributors.
- Official Meteor source: https://github.com/MeteorDevelopment/meteor-client
- Official Meteor 1.21.11 archive: https://www.meteorclient.com/archive (build 86).

Meteor is bundled unmodified as a nested Fabric mod. Its modules, GUI, and keybinds are provided by that bundled Meteor build. Null Client's own screen and keybinds remain separate. This project is not affiliated with or endorsed by Meteor Development.

## In-game controls

- Press **F8** to open the Null Client page.
- The Local No-Clip, Anti-Lag, Toggle Sprint, and Toggle Sneak rows are clickable and toggle their implemented Null Client features.
- Press **Right Shift** to open the bundled Meteor Client Click GUI. Meteor's own category tabs, module toggles, settings, and keybind editing are handled by Meteor itself.
- Change Null Client's keybinds in **Options → Controls → Key Binds → Null Client**. Configure Meteor module keybinds in Meteor's Click GUI.

## Build

Requirements: JDK 21 and internet access.

```bash
gradle build
```

The build downloads the official Meteor 1.21.11 artifact, checks its Fabric metadata, and embeds it at `META-INF/jars/meteor-client.jar`. The resulting Null Client JAR is the only mod file users need to add.

## License

This combined distribution is provided under **GNU GPL v3**. See [LICENSE](LICENSE). Meteor Client's corresponding source is available from the official repository linked above.
