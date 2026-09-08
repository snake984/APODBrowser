# 🛠 Git Skill: Best Practices & Workflow

## 1. Commit Message Standards (Conventional Commits)
Follow the [Conventional Commits](https://www.conventionalcommits.org/) specification to make your project history readable and automated-tool friendly.

*   **Format**: `<type>(<scope>): <description>`
*   **Types**:
    *   `feat`: A new feature (e.g., `feat(storage): implement MediaStore integration`)
    *   `fix`: A bug fix (e.g., `fix(ui): resolve crash on favorite click`)
    *   `refactor`: A code change that neither fixes a bug nor adds a feature
    *   `test`: Adding missing tests or correcting existing tests
    *   `docs`: Documentation only changes
    *   `chore`: Updating build tasks, package manager configs, etc.

---

## 2. Git Flow Principles
The project uses a simplified branching model to ensure stability in the `main` branch.

### Branching Model
*   **`main`**: The "Production" branch. Always stable.
*   **`develop`**: The "Integration" branch. All features merge here first.
*   **`feature/*`**: Used for developing new features. 
    *   *Source*: `develop`
    *   *Destination*: `develop` via Pull Request.
*   **`hotfix/*`**: Used for critical production bug fixes.
    *   *Source*: `main`
    *   *Destination*: `main` and `develop`.

---

## 3. Best Practices for Developers

### Atomic Commits
Keep commits small and focused. One commit should represent a single logical change. 
*   **Bad**: "Refactored UI and fixed database bug" (One huge commit)
*   **Good**: 
    1. `refactor(ui): extract list item to component`
    2. `fix(storage): update room schema version`

### Clean History with Rebasing
Use `git rebase` to keep your feature branch up-to-date with `develop` without creating unnecessary merge commits.
```bash
git fetch origin
git rebase origin/develop
```

### Protect the Source
*   **Never commit directly to `main`**.
*   **Use `.gitignore`**: Ensure environment-specific files (like `local.properties`) and build artifacts are never tracked.
*   **Verify before committing**: Run `./gradlew lint` and `./gradlew test` to ensure you aren't committing broken code.

---

## 4. Common Workflow Commands

| Action | Command |
| :--- | :--- |
| **Start Feature** | `git checkout -b feature/my-awesome-feature` |
| **Stage Changes** | `git add .` |
| **Commit** | `git commit -m "feat(scope): description"` |
| **Sync with Remote**| `git pull --rebase origin develop` |
| **Push Feature** | `git push origin feature/my-awesome-feature` |
| **Clean Last Commit**| `git commit --amend --no-edit` |
| **Undo Local Commit**| `git reset --soft HEAD~1` |

---
