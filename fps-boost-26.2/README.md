# Null FPS Boost 26.2

A standalone Fabric client-side performance preset for Minecraft Java 26.2.

## What it changes

On client startup it caps render distance at 8 chunks, simulation distance at 5 chunks, and entity distance scaling at 0.75 when the current values are higher. You can change these values again in Video Settings after launch.

This is a small, conservative starter optimization. It does not promise a specific FPS increase; results depend on your CPU, GPU, memory, shaders, resource packs, and world/server.

## Ping / latency

No Minecraft mod can guarantee 0 ms ping. Ping is the round-trip time between your device and the server and is affected by distance, routing, Wi-Fi/Ethernet, congestion, and server response time. This mod does not fake ping or modify packets.

## Requirements

- Minecraft Java 26.2
- Fabric Loader 0.19.3 or newer
- Fabric API 0.161.0+26.2
- Java 25

## Build

From this directory, run `gradle build` with Gradle 9.5.1 and Java 25. The JAR is created in `build/libs/`.
