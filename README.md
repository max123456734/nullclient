# Null Client

A Fabric client-side utility mod for **Minecraft Java 1.21.11**.

## Features

- **Freecam movement (F6):** local no-clip movement. This first implementation is not a detached camera and may be corrected by multiplayer servers.
- **Auto Mine (F7):** holds the attack input while enabled and no GUI is open. It mines the block under the crosshair; normal tool and server rules still apply.
- **Custom HUD (F8):** shows the state of enabled features.
- **Anti-Lag profile (F9):** switches the particle setting between Minimal and All.

## Keybinds

Open **Options → Controls → Key Binds → Null Client** to change the defaults.

| Key | Action |
|---|---|
| F6 | Toggle freecam movement |
| F7 | Toggle auto mine |
| F8 | Toggle HUD |
| F9 | Toggle anti-lag profile |

## Build

Requirements: JDK 21 and internet access for Gradle dependency downloads.

```bash
./gradlew build
```

The distributable mod jar is written to `build/libs/nullclient-1.0.0.jar`.

On Windows use `gradlew.bat build`.

## Install

1. Install Fabric Loader for Minecraft 1.21.11.
2. Install the matching Fabric API.
3. Copy the built Null Client jar into the Minecraft `mods` folder.
4. Launch the Fabric profile.

## Important limitations

This is an early client-side build. Freecam currently means local no-clip movement, not a separate camera entity. Auto Mine only automates the local attack input and does not bypass server protections. The Auto Relog feature and a graphical settings screen are not implemented yet. The anti-lag profile changes the particle setting only.

Use only where client-side mods and automation are permitted. Back up worlds before testing.

## License

MIT. See [LICENSE](LICENSE).
