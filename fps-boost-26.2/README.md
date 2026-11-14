# Null FPS Boost 26.2

A standalone Fabric client-side performance preset for Minecraft Java 26.2.

## What it changes

On client startup it caps render distance at 8 chunks, simulation distance at 5 chunks, and entity distance scaling at 0.75 when the current values are higher. You can change these values again in Video Settings after launch.

This is a small, conservative starter optimization. It does not promise a specific FPS increase; results depend on your CPU, GPU, memory, shaders, resource packs, world, and server.

## Network latency and server lag

This client-only build does not claim to reduce a server's tick time or guarantee lower ping. Ping is round-trip network latency and depends on physical distance, routing, Wi-Fi/Ethernet quality, congestion, and server responsiveness. No client mod can make a distant server produce true 0 ms latency.

The mod will not fake latency readings, suppress required packets, or change protocol behavior to make a ping display look lower. Client-side graphics settings can make gameplay feel smoother when the client is overloaded, but they cannot repair server TPS lag.

For a server you administer, server-side profiling and configuration are needed to address tick lag (for example, identifying overloaded entities, chunks, plugins/mods, or storage). A server-side companion would need to be installed on that server and tested against its exact software and version; it is not included in this client-only JAR.

For a more stable connection, use wired Ethernet where possible, stop large downloads/uploads, select a server region geographically close to you, and avoid unstable VPN routes.

## Requirements

- Minecraft Java 26.2
- Fabric Loader 0.19.3 or newer
- Fabric API 0.161.0+26.2
- Java 25

## Build

From this directory, run `gradle build` with Gradle 9.5.1 and Java 25. The JAR is created in `build/libs/`.

