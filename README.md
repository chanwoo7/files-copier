# Files Copier (JetBrains Plugin)

[![GitHub release (latest by date)](https://img.shields.io/github/release/chanwoo7/files-copier)](https://github.com/chanwoo7/files-copier/releases)

**Files Copier** is a powerful JetBrains plugin that copies the contents of selected files and folders in clean, well-formatted Markdown blocks directly to your clipboard.  
Perfect for sharing code, preparing AI prompts, or documenting logic during reviews.

---

## ✨ Features

- ✅ **Multi-file and folder support**  
  Select one or more files/folders in Project View. It recursively handles all files inside folders.

- ✅ **Project-relative file path headers**  
  Each file is prefixed with a header showing its path from the project root (e.g. `### src/components/Button.tsx`).

- ✅ **Markdown code blocks with language hints**  
  File contents are wrapped in triple backticks (` ``` `) with appropriate syntax hints based on file extension (e.g. `typescript`, `kotlin`, `html`...).

- ✅ **Duplicate prevention**  
  If you select both a folder and its files, each file is copied only once.

- ✅ **Hidden file skip**  
  Ignores files like `.DS_Store`, `.gitignore`, and others that shouldn't be copied.

- ✅ **Immediate feedback**  
  A small notification appears in the bottom-right corner showing how many files were copied.

- ✅ **3 convenient ways to trigger**
    - Toolbar button
    - Project View right-click menu
    - Keyboard shortcut
        - **Mac:** ⌘ + ⌥ + C
        - **Windows/Linux:** Ctrl + Alt + C

---

## 🚀 Installation

### Option 1: Install via Plugin Disk

1. Build the plugin ZIP (see [Build](#-build) section).
2. In JetBrains IDE: `Settings → Plugins → ⚙ → Install Plugin from Disk`
3. Select the `.zip` file in `build/distributions/`
4. Restart the IDE.

---

## 🛠 Build

This plugin uses the [Gradle IntelliJ Plugin](https://plugins.jetbrains.com/docs/intellij/welcome.html).

### Build the plugin

```bash
./gradlew build
````

* Output will be at: `build/distributions/files-copier-*.zip`

### Launch sandbox IDE for testing

```bash
./gradlew runIde
```

---

## 🎯 Usage

1. Open the **Project View** in your JetBrains IDE (e.g. IntelliJ, WebStorm, PyCharm).
2. Select any number of files and/or folders.
3. Trigger copy via:

    * 🔘 Toolbar button
    * 🖱 Right-click → `Copy Files Content to Clipboard`
    * ⌨ Shortcut:

        * Mac: `⌘ + ⌥ + C`
        * Windows/Linux: `Ctrl + Alt + C`
4. ✅ Notification will confirm the number of files copied.
5. 🧩 Paste the Markdown snippet anywhere: GitHub, Slack, Notion, ChatGPT, etc.

---

## 📋 Example Output

Here's how your clipboard output looks after copying files:

````markdown
### src/components/Button.tsx
```typescript
import React from 'react';

export function Button() {
  return <button>Click me</button>;
}
````

### src/utils/helper.ts

```typescript
export const add = (a: number, b: number) => a + b;
```


---

## 🔗 Related

- [JetBrains Plugin SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html)
- [Gradle IntelliJ Plugin](https://github.com/JetBrains/gradle-intellij-plugin)

---

## 📄 License

MIT License  
See [LICENSE](./LICENSE) for full terms.
