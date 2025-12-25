## Warhammer Damage Evaluator — Copilot instructions

Purpose: short, actionable guidance so an AI coding agent can quickly make safe, focused changes in this repository.

- Project type: small Java app (no build system present). Source is under `src/` following package `com.warhammer`.
- Entry point: `src/com/warhammer/Main.java` (constructs unit instances and calls `attackTarget`).

Key concepts & files
- `src/com/warhammer/model/Model.java`: core of the simulation. Implements hit/wound/save/resistance math and exposes `attackTarget(Model)` which prints average damage for each `Profile`.
- `src/com/warhammer/attacks/Profile.java`: immutable value object describing an attack profile: name, attack count, skill, strength, AP, damage, and a list of `AttackProfileModificationEnum` modifiers.
- `src/com/warhammer/modifiers/AttackProfileModificationEnum.java`: enumerates per-profile effects (LETHAL_HITS, SUSTAINED_HITS, DEVASTATING_WOUNDS, RAPID_FIRE_1/2).
- Unit implementations: `src/com/warhammer/units/*/*` (e.g. `RobouteGuilliman.java`, `Tyrannofex.java`, `Norn.java`). Units extend `Model` and provide static `getAttackModifications()` factories that return `List<Profile>`.

Data files
- `data/*.csv` (pipe-separated) are a data dump of official datasheets (models, abilities, costs). Important: the current code base does not parse these CSVs — models are constructed in code. Treat the CSVs as reference data unless you add a CSV parser.

Project-specific patterns & conventions
- Constructor order in `Model` is important: (toughness, save, wounds, name, attackReroll, woundReroll, plusHit, plusWound, invulSave, feelNoPain, profiles). New units should follow this exact order.
- Attack profiles are composed in static helper methods named like `getAttackModifications()` and return `List.of(...)` of `Profile` instances. Example: `new Profile("Hand of Dominion ranged", List.of(AttackProfileModificationEnum.RAPID_FIRE_2), 2,2,6,2,2)` in `RobouteGuilliman`.
- Rule changes to the game logic belong in `Model.java` (methods to inspect: `getIncomingDamage`, `calculateHits`, `calculateWounds`, `getWoundThreshold`, `resistance`). Avoid duplicating logic in unit classes.
- `modificationlist` field on `Model` exists but is currently unused by math — if you add per-model global modifiers, wire them into `getIncomingDamage` explicitly.

Build / run / debug (recommended)
- No Maven/Gradle detected. Use the JDK directly. From the repository root (PowerShell):

```powershell
javac -d out -sourcepath src (Get-ChildItem -Recurse -Filter "*.java" -Path src | ForEach-Object FullName)
java -cp out com.warhammer.Main
```

Notes:
- The project compiles to `out/` by convention in this workspace. IDEs (IntelliJ) can open the `warhammer.iml` but CI/build files are not present.
- Keep changes minimal and localized: editing `Model.java` affects all units. When changing signatures, update every `new` call in `src/com/warhammer/units`.

Examples of safe edits for an AI agent
- To add a new attack modifier enum: update `AttackProfileModificationEnum`, then update `Model.containsAttackProfile` usage sites (search for `.contains(AttackProfileModificationEnum`).
- To add a new unit: add a class under `src/com/warhammer/units/<faction>/` that extends `Model` and implements a static `getAttackModifications()`.
- To drive the code from CSV data: add a lightweight CSV parser (pipe '|' delimiter) and a factory that maps `datasheet_id` → `Model` constructor arguments. Document it in a new package like `com.warhammer.importer`.

Where to look first when debugging
- Run `Main.java` to reproduce output. Look at `Model.attackTarget()` — it loops profiles and calls `getIncomingDamage()` which contains the whole damage pipeline.

Questions to ask the human
- Do you want CSV ingestion (map `data/Datasheets_models.csv` → code) or keep unit definitions in Java source?
- Any preferred build system (Maven/Gradle) to add so CI can run builds/tests?

If unclear, open these files first: `src/com/warhammer/model/Model.java`, `src/com/warhammer/attacks/Profile.java`, `src/com/warhammer/units/ultraMarines/RobouteGuilliman.java`, and `readme.md`.
