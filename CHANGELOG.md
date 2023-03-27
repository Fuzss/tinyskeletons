# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog].

## [v5.0.0-1.19.3] - 2023-03-27
- Ported to Minecraft 1.19.3
### Changed
- Baby skeletons now use their own texture files, which are dynamically copied at runtime from the vanilla originals, so they are still compatible with resource packs changing the normal adult textures
- This also means they can be overridden using a resource pack separately from the adult textures
- Texture location: `tinyskeletons:textures/entity/skeleton/baby_skeleton.png`, `tinyskeletons:textures/entity/skeleton/baby_wither_skeleton.png`, `tinyskeletons:textures/entity/skeleton/baby_stray.png`
- This also fixes compatibility with resource packs the change the adult skeleton texture aspect ratio such as [Better Skeletons](https://www.curseforge.com/minecraft/texture-packs/better-skeletons), the baby skeleton textures will automatically fall back to vanilla

[Keep a Changelog]: https://keepachangelog.com/en/1.0.0/
