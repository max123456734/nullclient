# Null FPS Boost 26.2

A lightweight Fabric mod project for Minecraft Java 26.2 using the current split main/client source-set layout.

## Current build

This version uses the official 26.2 Fabric example-mod structure: Loom split environment source sets, Java 25, Fabric Loader 0.20.1, and Fabric API 0.161.0+26.2. It has separate common and client entrypoints and avoids referencing outdated Minecraft implementation class names.

## FPS tips

For immediate client-side FPS improvements, try:
- Render distance: 6–8 chunks
- Simulation distance: 4–5 chunks
- Graphics: Fast
- Particles: Minimal
- Entity distance: lower
- Clouds: Off
- VSync: Off if it causes input lag
- Close heavy background applications and avoid high-resolution shaders on a low-end GPU

The current starter code initializes safely and logs these recommendations. It does not yet dynamically change video settings or inject rendering optimizations.

## Network latency

No client mod can guarantee true 0 ms ping. Latency depends on physical distance, routing, Wi-Fi/Ethernet quality, congestion, and server responsiveness. Client graphics changes can reduce local stutter but cannot fix server TPS lag.

## Requirements

- Minecraft Java 26.2
- Fabric Loader 0.20.1 or newer
- Fabric API 0.161.0+26.2
- Java 25
